pushd %~dp0\..

call mvn clean install -Dmaven.test.skip=true -Pdocker -pl :migr-isc-console-webapp,:migr-isc-runtime,:migr-routering-runtime,:migr-synchronisatie-runtime,:migr-tools-brp-database,:migr-tools-brp-routering,:migr-tools-mailbox,:migr-voisc-runtime,:migr-tools-brp-database,:migr-tools-brp-routering %*

popd