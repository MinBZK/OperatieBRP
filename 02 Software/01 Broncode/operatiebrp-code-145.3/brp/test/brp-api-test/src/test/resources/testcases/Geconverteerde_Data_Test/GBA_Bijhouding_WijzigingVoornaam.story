Meta:
@status             Klaar
@sleutelwoorden     GeconverteerdeDataTest

Narrative:
GBA Actualisering Wijziging voornaam

Scenario: 0.    INIT Vulling GBA Actualisering Wijziging voornaam
                LT:

Given leveringsautorisatie uit autorisatie/Levering_obv_doelbinding,
                               autorisatie/Levering_doelbinding_AlleenMatHis,
                               autorisatie/Levering_doelbinding_GeenFMV,
                               autorisatie/Levering_doelbinding_GeenGerelateerde,
                               autorisatie/vektis

Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01a_INITVULLING_C10T10_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering obv doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|854820425

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan Expecteds/GBA_BIJH_Voornaam_init.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 0_AlleenMatHis GBA Actualisering Wijziging voornaam

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_AlleenMatHis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|854820425

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 0_GeenFMV GBA Actualisering Wijziging voornaam

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenFMV'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|854820425

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 0_GeenGerelateerde GBA Actualisering Wijziging voornaam

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenGerelateerde'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|854820425

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1.    GBA Actualisering wijziging voornaam
                LT:
                Verwacht resultaat:
                - Mutatiebericht wijziging voornaam persoon

Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T10_xls

When voor persoon 854820425 wordt de laatste handeling geleverd

When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering obv doelbinding is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan Expecteds/GBA_BIJH_voornaam_mutatie.xml voor expressie //brp:lvg_synVerwerkPersoon

When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering_doelbinding_AlleenMatHis is ontvangen en wordt bekeken
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering_doelbinding_GeenFMV is ontvangen en wordt bekeken
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering_doelbinding_GeenGerelateerde is ontvangen en wordt bekeken
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie vektis is ontvangen en wordt bekeken

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering obv doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|854820425

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan Expecteds/GBA_BIJH_voornaam_volledig_na_mutatie.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 1_AlleenMatHis GBA Actualisering Wijziging voornaam

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_AlleenMatHis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|854820425

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_GeenFMV GBA Actualisering Wijziging voornaam

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenFMV'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|854820425

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_GeenGerelateerde GBA Actualisering Wijziging voornaam

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenGerelateerde'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|854820425

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_Vektis GBA Actualisering Wijziging voornaam

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'vektis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|854820425

Then heeft het antwoordbericht verwerking Geslaagd