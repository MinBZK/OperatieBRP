Meta:
@status             Klaar
@sleutelwoorden     Zoekpaden
@regels             ZoekPersoon

Narrative:
Jacobi:
- Geboortedatum: 22-08-1978
- geslachtsnaamstam: Jacobi
- Woonplaats: Drachten
Jacobiele:
- Geboortedatum: 22-08-1978
- geslachtsnaamstam: Jacobiele
- Woonplaats: Drachten
Jacoco:
- Geboortedatum: 22-08-1978
- geslachtsnaamstam: Jacoco
- Woonplaats: Drachten
Jan:
- Geboortedatum: 21-08-1960
- geslachtsnaamstam: Jansen
- Woonplaats: Voorschoten

Zoeken op Geboortedatum:22-08-1978, geslachtsnaam: Vanaf Jacobi en woonplaats: Drachten, Historisch?:Nee

Scenario: 1. Jacobi en Jacobiele hebben zelfde geboortedatum en woonplaats,Jan en Jacoco wordt ingeladen om extra persoon te hebben
             LT:
             Verwacht resultaat: Jacobi en Jacobiele geleverd. Dus 2 personen

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jacobi_Zoekpaden.xls
Given enkel initiele vulling uit bestand /LO3PL/Jacobiele_Zoekpaden.xls
Given enkel initiele vulling uit bestand /LO3PL/Jacoco_Zoekpaden.xls
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Zoekpaden_Top_20/Requests/Zoek_Persoon_Story_3.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 2 groepen 'persoon'
Then is er voor xpath //brp:samengesteldeNaam[brp:geslachtsnaamstam = 'Jacobi'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:samengesteldeNaam[brp:geslachtsnaamstam = 'Jacobiele'] een node aanwezig in het antwoord bericht




