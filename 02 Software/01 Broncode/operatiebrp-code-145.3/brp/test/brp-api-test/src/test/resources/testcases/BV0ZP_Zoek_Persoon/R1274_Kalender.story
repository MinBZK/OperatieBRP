Meta:

@status             Klaar
@usecase            BV.0.GD
@sleutelwoorden     Geef Details Persoon
@regels             R1274

Narrative:
De waarde van datum moet in de combinatie van jaar, maand en dag geldig zijn binnen de Gregoriaanse kalender.

Scenario: 1.    Peilmoment materieel is 28 februari, Schrikkeljaar, Dienst Zoek Persoon
                LT: R1274_LT57
                Verwacht resulaat:
                - Zoek Persoon geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|peilmomentMaterieel|'2016-02-28'

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 2.    Peilmoment materieel is 28 februari, GEEN Schrikkeljaar, Dienst Zoek Persoon
                LT: R1274_LT58
                Verwacht resulaat:
                - Zoek Persoon geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|peilmomentMaterieel|'2015-02-28'

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 3.    Peilmoment materieel is 29 februari, Schrikkeljaar, Dienst Zoek Persoon
                LT: R1274_LT59
                Verwacht resulaat:
                - Zoek Persoon geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|peilmomentMaterieel|'2016-02-29'

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 4.    Peilmoment materieel is 29 februari, GEEN Schrikkeljaar, Dienst Zoek Persoon
                LT: R1274_LT60
                Verwacht resulaat:
                - Foutief
                - De opgegeven datum is geen geldige kalenderdatum.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|peilmomentMaterieel|'2015-02-29'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1274     | De opgegeven datum is geen geldige kalenderdatum.

Scenario: 5.    Peilmoment materieel is 30 februari, Schrikkeljaar, Dienst Zoek Persoon
                LT: R1274_LT61
                Verwacht resulaat:
                - Foutief
                - De opgegeven datum is geen geldige kalenderdatum.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|peilmomentMaterieel|'2016-02-30'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1274     | De opgegeven datum is geen geldige kalenderdatum.

Scenario: 6.    Peilmoment materieel is 29 februari, Schrikkeljaar 2000 , Dienst Zoek Persoon
                LT: R1274_LT62
                Verwacht resulaat:
                - Zoek Persoon Geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|peilmomentMaterieel|'2000-02-29'

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 7.    Peilmoment materieel is 29 februari, GEEN Schrikkeljaar 1900, Dienst Zoek Persoon
                LT: R1274_LT63
                Verwacht resulaat:
                - Foutief
                - De opgegeven datum is geen geldige kalenderdatum.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|peilmomentMaterieel|'1900-02-29'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1274     | De opgegeven datum is geen geldige kalenderdatum.

Scenario: 8.    Peilmoment materieel is 31 april, GEEN Schrikkeljaar, Dienst Zoek Persoon
                LT: R1274_LT64
                Verwacht resulaat:
                - Foutief
                - De opgegeven datum is geen geldige kalenderdatum.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|peilmomentMaterieel|'2015-04-31'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1274     | De opgegeven datum is geen geldige kalenderdatum.

Scenario: 9.    Peilmoment materieel is 31 juni, GEEN Schrikkeljaar, Dienst Zoek Persoon
                LT: R1274_LT65
                Verwacht resulaat:
                - Foutief
                - De opgegeven datum is geen geldige kalenderdatum.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|peilmomentMaterieel|'2015-06-31'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1274     | De opgegeven datum is geen geldige kalenderdatum.

Scenario: 10.    Peilmoment materieel is 31 augustus, GEEN Schrikkeljaar, Dienst Zoek Persoon
                LT: R1274_LT66
                Verwacht resulaat:
                - Zoek Persoon Geslaagd

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|peilmomentMaterieel|'2015-08-31'

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 11.    Peilmoment materieel is 31 september, GEEN Schrikkeljaar, Dienst Zoek Persoon
                LT: R1274_LT67
                Verwacht resulaat:
                - Foutief
                - De opgegeven datum is geen geldige kalenderdatum.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|peilmomentMaterieel|'2015-09-31'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1274     | De opgegeven datum is geen geldige kalenderdatum.

Scenario: 12.    Peilmoment materieel is 31 november, GEEN Schrikkeljaar, Dienst Zoek Persoon
                LT: R1274_LT68
                Verwacht resulaat:
                - Foutief
                - De opgegeven datum is geen geldige kalenderdatum.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|peilmomentMaterieel|'2015-11-31'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1274     | De opgegeven datum is geen geldige kalenderdatum.

Scenario: 13.    Peilmoment materieel is 32 januari, GEEN Schrikkeljaar, Dienst Zoek Persoon
                LT: R1274_LT69
                Verwacht resulaat:
                - Foutief
                - De opgegeven datum is geen geldige kalenderdatum.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|peilmomentMaterieel|'2015-01-32'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1274     | De opgegeven datum is geen geldige kalenderdatum.

Scenario: 14.    Peilmoment materieel is 01-13-2015, GEEN Schrikkeljaar, Dienst Zoek Persoon
                LT: R1274_LT70
                Verwacht resulaat:
                - Foutief
                - De opgegeven datum is geen geldige kalenderdatum.

Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|peilmomentMaterieel|'2015-13-01'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1274     | De opgegeven datum is geen geldige kalenderdatum.
