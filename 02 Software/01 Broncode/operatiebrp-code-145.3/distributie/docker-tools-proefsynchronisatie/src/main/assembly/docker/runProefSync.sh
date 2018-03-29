#!/bin/sh

java -Xmx1024M -cp "conf:lib/*" nl.bzk.migratiebrp.tools.proefsynchronisatie.Main -config conf/proef-sync.properties -batchSize 10000 -timeout 1200000
