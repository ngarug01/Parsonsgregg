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
                stage('Quick Check') {
                    steps {
                        sh 'mvn -B clean test-compile'
                    }
                }
                stage('Build and Test') {
                    steps {
                        sh 'mvn -B verify'
                    }
                    post {
                        always {
                            junit '**/target/surefire-reports/*.xml'
                            junit '**/target/failsafe-reports/*.xml'
                        }
                    }
                }
            }
        }
        stage('Parallel Steps') {
            parallel {
                stage('Unit-Test Coverage') {
                    steps {
                        sh 'mvn -B test -Djacoco.skip=false'
                    }
                    post {
                        always {
                            jacoco changeBuildStatus: true, execPattern: '*/target/coverage-reports/jacoco-ut.exec', maximumBranchCoverage: '75', maximumMethodCoverage: '85'
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
                    when {
                        branch 'master'
                    }
                    stages {
                        stage('Build Container') {
                            steps {
                                echo "Building container"

                            }
                        }
                        stage('Deploy Container') {
                            steps {
                                echo "Deploying..."
                            }
                        }
                        stage('Restart Container') {
                            steps {
                                echo "Restarting..."
                            }
                        }
                        stage('Acceptance tests') {
                            steps {
                                echo "Running Acceptance Tests"
                            }
                        }
                    }
                }
            }
        }
    }
}
