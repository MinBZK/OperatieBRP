cp -lf lib/migr-utils-*.jar lib/migr-utils.jar
cp -lf migr-init-logging-*.jar migr-init-logging.jar
nohup java -Dconfig=config.properties -cp "lib/migr-utils.jar:migr-init-logging.jar:lib/*:." nl.moderniseringgba.migratie.logging.runtime.Main &