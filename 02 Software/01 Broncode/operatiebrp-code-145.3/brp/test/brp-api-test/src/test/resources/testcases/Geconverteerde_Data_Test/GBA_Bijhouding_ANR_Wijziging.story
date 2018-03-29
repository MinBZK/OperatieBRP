Meta:
@status             Klaar
@sleutelwoorden     GeconverteerdeDataTest

Narrative:
Leveren van GBA Bijhouding Persoon ANR wijziging

Scenario: 0.    INIT Vulling Jan voor ANR wijziging
                LT:
                Verwacht resultaat:
                - Volledig bericht voor ANR wijziging

Given leveringsautorisatie uit autorisatie/Levering_obv_doelbinding,
                               autorisatie/Levering_doelbinding_AlleenMatHis,
                               autorisatie/Levering_doelbinding_GeenFMV,
                               autorisatie/Levering_doelbinding_GeenGerelateerde,
                               autorisatie/vektis

Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering obv doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
Then is het antwoordbericht gelijk aan  /testcases/Geconverteerde_Data_Test/Expecteds/ANR_Wijziging/GDP_voor_ANR-Wijziging.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 0_AlleenMatHis

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_AlleenMatHis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan  /testcases/Geconverteerde_Data_Test/Expecteds/ANR_Wijziging/GDP_voor_ANR_Wijziging_AlleenMatHis.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 0_GeenFMV

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenFMV'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan  /testcases/Geconverteerde_Data_Test/Expecteds/ANR_Wijziging/GDP_voor_ANR_Wijziging_GeenFMV.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 0_GeenGerelateerde

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenGerelateerde'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan  /testcases/Geconverteerde_Data_Test/Expecteds/ANR_Wijziging/GDP_voor_ANR_Wijziging_GeenGerelateerde.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 1.    GBA ANR wijziging voor JAN
                LT:
                Verwacht resultaat:
                - Mutatiebericht na ANR wijziging

!-- Mutatiebericht door administratieve handeling ANR wijziging
Given persoonsbeelden uit specials:specials/Jan_na_ANR_wijziging_xls
When voor persoon 606417801 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering obv doelbinding is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan /testcases/Geconverteerde_Data_Test/Expecteds/ANR_Wijziging/GBA_Bijhouding_ANR_Wijziging_Mutatie.xml voor expressie //brp:lvg_synVerwerkPersoon

When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering_doelbinding_AlleenMatHis is ontvangen en wordt bekeken
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering_doelbinding_GeenFMV is ontvangen en wordt bekeken
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie vektis is ontvangen en wordt bekeken

!-- Geef details persoon na ANR wijziging voor JAN
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering obv doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/Geconverteerde_Data_Test/Expecteds/ANR_Wijziging/GDP_na_ANR_Wijziging.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 1_AlleenMatHis

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_AlleenMatHis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/Geconverteerde_Data_Test/Expecteds/ANR_Wijziging/GDP_na_ANR_Wijziging_AlleenMatHis.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 1_GeenFMV

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenFMV'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/Geconverteerde_Data_Test/Expecteds/ANR_Wijziging/GDP_na_ANR_Wijziging_Geen_FMV.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 1_GeenGerelateerde

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenGerelateerde'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/Geconverteerde_Data_Test/Expecteds/ANR_Wijziging/GDP_na_ANR_Wijziging_GeenGerelateerde.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 1_vektis

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'vektis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd