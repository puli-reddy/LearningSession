pipeline {
    agent any
    tools {
        maven 'Maven-3.9.11'
        jdk 'JDK 21 '
    }
    environment {
        APP_NAME = "LearningSession"
        BETA_PORT = 8081
        GAMMA_PORT = 8082
        PROD_PORT = 8083
        SERVER_IP = "localhost"
        LOG_DIR = "${WORKSPACE}/logs"
        DOCKER_IMAGE = "thoufiqzeero/LearningSession"
        DOCKER_TAG = "${env.BUILD_NUMBER}"
    }
    stages {
        stage("Verify Docker Access") {
            script {
                try {
                    sh 'docker ps'
                    echo "Docker access verified"
                } catch (Exception e) {
                    error "Docker not accessible. Ensure Jenkins user has Docker permissions (sudo usermod -aG docker jenkins)"
                }
            }
        }
        stage ("Checkout") {
            steps {
                git url: 'https://github.com/puli-reddy/LearningSession.git', branch: 'main'
                sh 'chmod +x mvnw'
            }
        }
        stage ("Create Log Directory") {
            steps {
                sh 'mkdir -p ${LOG_DIR}'
            }
        }
        stage ("Build") {
            steps {
                sh './mvnw clean package'
            }
        }
        stage ("Build Docker Image") {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'docker-hub-creds', passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]) {
                        sh """
                            docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} .
                        """
                    }
                }
            }
        }
        stage('Push to Docker Hub') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'docker-hub-creds', passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]) {
                        sh """
                            echo \$DOCKER_PASSWORD | docker login -u \$DOCKER_USERNAME --password-stdin
                            docker push ${DOCKER_IMAGE}:${DOCKER_TAG}
                        """
                    }
                }
            }
        }
        stage('Deploy to Beta') {
            when {
                expression { currentBuild.result == null || currentBuild.result == 'SUCCESS' }
            }
            steps {
                echo "Deploying to Beta environment on port ${BETA_PORT}"
                script {
                    sh """
                        docker rm -f ${APP_NAME}-beta || true
                    """
                    sh """
                        docker run -d --name ${APP_NAME}-beta -p ${BETA_PORT}:${BETA_PORT} \
                        -e SPRING_PROFILES_ACTIVE=beta \
                        ${DOCKER_IMAGE}:${DOCKER_TAG}
                    """
                    sleep(time: 60, unit: "SECONDS")
                    def maxRetries = 3
                    def retryDelay = 10
                    echo "Beta is running on http://${SERVER_IP}:${BETA_PORT}/"
                    sh "docker ps | grep ${APP_NAME}-beta || exit 1"
                }
            }
        }
        stage('Deploy to Gamma') {
             when {
                expression { currentBuild.result == null || currentBuild.result == 'SUCCESS' }
             }
             steps {
                echo "Deploying to Gamma environment on port ${GAMMA_PORT}"
                script {
                    sh """
                        docker rm -f ${APP_NAME}-gamma || true
                    """
                    sh """
                        docker run -d --name ${APP_NAME}-gamma -p ${GAMMA_PORT}:${GAMMA_PORT} \
                        -e SPRING_PROFILES_ACTIVE=gamma \
                        ${DOCKER_IMAGE}:${DOCKER_TAG}
                    """
                    sleep(time: 60, unit: "SECONDS")
                    def maxRetries = 3
                    def retryDelay = 10
                    echo "Beta is running on http://${SERVER_IP}:${GAMMA_PORT}/"
                    sh "docker ps | grep ${APP_NAME}-gamma || exit 1"
                }
             }
        }
        stage('Deploy to Prod') {
            when {
                expression { currentBuild.result == null || currentBuild.result == 'SUCCESS' }
            }
            steps {
                echo "Deploying to Prod environment on port ${PROD_PORT}"
                script {
                    sh """
                        docker rm -f ${APP_NAME}-prod || true
                    """
                    sh """
                        docker run -d --name ${APP_NAME}-prod -p ${PROD_PORT}:${PROD_PORT} \
                        -e SPRING_PROFILES_ACTIVE=prod \
                        ${DOCKER_IMAGE}:${DOCKER_TAG}
                    """
                    sleep(time: 60, unit: "SECONDS")
                    def maxRetries = 3
                    def retryDelay = 10
                    echo "Beta is running on http://${SERVER_IP}:${PROD_PORT}/"
                    sh "docker ps | grep ${APP_NAME}-prod || exit 1"
                }
            }
        }
    }
}