Meta:
@status             Klaar
@usecase            BV.0.ZP
@sleutelwoorden     Zoek Persoon
@regels             R2297

Narrative:
Zoek persoon op attributen met alleen formele historie

Scenario: 1.    Zoek persoon Jan op basis van aangepaste geboortedatum (van 1960-08-21 naar 1966-08-21)
                Verwacht resultaat:
                - Zoek persoon geslaagd, maar geen persoon gevonden
                Uitwerking:
                - Bericht.Peilmoment materieel <> systeemdatum, namelijk LEEG
                - Zoekbereik <> Materiele periode, namelijk LEEG
                - Zoeken op 'Persoon.Geboorte.Datum' = 1960-08-21
                - historiepatroon.Zoekcriterium.Element = Formele Historie

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan_aangepaste_geboortedatum.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2297_1.xml

Then heeft het antwoordbericht verwerking Geslaagd

Then heeft het antwoordbericht 0 groepen 'persoon'

Then is er voor xpath //brp:voornamen[text()='Jan'] geen node aanwezig in het antwoord bericht
Then is er voor xpath //brp:geslachtsnaamstam[text()='Jansen'] geen node aanwezig in het antwoord bericht

Scenario: 2.   Zoek persoon Jan op basis van aangepaste geboortedatum (van 1960-08-21 naar 1966-08-21)
                Verwacht resultaat:
                - Zoek persoon geslaagd, en persoon gevonden
                Uitwerking:
                - Bericht.Peilmoment materieel <> systeemdatum, namelijk LEEG
                - Zoekbereik <> Materiele periode, namelijk LEEG
                - Zoeken op 'Persoon.Geboorte.Datum' = 1966-08-21
                - historiepatroon.Zoekcriterium.Element = Formele Historie

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan_aangepaste_geboortedatum.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2297_2.xml

Then heeft het antwoordbericht verwerking Geslaagd

Then heeft het antwoordbericht 1 groepen 'persoon'

Then is er voor xpath //brp:voornamen[text()='Jan'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:geslachtsnaamstam[text()='Jansen'] een node aanwezig in het antwoord bericht

Scenario: 3.    Zoek persoon Jan op basis van aangepaste geboortedatum (van 1960-08-21 naar 1966-08-21)
                Verwacht resultaat:
                - Zoek persoon geslaagd, maar geen persoon gevonden
                Uitwerking:
                - Bericht.Peilmoment materieel = 2010-01-01
                - Zoekbereik = Materiele periode
                - Zoeken op 'Persoon.Geboorte.Datum' = 1960-08-21
                - historiepatroon.Zoekcriterium.Element = Formele Historie

!-- Materieel peilmoment heeft geen invloed

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan_aangepaste_geboortedatum.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2297_3.xml

Then heeft het antwoordbericht verwerking Geslaagd

Then heeft het antwoordbericht 0 groepen 'persoon'

Then is er voor xpath //brp:voornamen[text()='Jan'] geen node aanwezig in het antwoord bericht
Then is er voor xpath //brp:geslachtsnaamstam[text()='Jansen'] geen node aanwezig in het antwoord bericht

Scenario: 4.    Zoek persoon Jan op basis van aangepaste geboortedatum (van 1960-08-21 naar 1966-08-21)
                Verwacht resultaat:
                - Zoek persoon geslaagd, maar geen persoon gevonden
                Uitwerking:
                - Bericht.Peilmoment materieel = 1962-01-01
                - Zoekbereik = Peilmoment
                - Zoeken op 'Persoon.Geboorte.Datum' = 1960-08-21
                - historiepatroon.Zoekcriterium.Element = Formele Historie
                - Geboortedatum 1960-08-21 record heeft een tsverval en komt niet terug in de zoekresultaten.

!-- Materieel peilmoment heeft geen invloed

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan_aangepaste_geboortedatum.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2297_4.xml

Then heeft het antwoordbericht verwerking Geslaagd

Then heeft het antwoordbericht 0 groepen 'persoon'

Then is er voor xpath //brp:voornamen[text()='Jan'] geen node aanwezig in het antwoord bericht
Then is er voor xpath //brp:geslachtsnaamstam[text()='Jansen'] geen node aanwezig in het antwoord bericht



Scenario: 5.    Zoek persoon Jan op basis van aangepaste achternaam (van 1960-08-21 tot 1966-08-21 Jansen, daarna Willems)
                Verwacht resultaat:
                - Zoek persoon geslaagd, maar geen persoon gevonden
                Uitwerking:
                - Bericht.Peilmoment materieel <> systeemdatum, namelijk LEEG
                - Zoekbereik <> Materiele periode, namelijk LEEG
                - Zoeken op 'Persoon.SamengesteldeNaam.Geslachtsnaamstam' = Jansen
                - historiepatroon.Zoekcriterium.Element = Formele Materiele Historie

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan_aangepaste_achternaam.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2297_5.xml

Then heeft het antwoordbericht verwerking Geslaagd

Then heeft het antwoordbericht 0 groepen 'persoon'

Then is er voor xpath //brp:voornamen[text()='Jan'] geen node aanwezig in het antwoord bericht
Then is er voor xpath //brp:geslachtsnaamstam[text()='Willems'] geen node aanwezig in het antwoord bericht

Scenario: 6.   Zoek persoon Jan op basis van aangepaste achternaam (van 1960-08-21 tot 1966-08-21 Jansen, daarna Willems)
                Verwacht resultaat:
                - Zoek persoon geslaagd, en persoon gevonden
                Uitwerking:
                - Bericht.Peilmoment materieel <> systeemdatum, namelijk LEEG
                - Zoekbereik <> Materiele periode, namelijk LEEG
                - Zoeken op 'Persoon.SamengesteldeNaam.Geslachtsnaamstam' = Willems
                - historiepatroon.Zoekcriterium.Element = Formele Materiele Historie

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan_aangepaste_achternaam.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2297_6.xml

Then heeft het antwoordbericht verwerking Geslaagd

Then heeft het antwoordbericht 1 groepen 'persoon'

Then is er voor xpath //brp:voornamen[text()='Jan'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:geslachtsnaamstam[text()='Willems'] een node aanwezig in het antwoord bericht

Scenario: 7.1   Zoek persoon Jan op basis van aangepaste achternaam (van 1960-08-21 tot 1966-08-21 Jansen, daarna Willems)
                Verwacht resultaat:
                - Zoek persoon geslaagd, maar geen persoon gevonden
                Uitwerking:
                - Bericht.Peilmoment materieel = 2010-01-01
                - Zoekbereik = Materiele periode
                - Zoeken op 'Persoon.SamengesteldeNaam.Geslachtsnaamstam' = Willems
                - historiepatroon.Zoekcriterium.Element = Formele Materiele Historie

!-- Materieel peilmoment heeft WEL invloed

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan_aangepaste_achternaam.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2297_7.1.xml

Then heeft het antwoordbericht verwerking Geslaagd

Then heeft het antwoordbericht 1 groepen 'persoon'

Then is er voor xpath //brp:voornamen[text()='Jan'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:geslachtsnaamstam[text()='Willems'] een node aanwezig in het antwoord bericht

Scenario: 7.2   Zoek persoon Jan op basis van aangepaste achternaam (van 1960-08-21 tot 1966-08-21 Jansen, daarna Willems)
                Verwacht resultaat:
                - Zoek persoon geslaagd, maar geen persoon gevonden
                Uitwerking:
                - Bericht.Peilmoment materieel = 2010-01-01
                - Zoekbereik = Materiele periode
                - Zoeken op 'Persoon.SamengesteldeNaam.Geslachtsnaamstam' = Jansen
                - historiepatroon.Zoekcriterium.Element = Formele Materiele Historie

!-- Materieel peilmoment heeft WEL invloed

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan_aangepaste_achternaam.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2297_7.2.xml

Then heeft het antwoordbericht verwerking Geslaagd

Then heeft het antwoordbericht 1 groepen 'persoon'

Then is er voor xpath //brp:voornamen[text()='Jan'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:geslachtsnaamstam[text()='Willems'] een node aanwezig in het antwoord bericht

Scenario: 7.3   Zoek persoon Jan op basis van aangepaste achternaam (van 1960-08-21 tot 1966-08-21 Jansen, daarna Willems)
                Verwacht resultaat:
                - Zoek persoon geslaagd, maar geen persoon gevonden
                Uitwerking:
                - Bericht.Peilmoment materieel = 1964-01-01
                - Zoekbereik = Materiele periode
                - Zoeken op 'Persoon.SamengesteldeNaam.Geslachtsnaamstam' = Willems
                - historiepatroon.Zoekcriterium.Element = Formele Materiele Historie

!-- Materieel peilmoment heeft WEL invloed

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan_aangepaste_achternaam.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2297_7.3.xml

Then heeft het antwoordbericht verwerking Geslaagd

Then heeft het antwoordbericht 0 groepen 'persoon'


Scenario: 8.1   Zoek persoon Jan op basis van aangepaste achternaam (van 1960-08-21 tot 1966-08-21 Jansen, daarna Willems)
                Verwacht resultaat:
                - Zoek persoon geslaagd, maar geen persoon gevonden
                Uitwerking:
                - Bericht.Peilmoment materieel = 1962-01-01
                - Zoekbereik = Peilmoment
                - Zoeken op 'Persoon.SamengesteldeNaam.Geslachtsnaamstam' = Willems
                - historiepatroon.Zoekcriterium.Element = Formele Materiele Historie

!-- Materieel peilmoment heeft WEL invloed

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan_aangepaste_achternaam.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2297_8.1.xml

Then heeft het antwoordbericht verwerking Geslaagd

Then heeft het antwoordbericht 0 groepen 'persoon'

Scenario: 8.2   Zoek persoon Jan op basis van aangepaste achternaam (van 1960-08-21 tot 1966-08-21 Jansen, daarna Willems)
                Verwacht resultaat:
                - Zoek persoon geslaagd, persoon gevonden
                Uitwerking:
                - Bericht.Peilmoment materieel = 1960-08-22
                - Zoekbereik = Peilmoment
                - Zoeken op 'Persoon.SamengesteldeNaam.Geslachtsnaamstam' = Jansen
                - historiepatroon.Zoekcriterium.Element = Formele Materiele Historie
                - Jan_met_aangepaste_en_vervallen_achternaam heeft een tsverval mag niet als resultaat terug komen.

!-- Materieel peilmoment heeft WEL invloed

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan_aangepaste_achternaam.xls
Given enkel initiele vulling uit bestand /LO3PL/Jan_aangepaste_en_vervallen_achternaam.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2297_8.2.xml

Then heeft het antwoordbericht verwerking Geslaagd

Then heeft het antwoordbericht 1 groepen 'persoon'

Then is er voor xpath //brp:voornamen[text()='Jan'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:geslachtsnaamstam[text()='Willems'] een node aanwezig in het antwoord bericht