def call(Map config = [:]){
	def imageName = config.imageName ?: error("Image Name Required")
	def imageTag = config.imageTag ?: error("Image Tag Required")
	def credentials = config.credentials ?: 'docker-hub-credentials'
	echo ("Pushing Docker Image: ${imageName}:${imageTag}")
	withCredentials([usernamePassword(
		credentialsId: credentials,
		usernameVariable: 'DOCKER_USERNAME',
		passwordVariable: 'DOCKER_PASSWORD',
)]) {
	sh """ 
		echo "\$DOCKER_PASSWORD" | docker login -u "\$DOCKER_USERNAME" --password-stdin
		docker push ${imageName}:${imageTag} 
	"""
	}
}
