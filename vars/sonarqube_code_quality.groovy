def call(){
	timeout(time:1, units:"minutes"){
		waitForQualityGate abortPipeline: false
	}
}
