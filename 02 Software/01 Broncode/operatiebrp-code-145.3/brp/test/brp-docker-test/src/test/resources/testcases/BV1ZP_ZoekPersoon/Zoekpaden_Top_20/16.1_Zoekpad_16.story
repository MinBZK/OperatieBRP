Meta:
@status             Klaar
@sleutelwoorden     Zoekpaden
@regels             ZoekPersoon

Narrative:
Piet Jansen, trouwt op 2016-05-10 met Libby

Scenario 16:
Zoeken op - Huwelijk.datum aanvang: V2016-05-10
          - Persoon.SamengesteldeNaam.Geslachtsnaamstam: Jan (Vanaf)
          - Persoon.Geslachtsaanduiding.Code: V


Scenario: 1.1 Zoeken op Huwelijk.datumaanvang

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/OHNL01C260T10-Anne.xls
Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Zoekpaden_Top_20/Requests/Zoek_Persoon_Story_16.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 1 groepen 'persoon'
Then is er voor xpath //brp:samengesteldeNaam[brp:geslachtsnaamstam = 'Bakker'] een node aanwezig in het antwoord bericht
