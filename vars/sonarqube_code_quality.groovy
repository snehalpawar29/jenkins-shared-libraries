def call() {
    timeout(time: 20, unit: 'MINUTES') {
        waitForQualityGate(abortPipeline: true)
    }
}
