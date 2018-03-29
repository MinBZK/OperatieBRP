pushd %~dp0..\..\..\..\..\distributie\shaded-tools-jconsole\target
for %%i in (distributie-shaded-tools-jconsole-*.jar) do set jarfile=%%i
start /b java -jar %jarfile%
popd