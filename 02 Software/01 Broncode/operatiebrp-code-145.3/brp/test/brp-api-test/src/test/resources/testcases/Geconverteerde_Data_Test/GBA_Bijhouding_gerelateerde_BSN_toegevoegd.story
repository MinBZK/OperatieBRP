Meta:
@status             Klaar
@sleutelwoorden     GeconverteerdeDataTest

Narrative:
Leveren van GBA Bijhouding toevoeging BSN/ANR bij gerelateerde persoon

Scenario: 0.   INIT Vulling Bertha is getrouwd met Jan, Jan krijgt BSN toegevoegd
                LT:
                Verwacht resultaat:
                - Volledig bericht

Given leveringsautorisatie uit autorisatie/Levering_obv_doelbinding,
                               autorisatie/Levering_doelbinding_AlleenMatHis,
                               autorisatie/Levering_doelbinding_GeenFMV,
                               autorisatie/Levering_doelbinding_GeenGerelateerde,
                               autorisatie/vektis

Given persoonsbeelden uit specials:specials/Huwelijkspartner_BSN_Toegevoegd_mutatie_IV_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering obv doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|773201993

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/Geconverteerde_Data_Test/Expecteds/GBA_Bijhouding_BSN_gerelateerde_toegevoegd_IV_GDP.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 0_AlleenMatHis

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_AlleenMatHis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|773201993

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 0_GeenFMV

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenFMV'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|773201993

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 0_GeenGerelateerde

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenGerelateerde'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|773201993

Scenario: 1.    Bertha is getrouwd met Jan, Jan krijgt BSN toegevoegd mutatie
                LT:
                Verwacht resultaat:

Given persoonsbeelden uit specials:specials/Huwelijkspartner_BSN_Toegevoegd_mutatie_xls

When voor persoon 773201993 wordt de laatste handeling geleverd

When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering obv doelbinding is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan /testcases/Geconverteerde_Data_Test/Expecteds/GBA_Bijhouding_BSN_gerelateerde_toegevoegd_2.xml voor expressie //brp:lvg_synVerwerkPersoon

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering obv doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|773201993

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/Geconverteerde_Data_Test/Expecteds/GBA_Bijhouding_BSN_gerelateerde_toegevoegd_2_GDP.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 1_AlleenMatHis

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_AlleenMatHis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|773201993

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_GeenFMV

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenFMV'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|773201993

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_vektis

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'vektis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|773201993

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_GeenGerelateerde

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenGerelateerde'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|773201993
