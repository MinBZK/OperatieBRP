Test tooling

Configuratie (default configuratie wijst naar localhost):
 - Kopieer een voor configuratie uit de example directory, hernoem deze naam datasource.properties (en pas eventueel de daarin gestelde waarden aan)
 
Uitvoeren:
 - Voer test.cmd uit
 - Mogelijke parameters [memory] <directory> <thema-filter> <testcasusfilter>
   [memory] De optionele indicatie memory geeft aan dat de tests tegen een in-memory database uitgevoerd moeten worden (en niet tegen een externe database)
   <directory> parameter is praktisch verplicht
 
Bijvoorbeeld(1):
 test.cmd P:\repos\isc-code\migr-test-preconditie\test
 voert alle tests, van alle themas, in de directory uit tegen een externe database
 
Bijvoorbeeld(2): 
 test.cmd memory P:\repos\isc-code\migr-test-preconditie\test date
 voert de tests van het thema date uit tegen een in-memory database

