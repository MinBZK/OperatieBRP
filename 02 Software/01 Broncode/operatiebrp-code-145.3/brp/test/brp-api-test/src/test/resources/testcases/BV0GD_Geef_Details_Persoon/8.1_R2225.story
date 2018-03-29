Meta:

@status             Klaar
@usecase            BV.0.GD
@sleutelwoorden     Geef Details Persoon
@regels             R2225

Narrative:
Indien in het bericht de Bericht.Historievorm de waarde "Materieel" heeft
dan worden alle materiële voorkomens geleverd tot en met Bericht.Peilmoment materieel resultaat
zoals bekend in het systeem op Bericht.Peilmoment formeel resultaat.

Indien één of beide peilmomenten leeg zijn (of niet voorkomen in het bericht) dan wordt 'Systeemdatum' (R2016) als peilmoment(en) gebruikt.

Indien er sprake is van Datum (deels) onbekend (R1273) dan moet de R1283 - Vergelijken (partiële) datums uitgevoerd worden.

Voorkomens met historiepatroon ‘Materieel’ voldoen als (TsReg <= Peilmoment Formeel Resultaat < TsVerval) EN (DAG <= Peilmoment Materieel Resultaat )

Scenario: 1.    Historievorm = Materieel, Peilmoment materieel resultaat is datum x (2016-01-01), Peilmoment Formeel resultaat is datum y (2015-12-31)
                LT: R2225_LT01
                Verwacht resultaat:
                - Utrecht in bericht
                - Oosterhout in bericht
                Uitwerking:
                - Uithoorn sinds 2016-01-02
                - Den Haag sinds 2016-01-01
                - Utrecht sinds  2015-21-31
                - Oosterhout sinds 2010

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding_Haarlem

Given persoonsbeelden uit specials:specials/Anne_Bakker_xls

Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|595891305|'1900-12-31 T23:59:00Z'

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding Haarlem'
|zendendePartijNaam|'Gemeente Haarlem'
|bsn|595891305
|historievorm|Materieel
|peilmomentMaterieelResultaat|'2016-01-01'
|peilmomentFormeelResultaat|2015-12-31T23:59:56.223Z

Then heeft het antwoordbericht verwerking Geslaagd

!-- R2225_LT01
Then heeft het antwoordbericht 2 groepen 'adres'
!-- check op scheidingsteken, in de testtooling werkt dit niet correct vandaar een check op xpath.
Then is er voor xpath //brp:scheidingsteken[text()=' '] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:woonplaatsnaam[text()='Utrecht'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:woonplaatsnaam[text()='Oosterhout'] een node aanwezig in het antwoord bericht

Then is het antwoordbericht gelijk aan /testcases/BV0GD_Geef_Details_Persoon/expected/R2225_scenario_1.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R


Scenario: 2.    Historievorm = Materieel, Peilmoment materieel resultaat is datum x (2016-01-01), Peilmoment Formeel resultaat is datum y (2016-01-01)
                LT: R2225_LT02
                Verwacht resultaat:
                - Den Haag in bericht
                - Utrecht in bericht
                - Oosterhout in bericht
                Uitwerking:
                - Uithoorn sinds 2016-01-02
                - Den Haag sinds 2016-01-01 (naamOpenbareRuimte = Spui)
                - Utrecht sinds  2015-21-31
                - Oosterhout sinds 2010

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding_Haarlem

Given persoonsbeelden uit specials:specials/Anne_Bakker_xls

Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|595891305|'1900-12-31 T23:59:00Z'

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding Haarlem'
|zendendePartijNaam|'Gemeente Haarlem'
|bsn|595891305
|historievorm|Materieel
|peilmomentMaterieelResultaat|'2016-01-01'
|peilmomentFormeelResultaat|2016-01-01T23:59:56.223Z

Then heeft het antwoordbericht verwerking Geslaagd

Then heeft het antwoordbericht 3 groepen 'adres'

Then is er voor xpath //brp:naamOpenbareRuimte[text()='Spui'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:woonplaatsnaam[text()='Utrecht'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:woonplaatsnaam[text()='Oosterhout'] een node aanwezig in het antwoord bericht

Then is het antwoordbericht gelijk aan /testcases/BV0GD_Geef_Details_Persoon/expected/R2225_scenario_2.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 3.    Historievorm = Materieel, Peilmoment materieel resultaat is datum x (2016-01-01), Peilmoment Formeel resultaat is datum y (2016-01-02)
                LT: R2225_LT03
                Verwacht resultaat:
                - Den Haag in bericht
                - Utrecht in bericht
                - Oosterhout in bericht
                Uitwerking:
                - Uithoorn sinds 2016-01-02
                - Den Haag sinds 2016-01-01 (naamOpenbareRuimte = Spui)
                - Utrecht sinds  2015-21-31
                - Oosterhout sinds 2010

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding_Haarlem

Given persoonsbeelden uit specials:specials/Anne_Bakker_xls

Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|595891305|'1900-12-31 T23:59:00Z'

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding Haarlem'
|zendendePartijNaam|'Gemeente Haarlem'
|bsn|595891305
|historievorm|Materieel
|peilmomentMaterieelResultaat|'2016-01-01'
|peilmomentFormeelResultaat|2016-01-02T23:59:56.223Z

Then heeft het antwoordbericht verwerking Geslaagd

Then heeft het antwoordbericht 3 groepen 'adres'

Then is er voor xpath //brp:naamOpenbareRuimte[text()='Spui'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:woonplaatsnaam[text()='Utrecht'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:woonplaatsnaam[text()='Oosterhout'] een node aanwezig in het antwoord bericht

Then is het antwoordbericht gelijk aan /testcases/BV0GD_Geef_Details_Persoon/expected/R2225_scenario_3.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 4.    Historievorm = Materieel, Peilmoment materieel resultaat is LEEG, Peilmoment Formeel resultaat is datum y (2015-12-31)
                LT: R2225_LT04
                Verwacht resultaat:
                - Utrecht in bericht
                - Oosterhout in bericht
                Uitwerking:
                - Uithoorn sinds 2016-01-02
                - Den Haag sinds 2016-01-01 (naamOpenbareRuimte = Spui)
                - Utrecht sinds  2015-21-31
                - Oosterhout sinds 2010

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding_Haarlem

Given persoonsbeelden uit specials:specials/Anne_Bakker_xls

Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|595891305|'1900-12-31 T23:59:00Z'

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding Haarlem'
|zendendePartijNaam|'Gemeente Haarlem'
|bsn|595891305
|historievorm|Materieel
|peilmomentFormeelResultaat|2015-12-31T23:59:56.223Z

Then heeft het antwoordbericht verwerking Geslaagd

!-- R2225_LT01
Then heeft het antwoordbericht 2 groepen 'adres'

Then is er voor xpath //brp:woonplaatsnaam[text()='Utrecht'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:woonplaatsnaam[text()='Oosterhout'] een node aanwezig in het antwoord bericht

Then is het antwoordbericht gelijk aan /testcases/BV0GD_Geef_Details_Persoon/expected/R2225_scenario_4.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 5.    Historievorm = Materieel, Peilmoment materieel resultaat is LEEG, Peilmoment Formeel resultaat is datum y (2016-01-01)
                LT: R2225_LT05
                Verwacht resultaat:
                - Den Haag in bericht
                - Utrecht in bericht
                - Oosterhout in bericht
                Uitwerking:
                - Uithoorn sinds 2016-01-02
                - Den Haag sinds 2016-01-01 (naamOpenbareRuimte = Spui)
                - Utrecht sinds  2015-21-31
                - Oosterhout sinds 2010

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding_Haarlem

Given persoonsbeelden uit specials:specials/Anne_Bakker_xls

Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|595891305|'1900-12-31 T23:59:00Z'

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding Haarlem'
|zendendePartijNaam|'Gemeente Haarlem'
|bsn|595891305
|historievorm|Materieel
|peilmomentFormeelResultaat|2016-01-01T23:59:56.223Z

Then heeft het antwoordbericht verwerking Geslaagd

Then heeft het antwoordbericht 3 groepen 'adres'

Then is er voor xpath //brp:naamOpenbareRuimte[text()='Spui'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:woonplaatsnaam[text()='Utrecht'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:woonplaatsnaam[text()='Oosterhout'] een node aanwezig in het antwoord bericht

Then is het antwoordbericht gelijk aan /testcases/BV0GD_Geef_Details_Persoon/expected/R2225_scenario_5.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 6.1   Historievorm = Materieel, Peilmoment materieel resultaat is LEEG, Peilmoment Formeel resultaat is datum y (2016-01-02)
                LT: R2225_LT06
                Verwacht resultaat:
                - Uithoorn in bericht
                - Den Haag in bericht
                - Utrecht in bericht
                - Oosterhout in bericht
                Uitwerking:
                - Uithoorn sinds 2016-01-02
                - Den Haag sinds 2016-01-01 (naamOpenbareRuimte = Spui)
                - Utrecht sinds  2015-21-31
                - Oosterhout sinds 2010

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding_Haarlem

Given persoonsbeelden uit specials:specials/Anne_Bakker_xls

Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|595891305|'1900-12-31 T23:59:00Z'

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding Haarlem'
|zendendePartijNaam|'Gemeente Haarlem'
|bsn|595891305
|historievorm|Materieel
|peilmomentFormeelResultaat|2016-01-02T23:59:56.223Z

Then heeft het antwoordbericht verwerking Geslaagd

Then heeft het antwoordbericht 4 groepen 'adres'

Then is er voor xpath //brp:woonplaatsnaam[text()='Uithoorn'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:naamOpenbareRuimte[text()='Spui'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:woonplaatsnaam[text()='Utrecht'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:woonplaatsnaam[text()='Oosterhout'] een node aanwezig in het antwoord bericht

Then is het antwoordbericht gelijk aan /testcases/BV0GD_Geef_Details_Persoon/expected/R2225_scenario_6.1.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 6.2   Historievorm = Materieel, Peilmoment materieel resultaat is LEEG, Peilmoment Formeel resultaat is datum y (2016-01-02)
                LT: R2225_LT07
                Verwacht resultaat:
                - Uithoorn NIET in bericht (op basis van TsReg)
                - Den Haag in bericht
                - Utrecht in bericht
                - Oosterhout in bericht
                Uitwerking:
                - Uithoorn sinds 2016-01-02 (Tsreg = 2016-01-03)
                - Den Haag sinds 2016-01-01 (naamOpenbareRuimte = Spui)
                - Utrecht sinds  2015-21-31
                - Oosterhout sinds 2010

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding_Haarlem

Given persoonsbeelden uit specials:specials/Anne_met_Historie2TsReg_xls

Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|986096969|'1900-12-31 T23:59:00Z'

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding Haarlem'
|zendendePartijNaam|'Gemeente Haarlem'
|bsn|986096969
|historievorm|Materieel
|peilmomentFormeelResultaat|2016-01-02T23:59:56.223Z

Then heeft het antwoordbericht verwerking Geslaagd

Then heeft het antwoordbericht 3 groepen 'adres'

Then is er voor xpath //brp:woonplaatsnaam[text()='Uithoorn'] geen node aanwezig in het antwoord bericht
Then is er voor xpath //brp:naamOpenbareRuimte[text()='Spui'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:woonplaatsnaam[text()='Utrecht'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:woonplaatsnaam[text()='Oosterhout'] een node aanwezig in het antwoord bericht

Then is het antwoordbericht gelijk aan /testcases/BV0GD_Geef_Details_Persoon/expected/R2225_scenario_6.2.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 7.    Historievorm = Materieel, Peilmoment materieel resultaat is datum x (31-12-2015), Peilmoment Formeel resultaat is LEEG
                LT: R2225_LT08
                Uitwerking:
                - Verhuizing naar Utrecht  op 31-12-2015
                - Verhuizing naar Rotterdam op 01-01-2016
                - Verhuizing naar Uithoorn op 02-01-2016
                Verwacht Resultaat
                - Oosterhout in het bericht
                - Utrecht in het bericht
                - Rotterdam in het bericht
                - Uithoorn niet in het bericht

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Anne_met_Historie2_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|986096969
|historievorm|Materieel
|peilmomentMaterieelResultaat|'2015-12-31'

Then heeft het antwoordbericht verwerking Geslaagd

!-- R2225_LT08
Then heeft het antwoordbericht 2 groepen 'adres'
Then is er voor xpath //brp:woonplaatsnaam[text()='Utrecht'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:woonplaatsnaam[text()='Oosterhout'] een node aanwezig in het antwoord bericht

Then is het antwoordbericht gelijk aan /testcases/BV0GD_Geef_Details_Persoon/expected/R2225_scenario_7.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 8.    Historievorm = Materieel, Peilmoment materieel resultaat is datum x (01-01-2016), Peilmoment Formeel resultaat LEEG
                LT: R2225_LT09
                Uitwerking:
                - Oorspronkelijke woonplaats Oosterhout
                - Verhuizing naar Utrecht  op 31-12-2015
                - Verhuizing naar Rotterdam op 01-01-2016
                - Verhuizing naar Uithoorn op 02-01-2016
                Verwacht Resultaat
                - Oosterhout in het bericht
                - Utrecht in het bericht
                - Rotterdam en Uithoorn niet in het bericht

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Anne_met_Historie2_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|986096969
|historievorm|Materieel
|peilmomentMaterieelResultaat|'2016-01-01'

Then heeft het antwoordbericht verwerking Geslaagd

!-- R2225_LT09
Then heeft het antwoordbericht 3 groepen 'adres'
Then is er voor xpath //brp:naamOpenbareRuimte[text()='Spui'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:woonplaatsnaam[text()='Utrecht'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:woonplaatsnaam[text()='Oosterhout'] een node aanwezig in het antwoord bericht

Then is het antwoordbericht gelijk aan /testcases/BV0GD_Geef_Details_Persoon/expected/R2225_scenario_8.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 9.   Historievorm = Materieel, Peilmoment materieel resultaat is datum x (02-01-2016), Peilmoment Formeel resultaat is LEEG
                LT: R2225_LT10
                Uitwerking:
                - Oorspronkelijke woonplaats Oosterhout
                - Verhuizing naar Utrecht  op 31-12-2015
                - Verhuizing naar Rotterdam op 01-01-2016
                - Verhuizing naar Uithoorn op 02-01-2016
                Verwacht Resultaat
                - Oosterhout in het bericht
                - Utrecht in het bericht
                - Rotterdam in het bericht
                - Uithoorn in het bericht

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Anne_met_Historie2_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|986096969
|historievorm|Materieel
|peilmomentMaterieelResultaat|'2016-01-02'

Then heeft het antwoordbericht verwerking Geslaagd

!-- R2225_LT10
Then heeft het antwoordbericht 4 groepen 'adres'
Then is er voor xpath //brp:woonplaatsnaam[text()='Uithoorn'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:naamOpenbareRuimte[text()='Spui'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:woonplaatsnaam[text()='Utrecht'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:woonplaatsnaam[text()='Oosterhout'] een node aanwezig in het antwoord bericht

Then is het antwoordbericht gelijk aan /testcases/BV0GD_Geef_Details_Persoon/expected/R2225_scenario_9.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 10.   Historievorm = Materieel, Peilmoment materieel resultaat is LEEG, Peilmoment Formeel resultaat is LEEG
                LT: R2225_LT11
                Uitwerking:
                - Oorspronkelijke woonplaats Oosterhout
                - Verhuizing naar Utrecht  op 31-12-2015
                - Verhuizing naar Rotterdam op 01-01-2016
                - Verhuizing naar Uithoorn op 02-01-2016
                Verwacht Resultaat
                - Oosterhout in het bericht
                - Utrecht in het bericht
                - Rotterdam in het bericht
                - Uithoorn in het bericht

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Anne_met_Historie2_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|986096969
|historievorm|Materieel

Then heeft het antwoordbericht verwerking Geslaagd

!-- R2225_LT11
Then heeft het antwoordbericht 4 groepen 'adres'
Then is er voor xpath //brp:woonplaatsnaam[text()='Uithoorn'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:naamOpenbareRuimte[text()='Spui'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:woonplaatsnaam[text()='Utrecht'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:woonplaatsnaam[text()='Oosterhout'] een node aanwezig in het antwoord bericht

Then is het antwoordbericht gelijk aan /testcases/BV0GD_Geef_Details_Persoon/expected/R2225_scenario_10.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario:  11.  Historievorm = Materieel, Peilmoment materieel resultaat is LEEG, Peilmoment Formeel resultaat is LEEG
                LT: R2225_LT13, R2225_LT14
                Verwacht resultaat:
                - Uithoorn              NIET in bericht
                - Den Haag              in bericht
                - Utrecht in bericht    in bericht
                - Oosterhout            in bericht
                Uitwerking:
                - Uithoorn sinds 2016-02-00 tot heden
                - Den Haag sinds 2016-01-00 tot 2016-02-00 (naamOpenbareRuimte = Spui)
                - Oosterhout sinds 2010-12-31 tot 2016-01-00
                - Utrecht sinds  0000-00-00 tot 2010-12-31



Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Anne_met_Historie_geheel_onb_dat_xls

Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|986096969|'1900-12-31 T23:59:00Z'

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|986096969
|historievorm|Materieel
|peilmomentMaterieelResultaat|'2016-01-01'

Then heeft het antwoordbericht verwerking Geslaagd
Then heeft het antwoordbericht 3 groepen 'adres'

Then is er voor xpath //brp:woonplaatsnaam[text()='Uithoorn'] geen node aanwezig in het antwoord bericht
Then is er voor xpath //brp:naamOpenbareRuimte[text()='Spui'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:woonplaatsnaam[text()='Utrecht'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:woonplaatsnaam[text()='Oosterhout'] een node aanwezig in het antwoord bericht

Then is het antwoordbericht gelijk aan /testcases/BV0GD_Geef_Details_Persoon/expected/R2225_scenario_11.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario:  12.  Materieel voor groepen met alleen formeel historie patroon (geboorte), Formeel peilmoment op 01-01-2016
                LT: R2225_LT15
                Verwacht resultaat:
                - Kindje 1 wel in bericht (geboortedatum 01-01-2010) (TsReg 01-01-2010)
                - Kindje 2 Niet in bericht (geboortedatum 01-01-2015) (TsReg 01-01-2015)

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Anne_met_Historie_geheel_onb_dat_xls

Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|986096969|'1900-12-31 T23:59:00Z'

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|986096969
|historievorm|Materieel
|peilmomentMaterieelResultaat|'2016-01-01'
|peilmomentFormeelResultaat|2014-12-31T23:59:56.223Z

Then heeft het antwoordbericht verwerking Geslaagd

!-- R2225_LT15  4x samengestelde naam
!-- Samengestelde naam 1 is Anne Bakker
!-- Samengestelde naam 2 is Vader- Anne Bakker
!-- Samengestelde naam 3 is Moeder- Anne Bakker
!-- Samengestelde naam 4 is kindje Co Bakker

Then heeft het antwoordbericht 4 groepen 'samengesteldeNaam'

Then is het antwoordbericht gelijk aan /testcases/BV0GD_Geef_Details_Persoon/expected/R2225_scenario_12.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 13.   Geef Details persoon voor UC_Kruimeltje leveren afnemerindicatie met historie
                Blob datum/tijd registratie = Sun, 07 Aug 2016 18:48:17 GMT
                Blob datum/tijd verval = Sat, 20 Aug 2016 12:38:17 GMT
                Condities:
                - Soort dienst Geef Details Persoon
                - Partij rol = Afnemer
                - Afnemerindicatie aanwezig
                - afnemerindicatie attributen geautoriseerd
                - Datum/tijd verval = GEVULD
                - Protocolleringsniveau <> Geheim
                - Historievorm Materieel, peilmoment 2016-08-09 peilmoment formeeel omdat alleen formele historie is.
                Verwacht Resultaat:
                - Afnemerindicatie WEL in bericht, op peilmoment was de afnemerindicatie geldig.


Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie

Given persoonsbeelden uit specials:specials/Jan_xls
Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam                               | partijNaam      | datumEindeVolgen | datumAanvangMaterielePeriode | tsReg                 | dienstId | TsVerval              | DienstIdVerval |
| 606417801 | 'Geen pop.bep. levering op basis van afnemerindicatie' | 'Gemeente Utrecht' | 2029-01-01       | 2010-01-01                   | 2014-01-01 T00:00:00Z | 1        | 2016-11-01 T00:00:00Z | 2              |

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|historievorm|Materieel
|peilmomentMaterieelResultaat|VANDAAG
|peilmomentFormeelResultaat|VANDAAG

Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'afnemerindicatie' in 'afnemerindicaties' nummer 1 ja
Then is er voor xpath //brp:afnemerindicaties een node aanwezig in het antwoord bericht

Then is het antwoordbericht gelijk aan /testcases/BV0GD_Geef_Details_Persoon/expected/R2225_scenario_13.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R
