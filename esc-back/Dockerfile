FROM openjdk:17-alpine
#아래 경로의 jar파일을 사용해서 도커 이미지를 만든다.
COPY build/libs/*.jar app.jar

#터미널에서의 java -jar app.jar --jasypt.encryptor.password=team5esc 와 같다.
ENTRYPOINT ["java", "-jar", "app.jar", "--jasypt.encryptor.password=team5esc"]