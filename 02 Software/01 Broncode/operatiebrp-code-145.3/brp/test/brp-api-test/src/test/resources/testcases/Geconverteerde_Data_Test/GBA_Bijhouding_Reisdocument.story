Meta:
@status             Klaar
@sleutelwoorden     GeconverteerdeDataTest

Narrative:
Leveren van GBA Bijhouding Persoon nieuwe uitgifte Reisdocument

Scenario: 0.    Initiele vulling voor nieuwe uitgifte Reisdocument (oude reisdocument vervalt)
                LT:
                Verwacht resultaat:
                - GDP voor nieuwe uitgifte Reisdocument (oude reisdocument vervalt)

Given leveringsautorisatie uit autorisatie/Levering_obv_doelbinding,
                               autorisatie/Levering_doelbinding_AlleenMatHis,
                               autorisatie/Levering_doelbinding_GeenFMV,
                               autorisatie/Levering_doelbinding_GeenGerelateerde,
                               autorisatie/vektis

Given persoonsbeelden uit specials:specials/Reisdocument_initiele_vulling_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering obv doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|995347529
Then is het antwoordbericht gelijk aan  /testcases/Geconverteerde_Data_Test/Expecteds/Reisdocument/GDP_voor_nieuwe_uitgifte_reisdocument.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 0_AlleenMatHis

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_AlleenMatHis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|995347529

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 0_GeenFMV

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenFMV'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|995347529

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 0_GeenGerelateerde

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenGerelateerde'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|995347529

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1.    Mutatiebericht na nieuwe uitgifte Reisdocument (oude reisdocument vervalt)
                LT:
                Verwacht resultaat:
                - mutatiebericht

!-- mutatiebericht door administratieve handeling nieuwe uitgifte Reisdocument, oude document krijgt aanduiding inhouding
Given persoonsbeelden uit specials:specials/Reisdocument_delta_xls
When voor persoon 995347529 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering obv doelbinding is ontvangen en wordt bekeken

Then is het synchronisatiebericht gelijk aan /testcases/Geconverteerde_Data_Test/Expecteds/Reisdocument/Mutatiebericht_na_nieuw_Reisdocument.xml voor expressie //brp:lvg_synVerwerkPersoon

When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering_doelbinding_AlleenMatHis is ontvangen en wordt bekeken
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering_doelbinding_GeenFMV is ontvangen en wordt bekeken

!-- Geef details persoon na nieuwe uitgifte Reisdocument
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering obv doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|995347529

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/Geconverteerde_Data_Test/Expecteds/Reisdocument/GDP_na_nieuwe_uitgifte_reisdocument.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 1_AlleenMatHis

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_AlleenMatHis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|995347529

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_GeenFMV

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenFMV'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|995347529

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_GeenGerelateerde

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenGerelateerde'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|995347529

Then heeft het antwoordbericht verwerking Geslaagd
