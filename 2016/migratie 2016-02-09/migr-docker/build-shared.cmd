pushd %~dp0\..\..\shared-code

cd docker\logging
call mvn clean install -Pdocker %*

popd