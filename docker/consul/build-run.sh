#!/bin/bash

set -x

for container in consul-server-01 consul-server-02 consul-server-03 consul-client-gateway consul-client-user
do
  if [ $(docker ps -a -q -f name=$container) ]; then
    docker rm -f $container
  fi
done

docker compose -f consul-compose.yaml up -d

set +x