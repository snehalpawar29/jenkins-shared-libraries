#!/usr/bin/env groovy

def call(Map config = [:]){
	def imageTag = config.imageTag ?: error("Image Tag Required")
	def manifestsPath = config.manifestPath ?: 'kubernetes'
	def gitCredentials = config.gitCredentials ?: 'github-credentials'
	def gitUserEmail = config.gitUserEmail ?: 'sbpawar2904@gmail.com'
	def gitUserName = config.gitUserName ?: 'Jenkins CI'
	echo "Updating Kubenetes manifest with image tag : ${imageTag}"
	
	withCredentials([usernamePassword(
		credentialsId: gitCredentials,
		usernameVariable: 'GIT_USERNAME',
		passwordVariable: 'GIT_PASSWORD'
	)]) {

	sh """ 
		git config user.name "${gitUserName}"
		git config user.email "${gitUserEmail}"
	"""
	sh """ 
		sed -i "s|image: snehalpawar2945/easyshop:.*|image: snehalpawar2945/easyshop:${imageTag}|g" ${manifestsPath}/easyshop-deployment.yml

		if [ -f "${manifestsPath}/migration-job.yml" ]; then
			sed -i "s|image: snehalpawar2945/easyshop-migration.*|image: snehalpawar2945/easyshop-migration:${imageTag}|g" ${manifestsPath}/migration-job.yml
		fi

		if git diff --quiet; then
			echo "No change to commit"
		else
			git add ${manifestsPath}/*.yml
			git commit -m "Update images tags to ${imageTag} and ensure correct domain [ci skip]"
			
			git remote set-url origin https://\${GIT_USERNAME}:\${GIT_PASSWORD}@github.com/snehalpawar29/EasyShop-E-Commerce-App.git
			git push origin HEAD:\${GIT_BRANCH}
		fi
	
	"""
	}
}
