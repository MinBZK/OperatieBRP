cp -lf lib/migr-utils-*.jar lib/migr-utils.jar
cp -lf migr-init-runtime-*.jar migr-init-runtime.jar
java -cp "lib/migr-utils.jar:migr-init-runtime.jar:lib/*:." nl.moderniseringgba.migratie.init.runtime.Main -config config.properties -excelvulling ./excel-regressie
