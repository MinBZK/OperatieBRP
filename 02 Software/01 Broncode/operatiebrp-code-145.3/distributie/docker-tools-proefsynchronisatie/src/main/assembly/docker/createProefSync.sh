#!/bin/sh

java -Xmx1024M -cp "conf:lib/*" nl.bzk.migratiebrp.tools.proefsynchronisatie.Main -config conf/proef-sync.properties -create -datumVanaf "$1" -datumTot "$2"
