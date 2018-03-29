Meta:
@sprintnummer           78
@epic                   PDT & aanpassen SW Leveren tbv productiedata 3.1
@status                 Klaar
@sleutelwoorden         GeconverteerdeDataTest, cat4



Narrative: Leveren categorie 4 (nationaliteit) mutatieberichten


Scenario: DELTAVERS04aC20T10 Correctie verkrijging NL nationaliteit
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC20T10_xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_2/Scenario1.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: DELTAVERS04aC20T20 Correctie verkrijging vreemde nationaliteit
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC20T20_xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_2/Scenario2.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: DELTAVERS04aC20T30 Correctie verkrijging NL nationaliteit (nationaliteit eerder correct verkregen en beeindigd)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC20T30_xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_2/Scenario3.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: DELTAVERS04aC30T10 Correctie beeindiging NL nationaliteit (Verlies gelijk, ingangsdatum later)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC30T10_xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_2/Scenario4.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: DELTAVERS04aC30T20 Correctie beeindiging vreemde nationaliteit
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC30T20_xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_2/Scenario5.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: DELTAVERS04aC30T30 Correctie beeindiging vreemde nationaliteit (continuering vreemde nationaliteit)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC30T30_xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_2/Scenario6.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: DELTAVERS04aC30T40 Correctie beeindiging NL nationaliteit (Verlies gelijk, ingangsdatum eerder)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC30T40_xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_2/Scenario7.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: DELTAVERS04aC50T10 Onterecht geregistreerde nationaliteit
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC50T10_xls
When voor persoon 954911945 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_2/Scenario8.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: DELTAVERS04aC50T20 Onterecht geregistreerde nationaliteit na eerdere onterechte registratie
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC50T20_xls
When voor persoon 954911945 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_2/Scenario9.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: DELTAVERS04aC50T30 Vervallen nationaliteit met in historie 2x vervallen nationaliteit
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC50T30_xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_2/Scenario10.xml voor expressie //brp:lvg_synVerwerkPersoon


