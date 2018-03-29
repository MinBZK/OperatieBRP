Meta:
@status             Klaar
@usecase            BV.0.ZA
@sleutelwoorden     Zoek persoon op adres
@regels             R1274

Narrative:
De waarde van datum moet in de combinatie van jaar, maand en dag geldig zijn binnen de Gregoriaanse kalender.

Scenario: 1.    Peilmoment materieel is 29 februari, Geen Schrikkeljaar, Dienst Zoek Persoon op adres
                LT: R1274_LT71
                Verwacht resulaat:
                - Zoek Persoon op adres verwerking foutief

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_op_adres
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Op Adres'
|zendendePartijNaam|'Gemeente Utrecht'
|dienstId|2
|peilmomentMaterieel|'2015-02-29'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Postcode,Waarde=3512AE


Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1274     | De opgegeven datum is geen geldige kalenderdatum.
