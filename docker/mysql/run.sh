#!/bin/bash

set -x

if [ $(docker ps -a -q -f name=mysql) ]; then
  docker rm -f mysql
fi

docker run \
  -p 3306:3306 \
  --name mysql \
  --restart=always \
  -v /home/wuzhenhua/docker/mysql/my.cnf:/etc/my.cnf \
  -v /home/wuzhenhua/docker/mysql/my.cnf.d:/etc/my.cnf.d \
  -v /home/wuzhenhua/docker/mysql/mysql:/etc/mysql \
  -v /home/wuzhenhua/docker/mysql/data:/var/lib/mysql \
  -v /home/wuzhenhua/docker/mysql/log:/var/log/mysql \
  --network bilibili-network \
  -e MYSQL_ROOT_PASSWORD=123456 \
  -d mysql

set +x