#!/bin/bash

scp /home/fvgoldman/Documents/apache-tomcat-7.0.94/bin/newCrons.txt gadm@cbsugobiixvm14.biohpc.cornell.edu:/home/gadm
ssh gadm@cbsugobiixvm14.biohpc.cornell.edu "docker cp newCrons.txt gobii-compute-node:/data"
ssh gadm@cbsugobiixvm14.biohpc.cornell.edu "docker exec gobii-compute-node bash -c 'crontab -u gadm /data/newCrons.txt'"
rm newCrons.txt
ssh gadm@cbsugobiixvm14.biohpc.cornell.edu "rm newCrons.txt"
ssh gadm@cbsugobiixvm14.biohpc.cornell.edu "docker exec gobii-compute-node bash -c 'rm newCrons.txt'"


