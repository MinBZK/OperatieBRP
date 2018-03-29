In endpoints.properties staan de endpoints van de url's van de beheerapplicatie waar de POST-requests (de template bestanden) heen gestuurd moeten worden.
Het bestand heeft de volgende structuur: 
'naam template'='endpoint'
Het eerste gedeelte moet dus overeen komen met een naam van een template uit de Template map.
Het tweede gedeelte bevat het endpoint. In dit endpoint kan het voorkomen dat er een id gebruikt moet worden. In het document inhoudLeveringsautorisaties.xls staat uitgelegd hoe id's uit de POST-responses opgeslagen kunnen worden. Deze id's kunnen vervolgens gebruikt worden in de endpoints door de naam in te vullen voorafgegaan door een $, bijvoorbeeld $idlevaut.