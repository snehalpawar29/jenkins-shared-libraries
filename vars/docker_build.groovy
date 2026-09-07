def call(Map config = [:]){
	def imageName = config.imageName ?: error("Image Name Required.")
	def imageTag = config.imageTag ?: error("Imae Tag Required.")
	def dockerfile = config.dockerfile ?: error("Docker File Required")
	def context = config.context ?: '.'
	echo "Building Docker Image: ${imageName}:${imageTag} using ${dockerfile}"

	sh "docker build -t ${imageName}:${imageTag} -f ${dockerfile} ${context}"

}
