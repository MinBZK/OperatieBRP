Meta:
@status             Klaar
@sleutelwoorden     GeconverteerdeDataTest

Narrative:
Wijzigen .-ouder naar ouder1 (DELTAVERS02C10T10)

Scenario: 0.    INIT Vulling wijzigen punt ouder naar ouder1
                LT:

Given leveringsautorisatie uit autorisatie/levering_op_basis_van_afnemerindicatie_Afnemer,
                               autorisatie/Levering_doelbinding_AlleenMatHis,
                               autorisatie/Levering_doelbinding_GeenFMV,
                               autorisatie/Levering_doelbinding_GeenGerelateerde,
                               autorisatie/vektis

Given persoonsbeelden uit oranje:DELTAVERS02/DELTAVERS02_INITVULLING_C10T10_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|258096329

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 0_AlleenMatHis wijzigen punt ouder naar ouder1

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_AlleenMatHis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|258096329

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 0_GeenFMV wijzigen punt ouder naar ouder1

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenFMV'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|258096329

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 0_GeenGerelateerde wijzigen punt ouder naar ouder1

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenGerelateerde'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|258096329

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1.    Wijziging punt-ouder naar ouder1
                LT:
                Issue: WIT-1222 - foutieve expected nu in scenario.
                Verwacht resultaat:
                - Mutatiebericht voor beeindigen partnerschap



Given persoonsbeelden uit oranje:DELTAVERS02/DELTAVERS02C10T10_xls
Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam                 | partijNaam         | datumEindeVolgen | datumAanvangMaterielePeriode | tsReg                 | dienstId |
| 258096329 | 'levering op basis van afnemerindicatie' | 'Gemeente Utrecht' |                  |                              | 2014-01-01 T00:00:00Z | 2        |

When voor persoon 258096329 wordt de laatste handeling geleverd

When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan /testcases/Geconverteerde_Data_Test/Expecteds/GBA_Bijhouding_wijzigen_punt_ouder_naar_ouder_1.xml voor expressie //brp:lvg_synVerwerkPersoon

When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering_doelbinding_AlleenMatHis is ontvangen en wordt bekeken
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering_doelbinding_GeenFMV is ontvangen en wordt bekeken
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering_doelbinding_GeenGerelateerde is ontvangen en wordt bekeken

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|258096329

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_AlleenMatHis wijzigen punt ouder naar ouder1

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_AlleenMatHis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|258096329

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_GeenFMV wijzigen punt ouder naar ouder1

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenFMV'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|258096329

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_GeenGerelateerde wijzigen punt ouder naar ouder1

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenGerelateerde'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|258096329

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_vektis wijzigen punt ouder naar ouder1

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'vektis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|258096329

Then heeft het antwoordbericht verwerking Geslaagd