def call(String serverName, String projectKey, String projectName) {
    def scannerHome = tool 'SonarScanner'

    dir('wanderlust-devops-automation') {
        withSonarQubeEnv(serverName) {
            sh """
                "${scannerHome}/bin/sonar-scanner" \
                    -Dsonar.projectKey="${projectKey}" \
                    -Dsonar.projectName="${projectName}" \
                    -Dsonar.sources=.
            """
        }
    }
}
