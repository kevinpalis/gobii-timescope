#!/usr/bin/env bash

x=1
if [[ $1 == ${x} ]]
then
    ssh gadm@cbsugobiixvm14.biohpc.cornell.edu "docker exec gobii-web-node bash -c 'cp /usr/local/tomcat/webapps/gobii-dev.war /usr/local/tomcat/webapps/$2.war'"
else
    ssh gadm@cbsugobiixvm14.biohpc.cornell.edu "docker exec gobii-web-node bash -c 'rm /usr/local/tomcat/webapps/$2.war'"
fi