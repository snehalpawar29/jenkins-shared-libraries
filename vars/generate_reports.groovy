def call(Map config=[:]){
	def projectName = config.projectName ?: 'Project'
	def imageName = config.imageName ?: ''
	def imageTag = config.imageTag ?: ''
	echo ("Generating build Reports...")
	sh "mkdir -p reports"
	sh """
		echo "------${projectName} build reports------" > reports/build-report.txt
		echo "Generated: \$(date)" >> reports/build-report.txt
		echo "Build Number: ${env.BUILD_NUMBER}" >> reports/build-report.txt
		echo "Docker Images: ${imageName}" >> reports/build-report.txt
		echo "Image Tag: ${imageTag}" >> reports/build-report.txt
		echo "Build Status: ${currentBuild.result ?: 'SUCCESS'}" >> reports/build-report.txt
		echo "Build URL: ${env.BUILD_URL}" >> reports/build-report.txt
	"""
	archiveArtifacts artifacts: 'reports/*', allowEmptyArchive: true
}
