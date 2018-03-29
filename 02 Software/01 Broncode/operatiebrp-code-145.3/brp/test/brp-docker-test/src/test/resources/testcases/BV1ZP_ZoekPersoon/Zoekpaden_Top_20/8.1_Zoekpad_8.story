Meta:
@status             Klaar
@sleutelwoorden     Zoekpaden
@regels             ZoekPersoon

Narrative:
Willem Jansen:
- Adelijke Titel: H
- Geslachtsaanduiding: M
- geslachtsnaamstam: Jansen
- Adres afgekorte naam: Agaatstraat
- Huisnummer: 17
- Woonplaatsnam Breda
- LocatietenopzichteVanAdres: AB

Scenario 8:
Zoeken op - geslachtsnaamstam: Jansen
          - Adres afgekorte naam: Agaatstraat (Exact)
          - Huisnummer: 17
          - Woonplaatsnaam Breda
          - Huisletter: Leeg
          - LocatietenopzichteVanAdres: to


Scenario: 1. Willem Jansen zoeken

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Willem_Jansen_Breda_Zoekpaden.xls
Given de database is aangepast met: update kern.persadres set loctenopzichtevanadres='to' where wplnaam = 'Breda'

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Zoekpaden_Top_20/Requests/Zoek_Persoon_Story_8.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 1 groepen 'persoon'
Then is er voor xpath //brp:samengesteldeNaam[brp:geslachtsnaamstam = 'Jansen'] een node aanwezig in het antwoord bericht
