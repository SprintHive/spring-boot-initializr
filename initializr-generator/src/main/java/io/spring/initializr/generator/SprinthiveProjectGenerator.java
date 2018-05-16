package io.spring.initializr.generator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SprinthiveProjectGenerator extends ProjectGenerator {
    @Value("${TMPDIR:/tmp}/initializr")
    private String tempDir;

    // The base class will clean up these files
    private transient Map<String, List<File>> temporaryFiles = new LinkedHashMap<>();

    public SprinthiveProjectGenerator() {
        this.setTemporaryFiles(temporaryFiles);
    }

    @Override
    protected File generateProjectStructure(ProjectRequest request,
                                            Map<String, Object> model) {
        addModelParameters(model);
        String basePackagePath = request.getPackageName().replace(".", "/");

        File rootDir = createTemporaryDirectory();
        File projectDir = initializerProjectDir(rootDir, request);
        String applicationName = request.getApplicationName();

        writeStarterFiles(projectDir, model, "starter-base");
        createSrcFiles(projectDir, basePackagePath, applicationName, model);
        createTestFiles(projectDir, basePackagePath, applicationName, request, model);
        createResourceFiles(projectDir);

        return rootDir;
    }

    private void addModelParameters(Map<String, Object> model) {
        model.put("localServicePort", new Random().nextInt(919) + 8081);
        model.put("localManagementPort", new Random().nextInt(919) + 9081);
        model.put("prettyName", getPrettyName(model.get("name").toString()));
        String javaName = model.get("applicationName").toString().replace("Application", "");
        model.put("javaName", javaName);
        String javaNameCamel = javaName.substring(0, 1).toLowerCase() + javaName.substring(1);
        model.put("javaNameCamel", javaNameCamel);
    }

    private String getPrettyName(String name) {
        return String.join(" ", Arrays.stream(name.split("-")).map(StringUtils::capitalize).toArray(String[]::new));
    }

    private void createSrcFiles(File projectDir, String basePackagePath, String applicationName, Map<String, Object> model) {
        File targetSrcDir = new File(new File(projectDir, "src/main/java"), basePackagePath);
        writeStarterFiles(targetSrcDir, model, "starter-src-package");
    }

    private void createTestFiles(File projectDir, String basePackagePath, String applicationName, ProjectRequest request, Map<String, Object> model) {
        File targetTestDir = new File(new File(projectDir, "src/test/java"), basePackagePath);
        writeStarterFiles(targetTestDir, model, "starter-test-package");
    }

    private void createResourceFiles(File projectDir) {
        File resources = new File(projectDir, "src/main/resources");
        resources.mkdirs();
    }

    private File createTemporaryDirectory() {
        File rootDir;
        try {
            File tmpPath = new File(tempDir, "initializr");
            tmpPath.mkdirs();
            rootDir = File.createTempFile("tmp", "", tmpPath);
        }
        catch (IOException e) {
            throw new IllegalStateException("Cannot create temp dir", e);
        }
        addTempFile(rootDir.getName(), rootDir);
        rootDir.delete();
        rootDir.mkdirs();

        return rootDir;
    }

    private File initializerProjectDir(File rootDir, ProjectRequest request) {
        if (request.getBaseDir() != null) {
            File dir = new File(rootDir, request.getBaseDir());
            dir.mkdirs();
            return dir;
        }
        else {
            return rootDir;
        }
    }

    private void addTempFile(String group, File file) {
        temporaryFiles.computeIfAbsent(group, key -> new ArrayList<>()).add(file);
    }

    private void writeBinary(File target, byte[] body) {
        try (OutputStream stream = new FileOutputStream(target)) {
            StreamUtils.copy(body, stream);
        }
        catch (Exception e) {
            throw new IllegalStateException("Cannot write file " + target, e);
        }
    }

    private void writeStarterFiles(File targetDir, Map<String, Object> model, String templateDir) {
        Resource[] starterResources = getDirResources(templateDir);
        String templateDirPath = "templates/" + templateDir;

        for (Resource starterResource : starterResources) {
            String resourcePath = getResourcePath(starterResource);
            if (!resourcePath.endsWith("/")) {

                String relativePath = resourcePath.substring(resourcePath.indexOf(templateDirPath) +
                        templateDirPath.length());
                relativePath = relativePath.replace("%7b", "{");
                relativePath = relativePath.replace("%7d", "}");
                String targetFilePath = templateRenderer.processString(relativePath, model);
                File targetFile = new File(targetDir, targetFilePath.replace(".bin", ""));
                ensurePathExists(targetFile);
                if (!relativePath.endsWith(".bin")) {
                    write(targetFile, templateDir + relativePath, model);
                } else {
                    try {
                        writeBinary(targetFile, StreamUtils.copyToByteArray(starterResource.getInputStream()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    private void ensurePathExists(File file) {
        String filePath = file.getAbsolutePath();
        String parentDir = filePath.substring(0, filePath.lastIndexOf("/"));
        new File(parentDir).mkdirs();
    }

    private String getResourcePath(Resource resource) {
        try {
            return resource.getURL().getPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Resource[] getDirResources(String directory) {
        String prefix = "classpath:/templates/" + directory + "/**";
        try {
            return new PathMatchingResourcePatternResolver().getResources(prefix);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
