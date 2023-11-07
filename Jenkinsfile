pipeline {
    agent any
    stages {
        
        stage('clean Maven') {
            steps{
                script{
                    sh "mvn clean" 
                }
            }
        }
        
         stage('Compile') {
            steps {
                script {
                    sh 'mvn compile'
                }
            }
        }

        stage('Test'){
             steps {
                script {
                   
                    sh 'mvn test'
                  }
            }
        }
}
}
