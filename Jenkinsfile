pipeline {
    agent any

    environment {
        SONARQUBE_SERVER = 'SonarQube'
        SONAR_TOKEN = credentials('sonarqube-token')
    }

    tools {
        maven 'Maven 3.8.5'
    }

    stages {
        stage('Clonar repositorio') {
            steps {
                git url: 'https://github.com/CrisD314159/proyecto-apis-maven', branch: 'main'
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
                sh './mvnw clean verify'
            }
        }

        stage('Ejecutar Quarkus en segundo plano') {
            steps {
                script {
                    // Ejecuta Quarkus en segundo plano
                    sh 'nohup ./mvnw quarkus:dev -Dquarkus.http.port=8100 &'

                    // Espera activa sobre un endpoint real (ajústalo si usas otro)
                    sh '''
                    echo "Esperando a que Quarkus esté listo en http://localhost:8100/comments/1"
                    for i in {1..30}; do
                        STATUS=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8100/comments/1)
                        if [ "$STATUS" -eq 200 ] || [ "$STATUS" -eq 404 ]; then
                            echo "Quarkus está listo."
                            exit 0
                        fi
                        echo "No disponible aún, reintentando..."
                        sleep 2
                    done

                    echo "Tiempo de espera agotado. Quarkus no respondió a tiempo."
                    exit 1
                    '''
                }
            }
        }

        stage('Ejecutar pruebas automatizadas') {
            steps {
                sh './mvnw test'
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