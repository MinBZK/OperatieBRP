Meta:
@status             Klaar
@sleutelwoorden     Zoekpaden
@regels             ZoekPersoon

Narrative:
Wilma Jansen:
- Geslachtsaanduiding: V
- geslachtsnaamstam: Jansen
- Adres afgekorte naam: Agaatstraat
- Huisnummer: 17
- Woonplaatsnaam: Drachten
- Persoon.Adres.BuitenlandsAdresRegel2: 75005 PARIS


Scenario 14:
Zoeken op - Persoon.Adres.BuitenlandsAdresRegel2: 75005 PARIS
          - Persoon.SamengesteldeNaam.Geslachtsnaamstam: Jan (Vanaf)
          - Persoon.Geslachtsaanduiding.Code: V


Scenario: 1. Wilma Jansen zoeken

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Wilma_Jansen_Drachten_Zoekpaden.xls

Given de database is aangepast met: update kern.persadres set bladresregel2='75005 PARIS' where wplnaam = 'Drachten'

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Zoekpaden_Top_20/Requests/Zoek_Persoon_Story_14.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 1 groepen 'persoon'
Then is er voor xpath //brp:samengesteldeNaam[brp:geslachtsnaamstam = 'Jansen'] een node aanwezig in het antwoord bericht
