Meta:
@sprintnummer           78
@epic                   PDT & aanpassen SW Leveren tbv productiedata 3.1
@status                 Klaar
@sleutelwoorden         GeconverteerdeDataTest


Narrative: Leveren categorie 11 (Gezagsverhouding) mutatieberichten

Scenario: 01 DELTAVERS11C10T10 TG-1
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS11/DELTAVERS11C10T10_xls
When voor persoon 950878601 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie11/01.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 02 DELTAVERS11C10T30 TG-3
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS11/DELTAVERS11C10T30_xls
When voor persoon 950878601 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie11/02m.xml voor expressie //brp:lvg_synVerwerkPersoon

Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|950878601

When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie11/02v.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 03 DELTAVERS11C10T40 TG-4
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS11/DELTAVERS11C10T40_xls
When voor persoon 950878601 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie11/03.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 04 DELTAVERS11C10T50 TG-5
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS11/DELTAVERS11C10T50_xls
When voor persoon 950878601 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie11/04.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 05 DELTAVERS11C10T70 TG-7
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS11/DELTAVERS11C10T70_xls
When voor persoon 950878601 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie11/05.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 06 DELTAVERS11C10T110 TG-11
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS11/DELTAVERS11C10T110_xls
When voor persoon 950878601 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie11/06.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 07 DELTAVERS11C10T130 TG-13
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS11/DELTAVERS11C10T130_xls
When voor persoon 950878601 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie11/07.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 08 DELTAVERS11C10T160 TG-16
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS11/DELTAVERS11C10T160_xls
When voor persoon 950878601 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie11/08.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 09  DELTAVERS11C10T170 TG-17
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS11/DELTAVERS11C10T170_xls
When voor persoon 950878601 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie11/09.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 10 DELTAVERS11C10T210 TG-21
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS11/DELTAVERS11C10T210_xls
When voor persoon 950878601 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie11/10.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 11 DELTAVERS11C20T20 TG-2 Geen wijziging
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS11/DELTAVERS11C20T20_xls
When voor persoon 950878601 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie11/11.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 12 DELTAVERS11C20T30 TG-3 Gezag verwijderen
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS11/DELTAVERS11C20T30_xls
When voor persoon 950878601 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie11/12.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 13 DELTAVERS11C20T40 TG-4 Gezag d.m.v. WALG wijzigen
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS11/DELTAVERS11C20T40_xls
When voor persoon 950878601 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie11/13.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 14 DELTAVERS11C20T50 TG-5 Gezag van 1D naar 2D naar D
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS11/DELTAVERS11C20T50_xls
When voor persoon 950878601 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie11/14.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 15 DELTAVERS11C20T60 TG-6 Gezag van 1D naar 2D naar D niet-onjuist
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS11/DELTAVERS11C20T60_xls
When voor persoon 950878601 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie11/15.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 16 DELTAVERS11C20T70 TG-7 Gezag van 1D onjuist naar 2D niet-onjuist naar D niet-onjuist
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS11/DELTAVERS11C20T70_xls
When voor persoon 950878601 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie11/16.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 17 DELTAVERS11C20T80 TG-8 Gezag van 1D onjuist naar 2D onjuist naar D niet-onjuist
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS11/DELTAVERS11C20T80_xls
When voor persoon 950878601 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie11/17.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 18 DELTAVERS11C20T90 TG-9 Ouder1 heeft geen gezag meer in PL2
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS11/DELTAVERS11C20T90_xls
When voor persoon 950878601 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie11/18.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 19 DELTAVERS11C20T100 TG-10 Ouder1 verliest gezag en krijgt die terug
             LT:
Meta:
@regels     ter_beoordeling

Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS11/DELTAVERS11C20T100_xls
When voor persoon 950878601 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie11/19.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 20 DELTAVERS11C20T10 TG-1 Toevoegen Gezagstapel
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS11/DELTAVERS11C20T10_xls
When voor persoon 950878601 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie11/20.xml voor expressie //brp:lvg_synVerwerkPersoon
