pipeline {
    agent any
    tools {
        maven 'Maven-3.9.11'
        jdk 'JDK 21 '
    }
    environment {
        REPO_URL = "https://github.com/puli-reddy/LearningSession.git"
        INTEGRATION_TESTS_REPO = "https://github.com/puli-reddy/LearningSessionIntegrationTests.git"
        BETA_URL="http://10.3.0.10:8081"
        GAMMA_URL="http://10.3.0.10:8082"
        APP_NAME = "PoornaLearningSession"
        DOCKER_IMAGE = "poorna_learning_session"
        BETA_PORT = 8081
        GAMMA_PORT = 8082
        SERVER_IP = "localhost"
        LOG_DIR = "${WORKSPACE}/logs"
        DOCKER_TAG = "${env.BUILD_NUMBER}"
    }
    stages {
        stage ("Checkout") {
            steps {
                script {
                    dir('source') {
                        git url: "${REPO_URL}", branch: 'main'
                    }
                    dir('tests') {
                        git url: "${INTEGRATION_TESTS_REPO}", branch: 'main'
                    }
                }
                sh 'chmod +x source/mvnw'
                sh 'chmod +x tests/mvnw'
            }
        }
        stage ("Create Log Directory") {
            steps {
                sh 'mkdir -p ${LOG_DIR}'
            }
        }
        stage ("Build") {
            steps {
                dir('source') {
                    sh './mvnw clean package'
                }
            }
        }
        stage ("Build Docker Image") {
            steps {
                dir('source') {
                    script {
                        sh """
                            docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} .
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
                    sleep(time: 30, unit: "SECONDS")
                    def maxRetries = 3
                    def retryDelay = 10
                    echo "Beta is running on http://${SERVER_IP}:${BETA_PORT}/"
                    sh "docker ps | grep ${APP_NAME}-beta || exit 1"
                }
            }
        }
        stage('Beta Integration Tests') {
            when {
                expression { currentBuild.result == null || currentBuild.result == 'SUCCESS' }
            }
            steps {
                dir('tests') {
                    sh '''
                        ./mvnw test -DserviceUrl=${BETA_URL}
                        if [ -d "target/failsafe-reports" ]; then
                        grep -l "FAILURE" target/failsafe-reports/*.txt && exit 1 || exit 0
                        fi
                    '''
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
                    sleep(time: 30, unit: "SECONDS")
                    def maxRetries = 3
                    def retryDelay = 10
                    echo "Gamma is running on http://${SERVER_IP}:${GAMMA_PORT}/"
                    sh "docker ps | grep ${APP_NAME}-gamma || exit 1"
                }
             }
        }
        stage('Gamma Integration Tests') {
            when {
                expression { currentBuild.result == null || currentBuild.result == 'SUCCESS' }
            }
            steps {
                dir('tests') {
                    sh '''
                        ./mvnw test -DserviceUrl=${GAMMA_URL}
                        if [ -d "target/failsafe-reports" ]; then
                        grep -l "FAILURE" target/failsafe-reports/*.txt && exit 1 || exit 0
                        fi
                    '''
                }
            }
        }
    }
}