def call() {
    timeout(time: 100, unit: 'MINUTES') {
        waitForQualityGate(abortPipeline: true)
    }
}
