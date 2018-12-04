pipeline {
    agent { docker { image 'maven:3-jdk-10' } }
    stages {
        stage('Build') {
            steps {
                sh "mvn package"
            }
        }
    }
}
