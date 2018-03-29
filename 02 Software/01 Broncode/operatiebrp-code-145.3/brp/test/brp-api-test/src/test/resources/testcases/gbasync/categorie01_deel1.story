Meta:
@sprintnummer           77
@epic                   PDT & aanpassen SW Leveren tbv productiedata 3.1
@status                 Klaar
@sleutelwoorden         GeconverteerdeDataTest, cat1

Narrative: Leveren categorie 1 (Persoon) mutatieberichten

Scenario: 01. DELTAVERS01aC10T10 - DW-021 Wijziging naamgegevens (voornamen) persvoornaam
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T10_xls
When voor persoon 854820425 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie01_deel1/01.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 02. DELTAVERS01aC10T20 - DW-021 Wijziging naamgegevens (Adellijke titel/predikaat) perssamengesteldenaam
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T20_xls
When voor persoon 822062793 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie01_deel1/02.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 03. DELTAVERS01aC10T30 - DW-021 Wijziging naamgegevens (voorvoegsel geslachtsnaam) perssamengesteldenaam
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T30_xls
When voor persoon 211986689 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie01_deel1/03.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 04. DELTAVERS01aC10T40 - DW-021 Wijziging naamgegevens (geslachtsnaam) perssamengesteldenaam
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T40_xls
When voor persoon 860709577 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie01_deel1/04.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 05. DELTAVERS01aC10T50 - DW-021 Wijziging geslacht
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T50_xls
When voor persoon 495922985 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie01_deel1/05.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 06. DELTAVERS01aC10T60 - DW-011 Wijziging naamgebruik persnaamgebruik
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T60_xls
When voor persoon 401321897 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie01_deel1/06.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 07. DELTAVERS01aC10T70 - Wijziging naamgegevens, geslacht, naamgebruik en document
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T70_xls
When voor persoon 617045641 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie01_deel1/07.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 08. DELTAVERS01aC10T90 - Wijziging scheidingsteken
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T90_xls
When voor persoon 319343017 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie01_deel1/08.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 09. DELTAVERS01aC10T100 - DW-011 Wijziging geboorte
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T100_xls
When voor persoon 319343017 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie01_deel1/09.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 10. DELTAVERS01aC20T50
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC20T50_xls
When voor persoon 833478217 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie01_deel1/10.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 11. DELTAVERS01aC20T50a - 1a. Wijziging naamgegevens , geldigheid, verantwoording (WALG)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC20T50a_xls
When voor persoon 213581097 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie01_deel1/11.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 12. DELTAVERS01aC20T50b
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC20T50b_xls
When voor persoon 628333961 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie01_deel1/12.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 13. DELTAVERS01aC20T50c
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC20T50c_xls
When voor persoon 628333961 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie01_deel1/13.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 14. DELTAVERS01aC20T50d
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC20T50d_xls
When voor persoon 628333961 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie01_deel1/14.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 15. DELTAVERS01aC20T260
Meta:
@regels     ter_beoordeling

Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC20T260_xls
When voor persoon 854820425 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie01_deel1/15mut.xml voor expressie //brp:lvg_synVerwerkPersoon

Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|854820425

When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie01_deel1/15vol.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 16. DELTAVERS01aC20T280
Meta:
@regels     ter_beoordeling

Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC20T280_xls
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|854820425

When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie01_deel1/16.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- nieuw

Scenario: 17. DELTAVERS01aC20T10
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC20T10_xls
When voor persoon 987607625 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie01_deel1/17.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 18. DELTAVERS01aC20T20
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC20T20_xls
When voor persoon 845714697 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie01_deel1/18.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 19. DELTAVERS01aC20T30
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC20T30_xls
When voor persoon 963776393 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie01_deel1/19.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 20. DELTAVERS01aC20T40
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC20T40_xls
When voor persoon 382911593 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie01_deel1/20.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 21. DELTAVERS01aC30T10
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC30T10_xls
When voor persoon 854820425 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie01_deel1/21.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 22. DELTAVERS01C60T10
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01C60T10_xls
When voor persoon 150432513 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie01_deel1/22.xml voor expressie //brp:lvg_synVerwerkPersoon
