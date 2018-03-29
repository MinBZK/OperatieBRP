Meta:
@status             Klaar
@usecase            SA.0.PA
@sleutelwoorden     Plaats afnemerindicatie

Narrative:
De waarde van datum moet in de combinatie van jaar, maand en dag geldig zijn binnen de Gregoriaanse kalender.


Scenario: 1.    datumAanvangMaterielePeriode is 28 februari, Schrikkeljaar, Plaats afnemerindicatie
                LT: R1274_LT29
                Verwacht resulaat:
                - Geef details persoon geslaagd

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|datumAanvangMaterielePeriode|2016-02-28

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 2.    datumAanvangMaterielePeriode is 28 februari, GEEN Schrikkeljaar, Plaats afnemerindicatie
                LT: R1274_LT30
                Verwacht resulaat:
                - Geef details persoon geslaagd

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|datumAanvangMaterielePeriode|2015-02-28

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 3.    datumAanvangMaterielePeriode is 29 februari, Schrikkeljaar, Plaats afnemerindicatie
                LT: R1274_LT31
                Verwacht resulaat:
                - Geef details persoon geslaagd

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|datumAanvangMaterielePeriode|2016-02-29

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 4.    datumAanvangMaterielePeriode is 29 februari, GEEN Schrikkeljaar, Plaats afnemerindicatie
                LT: R1274_LT32
                Verwacht resulaat:
                - Foutief
                - De opgegeven datum is geen geldige kalenderdatum.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|datumAanvangMaterielePeriode|2015-02-29

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1274     | De opgegeven datum is geen geldige kalenderdatum.

Scenario: 5.    datumAanvangMaterielePeriode is 30 februari, Schrikkeljaar, Plaats afnemerindicatie
                LT: R1274_LT33
                Verwacht resulaat:
                - Foutief
                - De opgegeven datum is geen geldige kalenderdatum.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|datumAanvangMaterielePeriode|2016-02-30

Then heeft het antwoordbericht verwerking Foutief

And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1274     | De opgegeven datum is geen geldige kalenderdatum.

Scenario: 6.    datumAanvangMaterielePeriode is 29 februari, Schrikkeljaar 2000, Plaats afnemerindicatie
                LT: R1274_LT34
                Verwacht resulaat:
                - Geslaagd

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|datumAanvangMaterielePeriode|2000-02-29

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 7.    datumAanvangMaterielePeriode is 29 februari, GEEN Schrikkeljaar 1900, Plaats afnemerindicatie
                LT: R1274_LT35
                Verwacht resulaat:
                - Foutief
                - De opgegeven datum is geen geldige kalenderdatum.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|datumAanvangMaterielePeriode|1900-02-29

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1274     | De opgegeven datum is geen geldige kalenderdatum.

Scenario: 8.    datumAanvangMaterielePeriode is 31 april, GEEN Schrikkeljaar, Plaats afnemerindicatie
                LT: R1274_LT36
                Verwacht resulaat:
                - Foutief
                - De opgegeven datum is geen geldige kalenderdatum.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|datumAanvangMaterielePeriode|2015-04-31

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1274     | De opgegeven datum is geen geldige kalenderdatum.

Scenario: 9.    datumAanvangMaterielePeriode is 31 juni, GEEN Schrikkeljaar, Plaats afnemerindicatie
                LT: R1274_LT37
                Verwacht resulaat:
                - Foutief
                - De opgegeven datum is geen geldige kalenderdatum.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|datumAanvangMaterielePeriode|2015-06-31

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1274     | De opgegeven datum is geen geldige kalenderdatum.

Scenario: 10.   datumAanvangMaterielePeriode is 31 augustus, GEEN Schrikkeljaar, Plaats afnemerindicatie
                LT: R1274_LT38
                Verwacht resulaat:
                - Geslaagd

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|datumAanvangMaterielePeriode|2015-08-31

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 11.   datumAanvangMaterielePeriode is 31 september, GEEN Schrikkeljaar, Plaats afnemerindicatie
                LT: R1274_LT39
                Verwacht resulaat:
                - Foutief
                - De opgegeven datum is geen geldige kalenderdatum.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|datumAanvangMaterielePeriode|2015-09-31

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1274     | De opgegeven datum is geen geldige kalenderdatum.

Scenario: 12.   datumAanvangMaterielePeriode is 31 november, GEEN Schrikkeljaar, Plaats afnemerindicatie
                LT: R1274_LT40
                Verwacht resulaat:
                - Foutief
                - De opgegeven datum is geen geldige kalenderdatum.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|datumAanvangMaterielePeriode|2015-11-31

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1274     | De opgegeven datum is geen geldige kalenderdatum.

Scenario: 13.   datumAanvangMaterielePeriode is 32 januari, GEEN Schrikkeljaar, Plaats afnemerindicatie
                LT: R1274_LT41
                Verwacht resulaat:
                - Foutief
                - De opgegeven datum is geen geldige kalenderdatum.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|datumAanvangMaterielePeriode|2015-01-32

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1274     | De opgegeven datum is geen geldige kalenderdatum.


Scenario: 14.   datumAanvangMaterielePeriode is 01-13-2015, GEEN Schrikkeljaar, Plaats afnemerindicatie
                LT: R1274_LT42
                Verwacht resulaat:
                - Foutief
                - De opgegeven datum is geen geldige kalenderdatum.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|datumAanvangMaterielePeriode|2015-13-01

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1274     | De opgegeven datum is geen geldige kalenderdatum.


Scenario: 15.   datumEindeVolgen is 28 februari, Schrikkeljaar, Plaats afnemerindicatie
                LT: R1274_LT43
                Verwacht resulaat:
                - Geef details persoon geslaagd

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|datumEindeVolgen|2020-02-28

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 16.   datumEindeVolgen is 28 februari, GEEN Schrikkeljaar, Plaats afnemerindicatie
                LT: R1274_LT44
                Verwacht resulaat:
                - Geef details persoon geslaagd

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|datumEindeVolgen|2019-02-28

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 17.   datumEindeVolgen is 29 februari, Schrikkeljaar, Plaats afnemerindicatie
                LT: R1274_LT45
                Verwacht resulaat:
                - Geef details persoon geslaagd

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|datumEindeVolgen|2020-02-29

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 18.   datumEindeVolgen is 29 februari, GEEN Schrikkeljaar, Plaats afnemerindicatie
                LT: R1274_LT46
                Verwacht resulaat:
                - Foutief
                - De opgegeven datum is geen geldige kalenderdatum.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|datumEindeVolgen|2019-02-29

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1274     | De opgegeven datum is geen geldige kalenderdatum.

Scenario: 19.   datumEindeVolgen is 30 februari, Schrikkeljaar, Plaats afnemerindicatie
                LT: R1274_LT47
                Verwacht resulaat:
                - Foutief
                - De opgegeven datum is geen geldige kalenderdatum.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|datumEindeVolgen|2020-02-30

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1274     | De opgegeven datum is geen geldige kalenderdatum.

Scenario: 20.   datumEindeVolgen is 29 februari, Schrikkeljaar 2400, Plaats afnemerindicatie
                LT: R1274_LT48
                Verwacht resulaat:
                - Geslaagd

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|datumEindeVolgen|2400-02-29

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 21.   datumEindeVolgen is 29 februari, GEEN Schrikkeljaar 2100, Plaats afnemerindicatie
                LT: R1274_LT49
                Verwacht resulaat:
                - Foutief
                - De opgegeven datum is geen geldige kalenderdatum.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|datumEindeVolgen|2100-02-29

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1274     | De opgegeven datum is geen geldige kalenderdatum.

Scenario: 22.   datumEindeVolgen is 31 april, GEEN Schrikkeljaar, Plaats afnemerindicatie
                LT: R1274_LT50
                Verwacht resulaat:
                - Foutief
                - De opgegeven datum is geen geldige kalenderdatum.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|datumEindeVolgen|2019-04-31

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1274     | De opgegeven datum is geen geldige kalenderdatum.

Scenario: 23.   datumEindeVolgen is 31 juni, GEEN Schrikkeljaar, Plaats afnemerindicatie
                LT: R1274_LT51
                Verwacht resulaat:
                - Foutief
                - De opgegeven datum is geen geldige kalenderdatum.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|datumEindeVolgen|2019-06-31

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1274     | De opgegeven datum is geen geldige kalenderdatum.

Scenario: 24.   datumEindeVolgen is 31 augustus, GEEN Schrikkeljaar, Plaats afnemerindicatie
                LT: R1274_LT52
                Verwacht resulaat:
                - Geslaagd

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|datumEindeVolgen|2019-08-31

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 25.   datumEindeVolgen is 31 september, GEEN Schrikkeljaar, Plaats afnemerindicatie
                LT: R1274_LT53
                Verwacht resulaat:
                - Foutief
                - De opgegeven datum is geen geldige kalenderdatum.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|datumEindeVolgen|2019-09-31

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1274     | De opgegeven datum is geen geldige kalenderdatum.

Scenario: 26.   datumEindeVolgen is 31 november, GEEN Schrikkeljaar, Plaats afnemerindicatie
                LT: R1274_LT54
                Verwacht resulaat:
                - Foutief
                - De opgegeven datum is geen geldige kalenderdatum.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|datumEindeVolgen|2019-11-31

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1274     | De opgegeven datum is geen geldige kalenderdatum.

Scenario: 27.   datumEindeVolgen is 32 januari, GEEN Schrikkeljaar, Plaats afnemerindicatie
                LT: R1274_LT55
                Verwacht resulaat:
                - Foutief
                - De opgegeven datum is geen geldige kalenderdatum.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|datumEindeVolgen|2019-01-32

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1274     | De opgegeven datum is geen geldige kalenderdatum.


Scenario: 28.   datumEindeVolgen is 01-13-2019, GEEN Schrikkeljaar, Plaats afnemerindicatie
                LT: R1274_LT56
                Verwacht resulaat:
                - Foutief
                - De opgegeven datum is geen geldige kalenderdatum.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|datumEindeVolgen|2019-13-01

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1274     | De opgegeven datum is geen geldige kalenderdatum.