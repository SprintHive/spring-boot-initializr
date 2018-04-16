package {{packageName}}.domain;

public class {{javaName}} {

    public String greeting(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Missing name parameter");
        }

        return String.format("Hello %s!", name);
    }
}
