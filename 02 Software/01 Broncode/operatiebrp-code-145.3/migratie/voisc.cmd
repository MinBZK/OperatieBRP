@echo off

:shutdown_voisc
echo Shutdown VOISC
cd /d %~dp0
cd ..
cd ..
cd voisc-centraal
cd migr-voisc-runtime
call stopVoisc.cmd

:make_voisc
cd /d %~dp0
call mvn -pl migr-voisc-runtime -am clean install -Dtest=no -DfailIfNoTests=false
if errorlevel 1 goto error

:undeploy_voisc
echo Undeploy VOISC
cd /d %~dp0
cd ..
cd ..
cd voisc-centraal
rmdir /s /q migr-voisc-runtime
del migr-voisc-runtime.zip

:deploy_voisc
echo Deploy VOISC
cd /d %~dp0
cd ..
cd ..
cd voisc-centraal
copy %~dp0migr-voisc-runtime\target\migr-voisc-runtime.zip

mkdir migr-voisc-runtime
cd migr-voisc-runtime
jar -xf ..\migr-voisc-runtime.zip

:configure_voisc
echo Configure VOISC
cd /d %~dp0
cd ..
cd ..
cd voisc-centraal
cd migr-voisc-runtime
copy ..\config.properties

:run_voisc
echo Run VOISC
cd /d %~dp0
cd ..
cd ..
cd voisc-centraal
cd migr-voisc-runtime
start runVoisc.cmd

goto end

:errror
ECHO Error occured!

:end
echo Done
cd /d %~dp0
