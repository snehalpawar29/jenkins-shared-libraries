#!/usr/bin/env groovy

def call(Map config = [:]){
    // Resolve configurations and provide safe defaults
    def imageTag = config.imageTag ?: error("Image Tag Required")
    def manifestsPath = config.manifestsPath ?: 'kubernetes' // Note: corrected parameter name spelling to match your Jenkinsfile
    def gitCredentials = config.gitCredentials ?: 'github-credentials'
    def gitUserEmail = config.gitUserEmail ?: 'snehalpawar2945@gmail.com'
    def gitUserName = config.gitUserName ?: 'snehalpawar29'
    
    echo "Updating Kubernetes manifest with image tag : ${imageTag}"
    
    withCredentials([usernamePassword(
        credentialsId: gitCredentials,
        usernameVariable: 'GIT_USERNAME',
        passwordVariable: 'GIT_PASSWORD'
    )]) {

        // Use single quotes (''') to pass variables directly to the shell environment securely
        // We inject the non-sensitive parameters into the shell environment first
        withEnv([
            "TARGET_TAG=${imageTag}",
            "MANIFEST_PATH=${manifestsPath}",
            "GIT_CONFIG_NAME=${gitUserName}",
            "GIT_CONFIG_EMAIL=${gitUserEmail}"
        ]) {
            sh '''
                # 1. Verify we are in a valid Git repo; if not, configure locally anyway or track down workspace
                if [ ! -d ".git" ]; then
                    echo "WARNING: .git directory not found in current folder. Attempting step recovery..."
                fi

                # 2. Configure Git parameters using shell evaluation (No Groovy interpolation leaks)
                git config user.name "$GIT_CONFIG_NAME"
                git config user.email "$GIT_CONFIG_EMAIL"

                # 3. Update the deployment manifest files
                sed -i "s|image: snehalpawar2945/easyshop:.*|image: snehalpawar2945/easyshop:${TARGET_TAG}|g" ${MANIFEST_PATH}/easyshop-deployment.yml

                if [ -f "${MANIFEST_PATH}/migration-job.yml" ]; then
                    sed -i "s|image: snehalpawar2945/easyshop-migration:.*|image: snehalpawar2945/easyshop-migration:${TARGET_TAG}|g" ${MANIFEST_PATH}/migration-job.yml
                fi

                # 4. Check for changes, commit, and push changes securely
                if git diff --quiet; then
                    echo "No change to commit"
                fi
                
                git add ${MANIFEST_PATH}/*.yml
                git commit -m "Update images tags to ${TARGET_TAG} and ensure correct domain [ci skip]"
                
                # Use git credentials bound by withCredentials securely via shell injection
                git remote set-url origin "https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/snehalpawar29/EasyShop-E-Commerce-App.git"
                git push origin HEAD:main
            '''
        }
    }
}
