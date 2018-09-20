@echo on

echo Oude testcases verwijderen
cd /d %~dp0
del /q *.xls
for /d %%d in (*.*) do (
	rmdir /s /q "%%d"
)

echo Deltabepaling testcases kopieren
cd /d %~dp0
cd ..
cd ..
cd ..
cd ..
cd ..
cd ..
cd isc-code
cd migr-test-persoon-database
cd deltabepaling

for /d %%d in (*.*) do (
	copy "%%d\*.xls" "%~dp0"
)

echo Nieuwe levering testcases aanmaken
cd /d %~dp0
for %%f in (*.xls) do (
	echo %%~nf
	cd /d %~dp0
	mkdir "%%~nf"
	move /y "%%f" "%%~nf\delta.xls"
	copy /y autorisatie.csv "%%~nf"
)

cd /d %~dp0
