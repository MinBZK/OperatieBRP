Meta:
@status             Klaar
@sleutelwoorden     GeconverteerdeDataTest

Narrative:
GBA Actualisering overlijden

Scenario: 0.    INIT Vulling GBA Actualisering overlijden
                LT:

Given leveringsautorisatie uit autorisatie/Levering_obv_doelbinding,
                               autorisatie/Levering_doelbinding_AlleenMatHis,
                               autorisatie/Levering_doelbinding_GeenFMV,
                               autorisatie/Levering_doelbinding_GeenGerelateerde,
                               autorisatie/vektis

Given persoonsbeelden uit oranje:DELTAVERS06a/DELTAVERS06a_INITVULLING_C10T10_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering obv doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|201573337

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan Expecteds/GBA_BIJH_overlijden_init.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 0_AlleenMatHis GBA Actualisering overlijden

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_AlleenMatHis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|201573337

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 0_GeenFMV GBA Actualisering overlijden

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenFMV'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|201573337

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 0_GeenGerelateerde GBA Actualisering overlijden

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenGerelateerde'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|201573337

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1.    GBA Actualisering overlijden
                LT:
                Verwacht resultaat:
                - Mutatiebericht overlijden persoon
                - Toevoeging van voorkomen overlijden
                - Toevoeging van voorkomen van beeindiging relatie met reden einde = O

Given persoonsbeelden uit specials:GBA_BIJH/GBA_BIJH_overlijden_xls

When voor persoon 201573337 wordt de laatste handeling geleverd

When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering obv doelbinding is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan Expecteds/GBA_BIJH_overlijden_mutatie.xml voor expressie //brp:lvg_synVerwerkPersoon

When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering_doelbinding_AlleenMatHis is ontvangen en wordt bekeken
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering_doelbinding_GeenFMV is ontvangen en wordt bekeken
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering_doelbinding_GeenGerelateerde is ontvangen en wordt bekeken
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie vektis is ontvangen en wordt bekeken

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering obv doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|201573337

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan Expecteds/GBA_BIJH_overlijden_volledig_na_mutatie.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 1_AlleenMatHis GBA Actualisering overlijden

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_AlleenMatHis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|201573337

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_GeenFMV GBA Actualisering overlijden

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenFMV'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|201573337

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_GeenGerelateerde GBA Actualisering overlijden

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenGerelateerde'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|201573337

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_vektis GBA Actualisering overlijden

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'vektis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|201573337

Then heeft het antwoordbericht verwerking Geslaagd
