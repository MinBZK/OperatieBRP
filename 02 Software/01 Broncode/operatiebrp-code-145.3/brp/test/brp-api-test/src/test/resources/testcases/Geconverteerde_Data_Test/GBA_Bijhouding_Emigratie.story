Meta:
@status                 Klaar
@sleutelwoorden         GeconverteerdeDataTest

Narrative: Leveren GBA Bijhoudingen categorie 8 (verblijfplaats) mutatieberichten

Inladen autorisaties Volledig - Geen verantwoording - Geen Historie - Geen Verantwoording / Historie
Initiele vulling van persoon via een GDP
Mutatie / volledig bericht van de GBA Bijhouding
GDP van de persoon na de GBA Bijhouding ROL AFNEMER
GDP van de persoon na de GBA Bijhouding ROL BIJHOUDER
Zoek Persoon na de GBA Bijhouding

Scenario: 0. INIT VULLING voor GBA_Bijhouding_Emigratie
             LT:

Given leveringsautorisatie uit autorisatie/levering_op_basis_van_afnemerindicatie_Afnemer,
                               autorisatie/Levering_doelbinding_AlleenMatHis,
                               autorisatie/Levering_doelbinding_GeenFMV,
                               autorisatie/Levering_doelbinding_GeenGerelateerde,
                               autorisatie/vektis

Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08_INITVULLING_C40T10_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|616902505

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 0_AlleenMatHis

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_AlleenMatHis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|616902505

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 0_GeenFMV

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenFMV'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|616902505

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 0_GeenGerelateerde

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenGerelateerde'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|616902505

Then heeft het antwoordbericht verwerking Geslaagd


Scenario: 1. GBA_Bijhouding_Emigratie
             LT:


Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C40T10_xls

Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam                 | partijNaam         | datumEindeVolgen | datumAanvangMaterielePeriode | tsReg                 | dienstId |
| 616902505 | 'levering op basis van afnemerindicatie' | 'Gemeente Utrecht' |                  |                              | 2014-01-01 T00:00:00Z | 2        |

When voor persoon 616902505 wordt de laatste handeling geleverd
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan /testcases/Geconverteerde_Data_Test/Expecteds/GBA_bijhouding_migratie.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Levering_doelbinding_AlleenMatHis is ontvangen en wordt bekeken
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Levering_doelbinding_GeenFMV is ontvangen en wordt bekeken
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Levering_doelbinding_GeenGerelateerde is ontvangen en wordt bekeken
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie vektis is ontvangen en wordt bekeken

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|616902505

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_AlleenMatHis

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_AlleenMatHis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|616902505

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_GeenFMV

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenFMV'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|616902505

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_GeenGerelateerde

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering_doelbinding_GeenGerelateerde'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|616902505

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 1_Vektis

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'vektis'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|616902505

Then heeft het antwoordbericht verwerking Geslaagd
