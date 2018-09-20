cp -lf lib/migr-utils-*.jar lib/migr-utils.jar
cp -lf migr-sync-controle-*.jar migr-sync-controle.jar
java -Dconfig=config.properties -cp "lib/migr-utils.jar:migr-sync-controle.jar:lib/*:." nl.moderniseringgba.migratie.controle.runtime.Main -config config.properties -vanaf 01012002 -tot 01012013 -type ALLE_PERSONEN -niveau VOLLEDIGE_PL
