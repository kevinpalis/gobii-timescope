#!/usr/bin/env bash

# $1 is the new crop name
# $2 is flag for deletion or creation (1 == create)
# $3 is other crop name to copy confidentiality.txt from

x=1
if [[ $2 == ${x} ]]
then
    ssh gadm@cbsugobiixvm14.biohpc.cornell.edu "docker exec gobii-web-node bash -c 'mkdir /data/gobii_bundle/crops/$1'"
    ssh gadm@cbsugobiixvm14.biohpc.cornell.edu "docker exec gobii-web-node bash -c 'mkdir gobii_bundle/crops/$1/extractor'"
    ssh gadm@cbsugobiixvm14.biohpc.cornell.edu "docker exec gobii-web-node bash -c 'mkdir gobii_bundle/crops/$1/extractor/done'"
    ssh gadm@cbsugobiixvm14.biohpc.cornell.edu "docker exec gobii-web-node bash -c 'mkdir gobii_bundle/crops/$1/extractor/inprogress'"
    ssh gadm@cbsugobiixvm14.biohpc.cornell.edu "docker exec gobii-web-node bash -c 'mkdir gobii_bundle/crops/$1/extractor/instructions'"
    ssh gadm@cbsugobiixvm14.biohpc.cornell.edu "docker exec gobii-web-node bash -c 'mkdir gobii_bundle/crops/$1/extractor/output'"
    ssh gadm@cbsugobiixvm14.biohpc.cornell.edu "docker exec gobii-web-node bash -c 'mkdir gobii_bundle/crops/$1/files'"
    ssh gadm@cbsugobiixvm14.biohpc.cornell.edu "docker exec gobii-web-node bash -c 'mkdir gobii_bundle/crops/$1/hdf5'"
    ssh gadm@cbsugobiixvm14.biohpc.cornell.edu "docker exec gobii-web-node bash -c 'mkdir gobii_bundle/crops/$1/loader'"
    ssh gadm@cbsugobiixvm14.biohpc.cornell.edu "docker exec gobii-web-node bash -c 'mkdir gobii_bundle/crops/$1/loader/digest'"
    ssh gadm@cbsugobiixvm14.biohpc.cornell.edu "docker exec gobii-web-node bash -c 'mkdir gobii_bundle/crops/$1/loader/done'"
    ssh gadm@cbsugobiixvm14.biohpc.cornell.edu "docker exec gobii-web-node bash -c 'mkdir gobii_bundle/crops/$1/loader/inprogress'"
    ssh gadm@cbsugobiixvm14.biohpc.cornell.edu "docker exec gobii-web-node bash -c 'mkdir gobii_bundle/crops/$1/loader/instructions'"
    ssh gadm@cbsugobiixvm14.biohpc.cornell.edu "docker exec gobii-web-node bash -c 'mkdir gobii_bundle/crops/$1/loader/qc'"
    ssh gadm@cbsugobiixvm14.biohpc.cornell.edu "docker exec gobii-web-node bash -c 'mkdir gobii_bundle/crops/$1/notices'"
    ssh gadm@cbsugobiixvm14.biohpc.cornell.edu "docker exec gobii-web-node bash -c 'cp gobii_bundle/crops/$3/notices/confidentiality.txt gobii_bundle/crops/$1/notices/confidentiality.txt'"
else
    ssh gadm@cbsugobiixvm14.biohpc.cornell.edu "docker exec gobii-web-node bash -c 'rm -r gobii_bundle/crops/$1'"
fi






