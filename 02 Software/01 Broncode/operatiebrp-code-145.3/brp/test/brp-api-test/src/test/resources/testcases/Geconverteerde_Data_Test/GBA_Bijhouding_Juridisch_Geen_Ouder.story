Meta:
@status             Klaar
@sleutelwoorden     GeconverteerdeDataTest

Narrative:
Leveren van persoon met juridisch geen ouder

Scenario: 0.   GBA Initiele vulling juridisch geen ouder
                LT:
                Verwacht resultaat:
                - Volledig bericht persoon zonder 'juridische' ouders

Given leveringsautorisatie uit autorisatie/Levering_obv_doelbinding,
                               autorisatie/Levering_doelbinding_AlleenMatHis,
                               autorisatie/Levering_doelbinding_GeenFMV,
                               autorisatie/Levering_doelbinding_GeenGerelateerde,
                               autorisatie/vektis

Given persoonsbeelden uit specials:GBA_BIJH/OUDS01C20T10_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering obv doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|317118985

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/Geconverteerde_Data_Test/Expecteds/GBA_BIJH_JuridischGeenOuder.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 0_AlleenMatHis

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_AlleenMatHis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|317118985

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 0_GeenFMV

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenFMV'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|317118985

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 0_GeenGerelateerde

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenGerelateerde'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|317118985

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 0_Vektis

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'vektis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|317118985

Then heeft het antwoordbericht verwerking Geslaagd

!-- ----------------

Scenario: 1.    Mutatiebericht na juridisch ouder toevoeging
                LT:
                Verwacht resultaat:
                - Mutatiebericht

!-- Mutatiebericht door toevoegen juridisch ouder
Given persoonsbeelden uit specials:GBA_BIJH/GBA_BIJH_Juridisch_geen_ouder_naar_juridisch_ouder_xls
When voor persoon 317118985 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering obv doelbinding is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan /testcases/Geconverteerde_Data_Test/Expecteds/GBA_BIJH_Juridisch_geen_ouder_mutatiebericht.xml voor expressie //brp:lvg_synVerwerkPersoon

When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering_doelbinding_AlleenMatHis is ontvangen en wordt bekeken
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering_doelbinding_GeenFMV is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht voor leveringsautorisatie vektis


!-- Geef details persoon na Intergemeentelijke verhuizing
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering obv doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|317118985

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/Geconverteerde_Data_Test/Expecteds/GBA_BIJH_Juridisch_geen_ouder_volledigbericht_na_mutatie.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 1_AlleenMatHis Juridisch geen ouder naar juridisch wel ouder

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_AlleenMatHis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|317118985

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_GeenFMV Juridisch geen ouder naar juridisch wel ouder

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenFMV'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|317118985

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_GeenGerelateerde Juridisch geen ouder naar juridisch wel ouder

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenGerelateerde'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|317118985

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_vektis Juridisch geen ouder naar juridisch wel ouder

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'vektis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|317118985

Then heeft het antwoordbericht verwerking Geslaagd