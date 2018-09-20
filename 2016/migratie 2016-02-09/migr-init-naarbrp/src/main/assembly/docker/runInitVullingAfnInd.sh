#!/bin/sh

java -Xms256m -Xmx512m -XX:MaxPermSize=256m -cp "conf:lib/*" nl.bzk.migratiebrp.init.naarbrp.Main -sync_afn
