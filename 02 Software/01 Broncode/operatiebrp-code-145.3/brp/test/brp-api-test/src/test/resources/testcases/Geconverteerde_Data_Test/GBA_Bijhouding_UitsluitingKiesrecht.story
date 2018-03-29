Meta:
@status             Klaar
@sleutelwoorden     GeconverteerdeDataTest

Narrative:
GBA Actualisering indicatie uitsluiting kiesrecht

Scenario: 0.    INIT Vulling GBA Actualisering indicatie uitsluiting kiesrecht
                LT:

Given leveringsautorisatie uit autorisatie/Levering_obv_doelbinding,
                               autorisatie/Levering_doelbinding_AlleenMatHis,
                               autorisatie/Levering_doelbinding_GeenFMV,
                               autorisatie/Levering_doelbinding_GeenGerelateerde,
                               autorisatie/vektis

Given persoonsbeelden uit oranje:DELTAVERS13/DELTAVERS13_INITVULLING_C10T40_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering obv doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|629228425

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan Expecteds/GBA_BIJH_UitsluitingKiesrecht_init.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 0_AlleenMatHis GBA Actualisering indicatie uitsluiting kiesrecht

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_AlleenMatHis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|629228425

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 0_GeenFMV GBA Actualisering indicatie uitsluiting kiesrecht

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenFMV'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|629228425

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 0_GeenGerelateerde GBA Actualisering indicatie uitsluiting kiesrecht

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenGerelateerde'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|629228425

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1.    GBA Actualisering indicatie uitsluiting kiesrecht
                LT:
                Verwacht resultaat:
                - Mutatiebericht actualisering indicatie uitsluiting kiesrecht

Given persoonsbeelden uit oranje:DELTAVERS13/DELTAVERS13C10T40_xls

When voor persoon 629228425 wordt de laatste handeling geleverd

When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering obv doelbinding is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan Expecteds/GBA_BIJH_UitsluitingKiesrecht_mutatie.xml voor expressie //brp:lvg_synVerwerkPersoon

When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering_doelbinding_AlleenMatHis is ontvangen en wordt bekeken
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering_doelbinding_GeenFMV is ontvangen en wordt bekeken
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering_doelbinding_GeenGerelateerde is ontvangen en wordt bekeken

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering obv doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|629228425

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan Expecteds/GBA_BIJH_UitsluitingKiesrecht_volledig_na_mutatie.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 1_AlleenMatHis GBA Actualisering indicatie uitsluiting kiesrecht

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_AlleenMatHis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|629228425

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_GeenFMV GBA Actualisering indicatie uitsluiting kiesrecht

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenFMV'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|629228425

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_GeenGerelateerde GBA Actualisering indicatie uitsluiting kiesrecht

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenGerelateerde'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|629228425

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_vektis GBA Actualisering indicatie uitsluiting kiesrecht

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'vektis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|629228425

Then heeft het antwoordbericht verwerking Geslaagd