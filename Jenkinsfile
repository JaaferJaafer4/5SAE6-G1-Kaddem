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
                      sh 'mvn sonar:sonar -Dsonar.login=squ_f51f945e2a2c43603092d67732b3d09e02e773d6'
                }
            }
        }
        stage('Upload to Nexus') {
            steps {
                 script {
                     nexusArtifactUploader( 
                         nexusVersion: 'nexus3',
                         protocol: 'http',
                         nexusUrl: '192.168.56.36:8081',
                         groupId: 'tn.esprit.spring',
                         version: "1.0", 
                         repository: 'maven-releases',
                        credentialsId: 'Nexus', 
                        artifacts: [ 
                            [ 
                                artifactId: 'kaddem',
                                classifier: '',
                                file: "target/kaddem-1.0.jar", 
                                type: 'jar' 
                            ]
                        ]
                    ) 
                }
            }
         }
}
}
