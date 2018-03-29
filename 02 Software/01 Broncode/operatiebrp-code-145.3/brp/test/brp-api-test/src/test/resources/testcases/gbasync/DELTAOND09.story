Meta:
@sprintnummer           78
@epic                   PDT & aanpassen SW Leveren tbv productiedata 3.1
@status                 Klaar
@sleutelwoorden         GeconverteerdeDataTest

!-- Geen volledige expectds geplaatst omdat deze niet geleverd worden

Scenario: 1.DELTAOND09C10T100_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND09/DELTAOND09C10T100_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND09/scenario1.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 2.DELTAOND09C10T10_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND09/DELTAOND09C10T10_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND09/scenario2.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 3.DELTAOND09C10T110_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND09/DELTAOND09C10T110_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND09/scenario3.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 4.DELTAOND09C10T120_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND09/DELTAOND09C10T120_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND09/scenario4.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 5.DELTAOND09C10T130_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND09/DELTAOND09C10T130_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND09/scenario5.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 6.DELTAOND09C10T140_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND09/DELTAOND09C10T140_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND09/scenario6.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 7.DELTAOND09C10T150_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND09/DELTAOND09C10T150_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND09/scenario7.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 8.DELTAOND09C10T160_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND09/DELTAOND09C10T160_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND09/scenario8.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 9.DELTAOND09C10T170_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND09/DELTAOND09C10T170_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND09/scenario9.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 10.DELTAOND09C10T180_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND09/DELTAOND09C10T180_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek gelijk aan expected/DELTAOND09/scenario10.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 11.DELTAOND09C10T190_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND09/DELTAOND09C10T190_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken

!-- Er wordt een kind bijgeschreven op PL en tegelijkertijd in onderzoek gezet.
!-- Omdat gerelateerde kind identificerende gegevens bevat worden deze ook als referentie onderzoek opgenomen
!-- Het nieuwe onderzoek wordt geleverd met verwerksoort toevoeging
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND09/scenario11.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 12.DELTAOND09C10T200_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND09/DELTAOND09C10T200_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND09/scenario12.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 13.DELTAOND09C10T20_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND09/DELTAOND09C10T20_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND09/scenario13.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 14.DELTAOND09C10T210_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND09/DELTAOND09C10T210_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND09/scenario14.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 15.DELTAOND09C10T30_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND09/DELTAOND09C10T30_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND09/scenario15.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 16.DELTAOND09C10T40_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND09/DELTAOND09C10T40_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND09/scenario16.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 17.DELTAOND09C10T50_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND09/DELTAOND09C10T50_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND09/scenario17.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 18.DELTAOND09C10T60_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND09/DELTAOND09C10T60_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND09/scenario18.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 19.DELTAOND09C10T70_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND09/DELTAOND09C10T70_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND09/scenario19.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 20.DELTAOND09C10T80_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND09/DELTAOND09C10T80_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND09/scenario20.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 21.DELTAOND09C10T90_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND09/DELTAOND09C10T90_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/DELTAOND09/scenario21.xml voor expressie //brp:lvg_synVerwerkPersoon