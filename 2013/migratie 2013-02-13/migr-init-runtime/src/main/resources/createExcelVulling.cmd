call copy lib\migr-utils*.jar lib\migr-utils.jar
call java -classpath "lib\migr-utils.jar;migr-init-runtime-1.0-SNAPSHOT.jar;lib\*;." nl.moderniseringgba.migratie.init.runtime.Main -config config.properties -excelvulling D:\excel
