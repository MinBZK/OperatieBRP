Meta:
@status             Klaar
@sleutelwoorden     GeconverteerdeDataTest

Narrative:
Leveren van GBA Bijhouding Geboorte met Erkenning

!-- Mutatiebericht niet van toepassing gezien de bron excel enkel een Initiele vulling bevat

Scenario: 0.    Inititiele vulling GBA Bijhouding Geboorte met Erkenning
                LT:

Given leveringsautorisatie uit autorisatie/levering_op_basis_van_afnemerindicatie_Afnemer,
                               autorisatie/Levering_doelbinding_AlleenMatHis,
                               autorisatie/Levering_doelbinding_GeenFMV,
                               autorisatie/Levering_doelbinding_GeenGerelateerde,
                               autorisatie/vektis

Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC20T280_xls

Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|854820425

Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie levering op basis van afnemerindicatie is ontvangen en wordt bekeken


Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam                 | partijNaam         | datumEindeVolgen | datumAanvangMaterielePeriode | tsReg                 | dienstId |
| 854820425 | 'levering op basis van afnemerindicatie' | 'Gemeente Utrecht' |                  |                              | 2014-01-01 T00:00:00Z | 2        |


Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|854820425

Then heeft het antwoordbericht verwerking Geslaagd

Then is het antwoordbericht gelijk aan /testcases/Geconverteerde_Data_Test/Expecteds/GBA_Bijhouding_Geboorte_met_Erkenning.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 0_AlleenMatHis GBA Bijhouding Geboorte met Erkenning

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_AlleenMatHis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|854820425

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 0_GeenFMV GBA Bijhouding Geboorte met Erkenning

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenFMV'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|854820425

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 0_GeenGerelateerde GBA Bijhouding Geboorte met Erkenning

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenGerelateerde'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|854820425

Scenario: 0_vektis GBA Bijhouding Geboorte met Erkenning

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'vektis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|854820425
