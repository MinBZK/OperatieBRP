#!/bin/bash

nohup java -Xmx256M -cp "conf:lib/*" nl.bzk.migratiebrp.init.naarbrp.Main -laad_aut >output-createaut.txt 2>&1 &
