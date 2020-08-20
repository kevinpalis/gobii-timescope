#!/usr/bin/env bash

ssh gadm@$1 "docker exec gobii-db-node bash -c 'cd /var/lib/postgresql/gobii.db/builder/rawbase; sh create_or_replace_gobii_db.sh $6 $2 $1 $7 $3'"
cd /data/liquibase
java -jar bin/liquibase.jar --username=$2 --password=$3 --url=jdbc:postgresql://$4:$5/$6 --driver=org.postgresql.Driver --classpath=drivers/postgresql-9.4.1209.jar --changeLogFile=changelogs/db.changelog-master.xml --contexts=general,seed_general update
