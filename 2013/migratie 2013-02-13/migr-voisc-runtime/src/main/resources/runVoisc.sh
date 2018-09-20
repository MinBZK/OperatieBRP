rm -Rf migr-isc-voisc-0.0.20-SNAPSHOT-*.jar
cp -lf migr-isc-voisc-*.jar migr-isc-voisc.jar
java -cp "lib/migr-utils-0.0.20-SNAPSHOT.jar:migr-isc-voisc.jar:lib/*:." nl.moderniseringgba.isc.voisc.VoaMain $@
