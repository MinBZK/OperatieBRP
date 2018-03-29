#!/bin/sh

java -Xmx256M -cp "conf:lib/*" nl.bzk.migratiebrp.init.naarbrp.Main -sync_pers
