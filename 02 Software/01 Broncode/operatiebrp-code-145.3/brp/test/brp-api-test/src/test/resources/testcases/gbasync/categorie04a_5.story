Meta:
@sprintnummer           78
@epic                   PDT & aanpassen SW Leveren tbv productiedata 3.1
@status                 Klaar
@sleutelwoorden         GeconverteerdeDataTest, cat4

Narrative: Leveren categorie 4 (nationaliteit) mutatieberichten

Scenario: 1. DELTAVERS04aC80T70 PROBAS verwijderd (zonder aanmaak historie, zonder groep 82)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC80T70_xls
When voor persoon 434328297 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_5/Scenario1.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 2. DELTAVERS04aC90T10a Verschillende mutaties mbt nationaliteit
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC90T10a_xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_5/Scenario2.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 3. DELTAVERS04aC90T10b Verschillende mutaties mbt nationaliteit
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC90T10b_xls

!-- When voor persoon 963363529 wordt de 1 na laatste handeling geleverd
!-- When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken

When voor persoon 963363529 wordt de laatste handeling geleverd
When mutatiebericht voor leveringsautorisatie autorisatiealles wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_5/Scenario3.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 4. DELTAVERS04aC90T10c Verschillende mutaties mbt nationaliteit
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC90T10c_xls

!-- When voor persoon 963363529 wordt de 2 na laatste handeling geleverd
!-- When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken

!-- When voor persoon 963363529 wordt de 1 na laatste handeling geleverd
!-- When mutatiebericht voor leveringsautorisatie autorisatiealles wordt bekeken

When voor persoon 963363529 wordt de laatste handeling geleverd
When mutatiebericht voor leveringsautorisatie autorisatiealles wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_5/Scenario4.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 5. DELTAVERS04aC90T10d Verschillende mutaties mbt nationaliteit
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC90T10d_xls

!-- When voor persoon 963363529 wordt de 3 na laatste handeling geleverd
!-- When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
!-- When voor persoon 963363529 wordt de 2 na laatste handeling geleverd
!-- When mutatiebericht voor leveringsautorisatie autorisatiealles wordt bekeken
!-- When voor persoon 963363529 wordt de 1 na laatste handeling geleverd
!-- When mutatiebericht voor leveringsautorisatie autorisatiealles wordt bekeken

When voor persoon 963363529 wordt de laatste handeling geleverd
When mutatiebericht voor leveringsautorisatie autorisatiealles wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_5/Scenario5.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 6. DELTAVERS04aC90T10e Verschillende mutaties mbt nationaliteit
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC90T10e_xls

!-- When voor persoon 963363529 wordt de 4 na laatste handeling geleverd
!-- When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
!-- When voor persoon 963363529 wordt de 3 na laatste handeling geleverd
!-- When mutatiebericht voor leveringsautorisatie autorisatiealles wordt bekeken
!-- When voor persoon 963363529 wordt de 2 na laatste handeling geleverd
!-- When mutatiebericht voor leveringsautorisatie autorisatiealles wordt bekeken
!-- When voor persoon 963363529 wordt de 1 na laatste handeling geleverd
!-- When mutatiebericht voor leveringsautorisatie autorisatiealles wordt bekeken

When voor persoon 963363529 wordt de laatste handeling geleverd
When mutatiebericht voor leveringsautorisatie autorisatiealles wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_5/Scenario6.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 7. DELTAVERS04aC90T10f Verschillende mutaties mbt nationaliteit
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC90T10f_xls

!-- When voor persoon 963363529 wordt de 5 na laatste handeling geleverd
!-- When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
!-- When voor persoon 963363529 wordt de 4 na laatste handeling geleverd
!-- When mutatiebericht voor leveringsautorisatie autorisatiealles wordt bekeken
!-- When voor persoon 963363529 wordt de 3 na laatste handeling geleverd
!-- When mutatiebericht voor leveringsautorisatie autorisatiealles wordt bekeken
!-- When voor persoon 963363529 wordt de 2 na laatste handeling geleverd
!-- When mutatiebericht voor leveringsautorisatie autorisatiealles wordt bekeken
!-- When voor persoon 963363529 wordt de 1 na laatste handeling geleverd
!-- When mutatiebericht voor leveringsautorisatie autorisatiealles wordt bekeken

When voor persoon 963363529 wordt de laatste handeling geleverd
When volledigbericht voor leveringsautorisatie autorisatiealles wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_5/Scenario7.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 8. DELTAVERS04aC90T10g Verschillende mutaties mbt nationaliteit
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC90T10g_xls

!-- When voor persoon 963363529 wordt de 5 na laatste handeling geleverd
!-- When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
!-- When voor persoon 963363529 wordt de 4 na laatste handeling geleverd
!-- When mutatiebericht voor leveringsautorisatie autorisatiealles wordt bekeken
!-- When voor persoon 963363529 wordt de 3 na laatste handeling geleverd
!-- When mutatiebericht voor leveringsautorisatie autorisatiealles wordt bekeken
!-- When voor persoon 963363529 wordt de 2 na laatste handeling geleverd
!-- When mutatiebericht voor leveringsautorisatie autorisatiealles wordt bekeken
!-- When voor persoon 963363529 wordt de 1 na laatste handeling geleverd
!-- When mutatiebericht voor leveringsautorisatie autorisatiealles wordt bekeken

When voor persoon 963363529 wordt de laatste handeling geleverd
When volledigbericht voor leveringsautorisatie autorisatiealles wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_5/Scenario8.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 9. DELTAVERS04aC120T10 Actualisering reden verkrijging op dezelfde dag
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC120T10_xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_5/Scenario9.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Nieuw

Scenario: 10. DELTAVERS04aC130T10_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC130T10_xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_5/10.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 11. DELTAVERS04aC130T20_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC130T20_xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_5/11.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 12. DELTAVERS04aC130T30_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC130T30_xls
When voor persoon 850275209 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_5/12.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 13. DELTAVERS04aC130T40_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC130T40_xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_5/13.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 14. DELTAVERS04aC130T50_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC130T50_xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_5/14.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 15. DELTAVERS04aC130T60_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC130T60_xls
When voor persoon 850275209 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_5/15.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 16. DELTAVERS04aC130T70_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC130T70_xls
When voor persoon 850275209 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_5/16.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 17. DELTAVERS04aC130T80_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC120T10_xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_5/17.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 18. DELTAVERS04aC130T90_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC130T90_xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_5/18.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 19. DELTAVERS04aC130T100_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC120T10_xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_5/19.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 20. DELTAVERS04aC130T110_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC120T10_xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_5/20.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 21. DELTAVERS04aC130T120_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC130T120_xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_5/21.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 22. DELTAVERS04aC130T130_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC130T130_xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_5/22.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 23. DELTAVERS04aC130T140_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC130T140_xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_5/23.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 24. DELTAVERS04aC130T150_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC120T10_xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_5/24.xml voor expressie //brp:lvg_synVerwerkPersoon
