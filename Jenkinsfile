pipeline {
    agent any
    stages {
        stage ('Compile') {
            steps {
                withMaven(maven : 'maven_3') {
                    sh 'mvn clean install'
                }
            }
        }
        stage ('Unit Tests') {
            steps {
                withMaven(maven : 'maven_3') {
                    sh 'mvn test'
                }
            }
        }
        stage ('SonarQube analysis') {
            steps {
                withMaven(maven : 'maven_3') {
                    withSonarQubeEnv('SonarQube') {
                        sh 'mvn clean verify sonar:sonar'
                    }
                }
                waitForQualityGate abortPipeline: true
            }
        }
        stage('Build Docker Image') {
            steps {
                 script {
                    sh 'docker build -t elbrusgarayev/phonebook-final-app-1.0:latest .'
                 }
            }
        }
        stage('Push Docker Image') {
            steps {
                 script {
                     withCredentials([string(credentialsId: 'dockerpwd', variable: 'dockerpwd')]) {
                         sh 'docker login -u elbrusgarayev -p ${dockerpwd}'
                     }
                     sh 'docker push elbrusgarayev/phonebook-final-app-1.0:latest'
                 }
            }
        }
        stage('Deploy on K8S') {
            steps {
                script {
                   sh 'kubectl rollout restart deployment/phonebook-backend-deployment -n phonebook-app'
                }
            }
        }
    }
}