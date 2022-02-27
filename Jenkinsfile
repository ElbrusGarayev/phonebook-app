pipeline {
    agent any
    stages {
        stage ('Compile Stage') {
            steps {
                withMaven(maven : 'maven_3') {
                    sh 'mvn clean compile'
                }
            }
        }
        stage ('Testing Stage') {
            steps {
                withMaven(maven : 'maven_3') {
                    sh 'mvn test'
                }
            }
        }
        stage ('SonarQube analysis') {
            steps {
//                 withSonarQubeEnv('SonarQube server'){
//                     sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.2:sonar'
//                 }
                    withSonarQubeEnv(credentialsId: 'f225455e-ea59-40fa-8af7-08176e86507a', installationName: 'My SonarQube Server') { // You can override the credential to be used
                                  sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.7.0.1746:sonar'
                                }
            }
        }
    }
}