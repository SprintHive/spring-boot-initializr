#!/usr/bin/groovy
@Library('github.com/SprintHive/sprinthive-pipeline-library')

def componentName = 'spring-boot-initializr'
def versionTag = ''
def dockerImage
def resourcesDir = 'config/kubernetes'

mavenNode(label: 'maven-and-docker') {
    stage('Build and push docker image') {
        checkout scm
        versionTag = getNewVersion{}
        dockerImage = "quay.io/organization/sprinthive/spring-boot-initializr:${versionTag}"

		container('docker') {
			sh "docker build -t ${dockerImage} ."
			def img = docker.image(dockerImage)
			docker.withRegistry("https://quay.io", "quay") {
				img.push()
			}
		}
	}

    stage('Rollout to Development') {
        def namespace = 'default'
        def deployStage = 'development'

        def kubeResources = kubeResourcesFromTemplates{
            templates = [
                readFile(resourcesDir + '/deployment.yaml'),
                readFile(resourcesDir + '/service.yaml'),
                readFile(resourcesDir + '/ingress.yaml')
            ]
            stage = deployStage
          version = versionTag
          image = dockerImage
          name = componentName
        }

        for (String kubeResource : kubeResources) {
            kubernetesApply(file: kubeResource, environment: namespace)
        }
    }
}
