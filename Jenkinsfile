node {
    def mvnHome
    stage('Pull Code From Git Repo') { // for display purposes
        // Get some code from a GitHub repository
        echo '==================--------------about to log into github'
        git credentialsId: 'git_credentials', url: 'https://github.com/speedo234/localix'
        echo '==================--------------logged into guthub'
        // Get the Maven tool.
        // ** NOTE: This 'M3' Maven tool must be configured
        // **       in the global configuration.
        mvnHome = tool 'maven-3'
    }
    stage('Build Localix jar') {
        // Run the maven build
        withEnv(["MVN_HOME=$mvnHome"]) {
            if (isUnix()) {
                echo '==================--------------about to maven clean package source code'
                sh '"$MVN_HOME/bin/mvn" -Dmaven.test.failure.ignore clean package'
                echo '==================--------------done cleaning and packaging source code, although commented out at this point...'
            } else {
              //  bat(/"%MVN_HOME%\bin\mvn" -Dmaven.test.failure.ignore clean package/)
            }
        }
    }
    stage('Build Localix Image') {
        echo '==================--------------build docker image of project source code'
        sh 'docker image build -t speedy101/localix:v3 .'
        echo '==================--------------done building project source code image'
    }
    stage('Push Localix Image to Dockerhub') {
        //   sh 'docker login -u "speedy101" -p "T********" docker.io'
        // sh 'docker push speedy101/localix'
        echo '==================--------------about to log into dockerhub'
        withCredentials([usernamePassword(credentialsId: 'dockerhub_credentials', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')]) {
          sh "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPassword}"
          sh 'docker push speedy101/localix:v3'
          echo '==================--------------done logging into dockerhub and pushing image to dockerhub repo'
        }
    }
     stage('Starting Up Localix Application') {
        echo '==================--------------about starting up localix application'
        sh "docker-compose up --build -d"
        echo '==================--------------done starting up localix application using docker-compose command'
    }
    stage('Results') {
        junit '**/target/surefire-reports/TEST-*.xml'
        archiveArtifacts 'target/*.jar'
    }
}
