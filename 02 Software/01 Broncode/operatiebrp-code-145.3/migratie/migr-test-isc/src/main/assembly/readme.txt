Test tooling

Configuratie (default configuratie wijst naar localhost):
 - Kopieer een voor configuratie uit de example directory, hernoem deze naam datasource.properties (en pas eventueel de daarin gestelde waarden aan)
 
Uitvoeren:
 - Voer test.cmd uit
 - Drie mogelijke parameters <directory> <thema-filter> <testcasusfilter>
   Eerste parameter is praktisch verplicht
 
Bijvoorbeeld(1):
 test.cmd P:\repos\isc-code\migr-test-isc\test uc306
 voert alle tests voor het thema uc306 uit
 
Bijvoorbeeld(2): 
 test.cmd P:\repos\isc-code\migr-test-isc\test uc306 uc306-01C10T10
 voert alleen de test uc306-01C10T10 uit het thema uc306 uit
