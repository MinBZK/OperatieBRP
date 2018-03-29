#!/bin/bash

nohup java -Xmx256M -cp "conf:lib/*" nl.bzk.migratiebrp.init.naarbrp.Main -excelvulling ./excel-regressie >output-excel.txt 2>&1 &
