#!/usr/bin/env bash

# $1 as flag means create, anything else remove the given war file
# $2 is the new file
# $3 is the to be copied file, is prefixed by /

x=1
if [[ $1 == ${x} ]]
then
    ssh gadm@cbsugobiixvm14.biohpc.cornell.edu "docker exec gobii-web-node bash -c 'cp /usr/local/tomcat/webapps$3.war /usr/local/tomcat/webapps/$2.war'"
else
    ssh gadm@cbsugobiixvm14.biohpc.cornell.edu "docker exec gobii-web-node bash -c 'rm /usr/local/tomcat/webapps/$2.war'"
fi