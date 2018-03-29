Meta:
@status             Klaar
@sleutelwoorden     GeconverteerdeDataTest

Narrative:
Leveren van GBA Bijhouding Omzetting gereg. partnerschap naar Huwelijk met naamswijziging partner (DELTAVERS05C10T310)

Scenario: 0. INIT Vulling GBA Bijhouding Omzetting gereg. partnerschap
                LT:

Given leveringsautorisatie uit autorisatie/levering_op_basis_van_afnemerindicatie_Afnemer,
                               autorisatie/Levering_doelbinding_AlleenMatHis,
                               autorisatie/Levering_doelbinding_GeenFMV,
                               autorisatie/Levering_doelbinding_GeenGerelateerde,
                               autorisatie/vektis

Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05_INITVULLING_C10T310_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|434587977

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 0_AlleenMatHis GBA Bijhouding Omzetting gereg. partnerschap

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_AlleenMatHis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|434587977

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 0_GeenFMV GBA Bijhouding Omzetting gereg. partnerschap

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenFMV'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|434587977

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 0_GeenGerelateerde GBA Bijhouding Omzetting gereg. partnerschap

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenGerelateerde'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|434587977

Then heeft het antwoordbericht verwerking Geslaagd


Scenario: 1.    GBA Omzetting partnerschap in huwelijk met naamswijziging bij partner
                LT:
                Verwacht resultaat:
                - Mutatiebericht voor beeindigen partnerschap

Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T310_xls
Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam                 | partijNaam         | datumEindeVolgen | datumAanvangMaterielePeriode | tsReg                 | dienstId |
| 434587977 | 'levering op basis van afnemerindicatie' | 'Gemeente Utrecht' |                  |                              | 2014-01-01 T00:00:00Z | 2        |

When voor persoon 434587977 wordt de laatste handeling geleverd

When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan /testcases/Geconverteerde_Data_Test/Expecteds/GBA_BIJH_omzetting_huwelijk_partnerschap.xml voor expressie //brp:lvg_synVerwerkPersoon

When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering_doelbinding_AlleenMatHis is ontvangen en wordt bekeken
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering_doelbinding_GeenFMV is ontvangen en wordt bekeken
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering_doelbinding_GeenGerelateerde is ontvangen en wordt bekeken
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie vektis is ontvangen en wordt bekeken


Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|434587977

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_AlleenMatHis GBA Bijhouding Omzetting gereg. partnerschap

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_AlleenMatHis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|434587977

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_GeenFMV GBA Bijhouding Omzetting gereg. partnerschap

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenFMV'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|434587977

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_GeenGerelateerde GBA Bijhouding Omzetting gereg. partnerschap

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenGerelateerde'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|434587977

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_vektis GBA Bijhouding Omzetting gereg. partnerschap

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'vektis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|434587977

Then heeft het antwoordbericht verwerking Geslaagd

