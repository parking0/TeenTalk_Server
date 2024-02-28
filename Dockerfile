FROM openjdk:17-jdk
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} appp.jar
RUN echo 안녕하세요 Shell 형식 입니다.
RUN echo "test..."
ENTRYPOINT ["java","-jar","/appp.jar"]