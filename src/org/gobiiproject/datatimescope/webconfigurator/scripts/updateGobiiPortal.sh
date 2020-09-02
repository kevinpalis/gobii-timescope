#!/usr/bin/env bash

CONTENT="<launcher>\n<name>$1</name>\n<url>http://$2:8081/gobii-$1</url>\n<logo>extract.png</logo>\n<description>GDM Extractor for crop $1</description>\n<color>color-green</color>\n<category>Genotype Management</category>\n<type>Web Apps</type>\n<documentationList>\n<documentation>\n<displayName>GDM Extractor</displayName>\n<url>https://gobiiproject.atlassian.net/l/c/Bz31y18V</url>\n</documentation>\n</documentationList>\n</launcher>"

cd /usr/local/tomcat/webapps/gobii-portal/config/
C=$(echo $CONTENT | sed 's/\//\\\//g')
sed -i.BAK "/<\/gobii-launchers>/ s/.*/${C}\n&/" launchers.xml