FROM openjdk:8
EXPOSE 8089
ADD target/kaddemExec.jar kaddemE.jar
ENTRYPOINT ["java","-jar","/kaddemE.jar"]