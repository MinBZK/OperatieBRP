Meta:
@status             Klaar
@sleutelwoorden     Zoekpaden
@regels             ZoekPersoon

Narrative:
Jan Vermeer

Scenario 12:
Zoeken op
          - Persoon.Reisdocument.SoortCode : PD
          - Persoon.Reisdocument.Nummer :

Scenario: 1. Jan Vermeer zoeken

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/JanVermeer_met_ReisDoc.xls

Given de database is aangepast met: update kern.persadres set locoms='Achterzijde Centraal Station' where wplnaam = 'Breda'

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Zoekpaden_Top_20/Requests/Zoek_Persoon_Story_12.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 1 groepen 'persoon'
Then is er voor xpath //brp:samengesteldeNaam[brp:geslachtsnaamstam = 'Vermeer'] een node aanwezig in het antwoord bericht
