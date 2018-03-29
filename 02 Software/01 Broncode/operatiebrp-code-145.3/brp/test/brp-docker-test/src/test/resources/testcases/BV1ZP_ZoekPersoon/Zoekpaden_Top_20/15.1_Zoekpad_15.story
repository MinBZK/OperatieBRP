Meta:
@status             Klaar
@sleutelwoorden     Zoekpaden
@regels             ZoekPersoon

Narrative:
Sandy Clement
Zoeken op - Persoon.Adres.BuitenlandsAdresRegel2: adres2 (Vanaf)
          - Persoon.SamengesteldeNaam.Geslachtsnaamstam: Clement (Vanaf)
          - Persoon.Geslachtsaanduiding.Code: V


Scenario: 1. Sandy Clement zoeken op buitenlandsadres
             LT:
!-- Zoeken op Persoon.Migratie.BuitenlandseAdresRegel2Migratie kan niet. Is geen geldig attribuut

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/JamieSands_BuitenlandsAdres.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Zoekpaden_Top_20/Requests/Zoek_Persoon_Story_15.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 1 groepen 'persoon'
Then is er voor xpath //brp:samengesteldeNaam[brp:geslachtsnaamstam = 'Sands'] een node aanwezig in het antwoord bericht
