Meta:
@status             Klaar
@sleutelwoorden     GeconverteerdeDataTest

Narrative:
Leveren van GBA Bijhouding Persoon met verkrijging NL nationaliteit

Scenario: 0.    Initiele vulling voor verkrijging NL nationaliteit
                LT:
                Verwacht resultaat:
                - Volledig bericht voor verkrijging NL Nationaliteit

Given leveringsautorisatie uit autorisatie/Levering_obv_doelbinding,
                               autorisatie/Levering_doelbinding_AlleenMatHis,
                               autorisatie/Levering_doelbinding_GeenFMV,
                               autorisatie/Levering_doelbinding_GeenGerelateerde,
                               autorisatie/vektis

Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04a_INITVULLING_C10T20_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering obv doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|800570121
Then is het antwoordbericht gelijk aan  /testcases/Geconverteerde_Data_Test/Expecteds/Verkrijgen_NL_nationaitieit/GDP_voor_verkrijgen_nl_nationaliteit.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 0_AlleenMatHis GBA Bijhouding Persoon met verkrijging NL nationaliteit

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_AlleenMatHis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|800570121

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 0_GeenFMV GBA Bijhouding Persoon met verkrijging NL nationaliteit

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenFMV'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|800570121

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 0_GeenGerelateerde GBA Bijhouding Persoon met verkrijging NL nationaliteit

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenGerelateerde'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|800570121

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1.    Mutatiebericht na verkrijging NL nationaltieit
                LT:
                Verwacht resultaat:
                - Mutatiebericht

!-- Mutatiebericht door administratieve handeling verkrijging NL Nationaliteit
Given persoonsbeelden uit specials:GBA_BIJH/GBA_BIJH_Verkrijgen_NL_Nationaliteit_xls
When voor persoon 800570121 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering obv doelbinding is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan /testcases/Geconverteerde_Data_Test/Expecteds/Verkrijgen_NL_nationaitieit/Mutatiebericht_na_verkrijgen_nl_nationaliteit.xml voor expressie //brp:lvg_synVerwerkPersoon

When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering_doelbinding_AlleenMatHis is ontvangen en wordt bekeken
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering_doelbinding_GeenFMV is ontvangen en wordt bekeken
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie vektis is ontvangen en wordt bekeken

!-- Geef details persoon na verkrijging NL Nationaliteit
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering obv doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|800570121

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/Geconverteerde_Data_Test/Expecteds/Verkrijgen_NL_nationaitieit/GDP_na_verkrijgen_nl_nationaliteit.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 1_AlleenMatHis GBA Bijhouding Persoon met verkrijging NL nationaliteit

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_AlleenMatHis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|800570121

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_GeenFMV GBA Bijhouding Persoon met verkrijging NL nationaliteit

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenFMV'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|800570121

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_GeenGerelateerde GBA Bijhouding Persoon met verkrijging NL nationaliteit

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenGerelateerde'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|800570121

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_vektis GBA Bijhouding Persoon met verkrijging NL nationaliteit

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'vektis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|800570121

Then heeft het antwoordbericht verwerking Geslaagd
