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
                stage('Build & Stash') {
                    steps {
                        sh 'mvn -B -DskipTests clean package'

                        // Must use stash now because agent none.
                        stash includes: 'webapp/target/webapp-1.0-SNAPSHOT-exec.jar', name: 'fatJar'
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
                   // when {
                   //     branch 'master'
                   //  }
                    steps{
                        script{
                            node{
                                stage("Build and Smoke Test Docker Container"){
                                    // Build fails when unstash is not inside a node.

                                    /*
                                    [Pipeline] unstash
                                    Fetching changes from the remote Git repository
                                        > git config remote.origin.url git@src.thetestpeople.com:development-academy/java-parsons # timeout=10
                                    Fetching without tags
                                    Fetching upstream changes from git@src.thetestpeople.com:development-academy/java-parsons
                                        > git --version # timeout=10
                                    Required context class hudson.FilePath is missing
                                    using GIT_SSH to set credentials java-parsons@ci.ten10.com
                                        > git fetch --no-tags --progress git@src.thetestpeople.com:development-academy/java-parsons +refs/heads/*:refs/remotes/origin/*

                                    Perhaps you forgot to surround the code with a step that provides this, such as: node,dockerNode
                                    [Pipeline] error
                                    */
                                    
                                    unstash 'fatJar'
                                    // Build an image
                                    def customImage = docker.build("java-parsons:${env.BUILD_ID}")
                                    // Validate that running the image starts a TCP listener on 8080
                                    customImage.withRun { c ->
                                        docker.image('alpine').inside("--link ${c.id}:app") {
                                            sh 'while ! nc -vz app 8080; do sleep 2; done'
                                        }
                                    }
                                    sshagent (credentials: ['dockeruser']) {
                                    sh "docker save ${customImage.id} | ssh -o StrictHostKeyChecking=no dockeruser@169.254.83.5 docker load"
                                           sh "ssh dockeruser@169.254.83.5 docker run -d -p 8080:8080 --name java-parsons java-parsons:${env.BUILD_ID}"
                                    }
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
    }
}
