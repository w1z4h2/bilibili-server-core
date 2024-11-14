#!/bin/bash

set -x

IMAGE_NAME="gateway-service"
CONTAINER_NAME="gateway-service"

if [ $(docker ps -aq -f name=$CONTAINER_NAME) ]; then
    docker rm -f $CONTAINER_NAME
fi

if [ $(docker images -q $IMAGE_NAME) ]; then
    docker rmi -f $IMAGE_NAME
fi

docker build -t $IMAGE_NAME .

docker run -d \
  -p 8080:8080 \
  -v /home/wuzhenhua/docker/java/gateway/gateway-service.jar:/java/gateway-service.jar \
  -v /home/wuzhenhua/docker/java/gateway/application.yml:/java/config/application.yml \
  -v /home/wuzhenhua/docker/java/gateway/log:/java/log \
  --name $CONTAINER_NAME \
  --network bilibili-network \
  $IMAGE_NAME

set +x