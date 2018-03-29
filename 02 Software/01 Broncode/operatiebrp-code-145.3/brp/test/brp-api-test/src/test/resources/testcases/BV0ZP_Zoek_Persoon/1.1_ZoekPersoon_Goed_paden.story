Meta:
@status             Klaar
@usecase            BV.0.ZP
@sleutelwoorden     Zoek Persoon


Narrative:
Als afnemer
Wil ik een bevraging kunnen doen via de service ZoekPersoon
met verschillende zoekcriteria
Zodat ik een response bericht krijg met de personen die hieraan voldoen

Persoon (Piet):
BSN: 159247913
Geboorte datum: 1960-08-21
Adres:
Baron Schimmelpenninck van der Oyelaan 16
2252EB Voorschoten

Scenario: 1.    Goedpad Zoek Persoon op burger service nummer, zoekcriterium is geautoriseerd element.
                LT: R1262_LT21, R1264_LT17, R2056_LT17, R2290_LT01, R2054_LT03, R2055_LT03, R2389_LT03, R1983_LT10, R2055_LT05
                Verwacht Resultaat:
                - Persoon met BSN 159247913 gevonden

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801

Then heeft het antwoordbericht verwerking Geslaagd


Scenario: 2.    Goedpad Zoek Persoon op Geboorte datum
                LT:
                Verwacht Resultaat:
                - Persoon met geboorte datum 1960-08-21 gevonden

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Geboorte.Datum,Waarde=1960-08-21

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 3.    Goedpad Zoek Persoon op Huisnummer
                LT:
                Verwacht Resultaat:
                - Persoon met huisnummer 16 gevonden

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Huisnummer,Waarde=16
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.SamengesteldeNaam.Geslachtsnaamstam,Waarde=Jan

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 4.    Goedpad Zoek Persoon op BSN die niet voorkomt
                LT:
                Verwacht Resultaat:
                - Persoon met BSN niet gevonden

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=111111110

Then heeft het antwoordbericht verwerking Geslaagd


Scenario:   5. Historisch zoeken zonder autorisatie
            LT:
            Verwacht resultaat:
            - Geslaagd: Historisch zoeken mag nu ook het ontbreken bij autorisatie op de materiële historie van de opgegeven zoekcriteria
            Uitwerking:
            - Zoek persoon Jan op basis van burgerservicenummer
            - Regel 2297 is vervallen historisch zoeken mag nu altijd
            - Bericht.Peilmoment materieel <> systeemdatum
            - Zoekbereik <> Materiele periode, namelijk Peilmoment
            - historiepatroon Zoekcriterium.Element <> 'Formele historie en materiële historie' en 'Formele historie en materiële bestaansperiode
            namelijk Geen historie
            - Autorisatie materiële historie Dienstbundel \ Groep.Materiële historie? = Ja
            - Zoeken op 'Persoon.Identificatienummers.Burgerservicenummer' = 606417801
            - historiepatroon.Zoekcriterium.Element = Geen Historie

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/Zoek_Persoon_Geen_Materiele_historie
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekbereik|Peilmoment
|peilmomentMaterieel|2016-01-01
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801


Then heeft het antwoordbericht verwerking Geslaagd
