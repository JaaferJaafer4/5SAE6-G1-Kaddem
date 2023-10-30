FROM openjdk:8
EXPOSE 8091
ADD target/kaddemExec.jar kaddemE.jar
ENTRYPOINT ["java","-jar","/kaddemE.jar"]