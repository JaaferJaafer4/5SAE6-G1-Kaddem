pipeline {
    agent any
    tools{
        maven 'M2_HOME'
    }
    environment {
        NEXUS_VERSION = "nexus3"
        NEXUS_PROTOCOL = "http"
        NEXUS_URL = "192.168.56.20:8081"
        NEXUS_REPOSITORY = "maven-central-repository"
        NEXUS_CREDENTIAL_ID = "nexus_cred"
}
 stages{
    stage('Pre-Build Cleanup') {
     steps {
         deleteDir()
         }
       }

        stage('Git'){
            steps{
                echo "Getting project from git....."
                git branch: 'JaafarJaafar-5SAE6-G1', credentialsId: 'githubToken', url: 'https://github.com/JaaferJaafer4/5SAE6-G1-Kaddem.git'           }
        }
           stage('Maven'){
            steps{
                sh 'mvn clean compile'

            }
        }
        
        stage('SonarQube') {
            steps {
                sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=sonarqube'
            }
        }
        stage('JUnit/Mockito') {
            steps {
                sh 'mvn test'
                junit '**/target/surefire-reports/TEST-*.xml'
            }
        }
              stage('Collect JaCoCo Coverage') {
                    steps{
                                  def excludes = '
                                               **/entities/**,
                                                          **/repositories/**,
                                                          **/config/**,
                                                          **/services/UniversiteServiceImpl.class,
                                                          **/services/EtudiantServiceImpl.class,
                                                          **/services/EquipeServiceImpl.class,
                                                          **/services/DepartementServiceImpl.class,
                                                          **/controllers/DepartementRestController.class,
                                                          **/controllers/EquipeRestController.class,
                                                          **/controllers/EtudiantRestController.class,
                                                          **/controllers/UniversiteRestController.class,
                                                          tn/esprit/spring/kaddem/KaddemApplication.class'
                                      jacoco(execPattern: '**/target/jacoco.exec', excludes: excludes)
            }
                }
                 stage('Maven install') {
             steps {
                sh 'mvn install -DskipTests'
            }
         }
                stage("Nexus") {
            steps {
                script {
                    pom = readMavenPom file: "pom.xml";
                    filesByGlob = findFiles(glob: "target/*.${pom.packaging}");
                    echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
                    artifactPath = filesByGlob[0].path;
                    artifactExists = fileExists artifactPath;
                    if(artifactExists) {
                        echo "*** File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}";
                         nexusArtifactUploader(
                            nexusVersion: NEXUS_VERSION,
                            protocol: NEXUS_PROTOCOL,
                             nexusUrl: NEXUS_URL,
                             groupId: pom.groupId,
                             version: pom.version,
                             repository: NEXUS_REPOSITORY,
                             credentialsId: NEXUS_CREDENTIAL_ID,
                             artifacts: [
                                [artifactId: pom.artifactId,
                                 classifier: '',
                                 file: artifactPath,
                                 type: pom.packaging],
                                 [artifactId: pom.artifactId,
                                 classifier: '',
                                 file: "pom.xml",
                                 type: "pom"]
                             ]
                         );
                     } else {
                         error "*** File: ${artifactPath}, could not be found";
                    }
                 }
             }
        }

  stage('build images') {
            steps {
                script {
                    sh 'docker build -t jaafarjaafar/devops:backend .'
                     sh 'docker pull mysql:5.7'
                }
            }
         }
        //      stage('push images to hub') {
        //     steps {
        //         script {
        //             withCredentials([string(credentialsId: 'docker_pwd', variable: 'dockerpwd')]) {
        //                 sh 'docker login -u jaafarjaafar -p ${dockerpwd}'
        //                 sh 'docker push jaafarjaafar/devops:backend'
        //             }
        //         }
        //     }
        // }
       stage('Docker Compose') {
            steps {
                script {
                   sh 'docker-compose down'
                   sh 'docker compose up -d'
              }
            }
          }
 }
}