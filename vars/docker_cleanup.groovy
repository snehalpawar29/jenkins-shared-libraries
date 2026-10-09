def call(String projectName, String imageTag, String DOCKER_USERNAME){
	sh "docker rmi ${DOCKER_USERNAME}/${projectName}:${imageTag}"
}
