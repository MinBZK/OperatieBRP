@echo off
pushd %~dp0..\..\..\..\..\distributie\shaded-tools-queue\target
for %%i in (distributie-shaded-tools-queue-*.jar) do set jarfile=%%i
popd
java -jar %~dp0..\..\..\..\..\distributie\shaded-tools-queue\target\%jarfile% %*
