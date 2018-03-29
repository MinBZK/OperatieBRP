#!/bin/bash

nohup java -Xmx256M -cp "conf:lib/*" nl.bzk.migratiebrp.init.naarbrp.Main -laad_pers >output-create.txt 2>&1 &
