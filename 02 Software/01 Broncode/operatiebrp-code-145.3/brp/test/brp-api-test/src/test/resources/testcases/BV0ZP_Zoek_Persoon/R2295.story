Meta:
@status             Klaar
@usecase            BV.0.ZP
@sleutelwoorden     Zoek Persoon
@regels             R2295
@versie             1

Narrative:
Indien gevuld dan mag Bericht.Peilmoment materieel niet na 'Systeemdatum' (R2016) liggen.

Scenario: 1. Zoeken met Bericht.Peilmoment materieel = LEEG
            LT: R2295_LT01
            Verwacht resultaat: Geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 2. Zoeken met Bericht.Peilmoment materieel < systeemdatum
            LT: R2295_LT02
            Verwacht resultaat: Geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|peilmomentMaterieel|gisteren

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 3. Zoeken met Bericht.Peilmoment materieel = systeemdatum
            LT: R2295_LT03
            Verwacht resultaat: Geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|peilmomentMaterieel|vandaag

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 4. Zoeken met Bericht.Peilmoment materieel > systeemdatum
            LT: R2295_LT04
            Verwacht resultaat: Geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|peilmomentMaterieel|morgen

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                |
| R2295     | Peilmoment materieel mag niet in de toekomst liggen.   |