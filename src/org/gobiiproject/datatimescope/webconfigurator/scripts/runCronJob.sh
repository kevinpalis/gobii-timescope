#!/usr/bin/env bash

ssh gadm@$1 "docker exec gobii-compute-node bash -c 'cd /data/gobii_bundle/loaders/; ./cronjob.sh $2; cd /data/gobii_bundle/extractors/; ./cronjob.sh $2'"
