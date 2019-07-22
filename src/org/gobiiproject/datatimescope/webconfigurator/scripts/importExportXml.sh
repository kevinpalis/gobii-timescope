#!/usr/bin/env bash

# OPTIONS: 1 copies to remote, 2 overwrites old xml (after validation), 3 removes new one (failed validation), 4 copies from remote
case $2 in
    scpto)
        scp $3 gadm@1:/data/gobii_bundle/config/gobii-web-tmp.xml
        rm $3
        ;;
    passed)
        mv /data/gobii_bundle/config/gobii-web-tmp.xml /data/gobii_bundle/config/gobii-web.xml
        ;;
    failed)
        rm /data/gobii_bundle/config/gobii-web-tmp.xml
        ;;
    scpfrom)
        scp gadm@$1:/data/gobii_bundle/config/gobii-web.xml $3
        ;;
esac
