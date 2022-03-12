pipeline {
    agent {
        kubernetes {
          yaml '''
            apiVersion: v1
            kind: Pod
            spec:
              containers:
              - name: docker
                image: docker
                command:
                - cat
                tty: true
            '''
        }
      }
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
                     withCredentials([string(credentialsId: 'Docker-Hub-Password', variable: 'dockerhubpwd')]) {
                         sh 'docker login -u elbrusgarayev -p ${dockerhubpwd}'
                     }
                     sh 'docker push elbrusgarayev/phonebook-final-app-1.0'
                 }
            }
        }
//         stage('Deploy k8s') {
//             steps {
// //                 sshagent(['k8s']) {
// //                             sh "scp -o StrictHostKeyChecking=no nodejsapp.yaml ubuntu@IPofk8scluster:/home/ubuntu"
//                             script {
//
//                                     sh "kubectl config view"
//
//
//                 }
// //                         }
//             }
//         }
    }
}