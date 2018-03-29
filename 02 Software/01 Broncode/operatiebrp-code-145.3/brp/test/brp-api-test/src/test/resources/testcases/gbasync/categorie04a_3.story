Meta:
@sprintnummer           78
@epic                   PDT & aanpassen SW Leveren tbv productiedata 3.1
@status                 Klaar
@sleutelwoorden         GeconverteerdeDataTest, cat4




Narrative: Leveren categorie 4 (nationaliteit) mutatieberichten

Scenario: DELTAVERS04aC60T10 Correctie beeindiging NL nationaliteit (Verlies aangepast, ingangsdatum ongelijk)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC60T10_xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_3/Scenario1.xml voor expressie //brp:lvg_synVerwerkPersoon



Scenario: DELTAVERS04aC60T20 Correctie beeindiging NL nationaliteit (Verlies aangepast, ingangsdatum gelijk)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC60T20_xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_3/Scenario2.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: DELTAVERS04aC60T30 Verkrijging NL nationaliteit + Verkrijging vreemde nationaliteit (na eerdere verkrijging en beeindiging)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC60T30_xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_3/Scenario3.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: DELTAVERS04aC60T40 Correctie (in historie) verkrijging NL na beeindiging (latere verkrijging)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC60T40_xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_3/Scenario4.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: DELTAVERS04aC60T50 Correctie (in historie) verkrijging NL na beeindiging (eerdere verkrijging)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC60T50_xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_3/Scenario5.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: DELTAVERS04aC60T60 Nationaliteit wijziging in adm gegevens zonder historie (WALG)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC60T60_xls
When voor persoon 409753257 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_3/Scenario6.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: DELTAVERS04aC60T70 Correctie verlies met verkrijging en (latere) verlies
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC60T70_xls
When voor persoon 528618313 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_3/Scenario7.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: DELTAVERS04aC70T10 Correctie van verkrijging NL na eerdere verkrijging NL (zonder verlies) DW-
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC70T10_xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_3/Scenario8.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: DELTAVERS04aC70T20 Correctie van verkrijging NL na eerdere verkrijging NL (zonder verlies, nieuwe DAG)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC70T20_xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_3/Scenario9.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: DELTAVERS04aC70T30 Correctie van verkrijging en verlies NL nationaliteit (2 lege rijen toevoegen) - DW-
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC70T30_xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_3/Scenario10.xml voor expressie //brp:lvg_synVerwerkPersoon

