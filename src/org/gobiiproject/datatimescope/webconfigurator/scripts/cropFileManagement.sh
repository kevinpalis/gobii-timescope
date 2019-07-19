#!/usr/bin/env bash

# $1 is the new crop name
# $2 is flag for deletion or creation (1 == create)
# $3 is other crop name to copy confidentiality.txt from

x=1
if [[ $3 == ${x} ]]
then
    ssh gadm@$1 "docker exec gobii-web-node bash -c 'mkdir /data/gobii_bundle/crops/$2'"
    ssh gadm@$1 "docker exec gobii-web-node bash -c 'mkdir gobii_bundle/crops/$2/extractor'"
    ssh gadm@$1 "docker exec gobii-web-node bash -c 'mkdir gobii_bundle/crops/$2/extractor/done'"
    ssh gadm@$1 "docker exec gobii-web-node bash -c 'mkdir gobii_bundle/crops/$2/extractor/inprogress'"
    ssh gadm@$1 "docker exec gobii-web-node bash -c 'mkdir gobii_bundle/crops/$2/extractor/instructions'"
    ssh gadm@$1 "docker exec gobii-web-node bash -c 'mkdir gobii_bundle/crops/$2/extractor/output'"
    ssh gadm@$1 "docker exec gobii-web-node bash -c 'mkdir gobii_bundle/crops/$2/files'"
    ssh gadm@$1 "docker exec gobii-web-node bash -c 'mkdir gobii_bundle/crops/$2/hdf5'"
    ssh gadm@$1 "docker exec gobii-web-node bash -c 'mkdir gobii_bundle/crops/$2/loader'"
    ssh gadm@$1 "docker exec gobii-web-node bash -c 'mkdir gobii_bundle/crops/$2/loader/digest'"
    ssh gadm@$1 "docker exec gobii-web-node bash -c 'mkdir gobii_bundle/crops/$2/loader/done'"
    ssh gadm@$1 "docker exec gobii-web-node bash -c 'mkdir gobii_bundle/crops/$2/loader/inprogress'"
    ssh gadm@$1 "docker exec gobii-web-node bash -c 'mkdir gobii_bundle/crops/$2/loader/instructions'"
    ssh gadm@$1 "docker exec gobii-web-node bash -c 'mkdir gobii_bundle/crops/$2/loader/qc'"
    ssh gadm@$1 "docker exec gobii-web-node bash -c 'mkdir gobii_bundle/crops/$2/notices'"
    ssh gadm@$1 "docker exec gobii-web-node bash -c 'cp gobii_bundle/crops/$4/notices/confidentiality.txt gobii_bundle/crops/$2/notices/confidentiality.txt'"
else
    ssh gadm@$1 "docker exec gobii-web-node bash -c 'rm -r gobii_bundle/crops/$2'"
fi






