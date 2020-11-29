#!/usr/bin/env bash

# $1 as flag means create, anything else remove the given war file
# $2 is the new file
# $3 current crop /
# $4 new cropname OR original war to duplicate / will depend on second parameter

x=1
y=2
if [[ $2 == ${x} ]]
then
    cp /usr/local/tomcat/webapps$4.war /usr/local/tomcat/webapps/$3.war
elif [[ $2 == ${y} ]]
then
    mv /usr/local/tomcat/webapps/$3.war /usr/local/tomcat/webapps/$4.war
else
    rm /usr/local/tomcat/webapps/$3.war
fi