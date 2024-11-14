FROM openjdk:8

COPY user-service.jar /java/

WORKDIR /java

COPY application.yml /config/application.yml

RUN echo 'Asia/Shanghai' > /etc/timezone

CMD ["sh", "-c", "java -jar user-service.jar --spring.config.location=file:/java/config/application.yml > /java/log/user-service.log 2>&1"]
