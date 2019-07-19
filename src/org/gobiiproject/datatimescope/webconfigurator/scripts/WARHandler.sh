#!/usr/bin/env bash

# $1 as flag means create, anything else remove the given war file
# $2 is the new file
# $3 is the to be copied file, is prefixed by /

x=1
if [[ $2 == ${x} ]]
then
    ssh gadm@$1 "docker exec gobii-web-node bash -c 'cp /usr/local/tomcat/webapps$4.war /usr/local/tomcat/webapps/$3.war'"
else
    ssh gadm@$1 "docker exec gobii-web-node bash -c 'rm /usr/local/tomcat/webapps/$3.war'"
fi