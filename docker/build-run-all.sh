#!/bin/bash

set -x

docker stop $(docker ps -q) && docker rm $(docker ps -aq)

if [ -n "$(docker images gateway-service -q)" ]; then
  docker rmi -f gateway-service
fi

if [ -n "$(docker images user-service -q)" ]; then
  docker rmi -f user-service
fi

if docker network ls | grep -q bilibili-network; then
  docker network rm bilibili-network
fi

docker build -t gateway-service ./java/gateway

docker build -t user-service ./java/user

docker compose up -d

set +x