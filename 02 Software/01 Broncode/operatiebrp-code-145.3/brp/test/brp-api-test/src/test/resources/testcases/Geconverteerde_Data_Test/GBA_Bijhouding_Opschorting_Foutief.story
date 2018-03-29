Meta:
@status             Klaar
@sleutelwoorden     GeconverteerdeDataTest

Narrative:
Leveren van GBA Bijhouding voor opschorting overlijden met opschortreden Foutief

Scenario: 0.    Initiele vulling voor opschorting overlijden met opschortreden Foutief
                LT:
                Verwacht resultaat:
                - Geef details persoon voor opschorting overlijden met opschortreden Foutief

Given leveringsautorisatie uit autorisatie/Levering_obv_doelbinding,
                               autorisatie/Levering_doelbinding_AlleenMatHis,
                               autorisatie/Levering_doelbinding_GeenFMV,
                               autorisatie/Levering_doelbinding_GeenGerelateerde,
                               autorisatie/vektis

Given persoonsbeelden uit specials:specials/GBA_BIJH_Opschorting_foutief_initielevulling_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering obv doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|201573337
Then is het antwoordbericht gelijk aan  /testcases/Geconverteerde_Data_Test/Expecteds/Opschorting/GDP_voor_opschorting.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 0_AlleenMatHis

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_AlleenMatHis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|201573337

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 0_GeenFMV

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenFMV'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|201573337

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 0_GeenGerelateerde

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenGerelateerde'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|201573337

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1.    Mutatiebericht na opschorting overlijden met opschortreden Foutief
                LT:
                Verwacht resultaat:
                - Mutatiebericht

!-- Mutatiebericht door administratieve handeling opschorting overlijden met opschortreden Foutief
Given persoonsbeelden uit specials:specials/GBA_BIJH_Opschorting_foutief_xls
When voor persoon 201573337 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering obv doelbinding is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan /testcases/Geconverteerde_Data_Test/Expecteds/Opschorting/Mutatiebericht_na_opschorting.xml voor expressie //brp:lvg_synVerwerkPersoon

When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering_doelbinding_AlleenMatHis is ontvangen en wordt bekeken
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering_doelbinding_GeenFMV is ontvangen en wordt bekeken
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie vektis is ontvangen en wordt bekeken

!-- Geef details persoon na Intergemeentelijke verhuizing
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering obv doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|201573337

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/Geconverteerde_Data_Test/Expecteds/Opschorting/GDP_na_opschorting.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

!-- Door nadere bijhoudingsaard = Fout geen geef details persoon meer mogelijk