Meta:
@status             Klaar
@sleutelwoorden     GeconverteerdeDataTest

Narrative:
GBA Actualisering Binnengemeentelijke verhuizing na Intergemeentelijke verhuizing

Scenario: 0.    INIT Vulling GBA voor Binnengemeentelijke verhuizing
                LT:

Given leveringsautorisatie uit autorisatie/Levering_obv_doelbinding,
                               autorisatie/Levering_doelbinding_AlleenMatHis,
                               autorisatie/Levering_doelbinding_GeenFMV,
                               autorisatie/Levering_doelbinding_GeenGerelateerde,
                               autorisatie/vektis

Given persoonsbeelden uit specials:GBA_BIJH/GBA_BIJH_Binnengemeentelijke_Verhuizing_na_Intergemeentelijk_IV_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering obv doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|941099593

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/Geconverteerde_Data_Test/Expecteds/Binnengem_na_intergem_verhuizing/GDP_voor_binnengemeentelijke_verhuizing.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 0_AlleenMatHis GBA Actualisering voor Binnengemeentelijke verhuizing

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_AlleenMatHis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|941099593

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 0_GeenFMV GBA Actualisering voor Binnengemeentelijke verhuizing

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenFMV'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|941099593

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 0_GeenGerelateerde GBA Actualisering voor Binnengemeentelijke verhuizing

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenGerelateerde'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|941099593

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1.    GBA Actualisering binnengemeentelijke verhuizing
                LT:
                Verwacht resultaat:
                - Mutatiebericht binnengemeentelijke verhuizing

Given persoonsbeelden uit specials:GBA_BIJH/GBA_BIJH_Binnengemeentelijke_Verhuizing_na_Intergemeentelijk_xls

When voor persoon 941099593 wordt de laatste handeling geleverd

When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering obv doelbinding is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan /testcases/Geconverteerde_Data_Test/Expecteds/Binnengem_na_intergem_verhuizing/GBA_BIJH_binnengemeentelijke_verhuizing_mut.xml voor expressie //brp:lvg_synVerwerkPersoon

When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering_doelbinding_AlleenMatHis is ontvangen en wordt bekeken
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering_doelbinding_GeenFMV is ontvangen en wordt bekeken
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering_doelbinding_GeenGerelateerde is ontvangen en wordt bekeken
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie vektis is ontvangen en wordt bekeken

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering obv doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|941099593

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/Geconverteerde_Data_Test/Expecteds/Binnengem_na_intergem_verhuizing/GDP_na_binnengemeentelijke_verhuizing.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 1_AlleenMatHis GBA Actualisering Wijziging voornaam

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_AlleenMatHis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|941099593

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_GeenFMV GBA Actualisering Binnengemeentelijke verhuizing

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenFMV'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|941099593

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_GeenGerelateerde GBA Actualisering Binnengemeentelijke verhuizing

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenGerelateerde'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|941099593

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_Vektis GBA Actualisering Binnengemeentelijke verhuizing

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'vektis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|941099593

Then heeft het antwoordbericht verwerking Geslaagd