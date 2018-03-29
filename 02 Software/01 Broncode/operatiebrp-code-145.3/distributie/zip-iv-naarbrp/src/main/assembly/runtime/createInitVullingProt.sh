#!/bin/bash

nohup java -Xmx256M -cp "conf:lib/*" nl.bzk.migratiebrp.init.naarbrp.Main -laad_prot $1 &> output-createprot.txt &
