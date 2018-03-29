Meta:
@sprintnummer           78
@epic                   PDT & aanpassen SW Leveren tbv productiedata 3.1
@status                 Klaar
@sleutelwoorden         GeconverteerdeDataTest, cat8

Narrative: Leveren categorie 8 (verblijfplaats) mutatieberichten

Scenario: 01 DELTAVERS08C10T10 DW-021, DW-032 Intergemeentelijke adreswijziging C20T10
Meta:
@regels     ter_beoordeling

Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C10T10_xls
When voor persoon 270433417 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie08_deel1/Scenario1mut.xml voor expressie //brp:lvg_synVerwerkPersoon


Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|270433417
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie08_deel1/Scenario1vol.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 02 DELTAVERS08C20T10 DW-021, DW-032 Hervestiging door immigratie C30T10
Meta:
@regels     ter_beoordeling

Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C20T10_xls
When voor persoon 626949737 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie08_deel1/Scenario2mut.xml voor expressie //brp:lvg_synVerwerkPersoon


Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|626949737
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie08_deel1/Scenario2vol.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 03 DELTAVERS08C20T20 DW-025 Correctie immigratie
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C20T20_xls
When voor persoon 626949737 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie08_deel1/Scenario3.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 04 DELTAVERS08C30T10 Wijziging adres binnengemeentelijk C40T10
Meta:
@regels     ter_beoordeling

Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C30T10_xls
When voor persoon 941099593 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie08_deel1/Scenario4mut.xml voor expressie //brp:lvg_synVerwerkPersoon


Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|941099593

When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie08_deel1/Scenario4vol.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 05 DELTAVERS08C30T20 Wijziging adres buitenland C40T20
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C30T20_xls
When voor persoon 616902505 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie08_deel1/Scenario5.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 06 DELTAVERS08C30T30 Correctie op adres huisnummer C40T30
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C30T30_xls
When voor persoon 941099593 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie08_deel1/Scenario6.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 07 DELTAVERS08C30T40 Correctie op adres in historie huisnummer in historie
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C30T40_xls
When voor persoon 941099593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie08_deel1/Scenario7.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 08 DELTAVERS08C30T50 Correctie op adres in historie geldigheid in historie
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C30T50_xls
When voor persoon 941099593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie08_deel1/Scenario8.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 09 DELTAVERS08C30T60
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C30T60_xls
When voor persoon 221217241 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie08_deel1/Scenario9.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 10 DELTAVERS08C30T70
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C30T70_xls
When voor persoon 383096777 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie08_deel1/Scenario10.xml voor expressie //brp:lvg_synVerwerkPersoon


!-- onderstaande scenario bevat a known issue bij migratie, moet eigenlijk mutatiebericht leveren.

Scenario: 11 DELTAVERS08C40T10 Emigratie C50T10
Meta:
@regels     ter_beoordeling

Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C40T10_xls
When voor persoon 616902505 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie08_deel1/Scenario11.xml voor expressie //brp:lvg_synVerwerkPersoon



Scenario: 12 DELTAVERS08C50T10 Correctie in de omschrijving van de aangifte adreshouding C70T10
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C50T10_xls
When voor persoon 599136777 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie08_deel1/Scenario12.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 13 DELTAVERS08C60T10 Verwijderen van historische verblijfplaatsgegevens C90T10
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C60T10_xls
When voor persoon 971826377 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie08_deel1/Scenario13.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 14 DELTAVERS08C60T20 Invoegen (in midden) van historische verblijfplaatsgegevens
Meta:
@regels     ter_beoordeling

Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C60T20_xls
When voor persoon 971826377 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie08_deel1/Scenario14.xml voor expressie //brp:lvg_synVerwerkPersoon


