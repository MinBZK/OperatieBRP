Meta:

@status             Klaar
@usecase            BV.0.GD
@sleutelwoorden     Geef Details Persoon
@regels             R1274

Narrative:
De waarde van datum moet in de combinatie van jaar, maand en dag geldig zijn binnen de Gregoriaanse kalender.

Scenario: 1.    Peilmoment materieel resultaat is 28 februari, Schrikkeljaar, Dienst geef details persoon
                LT: R1274_LT01
                Verwacht resulaat:
                - Geef details persoon geslaagd

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|historievorm|Materieel
|peilmomentMaterieelResultaat|'2016-02-28'

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 2.    Peilmoment materieel resultaat is 28 februari, GEEN Schrikkeljaar, Dienst geef details persoon
                LT: R1274_LT02
                Verwacht resulaat:
                - Geef details persoon geslaagd

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|historievorm|Materieel
|peilmomentMaterieelResultaat|'2015-02-28'

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 3.    Peilmoment materieel resultaat is 29 februari, Schrikkeljaar, Dienst geef details persoon
                LT: R1274_LT03
                Verwacht resulaat:
                - Geef details persoon geslaagd

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|historievorm|Materieel
|peilmomentMaterieelResultaat|'2016-02-29'

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 4.    Peilmoment materieel resultaat is 29 februari, GEEN Schrikkeljaar, Dienst geef details persoon
                LT: R1274_LT04
                Verwacht resulaat:
                - Foutief
                - De opgegeven datum is geen geldige kalenderdatum.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|historievorm|Materieel
|peilmomentMaterieelResultaat|'2015-02-29'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1274     | De opgegeven datum is geen geldige kalenderdatum.

Scenario: 5.    Peilmoment materieel resultaat is 30 februari, Schrikkeljaar, Dienst geef details persoon
                LT: R1274_LT05
                Verwacht resulaat:
                - Foutief
                - De opgegeven datum is geen geldige kalenderdatum.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|historievorm|Materieel
|peilmomentMaterieelResultaat|'2016-02-30'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1274     | De opgegeven datum is geen geldige kalenderdatum.

Scenario: 6.    Peilmoment materieel resultaat is 29 februari, Schrikkeljaar 2000 , Dienst geef details persoon
                LT: R1274_LT06
                Verwacht resulaat:
                - Geslaagd

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|historievorm|Materieel
|peilmomentMaterieelResultaat|'2000-02-29'

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 7.    Peilmoment materieel resultaat is 29 februari, GEEN Schrikkeljaar 1900, Dienst geef details persoon
                LT: R1274_LT07
                Verwacht resulaat:
                - Foutief
                - De opgegeven datum is geen geldige kalenderdatum.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|historievorm|Materieel
|peilmomentMaterieelResultaat|'1900-02-29'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1274     | De opgegeven datum is geen geldige kalenderdatum.

Scenario: 8.    Peilmoment materieel resultaat is 31 april, GEEN Schrikkeljaar, Dienst geef details persoon
                LT: R1274_LT08
                Verwacht resulaat:
                - Foutief
                - De opgegeven datum is geen geldige kalenderdatum.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|historievorm|Materieel
|peilmomentMaterieelResultaat|'2015-04-31'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1274     | De opgegeven datum is geen geldige kalenderdatum.

Scenario: 9.    Peilmoment materieel resultaat is 31 juni, GEEN Schrikkeljaar, Dienst geef details persoon
                LT: R1274_LT09
                Verwacht resulaat:
                - Foutief
                - De opgegeven datum is geen geldige kalenderdatum.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|historievorm|Materieel
|peilmomentMaterieelResultaat|'2015-06-31'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1274     | De opgegeven datum is geen geldige kalenderdatum.

Scenario: 10.    Peilmoment materieel resultaat is 31 augustus, GEEN Schrikkeljaar, Dienst geef details persoon
                LT: R1274_LT10
                Verwacht resulaat:
                - Geslaagd

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|historievorm|Materieel
|peilmomentMaterieelResultaat|'2015-08-31'

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 11.    Peilmoment materieel resultaat is 31 september, GEEN Schrikkeljaar, Dienst geef details persoon
                LT: R1274_LT11
                Verwacht resulaat:
                - Foutief
                - De opgegeven datum is geen geldige kalenderdatum.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|historievorm|Materieel
|peilmomentMaterieelResultaat|'2015-09-31'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1274     | De opgegeven datum is geen geldige kalenderdatum.

Scenario: 12.    Peilmoment materieel resultaat is 31 november, GEEN Schrikkeljaar, Dienst geef details persoon
                LT: R1274_LT12
                Verwacht resulaat:
                - Foutief
                - De opgegeven datum is geen geldige kalenderdatum.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|historievorm|Materieel
|peilmomentMaterieelResultaat|'2015-11-31'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1274     | De opgegeven datum is geen geldige kalenderdatum.

Scenario: 13.    Peilmoment materieel resultaat is 32 januari, GEEN Schrikkeljaar, Dienst geef details persoon
                LT: R1274_LT13
                Verwacht resulaat:
                - Foutief
                - De opgegeven datum is geen geldige kalenderdatum.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|historievorm|Materieel
|peilmomentMaterieelResultaat|'2015-01-32'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1274     | De opgegeven datum is geen geldige kalenderdatum.

Scenario: 14.    Peilmoment materieel resultaat is 01-13-2015, GEEN Schrikkeljaar, Dienst geef details persoon
                LT: R1274_LT14
                Verwacht resulaat:
                - Foutief
                - De opgegeven datum is geen geldige kalenderdatum.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|historievorm|Materieel
|peilmomentMaterieelResultaat|'2015-13-01'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1274     | De opgegeven datum is geen geldige kalenderdatum.


Scenario: 15.   Peilmoment Formeel resultaat is 28 februari, Schrikkeljaar, Dienst geef details persoon
                LT: R1274_LT15
                Verwacht resulaat:
                - Geef details persoon geslaagd

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls

Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|606417801|2014-12-31 T23:59:00Z

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|historievorm|Materieel
|peilmomentFormeelResultaat|'2016-02-28T23:59:00'

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 16.   Peilmoment formeel resultaat is 28 februari, GEEN Schrikkeljaar, Dienst geef details persoon
                LT: R1274_LT16
                Verwacht resulaat:
                - Geef details persoon geslaagd

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls

Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|606417801|2014-12-31 T23:59:00Z

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|historievorm|Materieel
|peilmomentFormeelResultaat|'2015-02-28T23:59:00'

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 17.   Peilmoment formeel resultaat is 29 februari, Schrikkeljaar, Dienst geef details persoon
                LT: R1274_LT17
                Verwacht resulaat:
                - Geef details persoon geslaagd

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls

Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|606417801|2014-12-31 T23:59:00Z

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|historievorm|Materieel
|peilmomentFormeelResultaat|'2016-02-29T23:59:00'

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 18.   Peilmoment formeel resultaat is 29 februari, GEEN Schrikkeljaar, Dienst geef details persoon
                LT: R1274_LT18
                Verwacht resulaat:
                - Foutief
                - De opgegeven datum is geen geldige kalenderdatum.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|historievorm|Materieel
|peilmomentFormeelResultaat|'2015-02-29T23:59:00'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1274     | De opgegeven datum is geen geldige kalenderdatum.

Scenario: 19.   Peilmoment formeel resultaat is 30 februari, Schrikkeljaar, Dienst geef details persoon
                LT: R1274_LT19
                Verwacht resulaat:
                - Foutief
                - De opgegeven datum is geen geldige kalenderdatum.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|historievorm|Materieel
|peilmomentFormeelResultaat|'2016-02-30T23:59:00'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1274     | De opgegeven datum is geen geldige kalenderdatum.

Scenario: 20.   Peilmoment fromeel resultaat is 29 februari, Schrikkeljaar 2000 , Dienst geef details persoon
                LT: R1274_LT20
                Verwacht resulaat:
                - Geslaagd

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls

Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|606417801|1900-12-31 T23:59:00Z

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|historievorm|Materieel
|peilmomentFormeelResultaat|'2000-02-29T23:59:00'

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 21.   Peilmoment formeel resultaat is 29 februari, GEEN Schrikkeljaar 1900, Dienst geef details persoon
                LT: R1274_LT21
                Verwacht resulaat:
                - Foutief
                - De opgegeven datum is geen geldige kalenderdatum.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|historievorm|Materieel
|peilmomentFormeelResultaat|'1900-02-29T23:59:00'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1274     | De opgegeven datum is geen geldige kalenderdatum.

Scenario: 22.   Peilmoment formeel resultaat is 31 april, GEEN Schrikkeljaar, Dienst geef details persoon
                LT: R1274_LT22
                Verwacht resulaat:
                - Foutief
                - De opgegeven datum is geen geldige kalenderdatum.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|historievorm|Materieel
|peilmomentFormeelResultaat|'2015-04-31T23:59:00'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1274     | De opgegeven datum is geen geldige kalenderdatum.

Scenario: 23.   Peilmoment formeel resultaat is 31 juni, GEEN Schrikkeljaar, Dienst geef details persoon
                LT: R1274_LT23
                Verwacht resulaat:
                - Foutief
                - De opgegeven datum is geen geldige kalenderdatum.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|historievorm|Materieel
|peilmomentFormeelResultaat|'2015-06-31T23:59:00'


Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1274     | De opgegeven datum is geen geldige kalenderdatum.

Scenario: 24.   Peilmoment formeel resultaat is 31 augustus, GEEN Schrikkeljaar, Dienst geef details persoon
                LT: R1274_LT24
                Verwacht resulaat:
                - Geslaagd

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls

Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|606417801|2014-12-31 T23:59:00Z

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|historievorm|Materieel
|peilmomentFormeelResultaat|'2015-08-31T23:59:00'

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 25.   Peilmoment formeel resultaat is 31 september, GEEN Schrikkeljaar, Dienst geef details persoon
                LT: R1274_LT25
                Verwacht resulaat:
                - Foutief
                - De opgegeven datum is geen geldige kalenderdatum.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|historievorm|Materieel
|peilmomentFormeelResultaat|'2015-09-31T23:59:00'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1274     | De opgegeven datum is geen geldige kalenderdatum.

Scenario: 26.   Peilmoment formeel resultaat is 31 november, GEEN Schrikkeljaar, Dienst geef details persoon
                LT: R1274_LT26
                Verwacht resulaat:
                - Foutief
                - De opgegeven datum is geen geldige kalenderdatum.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|historievorm|Materieel
|peilmomentFormeelResultaat|'2015-11-31T23:59:00'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1274     | De opgegeven datum is geen geldige kalenderdatum.

Scenario: 27.   Peilmoment formeel resultaat is 32 januari, GEEN Schrikkeljaar, Dienst geef details persoon
                LT: R1274_LT27
                Verwacht resulaat:
                - Foutief
                - De opgegeven datum is geen geldige kalenderdatum.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|historievorm|Materieel
|peilmomentFormeelResultaat|'2015-01-32T23:59:00'


Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1274     | De opgegeven datum is geen geldige kalenderdatum.

Scenario: 28.   Peilmoment formeel resultaat is 01-13-2003, GEEN Schrikkeljaar, Dienst geef details persoon
                LT: R1274_LT28
                Verwacht resulaat:
                - Foutief
                - De opgegeven datum is geen geldige kalenderdatum.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|historievorm|Materieel
|peilmomentFormeelResultaat|'2015-13-01T23:59:00'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1274     | De opgegeven datum is geen geldige kalenderdatum.
