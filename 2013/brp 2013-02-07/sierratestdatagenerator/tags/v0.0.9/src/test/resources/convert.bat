
java -jar sierratestdatagenerator-0.0.9-SNAPSHOT.jar -c -x SierraTestdataHP.xls -s ./test.sql
"c:\Program Files (x86)\PostgreSQL\9.2\bin\psql.exe" -d brp -U brp -f test.sql
pause