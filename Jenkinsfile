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
                      withSonarQubeEnv('SonarQubeScanner') {
                      sh """/var/lib/jenkins/tools/hudson.plugins.sonar.SonarRunnerInstallation/SonarQube/bin/sonar-scanner \
                     -D sonar.projectVersion=1.0-SNAPSHOT \
                       -D sonar.login=admin \
                      -D sonar.password=admin \
                      -D sonar.projectBaseDir=/var/lib/jenkins/workspace/jenkins-sonar/ \
                        -D sonar.projectKey=my-app1 \
                        -D sonar.sourceEncoding=UTF-8 \
                        -D sonar.language=java \
                        -D sonar.sources=my-app/src/main \
                        -D sonar.tests=my-app/src/test \
                        -D sonar.host.url=http://localhost:9095/"""
                        }
            }
        }
    }
}