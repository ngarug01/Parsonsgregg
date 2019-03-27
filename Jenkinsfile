pipeline {
    agent none
    stages {
        stage('Check') {
            agent   {
                docker {
                    image 'maven:3-jdk-10'
                    args '-v /var/lib/jenkins/.m2:/nonexistent/.m2'
                }
            }
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
                stages{
                    stage('Coverage') {
                        agent {
                            docker {
                                image 'maven:3-jdk-10'
                                args '-v /var/lib/jenkins/.m2:/nonexistent/.m2'
                            }
                        }
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
                        agent{ 
                            docker {
                                image 'maven:3-jdk-10'
                                args '-v /var/lib/jenkins/.m2:/nonexistent/.m2'
                            }
                        }
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
}
