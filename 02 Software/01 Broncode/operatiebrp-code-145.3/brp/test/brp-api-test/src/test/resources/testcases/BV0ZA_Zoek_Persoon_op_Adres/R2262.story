Meta:

@status             Klaar
@usecase            BV.0.ZA
@sleutelwoorden     Zoek Persoon op adresgegevens
@regels             R2262

Narrative:
R2262
Zoekresultaatberichten bevatten alleen op 'Systeemdatum' (R2016) actuele gegevens,
ongeacht de opgegeven peildatum en de autorisatie die is vastgelegd in Dienstbundel \ Groep

Scenario: 1.    Autorisatie = FMV, Peilmoment materieel resultaat is Leeg, Peilmoment Formeel resultaat is Leeg
                LT: R2262_LT04, R2130_LT19, R1983_LT13
                Uitwerking:
                - Verhuizing naar Utrecht  op 31-12-2015
                - Verhuizing naar Den Haag op 01-01-2016
                - Verhuizing naar Uithoorn op 02-01-2016
                Verwacht Resultaat
                - Alleen actueel adres, dus Uithoorn

Given leveringsautorisatie uit /levering_autorisaties_nieuw/R2262/Zoek_Persoon_op_adresgegevens
Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls
Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon op adresgegevens'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Postcode,Waarde=3512AE

Then heeft het antwoordbericht verwerking Geslaagd
Then heeft het antwoordbericht 1 groepen 'adres'
Then heeft in het antwoordbericht 'woonplaatsnaam' in 'adres' de waarde 'Uithoorn'
Then is het antwoordbericht gelijk aan /testcases/BV0ZA_Zoek_Persoon_op_Adres/expected/R2262_scenario_1_actuele_gegevens.xml voor expressie //brp:lvg_bvgZoekPersoonOpAdres_R

Scenario: 2.    Autorisatie = FMV, Peilmoment materieel resultaat is datum x (01-01-2016), Peilmoment Formeel resultaat is datum y (01-01-2016)
                LT: R2262_LT05
                Uitwerking:
                - Verhuizing naar Utrecht  op 31-12-2015
                - Verhuizing naar Den Haag op 01-01-2016
                - Verhuizing naar Uithoorn op 02-01-2016
                Verwacht Resultaat
                - Alleen actueel adres, dus Uithoorn
                - Gelijk aan scenario 1, want alleen actueel leveren

Given leveringsautorisatie uit /levering_autorisaties_nieuw/R2262/Zoek_Persoon_op_adresgegevens
Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon op adresgegevens'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Postcode,Waarde=3512AE
|peilmomentMaterieel|'2016-01-01'

Then heeft het antwoordbericht verwerking Geslaagd

Then heeft het antwoordbericht 1 groepen 'adres'
Then heeft in het antwoordbericht 'woonplaatsnaam' in 'adres' de waarde 'Uithoorn'
Then is het antwoordbericht gelijk aan /testcases/BV0ZA_Zoek_Persoon_op_Adres/expected/R2262_scenario_1_actuele_gegevens.xml voor expressie //brp:lvg_bvgZoekPersoonOpAdres_R
