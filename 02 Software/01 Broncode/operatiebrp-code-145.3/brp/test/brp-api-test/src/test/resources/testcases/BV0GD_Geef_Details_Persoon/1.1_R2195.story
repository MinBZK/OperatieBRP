Meta:

@status             Klaar
@usecase            BV.0.GD
@sleutelwoorden     Geef Details Persoon
@regels             R2195
@versie             2


Narrative:
De Dienst Geef details persoon levert afnemerindicaties bij de Persoon. Hierbij gelden de volgende filters:
Alleen geautoriseerde attributen worden geleverd (conform R1974 - Alleen attributen leveren waarvoor autorisatie bestaat)
Alleen actuele voorkomens worden geleverd (Persoon \ Afnemerindicatie.Standaard.Datum/tijd verval = leeg)
Alleen niet-geheime afnemerindicaties worden geleverd (Leveringsautorisatie.Protocolleringsniveau bij de
Persoon \ Afnemerindicatie dient ongelijk aan "Geheim" (2) te zijn)

Scenario: 1.    Geef detail persoon voordat er een afnemerindicatie geplaatst is (leveringsautorisatie mut.obv.afn.)
                LT: R2195_LT02, R2130_LT15, R1983_LT16
                Verwacht Resultaat:
                - Afnemerindicatie NIET in bericht

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|590984809

Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'afnemerindicatie' in 'afnemerindicaties' nummer 1 nee
Then is er voor xpath //brp:afnemerindicaties geen node aanwezig in het antwoord bericht

!-- Volledige expected toegevoegd om te checken dat bij geef details persoon (zonder scoping of filtering op historie)
!-- tsReg en ActieInhoud bij de standaard groepen van relaties en identiteitsgroepen van betrokkenheden mee komen in het bericht.
!-- Dit was eerst een bevinding in Emma: ROOD-816
Then is het antwoordbericht gelijk aan /testcases/BV0GD_Geef_Details_Persoon/expected/expected_1.1_R2195_scenario_1.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 2.    Geef Details persoon voor UC_Kruimeltje
                LT: R2195_LT01
                Condities:
                - Soort dienst Geef Details Persoon
                - Partij rol = Afnemer
                - Afnemerindicatie aanwezig
                - afnemerindicatie attributen geautoriseerd
                - Datum/tijd verval = LEEG
                - Protocolleringsniveau <> Geheim
                Verwacht Resultaat:
                - Afnemerindicatie in bericht
                - Atributen in bericht

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls

Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam                               | partijNaam      | datumEindeVolgen | datumAanvangMaterielePeriode | tsReg                 | dienstId |
| 606417801 | 'Geen pop.bep. levering op basis van afnemerindicatie' | 'Gemeente Utrecht' | 2019-01-01       | 2010-01-01                   | 2014-01-01 T00:00:00Z | 1        |

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'afnemerindicatie' in 'afnemerindicaties' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'partijCode' in 'afnemerindicatie' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'leveringsautorisatieIdentificatie' in 'afnemerindicatie' nummer 1 ja

Scenario: 3.    Geef Details persoon voor UC_Kruimeltje
                LT: R2195_LT03
                Condities:
                - Soort dienst Geef Details Persoon
                - Partij rol = Afnemer
                - Afnemerindicatie aanwezig
                - afnemerindicatie attributen NIET geautoriseerd
                - Datum/tijd verval = LEEG
                - Protocolleringsniveau <> Geheim
                Verwacht Resultaat:
                - Afnemerindicatie in bericht
                - Attributen (partijCode en leveringsautorisatieIdentificatie) uit bericht

Given leveringsautorisatie uit autorisatie/Mutatielevering_obv_afnemerindicatie_afnemerindicatie_niet_geautoriseerd
Given persoonsbeelden uit specials:specials/Jan_xls

Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam              | partijNaam      | datumEindeVolgen | datumAanvangMaterielePeriode | tsReg                 | dienstId |
| 606417801 | 'Afnemerindicatie niet geautoriseerd' | 'Gemeente Utrecht' | 2029-01-01       | 2010-01-01                   | 2014-01-01 T00:00:00Z | 1        |

Given verzoek geef details persoon:
| key                      | value                                 |
| leveringsautorisatieNaam | 'Afnemerindicatie niet geautoriseerd' |
| zendendePartijNaam       | 'Gemeente Utrecht'                       |
| bsn                      | 606417801                             |

Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'afnemerindicatie' in 'afnemerindicaties' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'partijCode' in 'afnemerindicatie' nummer 1 nee
Then is in antwoordbericht de aanwezigheid van 'leveringsautorisatieIdentificatie' in 'afnemerindicatie' nummer 1 nee


Scenario: 4.    Geef Details persoon voor UC_Kruimeltje
                LT: R2195_LT04
                Condities:
                - Soort dienst Geef Details Persoon
                - Partij rol = Afnemer
                - Afnemerindicatie aanwezig
                - afnemerindicatie attributen geautoriseerd
                - Datum/tijd verval = LEEG
                - Protocolleringsniveau = Geheim
                Verwacht Resultaat:
                - Afnemerindicatie NIET in bericht

Given leveringsautorisatie uit autorisatie/Mutatielevering_obv_afnemerindicatie_protniv2

Given persoonsbeelden uit specials:specials/Jan_xls

Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam                        | partijNaam      | datumEindeVolgen | datumAanvangMaterielePeriode | tsReg                 | dienstId |
| 606417801 | 'Mutatielevering obv afnemerindicatie protniv2' | 'Gemeente Utrecht' | 2029-01-01       | 2010-01-01                   | 2014-01-01 T00:00:00Z | 1        |


Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Mutatielevering obv afnemerindicatie protniv2'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'afnemerindicatie' in 'afnemerindicaties' nummer 1 nee
Then is er voor xpath //brp:afnemerindicaties geen node aanwezig in het antwoord bericht

Scenario: 5.    Geef Details persoon voor UC_Kruimeltje
                LT: R2195_LT05
                Condities:
                - Soort dienst Geef Details Persoon
                - Partij rol = Bijhouder
                - Afnemerindicatie aanwezig
                - afnemerindicatie attributen geautoriseerd
                - Datum/tijd verval = LEEG
                - Protocolleringsniveau <> Geheim
                Verwacht Resultaat:
                - Afnemerindicatie in bericht
                - Atributen in bericht

Given leveringsautorisatie uit autorisatie/Mutatielevering_obv_afnemerindicatie_minister
Given persoonsbeelden uit specials:specials/Jan_xls

Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam                        | partijNaam      | tsReg                 | dienstId |
| 606417801 | 'Mutatielevering obv afnemerindicatie Minister' | 'Gemeente Utrecht' | 2014-01-01 T00:00:00Z | 1        |



Given verzoek geef details persoon:
| key                      | value                                           |
| leveringsautorisatieNaam | 'Mutatielevering obv afnemerindicatie Minister' |
| zendendePartijNaam       | 'Minister'                                      |
| rolNaam                  | 'Bijhoudingsorgaan Minister'                    |
| bsn                      | 606417801                                       |

Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'afnemerindicatie' in 'afnemerindicaties' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'partijCode' in 'afnemerindicatie' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'leveringsautorisatieIdentificatie' in 'afnemerindicatie' nummer 1 ja

Scenario: 6.    Geef Details persoon voor UC_Kruimeltje
                LT: R2195_LT06
                Condities:
                - Soort dienst Geef Details Persoon
                - Partij rol = Bijhouder
                - Afnemerindicatie aanwezig
                - afnemerindicatie attributen geautoriseerd
                - Datum/tijd verval = LEEG
                - Protocolleringsniveau = Geheim
                Verwacht Resultaat:
                - Afnemerindicatie NIET in bericht

Given leveringsautorisatie uit autorisatie/Mutatielevering_obv_afnemerindicatie_minister_protniv_geheim
Given persoonsbeelden uit specials:specials/Jan_xls

Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam                                       | partijNaam      | datumEindeVolgen | datumAanvangMaterielePeriode | tsReg                 | dienstId |
| 606417801 | 'Mutatielevering obv afnemerindicatie Minister protniv geheim' | 'Gemeente Utrecht' | 2050-01-01       | 2010-01-01                   | 2014-01-01 T00:00:00Z | 1        |

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Mutatielevering obv afnemerindicatie Minister protniv geheim'
|zendendePartijNaam|'Minister'
|rolNaam| 'Bijhoudingsorgaan Minister'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'afnemerindicatie' in 'afnemerindicaties' nummer 1 nee
Then is er voor xpath //brp:afnemerindicaties geen node aanwezig in het antwoord bericht

Scenario: 7.    Synchroniseer persoon voor UC_Kruimeltje
                LT: R2195_LT07
                Condities:
                - Soort dienst Synchroniseer Persoon
                - Partij rol = Afnemer
                - Afnemerindicatie aanwezig
                - afnemerindicatie attributen geautoriseerd
                - Datum/tijd verval = LEEG
                - Protocolleringsniveau <> Geheim
                Verwacht Resultaat:
                - Afnemerindicatie NIET in bericht

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie

Given persoonsbeelden uit specials:specials/Jan_xls

Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam                               | partijNaam      | datumEindeVolgen | datumAanvangMaterielePeriode | tsReg                 | dienstId |
| 606417801 | 'Geen pop.bep. levering op basis van afnemerindicatie' | 'Gemeente Utrecht' | 2019-01-01       | 2010-01-01                   | 2014-01-01 T00:00:00Z | 1        |

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801


Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd
And heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Geen'

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then heeft het bericht 0 groepen 'afnemerindicaties'
Then is er voor xpath //brp:afnemerindicaties geen node aanwezig in het antwoord bericht

Scenario: 8.1   Geef Details persoon voor UC_Kruimeltje voor leveringsautorisatie Mutatielevering obv afnemerindicatie Bijhouder
                LT: R2195_LT08
                Condities:
                - Soort dienst Geef Details Persoon
                - Partij rol = Bijhouder
                - Afnemerindicatie aanwezig
                - afnemerindicatie attributen geautoriseerd
                - Datum/tijd verval = LEEG
                - Protocolleringsniveau <> Geheim
                Verwacht Resultaat:
                - 2 Afnemerindicaties in bericht

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie, autorisatie/Mutatielevering_obv_afnemerindicatie_bijhouder

Given persoonsbeelden uit specials:specials/Jan_xls

Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam                               | partijNaam      | datumEindeVolgen | datumAanvangMaterielePeriode | tsReg                 | dienstId |
| 606417801 | 'Geen pop.bep. levering op basis van afnemerindicatie' | 'Gemeente Utrecht' | 2019-01-01       | 2010-01-01                   | 2014-01-01 T00:00:00Z | 1        |
| 606417801 | 'Mutatielevering obv afnemerindicatie Bijhouder'       | 'College'       | 2019-01-01       | 2010-01-01                   | 2014-01-01 T00:00:00Z | 1        |

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Mutatielevering obv afnemerindicatie Bijhouder'
|zendendePartijNaam|'College'
|rolNaam| 'Bijhoudingsorgaan College'
|bsn|606417801


Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'afnemerindicatie' in 'afnemerindicaties' nummer 1 ja
!-- R2195_LT09 controle op aanwezigheid eigen afnemerindicatie
Then is in antwoordbericht de aanwezigheid van 'partijCode' in 'afnemerindicatie' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'leveringsautorisatieIdentificatie' in 'afnemerindicatie' nummer 1 ja

!-- R2195_LT09 controle op aanwezigheid afnemerindicatie andere partij
Then is in antwoordbericht de aanwezigheid van 'partijCode' in 'afnemerindicatie' nummer 2 ja
Then is in antwoordbericht de aanwezigheid van 'leveringsautorisatieIdentificatie' in 'afnemerindicatie' nummer 2 ja

Scenario: 8.2   Geef Details persoon voor UC_Kruimeltje voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie
                LT: R2195_LT09
                Condities:
                - Soort dienst Geef Details Persoon
                - Partij rol = Afnemer
                - Afnemerindicatie aanwezig
                - afnemerindicatie attributen geautoriseerd
                - Datum/tijd verval = LEEG
                - Protocolleringsniveau <> Geheim
                Verwacht Resultaat:
                - 2 Afnemerindicaties in bericht

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie, autorisatie/Mutatielevering_obv_afnemerindicatie_minister

Given persoonsbeelden uit specials:specials/Jan_xls

Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam                               | partijNaam      | datumEindeVolgen | datumAanvangMaterielePeriode | tsReg                 | dienstId |
| 606417801 | 'Geen pop.bep. levering op basis van afnemerindicatie' | 'Gemeente Utrecht' | 2019-01-01       | 2010-01-01                   | 2014-01-01 T00:00:00Z | 1        |
| 606417801 | 'Mutatielevering obv afnemerindicatie Minister'        | 'Minister'      | 2019-01-01       | 2010-01-01                   | 2014-01-01 T00:00:00Z | 1        |



Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'afnemerindicatie' in 'afnemerindicaties' nummer 1 ja
!-- R2195_LT10 controle op aanwezigheid eigen afnemerindicatie
Then is in antwoordbericht de aanwezigheid van 'partijCode' in 'afnemerindicatie' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'leveringsautorisatieIdentificatie' in 'afnemerindicatie' nummer 1 ja

!-- R2195_LT10 controle op aanwezigheid afnemerindicatie andere partij
Then is in antwoordbericht de aanwezigheid van 'partijCode' in 'afnemerindicatie' nummer 2 ja
Then is in antwoordbericht de aanwezigheid van 'leveringsautorisatieIdentificatie' in 'afnemerindicatie' nummer 2 ja


Scenario: 9.1   Geef Details persoon voor UC_Kruimeltje
                LT: R2195_LT10
                Condities:
                - Soort dienst Geef Details Persoon
                - Partij rol = Bijhouder
                - Afnemerindicatie aanwezig
                - afnemerindicatie attributen geautoriseerd
                - Datum/tijd verval = LEEG
                - Protocolleringsniveau <> Geheim
                Verwacht Resultaat:
                - Alleen eigen afnemerindicatie in bericht

Given leveringsautorisatie uit autorisatie/Mutatielevering_obv_afnemerindicatie_minister, autorisatie/Mutatielevering_obv_afnemerindicatie_protniv2

Given persoonsbeelden uit specials:specials/Jan_xls

Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam                               | partijNaam      | datumEindeVolgen | datumAanvangMaterielePeriode | tsReg                 | dienstId |
| 606417801 | 'Mutatielevering obv afnemerindicatie protniv2'        | 'Gemeente Utrecht' | 2019-01-01       | 2010-01-01                   | 2014-01-01 T00:00:00Z | 1        |
| 606417801 | 'Mutatielevering obv afnemerindicatie Minister'        | 'Minister'      | 2019-01-01       | 2010-01-01                   | 2014-01-01 T00:00:00Z | 1        |

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Mutatielevering obv afnemerindicatie Minister'
|zendendePartijNaam|'Minister'
|rolNaam| 'Bijhoudingsorgaan Minister'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd

!-- Controle op afnemerindicatie eigen partij
Then heeft in het antwoordbericht 'partijCode' in 'afnemerindicatie' de waarde '199901'

!-- Controle dat er ook echt maar 1 afnemerindicatie is
Then is in antwoordbericht de aanwezigheid van 'partijCode' in 'afnemerindicatie' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'leveringsautorisatieIdentificatie' in 'afnemerindicatie' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'partijCode' in 'afnemerindicatie' nummer 2 nee
Then is in antwoordbericht de aanwezigheid van 'leveringsautorisatieIdentificatie' in 'afnemerindicatie' nummer 2 nee

Scenario: 9.2  Geef Details persoon voor UC_Kruimeltje
                LT: R2195_LT11
                Condities:
                - Soort dienst Geef Details Persoon
                - Partij rol = Afnemer
                - Afnemerindicatie aanwezig
                - afnemerindicatie attributen geautoriseerd
                - Datum/tijd verval = LEEG
                - Protocolleringsniveau = Geheim
                Verwacht Resultaat:
                - Afnemerindicatie andere partij in bericht

Given leveringsautorisatie uit autorisatie/Mutatielevering_obv_afnemerindicatie_minister, autorisatie/Mutatielevering_obv_afnemerindicatie_protniv2
Given persoonsbeelden uit specials:specials/Jan_xls

Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam                               | partijNaam           | datumEindeVolgen | datumAanvangMaterielePeriode | tsReg                 | dienstId |
| 606417801 | 'Mutatielevering obv afnemerindicatie protniv2'        | 'Gemeente Utrecht'   | 2019-01-01       | 2010-01-01                   | 2014-01-01 T00:00:00Z | 1        |
| 606417801 | 'Mutatielevering obv afnemerindicatie Minister'        | 'Minister'           | 2019-01-01       | 2010-01-01                   | 2014-01-01 T00:00:00Z | 1        |

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Mutatielevering obv afnemerindicatie protniv2'
|zendendePartijNaam|'Gemeente Utrecht'
|rolNaam| 'Afnemer'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd

!-- Controle op afnemerindicatie andere partij
Then heeft in het antwoordbericht 'partijCode' in 'afnemerindicatie' de waarde '199901'

!-- Controle dat er ook echt maar 1 afnemerindicatie is
Then is in antwoordbericht de aanwezigheid van 'partijCode' in 'afnemerindicatie' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'leveringsautorisatieIdentificatie' in 'afnemerindicatie' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'partijCode' in 'afnemerindicatie' nummer 2 nee
Then is in antwoordbericht de aanwezigheid van 'leveringsautorisatieIdentificatie' in 'afnemerindicatie' nummer 2 nee