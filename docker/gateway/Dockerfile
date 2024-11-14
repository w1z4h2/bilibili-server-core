FROM openjdk:8

COPY gateway-service.jar /java/

WORKDIR /java

COPY application.yml /config/application.yml

RUN echo 'Asia/Shanghai' > /etc/timezone

CMD ["sh", "-c", "java -jar gateway-service.jar --spring.config.location=file:/java/config/application.yml > /java/log/gateway-service.log 2>&1"]
