Meta:
@status             Klaar
@usecase            BV.0.ZP
@sleutelwoorden     Zoek Persoon
@regels             R2297

Narrative:
Als er sprake is van een historische zoekvraag:
Bericht.Peilmoment materieel is gevuld en ongelijk aan 'Systeemdatum' (R2016)
OF
Zoekbereik heeft de waarde 'Materiele Periode'


Dan moet voor alle zoekcriteria in de zoekvraag gelden dat:

Het element heeft geen materiele historie: Zoekcriterium.Element is onderdeel van een Groep met
historiepatroon ongelijk aan 'Formele historie en materiële historie' en 'Formele historie en materiële bestaansperiode'

OF

Er bestaat autorisatie voor de materiele historie op het Element: Dienstbundel \ Groep.Materiële historie?
die hoort bij het betreffende Element en Dienstbundel is gelijk aan 'Ja'.

Scenario: 1.    Zoek persoon Jan op basis van burgerservicenummer
                LT: R2297_LT01
                Verwacht resultaat:
                - Zoek persoon geslaagd
                Uitwerking:
                - Bericht.Peilmoment materieel <> systeemdatum
                - Zoekbereik <> Materiele periode, namelijk Peilmoment
                - historiepatroon Zoekcriterium.Element <> 'Formele historie en materiële historie' en 'Formele historie en materiële bestaansperiode
                namelijk Geen historie
                - Autorisatie materiële historie Dienstbundel \ Groep.Materiële historie? = Ja
                - Zoeken op 'Persoon.Identificatienummers.Burgerservicenummer' = 606417801
                - historiepatroon.Zoekcriterium.Element = Geen Historie

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2297_9.xml

Then heeft het antwoordbericht verwerking Geslaagd

Then heeft het antwoordbericht 1 groepen 'persoon'

Then is er voor xpath //brp:voornamen[text()='Jan'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:geslachtsnaamstam[text()='Jansen'] een node aanwezig in het antwoord bericht

Scenario: 2.    Zoek persoon Jan op basis van burgerservicenummer
                LT: R2297_LT03
                Verwacht resultaat:
                - Zoek persoon geslaagd
                Uitwerking:
                - Bericht.Peilmoment materieel <> systeemdatum, namelijk LEEG
                - Zoekbereik = Materiele periode
                - historiepatroon Zoekcriterium.Element <> 'Formele historie en materiële historie' en 'Formele historie en materiële bestaansperiode
                namelijk Geen historie
                - Autorisatie materiële historie Dienstbundel \ Groep.Materiële historie? = Ja
                - Zoeken op 'Persoon.Identificatienummers.Burgerservicenummer' = 606417801
                - historiepatroon.Zoekcriterium.Element = Geen Historie

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2297_10.xml

Then heeft het antwoordbericht verwerking Geslaagd

Then heeft het antwoordbericht 1 groepen 'persoon'

Then is er voor xpath //brp:voornamen[text()='Jan'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:geslachtsnaamstam[text()='Jansen'] een node aanwezig in het antwoord bericht

Scenario: 3.   Zoek persoon Jan op basis van burgerservicenummer
                LT: R2297_LT05
                Verwacht resultaat:
                - Zoek persoon geslaagd
                Uitwerking:
                - Bericht.Peilmoment materieel <> systeemdatum, namelijk LEEG
                - Zoekbereik <> Materiele periode, namelijk LEEG
                - historiepatroon Zoekcriterium.Element <> 'Formele historie en materiële historie' en 'Formele historie en materiële bestaansperiode
                namelijk Geen historie
                - Autorisatie materiële historie Dienstbundel \ Groep.Materiële historie? = Nee
                - Zoeken op 'Persoon.Identificatienummers.Burgerservicenummer' = 606417801
                - historiepatroon.Zoekcriterium.Element = Geen Historie

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon geen materiele historie' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2297_11.xml

Then heeft het antwoordbericht verwerking Geslaagd

Then heeft het antwoordbericht 1 groepen 'persoon'

Then is er voor xpath //brp:voornamen[text()='Jan'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:geslachtsnaamstam[text()='Jansen'] een node aanwezig in het antwoord bericht

Scenario: 4.   Zoek persoon Jan op basis van geboortedatum
                LT: R2297_LT05
                Verwacht resultaat:
                - Zoek persoon geslaagd
                Uitwerking:
                - Bericht.Peilmoment materieel <> systeemdatum, namelijk LEEG
                - Zoekbereik <> Materiele periode, namelijk LEEG
                - historiepatroon Zoekcriterium.Element <> 'Formele historie en materiële historie' en 'Formele historie en materiële bestaansperiode
                namelijk Formele Historie
                - Autorisatie materiële historie Dienstbundel \ Groep.Materiële historie? = Nee
                - Zoeken op 'Persoon.Geboorte.Datum' = 1960-08-21
                - historiepatroon.Zoekcriterium.Element = Formele Historie

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon geen materiele historie' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2297_12.xml

Then heeft het antwoordbericht verwerking Geslaagd

Then heeft het antwoordbericht 1 groepen 'persoon'

Then is er voor xpath //brp:voornamen[text()='Jan'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:geslachtsnaamstam[text()='Jansen'] een node aanwezig in het antwoord bericht

Scenario: 5.    Zoek persoon Jan op basis van achternaam
                LT: R2297_LT06
                Verwacht resultaat:
                - Zoek persoon geslaagd
                Uitwerking:
                - Bericht.Peilmoment materieel = 2016-01-01
                - Zoekbereik <> Materiele periode, namelijk Peilmoment
                - historiepatroon Zoekcriterium.Element = Formele historie en materiële historie
                - Autorisatie materiële historie Dienstbundel \ Groep.Materiële historie? = Ja
                - Zoeken op 'Persoon.SamengesteldeNaam.Geslachtsnaamstam' = Jansen


Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2297_13.xml
Then heeft het antwoordbericht verwerking Geslaagd

Then heeft het antwoordbericht 1 groepen 'persoon'

Then is er voor xpath //brp:voornamen[text()='Jan'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:geslachtsnaamstam[text()='Jansen'] een node aanwezig in het antwoord bericht

Scenario: 6.    Zoek persoon Jan op basis van achternaam
                LT: R2297_LT07
                Verwacht resultaat:
                - Zoek persoon geslaagd
                Uitwerking:
                - Bericht.Peilmoment materieel = LEEG
                - Zoekbereik = Materiele periode
                - historiepatroon Zoekcriterium.Element = Formele historie en materiële historie
                - Autorisatie materiële historie Dienstbundel \ Groep.Materiële historie? = Ja
                - Zoeken op 'Persoon.SamengesteldeNaam.Geslachtsnaamstam' = Jansen


Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2297_14.xml

Then heeft het antwoordbericht verwerking Geslaagd

Then heeft het antwoordbericht 1 groepen 'persoon'

Then is er voor xpath //brp:voornamen[text()='Jan'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:geslachtsnaamstam[text()='Jansen'] een node aanwezig in het antwoord bericht

Scenario: 7.   Zoek persoon Jan op basis van achternaam
                LT: R2297_LT10
                Verwacht resultaat:
                - Zoek persoon geslaagd
                Uitwerking:
                - Bericht.Peilmoment materieel = LEEG
                - Zoekbereik = LEEG
                - historiepatroon Zoekcriterium.Element = Formele historie en materiële historie
                - Autorisatie materiële historie Dienstbundel \ Groep.Materiële historie? = Nee
                - Zoeken op 'Persoon.SamengesteldeNaam.Geslachtsnaamstam' = Jansen


Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon geen materiele historie' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2297_15.xml

Then heeft het antwoordbericht verwerking Geslaagd

Then heeft het antwoordbericht 1 groepen 'persoon'

Then is er voor xpath //brp:voornamen[text()='Jan'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:geslachtsnaamstam[text()='Jansen'] een node aanwezig in het antwoord bericht