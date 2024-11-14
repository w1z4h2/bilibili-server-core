#!/bin/bash

set -x

IMAGE_NAME="user-service"
CONTAINER_NAME="user-service"

if [ $(docker ps -aq -f name=$CONTAINER_NAME) ]; then
    docker rm -f $CONTAINER_NAME
fi

if [ $(docker images -q $IMAGE_NAME) ]; then
    docker rmi -f $IMAGE_NAME
fi

docker build -t $IMAGE_NAME .

docker run -d \
  -p 8081:8081 \
  -v /home/wuzhenhua/docker/java/user/user.jar:/java/user.jar \
  -v /home/wuzhenhua/docker/java/user/application.yml:/java/config/application.yml \
  -v /home/wuzhenhua/docker/java/user/log:/java/log \
  --name $CONTAINER_NAME \
  --network bilibili-network \
  $IMAGE_NAME

set +x