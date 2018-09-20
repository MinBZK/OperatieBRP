#!/bin/bash

nohup java -Xmx256M -cp "conf:lib/*" nl.bzk.migratiebrp.init.naarbrp.Main -laad_afn >output-createafn.txt 2>&1 &
