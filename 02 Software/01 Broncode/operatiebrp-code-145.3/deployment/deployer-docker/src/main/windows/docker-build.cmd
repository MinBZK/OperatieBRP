pushd %~dp0..\..\..\..\..\distributie
call mvn clean install -Pdocker
call %~dp0remove-dangling-images
popd