pipeline {
    agent any

    environment {
        SONARQUBE_SERVER = 'SonarQube'
        SONAR_TOKEN = credentials('sonarqube-token')
    }

    tools {
            maven 'Maven 3.8.5' // Asegúrate que este nombre coincida con el configurado en Jenkins
    }


    stages {
        stage('Clonar repositorio') {
            steps {
                git 'https://github.com/CrisD314159/proyecto-apis-maven'
            }
        }

        stage('Compilar y probar') {
            steps {
                sh './mvnw clean verify'
            }
        }

        stage('Análisis SonarQube') {
            steps {
                withSonarQubeEnv("${env.SONARQUBE_SERVER}") {
                    sh './mvn sonar:sonar'
                }
            }
        }

        stage('Publicar resultados de pruebas') {
            steps {
                junit '**/target/surefire-reports/*.xml'
            }
        }

        stage('Esperar calidad de Sonar (opcional)') {
            steps {
                timeout(time: 1, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }
}