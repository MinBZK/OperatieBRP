Meta:
@status             Klaar
@usecase            BV.0.ZP
@sleutelwoorden     Zoek Persoon
@regels             R2402

Narrative:
Hoe er historisch gezocht wordt is afhankelijk van de parameters uit het verzoekbericht:

Indien Bericht.Zoekbereik = 'Peilmoment' (of leeg) en Bericht.Peilmoment materieel is leeg:
Dan zoeken naar de actuele gegevens ('A laag').

Indien Bericht.Zoekbereik = 'Peilmoment' (of leeg) en Bericht.Peilmoment materieel heeft een waarde:
Dan zoeken naar gegevens zoals ze geldig waren op de opgegeven datum.

Indien Bericht.Zoekbereik = 'Materiele periode' en Bericht.Peilmoment materieel is leeg:
Dan zoeken naar geldigheid op een willekeurig moment.

Indien Bericht.Zoekbereik = 'Materiele periode' en Bericht.Peilmoment materieel heeft een waarde:
Dan zoeken naar gegevens zoals ze geldig waren 'op of voor' de opgegeven datum.

Waarbij geldt dat:

Alleen niet-vervallen voorkomens worden beschouwd

Groepen die geen materieel historiepatroon hebben materieel gezien altijd geldig zijn

Zijn er zoekcriteriums met elementen die deel uitmaken van een en dezelfde groep,
dan moet er een corresponderend groepsvoorkomen zijn waarbij de verschillende waarden aan elkaar gelijk zijn.

Er moet tenminste één datum bestaan in het opgegeven zoekbereik waarop minsten één zoekcriterium WAAR is.
Er hoeft dus geen overlap te zijn in de geldigheidsperioden van de groepen waar een match is.

Scenario: 1.a   PeilmomentMaterieel leeg en Zoekbereik leeg
                LT: R2402_LT01
                Verwacht resultaat:
                Anne met Historie wordt geleverd wordt gevonden op actueel adres Uithoorn
                Uitwerking:
                Anne met Historie wordt ingeladen, Anne heeft achternaam Bakker
                Anne heeft woonplaatsen Standaard(1976-04-01-20100101), 's-Gravenhage(20100101-20150101), Uithoorn(20150101-huidig)
                zoekopdracht op Achternaam en woonplaats Bakker en woonplaats Uithoorn

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Anne_met_Historie.xls
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls
Given enkel initiele vulling uit bestand /LO3PL/Jimmy_van_Loon_vervallenAdres.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/R2402/Zoek_Persoon_R2402_1a.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 1 groepen 'persoon'
Then heeft in het antwoordbericht 'burgerservicenummer' in 'identificatienummers' de waarde '590984809'

Scenario: 1b.   PeilmomentMaterieel leeg en Zoekbereik leeg
                LT: R2402_LT01
                Verwacht resultaat:
                Anne met Historie wordt geleverd wordt NIET gevonden op actueel adres Utrecht
                Uitwerking:
                Anne met Historie wordt ingeladen, Anne heeft achternaam Bakker
                Anne heeft woonplaatsen Standaard(1976-04-01-20100101), 's-Gravenhage(20100101-20150101), Uithoorn(20150101-huidig)
                zoekopdracht op Achternaam Bakker en woonplaats Utrecht

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/R2402/Zoek_Persoon_R2402_1b.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
!-- Utecht is NIET het huidige adres zou niet terug moeten komen is niet geldig.
Then is er voor xpath //brp:naam[text()='Personen'] geen node aanwezig in het antwoord bericht

Scenario: 2.a   Zoekbereik is peilmoment, peilmoment materieel is Leeg
                LT: R2402_LT02
                Verwacht resultaat:
                - Anne wordt gevonden
                Uitwerking:
                zoekopdracht op Achternaam Bakker en woonplaats Uithoorn
                Anne Bakker heeft woonplaatsen Utrecht(1976-04-01-20100101), 's-Gravenhage(20100101-20150101), Uithoorn(20150101-huidig)


Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/R2402/Zoek_Persoon_R2402_2a.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 1 groepen 'persoon'
Then heeft in het antwoordbericht 'burgerservicenummer' in 'identificatienummers' de waarde '590984809'

Scenario: 2.b   Zoekbereik is peilmoment, peilmoment materieel is Leeg
                LT: R2402_LT02
                Verwacht resultaat:
                - Anne wordt niet gevonden, omdat het actuele adres woonplaats 'Uithoorn' is en niet 'Utrecht'
                Uitwerking:
                zoekopdracht op Achternaam Bakker en woonplaats Utrecht
                Anne Bakker heeft woonplaatsen Utrecht(1976-04-01-20100101), 's-Gravenhage(20100101-20150101), Uithoorn(20150101-huidig)


Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/R2402/Zoek_Persoon_R2402_2b.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 0 groepen 'persoon'

Scenario: 3.a   Zoekbereik is Leeg, peilmoment materieel is Gevuld 2016-01-01
                LT: R2402_LT03
                Verwacht resultaat:
                - Anne wordt gevonden
                Uitwerking:
                zoekopdracht op Achternaam Bakker en woonplaats Uithoorn
                Anne heeft woonplaatsen Standaard(1976-04-01-20100101), 's-Gravenhage(20100101-20150101), Uithoorn(20150101-huidig)

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/R2402/Zoek_Persoon_R2402_3a.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 1 groepen 'persoon'
Then heeft in het antwoordbericht 'burgerservicenummer' in 'identificatienummers' de waarde '590984809'

Scenario: 3.b   Zoekbereik is Leeg, peilmoment materieel is Gevuld 2012-01-01
                LT: R2402_LT03
                Verwacht resultaat:
                - Anne wordt NIET gevonden
                Uitwerking:
                zoekopdracht op Achternaam Bakker en woonplaats Uithoorn
                Anne heeft woonplaatsen Utrecht(1976-04-01-20100101), 's-Gravenhage(20100101-20150101), Uithoorn(20150101-huidig)

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/R2402/Zoek_Persoon_R2402_3b.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 0 groepen 'persoon'


Scenario: 4.a   Zoekbereik is peilmoment, peilmoment materieel is Gevuld 2016-01-01
                LT: R2402_LT04
                Verwacht resultaat:
                - Anne wordt gevonden
                Uitwerking:
                zoekopdracht op Achternaam en woonplaats Bakker en woonplaats Uithoorn
                Anne heeft woonplaatsen Standaard(1976-04-01-20100101), 's-Gravenhage(20100101-20150101), Uithoorn(20150101-huidig)

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/R2402/Zoek_Persoon_R2402_4a.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 1 groepen 'persoon'
Then heeft in het antwoordbericht 'burgerservicenummer' in 'identificatienummers' de waarde '590984809'

Scenario: 4.b   Zoekbereik is peilmoment, peilmoment materieel is Gevuld 2012-01-01
                LT: R2402_LT04
                Verwacht resultaat:
                - Anne wordt NIET gevonden
                Uitwerking:
                zoekopdracht op Achternaam en woonplaats Bakker en woonplaats Uithoorn
                Anne heeft woonplaatsen Utrecht(1976-04-01-20100101), 's-Gravenhage(20100101-20150101), Uithoorn(20150101-huidig)

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/R2402/Zoek_Persoon_R2402_4b.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 0 groepen 'persoon'

!-- Zijn er zoekcriteriums met elementen die deel uitmaken van een en dezelfde groep,
!-- dan moet er een corresponderend groepsvoorkomen zijn waarbij de verschillende waarden aan elkaar gelijk zijn.
Scenario: 5.a   Zoekbereik Materiele Periode en PeilmomentMaterieel Leeg
                LT: R2402_LT05
                Verwacht resultaat:
                Anne met Historie wordt NIET geleverd omdat met Zoekbereik 'Materiele Periode' wordt gezocht naar
                elementen die ooit, niet simultaan, waar zijn geweest, tenzij ze tot dezelfde groep behoren, wat hier het geval is.
                Uitwerking:
                Zoekopdracht op Huisnummer 43 en woonplaats Uithoorn met peilmoment Leeg
                Anne heeft woonplaatsen
                Utrecht         (1976-04-01-20100101),      met huisnummer 11
                's-Gravenhage   (20100101-20150101),        met huisnummer 43
                Uithoorn        (20150101-huidig),          met huisnummer 157

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/R2402/Zoek_Persoon_R2402_5a.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 0 groepen 'persoon'

!-- Er moet tenminste één datum bestaan in het opgegeven zoekbereik waarop minsten één zoekcriterium WAAR is.
!-- Er hoeft dus geen overlap te zijn in de geldigheidsperioden van de groepen waar een match is.
Scenario: 5.b   Zoekbereik Materiele Periode en PeilmomentMaterieel Leeg
                LT: R2402_LT05
                Verwacht resultaat:
                Anne met Historie wordt geleverd omdat met Zoekbereik 'Materiele Periode' wordt gezocht naar
                elementen die ooit, niet simultaan, waar zijn geweest, tenzij ze tot dezelfde groep behoren, wat hier NIET het geval is.
                Uitwerking:
                Zoekopdracht op woonplaats Utrecht en GerelateerdeKind BSN 823826697 met peilmoment Leeg
                Anne heeft woonplaatsen
                Utrecht         (1976-04-01-20100101),      met huisnummer 11
                's-Gravenhage   (20100101-20150101),        met huisnummer 43
                Uithoorn        (20150101-huidig),          met huisnummer 157
                Anne heeft kinderen
                Sanne           (20150101 - heden)          met BSN 823826697

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/R2402/Zoek_Persoon_R2402_5b.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 1 groepen 'persoon'

Scenario: 6.a   Zoekbereik Materiele Periode en PeilmomentMaterieel Gevuld met 01-01-2012
                LT: R2402_LT06
                Verwacht resultaat:
                Anne met Historie wordt NIET geleverd omdat met Zoekbereik 'Materiele Periode' wordt gezocht naar
                elementen die ooit, niet simultaan, waar zijn geweest voor 01-01-2012
                Uitwerking:
                Zoekopdracht op woonplaats Utrecht en GerelateerdeKind BSN 823826697 met peilmoment Leeg
                Anne heeft woonplaatsen
                Utrecht         (1976-04-01-20100101),      met huisnummer 11
                's-Gravenhage   (20100101-20150101),        met huisnummer 43
                Uithoorn        (20150101-huidig),          met huisnummer 157
                Anne heeft kinderen
                Sanne           (20150101 - heden)          met BSN 823826697

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/R2402/Zoek_Persoon_R2402_6a.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

Then is er voor xpath //brp:naam[text()='Personen'] geen node aanwezig in het antwoord bericht


!-- Alleen niet-vervallen voorkomens worden beschouwd
Scenario: 6.b   PeilmomentMaterieel datum 19961101 en Zoekbereik Materiele Periode
                LT: R2402_LT06
                Verwacht resultaat:
                - Jimmy van Loon wordt niet geleverd omdat met Zoekbereik 'Materiele Periode' wordt gezocht naar niet vervallen voorkomens
                Uitwerking:
                Jimmy van Loon  wordt ingeladen
                zoekopdracht op Achternaam 'Loon' en adresregelbuiteland2 'Toronto' met peilmoment 19961101 beide waar maar voorkomens zijn vervallen

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/R2402/Zoek_Persoon_R2402_6b.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
!-- Zoeken op achternaam 'Loon' en adresregelbuiteland 'Toronto' beide zijn waar maar vervallen, dit wordt niet geleverd.
Then is er voor xpath //brp:naam[text()='Personen'] geen node aanwezig in het antwoord bericht

Scenario: 7 Historisch zoeken met zoekBereik= Peilmoment en Peilmoment materieel gevuld op element van groep met enkel formele historie
            LT: R2402_LT06
            Persoon heeft voorkomen van groep met enkel formele historie (Geboorte)
            Er wordt met een Materiele Periode en peilmoment gezocht op een moment van voor de tsreg van de geboorte (tsverval = null)
            Persoon wordt gevonden, want groepen met formele historie zijn altijd geldig.


Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/R2402/Zoek_Persoon_R2402_7.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 1 groepen 'persoon'

Scenario: 8 Historisch zoeken met zoekBereik= Peilmoment en Peilmoment materieel gevuld op vervallen groep met enkel formele historie
            LT: R2402_LT06
            Vervallen voorkomens buiten beschouwing van de zoekopdracht laten
            Persoon heeft vervallen voorkomen van groep met enkel formele historie (Geboorte)
            Er wordt met een Materiele Periode en peilmoment gezocht op een waarde van de vervallen geboorte datum
            Persoon wordt niet gevonden, want vervallen voorkomens worden buiten beschouwing gelaten
            en de geboortedatum in de zoekopdracht komt niet overeen met de actuele waarde

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan_aangepaste_geboortedatum.xls
Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/R2402/Zoek_Persoon_R2402_8.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 0 groepen 'persoon'

Scenario: 9 Peilmoment en zoekbereik zijn beide leeg
            LT: R2402_LT01
            Persoon wordt gevonden voor actuele verstrekkingsbeperking
            Er wordt met een zoekopdracht uitgevoerd zonder peilmoment en zoek bereik
            Persoon wordt gevonden, want actueel voorkomen van volledige verstrekkingsbeperking

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/R1983/Rick_volledige_verstrekkingsbeperking.xls
Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/R2402/Zoek_Persoon_R2402_9.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 1 groepen 'persoon'

