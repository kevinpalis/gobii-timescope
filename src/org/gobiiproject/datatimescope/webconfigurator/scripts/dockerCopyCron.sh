#!/bin/bash

#sends modified newCrons list back to the database

scp /usr/local/tomcat/bin/newCrons.txt gadm@$1:/home/gadm
ssh gadm@$1 "docker cp newCrons.txt gobii-compute-node:/data"
ssh gadm@$1 "docker exec gobii-compute-node bash -c 'crontab -u gadm /data/newCrons.txt'"
rm newCrons.txt
ssh gadm@$1 "rm newCrons.txt"
ssh gadm@$1 "docker exec gobii-compute-node bash -c 'rm newCrons.txt'"


