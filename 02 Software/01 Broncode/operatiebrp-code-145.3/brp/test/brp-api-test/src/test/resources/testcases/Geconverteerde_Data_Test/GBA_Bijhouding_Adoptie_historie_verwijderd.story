Meta:
@status             Klaar
@sleutelwoorden     GeconverteerdeDataTest

Narrative:
Leveren van GBA Bijhouding Persoon Adoptie, later ook historie oude ouders verwijderd

Scenario: 0.    Initiele vulling kind voor adoptie
                LT:
                Verwacht resultaat:
                - Volledig bericht voor kind voor adoptie

Given leveringsautorisatie uit autorisatie/Levering_obv_doelbinding,
                               autorisatie/Levering_doelbinding_AlleenMatHis,
                               autorisatie/Levering_doelbinding_GeenFMV,
                               autorisatie/Levering_doelbinding_GeenGerelateerde,
                               autorisatie/vektis

Given persoonsbeelden uit specials:specials/IV_Kind_voor_adoptie_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering obv doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|667626761
Then is het antwoordbericht gelijk aan  /testcases/Geconverteerde_Data_Test/Expecteds/GBA_BIJH_voor_adoptie.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 0_AlleenMatHis

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_AlleenMatHis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|667626761

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 0_GeenFMV

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenFMV'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|667626761

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 0_GeenGerelateerde

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenGerelateerde'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|667626761

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1.    Mutatiebericht na adoptie
                LT:
                Verwacht resultaat:
                - Mutatiebericht

!-- Mutatiebericht door adoptie
Given persoonsbeelden uit specials:specials/Delta_Kind_na_adoptie_xls
When voor persoon 667626761 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering obv doelbinding is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor ouder gelijk aan /testcases/Geconverteerde_Data_Test/Expecteds/GBA_BIJH_na_adoptie_mutatiebericht.xml voor expressie //brp:lvg_synVerwerkPersoon

When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering_doelbinding_AlleenMatHis is ontvangen en wordt bekeken
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering_doelbinding_GeenFMV is ontvangen en wordt bekeken
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie vektis is ontvangen en wordt bekeken

!-- Geef details persoon na adoptie
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering obv doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|667626761

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht ongeacht elementvolgorde voor ouder gelijk aan /testcases/Geconverteerde_Data_Test/Expecteds/GBA_BIJH_na_adoptie.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 1_AlleenMatHis

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_AlleenMatHis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|667626761

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_GeenFMV

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenFMV'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|667626761

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_GeenGerelateerde

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenGerelateerde'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|667626761

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_vektis

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenGerelateerde'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|667626761

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 2.    Volledig bericht na adoptie EN na verwijderen historie ouders
                LT:
                Verwacht resultaat:
                - Volledig bericht

!-- Volledig bericht door verwijderen historie ouders
Given persoonsbeelden uit specials:specials/Delta_Delta_Kind_na_adoptie_ouders_voor_adoptie_verwijderd_xls
When voor persoon 667626761 wordt de laatste handeling geleverd
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Levering obv doelbinding is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor ouder gelijk aan /testcases/Geconverteerde_Data_Test/Expecteds/GBA_BIJH_na_verwijderen_historie_ouders_adoptie_volledig_bericht.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Levering_doelbinding_AlleenMatHis is ontvangen en wordt bekeken
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Levering_doelbinding_GeenFMV is ontvangen en wordt bekeken
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie vektis is ontvangen en wordt bekeken

!-- Geef details persoon na verwijderen historie ouders
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering obv doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|667626761

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht ongeacht elementvolgorde voor ouder gelijk aan /testcases/Geconverteerde_Data_Test/Expecteds/GBA_BIJH_GDP_na_verwijderen_historie_ouders_adoptie.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 2_AlleenMatHis

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_AlleenMatHis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|667626761

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 2_GeenFMV

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenFMV'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|667626761

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 2_GeenGerelateerde

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenGerelateerde'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|667626761

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 2_vektis

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenGerelateerde'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|667626761

Then heeft het antwoordbericht verwerking Geslaagd