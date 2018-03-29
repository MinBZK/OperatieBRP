#!/bin/bash

AFLEVERPUNT=http://localhost/abc
WORKDIR=./afnemerindicatieworkdir

java -cp "autaut-conversie.jar:." -Dbrp.afleverpunt=$AFLEVERPUNT -Dworkdir=$WORKDIR nl.bzk.brp.util.autconv.lo3naarbrp.Main