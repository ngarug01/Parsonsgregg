pipeline {
    agent {
        docker {
            image 'maven:3-jdk-10'
            args '-v /var/lib/jenkins/.m2:/nonexistent/.m2'
        }
    }
    stages {
        stage('Check') {
            stages {
                stage('Build') {
                    steps {
                        sh 'mvn -B -DskipTests clean package'
                    }
                }
                stage('Test') {
                    steps {
                        sh 'mvn -B test'
                    }
                    post {
                        always {
                            junit '*/target/surefire-reports/*.xml'
                        }
                    }
                }
            }
        }
        stage('Parallel Steps') {
            parallel {
                stage('Coverage') {
                    steps {
                        sh 'mvn -B test -Djacoco.skip=false'
                    }
                    post {
                        always {
                            jacoco changeBuildStatus: true, execPattern: '*/target/coverage-reports/jacoco-ut.exec', maximumBranchCoverage: '90', maximumLineCoverage: '90', maximumMethodCoverage: '90'
                        }
                    }
                }
                stage('Docs') {
                    steps {
                        sh 'mvn -B javadoc:aggregate'
                        publishHTML([allowMissing: false, alwaysLinkToLastBuild: true, keepAll: false, reportDir: 'target/site/apidocs/', reportFiles: 'overview-summary.html', reportName: 'Javadoc', reportTitles: ''])
                    }
                }
                stage('Build and Deploy') {
                    stages {
                        stage('Build Container') {
                            echo "Building container"
                        }
                        stage('Deploy Container') {
                            echo "Deploying..."
                        }
                        stage('Restart Container') {
                            echo "Restarting..."
                        }
                        stage('Acceptance tests') {
                            echo "Running Acceptance Tests"
                        }
                    }
                }
            }
        }
    }
}
