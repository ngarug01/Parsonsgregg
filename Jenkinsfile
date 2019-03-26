node{
    docker.image('maven:3-jdk-10').inside('-v /var/lib/jenkins/.m2:/nonexistent/.m2'){
        stage('C-B-T'){
            stage('Check'){
                stage('Build'){
                    echo 'Building...'
                    sh 'mvn -B -DskipTests clean package'
                    echo 'Build Complete...'
                }
                stage('Test'){
                    try{
                        echo 'Running tests...'
                        sh 'mvn -B test'
                        echo 'Tests complete...'
                    }
                    finally{
                        junit '*/target/surefire-reports/*.xml'
                        // Probably archive this.
                    }
                }
                stage('Extract') {
                    archiveArtifacts artifacts: 'webapp/target/webapp-1.0-SNAPSHOT-exec.jar', fingerprint: true, onlyIfSuccessful: true
                    // $JENKINS_HOME/jobs/JOB_NAME/jobs/<Repository Name>/branches/<Branch Name>/builds/$BUILD_NUMBER/archive/ <--- path for archived artifacts.
                }
            }
        }
        stage('Parallel Steps'){
            parallel Coverage: {
                try{
                    sh 'mvn -B test -Djacoco.skip=false'
                }
                finally{
                    jacoco changeBuildStatus: true, execPattern: '*/target/coverage-reports/jacoco-ut.exec', maximumBranchCoverage: '75', maximumMethodCoverage: '85'
                }
            }
            Docs: {
                sh 'mvn -B javadoc:aggregate'
                publishHTML([allowMissing: false, alwaysLinkToLastBuild: true, keepAll: false, reportDir: 'target/site/apidocs/', reportFiles: 'overview-summary.html', reportName: 'Javadoc', reportTitles: ''])
            }
        }       
    }
    docker.image('maven:3-jdk-10'){
        // Fix above line
        stage("Build-Deploy-Restart-AcceptanceTest"){
            stage("Build Container"){
                echo 'Building...'
            }
            stage("Deploy Container"){
                echo 'Deploying'
            }
            stage("Restart Container"){
                echo "Restarting container..."
            }
            stage("Run Acceptance Tests"){
                echo 'Running acceptance tests'
            }
        }
    }
}



