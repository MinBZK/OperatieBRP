Meta:
@sprintnummer           78
@epic                   PDT & aanpassen SW Leveren tbv productiedata 3.1
@status                 Klaar
@sleutelwoorden         GeconverteerdeDataTest

Narrative: Leveren categorie 5 (Huwelijk/geregistreerd partnerschap) mutatieberichten


Scenario: 01 DELTAVERS05C10T400 Huwelijk, Overlijden en in PL2 correctie op overlijden
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T400_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel3/Scenario1.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 02 DELTAVERS05C20T10.xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C20T10_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel3/Scenario2.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 03 DELTAVERS05C20T20.xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C20T20_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel3/Scenario3.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 04 DELTAVERS05C20T30.xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C20T30_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel3/Scenario4.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 05 DELTAVERS05C20T40.xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C20T40_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel3/Scenario5.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 06 DELTAVERS05C20T50.xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C20T50_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel3/Scenario6.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 07 DELTAVERS05C20T60.xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C20T60_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel3/Scenario7.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 08 DELTAVERS05C20T70.xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C20T70_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel3/Scenario8.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 09 DELTAVERS05C20T80.xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C20T80_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel3/Scenario9.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 10 DELTAVERS05C30T10.xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C30T10_xls
When voor persoon 957772105 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor bron,actie gelijk aan expected/categorie05_deel3/Scenario10.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 11. DELTAVERS05C30T20
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C30T20_xls
When voor persoon 836042761 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel3/11.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 12. DELTAVERS05C30T30
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C30T30_xls
When voor persoon 836042761 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel3/12.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 13. DELTAVERS05C30T40
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C30T40_xls
When voor persoon 836042761 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel3/13.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 14. DELTAVERS05C30T50
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C30T50_xls
When voor persoon 836042761 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel3/14.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 15. DELTAVERS05C30T60
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C30T60_xls
When voor persoon 836042761 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel3/15.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 16. DELTAVERS05C30T70
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C30T70_xls
When voor persoon 836042761 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel3/16.xml voor expressie //brp:lvg_synVerwerkPersoon
