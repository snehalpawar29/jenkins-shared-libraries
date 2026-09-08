#!/usr/bin/env groovy

def call(Map config = [:]){
    def imageTag = config.imageTag ?: error("Image Tag Required")
    def manifestsPath = config.manifestsPath ?: 'kubernetes' 
    def gitCredentials = config.gitCredentials ?: 'github-credentials'
    def gitUserEmail = config.gitUserEmail ?: 'snehalpawar2945@gmail.com'
    def gitUserName = config.gitUserName ?: 'snehalpawar29'
    
    echo "Updating Kubernetes manifest with image tag : ${imageTag}"
    
    withCredentials([usernamePassword(
        credentialsId: gitCredentials,
        usernameVariable: 'GIT_USERNAME',
        passwordVariable: 'GIT_PASSWORD'
    )]) {

        withEnv([
            "TARGET_TAG=${imageTag}",
            "MANIFEST_PATH=${manifestsPath}",
            "GIT_CONFIG_NAME=${gitUserName}",
            "GIT_CONFIG_EMAIL=${gitUserEmail}"
        ]) {
            sh '''
                # 1. Look for the sub-folder if the root directory is empty
                if [ ! -d ".git" ]; then
                    if [ -d "EasyShop/.git" ]; then
                        echo "Found repository inside 'EasyShop' directory. Shifting execution context..."
                        cd EasyShop
                    else
                        echo "ERROR: Could not find a valid .git directory anywhere in the workspace workspace."
                        exit 1
                    fi
                fi

                # 2. Configure Git parameters securely via shell evaluation
                git config user.name "$GIT_CONFIG_NAME"
                git config user.email "$GIT_CONFIG_EMAIL"

                # 3. Update the deployment manifest files
                sed -i "s|image: snehalpawar2945/easyshop:.*|image: snehalpawar2945/easyshop:${TARGET_TAG}|g" ${MANIFEST_PATH}/easyshop-deployment.yml

                if [ -f "${MANIFEST_PATH}/migration-job.yml" ]; then
                    sed -i "s|image: snehalpawar2945/easyshop-migration:.*|image: snehalpawar2945/easyshop-migration:${TARGET_TAG}|g" ${MANIFEST_PATH}/migration-job.yml
                fi

                # 4. Check for changes, commit, and push modifications back to the master repository
                if git diff --quiet; then
                    echo "No change to commit"
                    exit 0
                fi
                
                git add ${MANIFEST_PATH}/*.yml
                git commit -m "Update images tags to ${TARGET_TAG} and ensure correct domain [ci skip]"
                
                git remote set-url origin https://\${GIT_USERNAME}:\${GIT_PASSWORD}@github.com/snehalpawar29/EasyShop.git
                git push origin HEAD:main
            '''
        }
    }
}
