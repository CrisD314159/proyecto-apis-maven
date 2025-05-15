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
                git url: 'https://github.com/CrisD314159/proyecto-apis-maven', branch:'main'

            }
        }
        stage('Análisis de Calidad con SonarQube') {
            steps {
                 sh 'chmod +x mvnw'
                 sh './mvnw sonar:sonar -Dsonar.projectKey=quarkus-app -Dsonar.host.url=http://sonarqube:9000 -Dsonar.login=$SONAR_TOKEN'
                 }
            }

        stage('Compilar y probar') {
            steps {
                sh 'chmod a+x mvnw'
                sh './mvnw clean verify'
            }
        }

        stage('Ejecutar Quarkus en Segundo Plano y Esperar') {
            steps {
                script {
                    sh 'nohup ./mvnw quarkus:dev &'
                    sh 'sleep 40'
                }
            }
        }

        stage('Ejecutar Pruebas Automatizadas') {
             steps {
                sh 'mvn test'
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