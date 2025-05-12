pipeline {
    agent any

    tools {
        maven 'Maven 3.8.5'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh './mvnw clean package -DskipTests=true'
            }
        }

        stage('Run Specific Tests') {
            steps {
                sh './mvnw -Dtest=EmailServiceTest,RegisterServiceTest test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }
    }

    post {
        success {
            echo 'Demo completed successfully!'
        }
        failure {
            echo 'Demo failed - check the test results!'
        }
    }
}
