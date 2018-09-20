#!/bin/bash

source omgeving.sh

ssh $user@$loadgenerator 'java -Ddbhost=pdb97 -Ddbname=nfr_profiel1 -DbrokerUrl=tcp://perf-mgmt02:61616 -Dload=15 -jar NFR-load-generator.jar'




