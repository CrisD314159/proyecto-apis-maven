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
                withEnv(['TESTCONTAINERS_RYUK_DISABLED=true']) {
                    sh './mvnw -Dtest=EmailServiceTest,UserServiceTests test'
                }
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