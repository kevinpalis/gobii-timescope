#!/bin/bash

sshpass -p g0b11Admin scp /home/fvgoldman/Documents/apache-tomcat-7.0.94/bin/newCrons.txt gadm@cbsugobiixvm14.biohpc.cornell.edu:/home/gadm
sshpass -p g0b11Admin ssh gadm@cbsugobiixvm14.biohpc.cornell.edu "docker cp newCrons.txt gobii-compute-node:/data"
sshpass -p g0b11Admin ssh gadm@cbsugobiixvm14.biohpc.cornell.edu "docker exec gobii-compute-node bash -c 'crontab -u gadm /data/newCrons.txt'"
rm newCrons.txt
sshpass -p g0b11Admin ssh gadm@cbsugobiixvm14.biohpc.cornell.edu "rm newCrons.txt"
sshpass -p g0b11Admin ssh gadm@cbsugobiixvm14.biohpc.cornell.edu "docker exec gobii-compute-node bash -c 'rm newCrons.txt'"


