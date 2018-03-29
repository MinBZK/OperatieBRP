Meta:
@sprintnummer           78
@epic                   PDT & aanpassen SW Leveren tbv productiedata 3.1
@status                 Klaar
@sleutelwoorden         GeconverteerdeDataTest

Narrative: Leveren categorie 5 (Huwelijk/geregistreerd partnerschap) mutatieberichten


Scenario: 01 DELTAVERS05C10T310 Omzetting + achternaamswijziging CAT05
Meta:
@regels     ter_beoordeling

Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T310_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel2/Scenario1.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 02 DELTAVERS05C10T320 Dubbele omzetting met 3 PL'en
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T320_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel2/Scenario2.xml voor expressie //brp:lvg_synVerwerkPersoon


!-- DELTAVERS05C10T330 Dubbel omzetting met dezelfde datum sluiting/ontbinding voor alle relaties
!-- Testgeval gaat fout door een known issue ORANJE-3292, Verwijderd.

Scenario: 03 DELTAVERS05C10T340 Correcties slutings datum
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T340_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel2/Scenario3.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 04 DELTAVERS05C10T350 Correctie slutings plaats + omzetting
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T350_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel2/Scenario4.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 05 DELTAVERS05C10T360 Correctie Huwelijk
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T360_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel2/Scenario5.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 06 DELTAVERS05C10T370 dubbele omzetting met standaardwaarde
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T370_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor partner gelijk aan expected/categorie05_deel2/Scenario6.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 07 DELTAVERS05C10T380 Directe ontbinding (alleen ontbinding op de PL)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T380_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel2/Scenario7.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 08 DELTAVERS05C10T390 Ontbinden huwelijk bij overlijden
Meta:
@regels     ter_beoordeling

Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T390_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel2/Scenario8.xml voor expressie //brp:lvg_synVerwerkPersoon

