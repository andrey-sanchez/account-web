#!/usr/bin/env bash

docker pull mysql:5.7.23

docker run --name account-db-container -e MYSQL_ROOT_PASSWORD=password -p 3306:3306 -d mysql:5.7.23
