Test tooling

Configuratie (default configuratie wijst naar localhost):
 - Kopieer een voorbeeldconfiguratie uit de example directory, hernoem deze naar datasource.properties (en pas eventueel de daarin gestelde waarden aan)
 
Uitvoeren:
 - Vul in test.sh de parameter MIGR_LIB_PATH in met de directory waar de libraries van de synchronisatie server staan.
 - Voer test.sh uit
 - Mogelijke parameters <thema-filter> <testcasusfilter>
   <thema-filter> De regressie-set is onderverdeeld in thema's. Door hier een waarde mee te geven, wordt er 1 specifiek thema gerund.
   <testcasusfilter> Selecteer 1 specifieke test binnen een thema.
