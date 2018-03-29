Meta:
@status             Klaar
@sleutelwoorden     Zoekpaden
@regels             ZoekPersoon

Narrative:
Jacobi:
- Postcode: 1234AB
- Huisnummer: 17
- Voornaam: Moh
- NaamOpenbareRuimte: Agaatstraat
- BijhoudingspartijCode: 62601

Jan Jansen:
- Postcode: 2252EB
- Huisnummer: 16
- Voornaam: Jan
- NaamOpenbareRuimte:Baron Schimmelpenninck van der Oyelaan

Scenario 5:
Zoeken op Postcode:1234AB, Huisnummer: 17, Huisletter: Leeg, Huisnummertoevoeging: Leeg, historisch?: Nee

Scenario 5a:
Zoeken op Postcode:1234AB, Huisnummer: 17, Huisletter: Leeg, Huisnummertoevoeging: Leeg, Voornamen: Vanf Moh, historisch?: Nee

Scenario 5b:
Zoeken op BijhoudingspartijCode: 62601, NaamOpenbareRuimte: Agaatstraat, Huisnummer: 17


Scenario: 5a. Jacobi voldoet aan adres, huisnummer en voornamen, jan wordt ingeladen om extra persoon te hebben
             Verwacht resultaat: Jacobi geleverd.

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jacobi_Zoekpaden.xls
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Zoekpaden_Top_20/Requests/Zoek_Persoon_Story_5a.xml
Then heeft het antwoordbericht verwerking Geslaagd

Then heeft het antwoordbericht 1 groepen 'persoon'
Then is er voor xpath //brp:samengesteldeNaam[brp:geslachtsnaamstam = 'Jacobi'] een node aanwezig in het antwoord bericht

Scenario: 5b. Jacobi voldoet aan adres, huisnummer en voornamen, en bijhoudingspartijcode
             Verwacht resultaat: Jacobi geleverd.

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jacobi_Zoekpaden.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Zoekpaden_Top_20/Requests/Zoek_Persoon_Story_5b.xml
Then heeft het antwoordbericht verwerking Geslaagd

Then heeft het antwoordbericht 1 groepen 'persoon'
Then is er voor xpath //brp:samengesteldeNaam[brp:geslachtsnaamstam = 'Jacobi'] een node aanwezig in het antwoord bericht



