#!/usr/bin/env bash

# 1 copies to remote, 2 overwrites old xml (after validation), 3 removes new one (failed validation), 4 copies from remote
case $1 in
    scpto)
        scp $2 gadm@cbsugobiixvm14.biohpc.cornell.edu:/data/gobii_bundle/config/gobii-web-tmp.xml
        rm $2
        ;;
    passed)
        ssh gadm@cbsugobiixvm14.biohpc.cornell.edu "mv /data/gobii_bundle/config/gobii-web-tmp.xml /data/gobii_bundle/config/gobii-web.xml"
        ;;
    failed)
        ssh gadm@cbsugobiixvm14.biohpc.cornell.edu "rm /data/gobii_bundle/config/gobii-web-tmp.xml"
        ;;
    scpfrom)
        scp gadm@cbsugobiixvm14.biohpc.cornell.edu:/data/gobii_bundle/config/gobii-web.xml $2
        ;;
esac
