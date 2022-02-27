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
                withSonarQubeEnv('SonarQube server'){
                    sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.2:sonar'
                }
            }
        }
    }
}