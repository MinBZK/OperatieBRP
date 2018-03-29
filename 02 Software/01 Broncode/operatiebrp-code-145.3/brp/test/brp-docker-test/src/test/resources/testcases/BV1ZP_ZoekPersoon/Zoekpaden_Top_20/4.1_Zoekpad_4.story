Meta:
@status             Klaar
@sleutelwoorden     Zoekpaden
@regels             ZoekPersoon

Narrative:
Zoeken op Geboortedatum:1976-04-01, geslachtsnaam: Vanaf Bak en woonplaats: Standaard, historisch?: Ja

Scenario: 1.    PeilmomentMaterieel leeg en Zoekbereik Materiele Periode
                LT:
                Anne met Historie wordt ingeladen, Anne heeft achternaam Bakker
                zoekopdracht op Geboortedatum 19760401 Achternaam Bak (vanaf) en woonplaats Standaard met peilmoment Leeg
                Anne heeft woonplaatsen Standaard(1976-04-01-20100101), 's-Gravenhage(20100101-20150101), Uithoorn(20150101-huidig)
                Verwacht resultaat:
                Anne met Historie wordt geleverd omdat met Zoekbereik 'Materiele Periode' wordt gezocht naar willekeurig geldig moment
                waarop alle zoekcriteria tegelijk waar zijn.

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Anne_met_Historie.xls
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Zoekpaden_Top_20/Requests/Zoek_Persoon_Story_4.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 1 groepen 'persoon'
Then heeft in het antwoordbericht 'burgerservicenummer' in 'identificatienummers' de waarde '590984809'

