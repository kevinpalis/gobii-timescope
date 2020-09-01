#!/usr/bin/env bash

ssh gadm@$1 "docker exec gobii-db-node bash -c 'cd /var/lib/postgresql/; git clone https://bitbucket.org/gobiiproject/gobii.db.git; cd gobii.db/builder/rawbase; sh create_or_replace_gobii_db.sh $6 $2 $1 $7 $3'"
cd /data/liquibase
java -jar bin/liquibase.jar --username=$2 --password=$3 --url=jdbc:postgresql://$1:$7/$6 --driver=org.postgresql.Driver --classpath=drivers/postgresql-9.4.1209.jar --changeLogFile=changelogs/db.changelog-master.xml --contexts=general,seed_cbsu,seed_general update
