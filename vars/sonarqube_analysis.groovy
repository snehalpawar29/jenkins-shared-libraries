def call(String SonarQubeAPI, String projectName, String projectKey){
	withSonarQubeEnv("${SonarQubeAPI}"){
		sh "$SONAR_HOME/bin/sonar-scanner -Dsonar.projectName=${projectName} -Dsonar.projectKey=${projectKey} -Dsonar.java.binaries=. -X"
	}
}
