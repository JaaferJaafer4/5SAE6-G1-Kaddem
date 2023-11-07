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
       stage('SONARQUBE') {
            steps {
                script {
                    dir('DevOps_Project')
                    {sh 'mvn sonar:sonar -Dsonar.login=squ_f51f945e2a2c43603092d67732b3d09e02e773d6'}
                }
            }
        }
}
}
