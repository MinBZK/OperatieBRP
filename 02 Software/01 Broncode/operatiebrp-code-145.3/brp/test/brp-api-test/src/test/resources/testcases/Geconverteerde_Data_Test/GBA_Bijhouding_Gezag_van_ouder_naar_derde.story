Meta:
@status             Klaar
@sleutelwoorden     GeconverteerdeDataTest

Narrative:
Van gezag ouder naar gezag derde

Scenario: 0. INIT Vulling van pl met ouder heeft gezag
             LT:

Given leveringsautorisatie uit autorisatie/levering_op_basis_van_afnemerindicatie_Afnemer,
                               autorisatie/Levering_doelbinding_AlleenMatHis,
                               autorisatie/Levering_doelbinding_GeenFMV,
                               autorisatie/Levering_doelbinding_GeenGerelateerde,
                               autorisatie/vektis

Given persoonsbeelden uit oranje:DELTAVERS11/DELTAVERS11_INITVULLING_C10T160_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|950878601

Then heeft het antwoordbericht verwerking Geslaagd


Scenario: 0_AlleenMatHis van pl met ouder heeft gezag

Given persoonsbeelden uit oranje:DELTAVERS11/DELTAVERS11_INITVULLING_C10T160_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_AlleenMatHis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|950878601

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 0_GeenFMV van pl met ouder heeft gezag

Given persoonsbeelden uit oranje:DELTAVERS11/DELTAVERS11_INITVULLING_C10T160_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenFMV'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|950878601

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 0_GeenGerelateerde van pl met ouder heeft gezag

Given persoonsbeelden uit oranje:DELTAVERS11/DELTAVERS11_INITVULLING_C10T160_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenGerelateerde'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|950878601

Then heeft het antwoordbericht verwerking Geslaagd


Scenario: 1.    Van ouder met gezag naar derde heeft gezag
                LT:
                Verwacht resultaat:
                - mutatie bericht nav wijzingen gezag

Given persoonsbeelden uit specials:GBA_BIJH/GBA_BIJH_vanOuder_met_gezag_naar_derde_met_gezag_xls
Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam                 | partijNaam         | datumEindeVolgen | datumAanvangMaterielePeriode | tsReg                 | dienstId |
| 950878601 | 'levering op basis van afnemerindicatie' | 'Gemeente Utrecht' |                  |                              | 2014-01-01 T00:00:00Z | 2        |

When voor persoon 950878601 wordt de laatste handeling geleverd

When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan /testcases/Geconverteerde_Data_Test/Expecteds/GBA_Bijhouding_Gezag_van_ouder_met_gezag_naar_derde.xml voor expressie //brp:lvg_synVerwerkPersoon

When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering_doelbinding_AlleenMatHis is ontvangen en wordt bekeken
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering_doelbinding_GeenFMV is ontvangen en wordt bekeken
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering_doelbinding_GeenGerelateerde is ontvangen en wordt bekeken
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie vektis is ontvangen en wordt bekeken

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|950878601

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_AlleenMatHis derde heeft gezag

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_AlleenMatHis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|950878601

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_GeenFMV derde heeft gezag

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenFMV'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|950878601

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_GeenGerelateerde derde heeft gezag

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenGerelateerde'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|950878601

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_vektis

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'vektis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|950878601

Then heeft het antwoordbericht verwerking Geslaagd