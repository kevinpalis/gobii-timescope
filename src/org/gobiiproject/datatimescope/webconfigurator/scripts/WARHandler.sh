#!/usr/bin/env bash

# $1 as flag means create, anything else remove the given war file
# $2 is the new file
# $3 old file /
# $4 new file /

x=1
y=2
if [[ $2 == ${x} ]]
then
    cp /usr/local/tomcat/webapps$4.war /usr/local/tomcat/webapps/$3.war
elif [[ $2 == ${y} ]]
then
    mv /usr/local/tomcat/webapps/$4.war /usr/local/tomcat/webapps/$3.war
else
    rm /usr/local/tomcat/webapps/$3.war
fi