#!/usr/bin/env bash

cd /usr/local/tomcat/webapps/gobii-portal/config/

CROPNAME="$1"

LASTLINE=$(sed -n '$p' launchers.xml)

L=$(echo $LASTLINE | sed 's/\//\\\//g')

sed -i.BAK -n "1p;/<launcher>/,/<\/launcher>/{ H
/<\/launcher>/{ s/.*//;x
/$CROPNAME/d
p
}
};/${L}/p" launchers.xml