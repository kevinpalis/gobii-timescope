#!/usr/bin/env bash

ssh gadm@cbsugobiixvm14.biohpc.cornell.edu "docker exec gobii-db-node bash -c 'cd /var/lib/postgresql/data-warehouse-postgresql/builder/rawbase; sh create_or_replace_gobii_db.sh $5 $1 $3 $4 $2'"
ssh gadm@cbsugobiixvm14.biohpc.cornell.edu "docker exec gobii-web-node bash -c 'cd liquibase; java -jar bin/liquibase.jar --username=$1 --password=$2 --url=jdbc:postgresql://$3:$4/$5 --driver=org.postgresql.Driver --classpath=drivers/postgresql-9.4.1209.jar --changeLogFile=changelogs/db.changelog-master.xml --contexts=general,seed_general update'"
