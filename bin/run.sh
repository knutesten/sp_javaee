#!/bin/bash

docker run -d -v $1:/var/lib/mysql -e MYSQL_PASS="hester er fine" --name db tutum/mysql:5.6

docker run -d -p 80:8080 -p 4848:4848 -p 81:8181 --name web --link db:db knutesten/sp_javaee:v1.0
