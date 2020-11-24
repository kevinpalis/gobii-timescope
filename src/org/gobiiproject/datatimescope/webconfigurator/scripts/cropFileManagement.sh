#!/usr/bin/env bash

# $2 is the current crop name
# $3 is flag for deletion or creation (1 is create, 2 is rename)
# $4 is other crop name to copy confidentiality.txt from OR new cropname (for modify feature)

x=1
y=2
if [[ $3 == ${x} ]]
then
    mkdir /data/gobii_bundle/crops/$2
    mkdir /data/gobii_bundle/crops/$2/extractor
    mkdir /data/gobii_bundle/crops/$2/extractor/done
    mkdir /data/gobii_bundle/crops/$2/extractor/inprogress
    mkdir /data/gobii_bundle/crops/$2/extractor/instructions
    mkdir /data/gobii_bundle/crops/$2/extractor/output
    mkdir /data/gobii_bundle/crops/$2/files
    mkdir /data/gobii_bundle/crops/$2/hdf5
    mkdir /data/gobii_bundle/crops/$2/loader
    mkdir /data/gobii_bundle/crops/$2/loader/digest
    mkdir /data/gobii_bundle/crops/$2/loader/done
    mkdir /data/gobii_bundle/crops/$2/loader/inprogress
    mkdir /data/gobii_bundle/crops/$2/loader/instructions
    mkdir /data/gobii_bundle/crops/$2/loader/qc
    mkdir /data/gobii_bundle/crops/$2/notices
    cp /data/gobii_bundle/crops/$4/notices/confidentiality.txt /data/gobii_bundle/crops/$2/notices/confidentiality.txt
elif [[ $3 == ${y} ]]
then
    mv /data/gobii_bundle/crops/$2 /data/gobii_bundle/crops/$5
	
else
    rm -r /data/gobii_bundle/crops/$2
fi






