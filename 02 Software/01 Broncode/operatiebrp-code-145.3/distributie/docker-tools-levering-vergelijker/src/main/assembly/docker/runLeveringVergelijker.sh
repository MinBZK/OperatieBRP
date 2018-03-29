#!/bin/sh

java -Xmx2048M -cp "conf:lib/*" nl.bzk.migratiebrp.tools.levering.vergelijker.LeveringVergelijkerRuntimeMain "$@"
