pipeline {
    agent any
    stages {
        stage ('Compile Stage') {

            steps {
                maven(maven : 'maven_3') {
                    sh 'mvn clean compile'
                }
            }
        }
        stage ('Testing Stage') {

            steps {
                maven(maven : 'maven_3') {
                    sh 'mvn test'
                }
            }
        }
//         stage ('Deployment Stage') {
//             steps {
//                 withMaven(maven : 'maven_3') {
//                     sh 'mvn deploy'
//                 }
//             }
//         }
    }
}