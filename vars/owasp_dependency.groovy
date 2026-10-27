def call(){
	dependencyCheck additionalArguments: '--scan ./ --nvdDatafeed https://github.io --cisaDatafeed https://cisa.gov', odcInstallation: 'OWASP'
	dependencyCheckPublisher pattern: '**/dependency-check-report.xml'
}
