Meta:
@status             Klaar
@sleutelwoorden     GeconverteerdeDataTest

Narrative:
GBA Hervestiging met huwelijk terwijl persoon in buitenland was, met geboorte terwijl persoon in het buitenland was, met wijziing naamgebruik terwijl persoon in buitenland was

Scenario: 0. INIT Vulling GBA Hervestiging met huwelijk en naamgebruik
                LT:

Given leveringsautorisatie uit autorisatie/Levering_obv_doelbinding,
                               autorisatie/Levering_doelbinding_AlleenMatHis,
                               autorisatie/Levering_doelbinding_GeenFMV,
                               autorisatie/Levering_doelbinding_GeenGerelateerde,
                               autorisatie/vektis

Given persoonsbeelden uit specials:specials/Jamie_Niet_Ingezetene2_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering obv doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|427389033

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan Expecteds/Hervestiging2/GDP_INIT_voor_Hervestiging.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 0_AlleenMatHis GBA Hervestiging

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_AlleenMatHis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|427389033

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 0_GeenFMV GBA Hervestiging

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenFMV'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|427389033

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 0_GeenGerelateerde GBA Hervestiging

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenGerelateerde'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|427389033

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1.    GBA Herinschrijving in Nederland met huwelijk en naamgebruik
                LT:
                Verwacht resultaat:
                - Mutatiebericht herinschrijving in Nederland

Given persoonsbeelden uit specials:specials/Jamie_Hervestiging4_xls
When voor persoon 427389033 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering obv doelbinding is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor bron,actie gelijk aan Expecteds/Hervestiging2/Mutatie_na_Hervestiging_huwelijk_geboorte.xml voor expressie //brp:lvg_synVerwerkPersoon

When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering_doelbinding_AlleenMatHis is ontvangen en wordt bekeken
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering_doelbinding_GeenFMV is ontvangen en wordt bekeken
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering_doelbinding_GeenGerelateerde is ontvangen en wordt bekeken
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie vektis is ontvangen en wordt bekeken

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering obv doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|427389033

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht ongeacht elementvolgorde voor bron,actie gelijk aan Expecteds/Hervestiging2/GDP_na_Hervestiging_huwelijk_geboorte.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 1_AlleenMatHis GBA Hervestiging

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_AlleenMatHis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|427389033

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_GeenFMV GBA Hervestiging

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenFMV'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|427389033

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_GeenGerelateerde GBA Hervestiging

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenGerelateerde'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|427389033

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_vektis GBA Hervestiging

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'vektis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|427389033

Then heeft het antwoordbericht verwerking Geslaagd
