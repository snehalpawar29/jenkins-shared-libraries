def call(){
    dependencyCheck additionalArguments: '--scan ./ --nvdDatafeed https://github.io', odcInstallation: 'OWASP'
    dependencyCheckPublisher pattern: '**/dependency-check-report.xml'
}
