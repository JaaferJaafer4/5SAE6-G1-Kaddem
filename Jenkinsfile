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
                git branch: 'JaafarJaafar-5SAE6-G1', credentialsId: 'github', url: 'https://github.com/JaaferJaafer4/5SAE6-G1-Kaddem.git'           }
        }
           stage('Maven'){
            steps{
                sh 'mvn clean compile'

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
                           jacoco(execPattern: '**/target/jacoco.exec',exclusionPattern : '**/repositories/**,**/entities/**,tn/esprit/spring/kaddem/KaddemApplication.class')
            }
                }
                        stage('Maven install') {
                             steps {
                                sh 'mvn install -DskipTests -U'
                            }
                         }
                    stage('SonarQube') {
                                            steps {
                                              script {

                                              withSonarQubeEnv(installationName : 'sonar-server') {
                                                sh 'mvn sonar:sonar'
                                                }

                                                }
                                            }
                                        }

                stage("Nexus") {
            steps {
                script {
                       sh 'docker start nexus'
             def nexusUrl = 'http://192.168.56.20:8081'
      def nexusReady = false
            def maxAttempts = 10
            def sleepTime = 30  // Adjust the sleep interval as needed

            for (int attempt = 1; attempt <= maxAttempts; attempt++) {
                sh "curl --fail ${nexusUrl}"
                if (currentBuild.resultIsNotWorseThan('SUCCESS')) {
                    nexusReady = true
                    break
                }

                echo "Nexus is not yet available, waiting for ${sleepTime} seconds..."
                sleep sleepTime
            }

            if (nexusReady) {
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
                    sh 'docker stop nexus'
                 }
                 }
                 }

        }

stage('Pull MySQL Image') {
    steps {
        script {
            def mysqlImageList = sh(script: 'docker image ls mysql:5.7', returnStdout: true).trim()
            if (!mysqlImageList.contains('mysql:5.7')) {
                sh 'docker pull mysql:5.7'
            }
        }
    }
}

stage('docker compose down')
{
   steps {
      script {
            sh 'docker-compose down -v'

      }
   }
}

 stage('build images') {
     steps {
         script {
          def tagsExists = sh(script: 'docker image ls | grep jaafarjaafar/devops', returnStatus: true)
          if (tagsExists == 0) {
            sh 'docker rmi jaafarjaafar/devops:backend'
            sh 'docker rmi jaafarjaafar/devops:frontend'
             }
           def backImageExists = sh(script: 'docker image ls | grep backend', returnStatus: true)

          if (backImageExists == 0) {
                sh 'docker rmi backend'
            }

            def frontImageExists = sh(script: 'docker image ls | grep frontend', returnStatus: true)
              if (frontImageExists == 0) {
                   sh 'docker rmi frontend'

                        }

             sh 'docker build -t jaafarjaafar/devops:backend .'

             sh 'docker build -t jaafarjaafar/devops:frontend kaddem-front'
         }
     }
 }

            stage('push images to hub') {
             steps {
                script {
                     withCredentials([string(credentialsId: 'docker_pwd', variable: 'dockerpwd')]) {
                        sh 'docker login -u jaafarjaafar -p ${dockerpwd}'
                         sh 'docker push jaafarjaafar/devops:backend'
                         sh 'docker push jaafarjaafar/devops:frontend'
                     }
                }
             }
         }
       stage('Docker Compose') {
            steps {
                script {
                   sh 'docker compose up -d --build'
              }
            }
          }

stage('Grafana') {
    steps {
        script {
            def grafanaEndpoint = "http://192.168.56.20:3000/api/datasources/proxy/1/api/v1/query"
            def query = "up"

            withCredentials([usernamePassword(credentialsId: 'grafana-cred', usernameVariable: 'admin', passwordVariable: 'grafana')]) {
                def grafanaResponse = sh(script: "curl -s -u ${admin}:${grafana} '${grafanaEndpoint}?query=${query}'", returnStdout: true).trim()
                echo "Grafana Response: ${grafanaResponse}"
            }
        }
    }
}
          stage('Email') {
                      steps {
                          script {
                              emailext subject: 'Build Status',
                                       body: 'The build and deployment are complete.',
                                       to: 'jaafer.jaafer@esprit.tn',
                                       attachLog: true
                          }
                      }
                  }
 }
}