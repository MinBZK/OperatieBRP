Meta:
@sprintnummer           78
@epic                   PDT & aanpassen SW Leveren tbv productiedata 3.1
@status                 Klaar
@sleutelwoorden         GeconverteerdeDataTest, cat4


Narrative: Leveren categorie 4 (nationaliteit) mutatieberichten

Scenario: 01 DELTAVERS04aC70T40 Correctie van verkrijging en verlies NL nationaliteit (1 lege rij toevoegen) - DW-
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC70T40_xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_4/Scenario1.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 02 DELTAVERS04aC70T50 Correctie in actueel en historie (2x lege rij)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC70T50_xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_4/Scenario2.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 03 DELTAVERS04aC70T60 Correctie in actueel en historie (lege rij) (variatie op 04aC70T70)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC70T60_xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_4/Scenario3.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 04 DELTAVERS04aC70T70 Correctie in actueel en historie (gevulde rij) (variatie op 04aC70T60)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC70T70_xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_4/Scenario4.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 05 DELTAVERS04aC80T10 Voornaamwijziging & beeindiging nationaliteit (geen invloed tussen verschillende groepen)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC80T10_xls
When voor persoon 475727241 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_4/Scenario5.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 06 DELTAVERS04aC80T20 Geen verschil uitsluitend datumtijdstempel en versienummer OK (niet uitgewerkt)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC80T20_xls
When voor persoon 475727241 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_4/Scenario6.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 07 DELTAVERS04aC80T30 Correctie PROBAS
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC80T30_xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_4/Scenario7.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 08 DELTAVERS04aC80T40 Correctie PROBAS (historie bevat geen aanduiding PROBAS)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC80T40_xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_4/Scenario8.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 09 DELTAVERS04aC80T50 WALG ingangsdatum cat 04 incl PROBAS
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC80T50_xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_4/Scenario9.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 10 DELTAVERS04aC80T60 PROBAS verwijderd (zonder aanmaak historie)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC80T60_xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_4/Scenario10.xml voor expressie //brp:lvg_synVerwerkPersoon

