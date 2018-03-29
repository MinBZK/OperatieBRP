Meta:
@status             Klaar
@sleutelwoorden     GeconverteerdeDataTest

Narrative:
Leveren van GBA Bijhouding Persoon met onderzoek

Scenario: 0.   Initiele vulling voor onderzoek (op naamgebruik code)
                LT:
                Verwacht resultaat:
                - Volledig bericht voor Onderzoek

Given leveringsautorisatie uit autorisatie/Levering_obv_doelbinding,
                               autorisatie/Levering_doelbinding_AlleenMatHis,
                               autorisatie/Levering_doelbinding_GeenFMV,
                               autorisatie/Levering_doelbinding_GeenGerelateerde,
                               autorisatie/vektis

Given persoonsbeelden uit oranje:DELTAOND/DELTAOND_INITVULLING_C10T10_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering obv doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|948226249
Then is het antwoordbericht gelijk aan  /testcases/Geconverteerde_Data_Test/Expecteds/Onderzoek/GDP_voor_onderzoek.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 0_AlleenMatHis

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_AlleenMatHis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|948226249

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 0_GeenFMV

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenFMV'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|948226249

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 0_GeenGerelateerde

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenGerelateerde'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|948226249

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1.    Mutatiebericht na Onderzoek (op naamgebruik code)
                LT:
                Verwacht resultaat:
                - Mutatiebericht

!-- Mutatiebericht door administratieve handeling onderzoek
Given persoonsbeelden uit specials:GBA_BIJH/GBA_bijh_Onderzoek_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Levering obv doelbinding is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan /testcases/Geconverteerde_Data_Test/Expecteds/Onderzoek/Mutatiebericht_na_onderzoek.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Levering_doelbinding_AlleenMatHis is ontvangen en wordt bekeken
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Levering_doelbinding_GeenFMV is ontvangen en wordt bekeken
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie vektis is ontvangen en wordt bekeken

!-- Geef details persoon na onderzoek
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering obv doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|948226249

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan /testcases/Geconverteerde_Data_Test/Expecteds/Onderzoek/GDP_na_Onderzoek.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 1_AlleenMatHis

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_AlleenMatHis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|948226249

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_GeenFMV

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenFMV'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|948226249

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_GeenGerelateerde

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenGerelateerde'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|948226249

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_vektis

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'vektis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|948226249

Then heeft het antwoordbericht verwerking Geslaagd
