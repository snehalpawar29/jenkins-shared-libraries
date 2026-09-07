def call(Map config = [:]){
	echo "Cloning Repository..."
	def branch = config.branch ?: error("Branch Name is Required")
	def url = config.url ?: error("URL is required.")
	sh "git clone -b '${branch}' '${url}'"
}
