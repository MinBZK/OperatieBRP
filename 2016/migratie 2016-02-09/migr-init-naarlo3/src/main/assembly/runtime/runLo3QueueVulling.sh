#!/bin/bash

nohup java -cp "conf:lib/*" nl.bzk.migratiebrp.init.naarlo3.Main >output.txt 2>&1 &
