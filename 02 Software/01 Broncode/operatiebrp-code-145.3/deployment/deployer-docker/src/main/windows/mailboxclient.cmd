@echo off
pushd %~dp0..\..\..\..\..\distributie\shaded-tools-mailboxclient\target
for %%i in (distributie-shaded-tools-mailboxclient-*.jar) do set jarfile=%%i
popd
java -jar %~dp0..\..\..\..\..\distributie\shaded-tools-mailboxclient\target\%jarfile% %*
