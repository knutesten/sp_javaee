#!/bin/bash

docker run -d -v $1:/var/lib/mysql --name db tutum/mysql:5.6

docker run -d -p 8080:8080 -p 4848:4848 -p 8181:8181 --name web --link db:db knutesten/sp_javaee:v1.0
