pipeline {
    agent {
        docker {
            image 'maven:3-jdk-10'
            args '-v /root/.m2:/var/lib/jenkins/.m2'
        }
     }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
    }
}
