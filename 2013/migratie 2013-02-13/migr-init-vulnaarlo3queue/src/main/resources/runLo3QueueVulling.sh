cp -lf lib/migr-utils-*.jar lib/migr-utils.jar
cp -lf lib/migr-isc-bericht-*.jar lib/migr-isc-bericht.jar
cp -lf lib/migr-conversie-model-*.jar lib/migr-conversie-model.jar
cp -lf  migr-init-vulnaarlo3queue-*.jar migr-init-vulnaarlo3queue.jar
java -Xmx128M -cp "lib/migr-conversie-model.jar:lib/migr-isc-bericht.jar:lib/migr-utils.jar:migr-init-vulnaarlo3queue.jar:lib/*:." nl.moderniseringgba.migratie.init.vulnaarlo3queue.Main -config config.properties
