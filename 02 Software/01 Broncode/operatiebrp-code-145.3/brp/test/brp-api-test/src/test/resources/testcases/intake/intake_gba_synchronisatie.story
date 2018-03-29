Meta:
@status                 Klaar
@sleutelwoorden         Intaketest, GBA synchronisatie

Narrative:
Intake test voor levering van synchronisaties uit het gba, puur om te kijken of het werkt

Scenario: 1. Mutatie levering na Synchronisatie uit het GBA waarbij een wijziging op categorie 5 (Huwelijk / Geregistreerd partnerschap) plaatsvindt
             LT:
             Verwacht resultaat: Levering volgens expected

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T150_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected_gba_sync_berichten/expected_scenario_1.xml voor expressie //brp:lvg_synVerwerkPersoon
