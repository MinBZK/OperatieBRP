Meta:
@status             Klaar
@sleutelwoorden     Zoekpaden
@regels             ZoekPersoon

Narrative:
Willem Jansen:
- Adelijke Titel: H
- geslachtsnaamstam: Jansen
- Geslachtsaanduiding: M
- Persoon.Bijhouding.Partijcode: 62601


Zoeken op Adelijke titel: Hertog, geslachtsnaam: Vanaf Jan en Geslachtsaanduiding: M, Persoon.Bijhouding.Partijcode: 62601

Scenario: 6. Willem voldoet aan zoekcriteria en bijhoudingspartijcode

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Willem_Jansen_Breda_Zoekpaden.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Zoekpaden_Top_20/Requests/Zoek_Persoon_Story_6.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 1 groepen 'persoon'
Then is er voor xpath //brp:samengesteldeNaam[brp:geslachtsnaamstam = 'Jansen'] een node aanwezig in het antwoord bericht


