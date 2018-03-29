Meta:
@status             Klaar
@sleutelwoorden     GeconverteerdeDataTest

Narrative:
Leveren van GBA Bijhouding Tweeling wordt geboren

Scenario: 0.    Initiele vulling voor Geboorte van een tweeling
                LT:
                Omdat we de initiele vulling niet makkelijk kunnen leveren van een excel met delta 2 excel files gebruikt
                Bertha_Voor_Geboorte_Tweeling_xls is gelijk aan Bertha_Geboorte_Tweeling_xls maar dan zonder de mutatie van de 2 kinderen.
                Verwacht resultaat:
                - GeefDetails persoon van Bertha voor de geboorte
                - Mutatie bericht met geboorte kinderen
                - Volledig bericht met geboorte kinderen


Given persoonsbeelden uit specials:specials/Bertha_Voor_Geboorte_Tweeling_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering obv doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|773201993
Then is het antwoordbericht gelijk aan /testcases/Geconverteerde_Data_Test/Expecteds/GBA_Bijhouding_Voor_Geboorte_Tweeling.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

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

Then heeft het antwoordbericht verwerking Geslaagd

Scenario:  1. Mutatie en volledig bericht van persoon na geboorte
              LT:
Given persoonsbeelden uit specials:specials/Bertha_Geboorte_Tweeling_xls
When voor persoon 773201993 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering obv doelbinding is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan /testcases/Geconverteerde_Data_Test/Expecteds/GBA_Bijhouding_Geboorte_Tweeling_Mutatie.xml voor expressie //brp:lvg_synVerwerkPersoon

When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering_doelbinding_AlleenMatHis is ontvangen en wordt bekeken
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering_doelbinding_GeenFMV is ontvangen en wordt bekeken


Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering obv doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|773201993

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan Expecteds/GBA_Bijhouding_Geboorte_Tweeling_GDP.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R


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

Scenario: 1_GeenGerelateerde

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenGerelateerde'
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