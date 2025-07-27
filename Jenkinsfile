pipeline {
    agent any

    environment {
        MAVEN_HOME = tool 'Maven 3.9.11' // or your installed Maven version
        JAVA_HOME = tool 'jdk-21'       // your installed JDK version
        PATH = "${JAVA_HOME}/bin:${MAVEN_HOME}/bin:${env.PATH}"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/NadunBothota/AWS-Cloud-Management-CLI-Application.git'

            }
        }

        stage('Build with Maven') {
            steps {
                bat 'mvn clean install'
            }
        }

        stage('Archive Artifact') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        // Optional: Add more stages for test, deploy, notify
    }

    post {
        success {
            echo "Build succeeded!"
        }
        failure {
            echo "Build failed!"
        }
    }
}
