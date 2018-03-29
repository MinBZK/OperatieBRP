Meta:
@status             Klaar
@sleutelwoorden     GeconverteerdeDataTest

Narrative:
Van adoptie-ouder (met voornaamwijizging) naar juridisch ouder (DELTAVERS02C10T200)

Scenario: 0. INIT Vulling adoptie ouder naar juridisch ouder
             LT:

Given leveringsautorisatie uit autorisatie/levering_op_basis_van_afnemerindicatie_Afnemer,
                               autorisatie/Levering_doelbinding_AlleenMatHis,
                               autorisatie/Levering_doelbinding_GeenFMV,
                               autorisatie/Levering_doelbinding_GeenGerelateerde,
                               autorisatie/vektis

Given persoonsbeelden uit oranje:DELTAVERS02/DELTAVERS02_INITVULLING_C10T200_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|203767433

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 0_AlleenMatHis adoptie ouder naar juridisch ouder

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_AlleenMatHis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|203767433

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 0_GeenFMV adoptie ouder naar juridisch ouder

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenFMV'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|203767433

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 0_GeenGerelateerde adoptie ouder naar juridisch ouder

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenGerelateerde'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|203767433

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1.    Van adoptie ouder naar Juridisch ouder
                LT:
                Verwacht resultaat:
                - volledig bericht nav wijzingen bij ouder

Given persoonsbeelden uit oranje:DELTAVERS02/DELTAVERS02C10T200_xls
Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam                 | partijNaam         | datumEindeVolgen | datumAanvangMaterielePeriode | tsReg                 | dienstId |
| 203767433 | 'levering op basis van afnemerindicatie' | 'Gemeente Utrecht' |                  |                              | 2014-01-01 T00:00:00Z | 2        |

When voor persoon 203767433 wordt de laatste handeling geleverd

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan /testcases/Geconverteerde_Data_Test/Expecteds/GBA_Bijhouding_AdoptieOuder_naar_JuridischOuder.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Levering_doelbinding_AlleenMatHis is ontvangen en wordt bekeken
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Levering_doelbinding_GeenFMV is ontvangen en wordt bekeken
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Levering_doelbinding_GeenGerelateerde is ontvangen en wordt bekeken

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|203767433

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_AlleenMatHis adoptie ouder naar juridisch ouder

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_AlleenMatHis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|203767433

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_GeenFMV adoptie ouder naar juridisch ouder

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenFMV'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|203767433

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_GeenGerelateerde adoptie ouder naar juridisch ouder

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenGerelateerde'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|203767433

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_vektis adoptie ouder naar juridisch ouder

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'vektis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|203767433

Then heeft het antwoordbericht verwerking Geslaagd