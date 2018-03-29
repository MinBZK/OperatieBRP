Meta:
@sprintnummer           78
@epic                   PDT & aanpassen SW Leveren tbv productiedata 3.1
@status                 Klaar
@sleutelwoorden         GeconverteerdeDataTest


Scenario: 01 DELTAONDC10T100_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T100_xls
When voor persoon 451123657 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/01.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 02 DELTAONDC10T10_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T10_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/02.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 03 DELTAONDC10T110_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T110_xls
When voor persoon 659797641 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/03.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 04 DELTAONDC10T120_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T120_xls
When voor persoon 659797641 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/04.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 05 DELTAONDC10T130_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T130_xls
When voor persoon 995347529 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/05.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 06 DELTAONDC10T140_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T140_xls
When voor persoon 948226249 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/06.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 07 DELTAONDC10T150_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T150_xls
When voor persoon 659797641 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/07.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 08 DELTAONDC10T160_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T160_xls
When voor persoon 445790441 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/08.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 09 DELTAONDC10T170_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T170_xls
When voor persoon 445790441 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/09.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 10 DELTAONDC10T180_xls

Meta:
@status Onderhanden

Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T180_xls
When voor persoon 451123657 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/10.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 11 DELTAONDC10T190_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T190_xls
When voor persoon 451123657 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/11.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 12 DELTAONDC10T200_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T200_xls
When voor persoon 451123657 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/12.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 13 DELTAONDC10T20_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T20_xls
When voor persoon 635783113 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek gelijk aan expected/DELTAOND/13.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 14 DELTAONDC10T210_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T210_xls
When voor persoon 451123657 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/14.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 15 DELTAONDC10T220_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T220_xls
When voor persoon 451123657 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/15.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 16 DELTAONDC10T230_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T230_xls
When voor persoon 451123657 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/16.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 17 DELTAONDC10T240_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T240_xls
When voor persoon 635783113 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/17.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 18 DELTAONDC10T250_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T250_xls
When voor persoon 635783113 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/18.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 19 DELTAONDC10T260_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T260_xls
When voor persoon 451123657 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/19.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 20 DELTAONDC10T270_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T270_xls
When voor persoon 451123657 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/20.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 21 DELTAONDC10T280_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T280_xls
When voor persoon 451123657 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/21.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 22 DELTAONDC10T30_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T30_xls
When voor persoon 465988969 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/22.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 23 DELTAONDC10T40_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T40_xls
When voor persoon 144892625 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/23.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 24 DELTAONDC10T50_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T50_xls
When voor persoon 445790441 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/24.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 25 DELTAONDC10T60_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T60_xls
When voor persoon 771217225 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/25.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 26 DELTAONDC10T70_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T70_xls
When voor persoon 179753137 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/26.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 27 DELTAONDC10T80_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T80_xls
When voor persoon 659797641 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/27.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 28 DELTAONDC10T90_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T90_xls
When voor persoon 144343897 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/28.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 29 DELTAONDC20T100_xls

Meta:
@status Bug
!-- WIT-1765

Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC20T100_xls
When voor persoon 720950089 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
!-- In deze casus is er een onderzoek naar de nationaliteitcode bij de persoon. Deze nationaliteit vervalt met een reden beeindiging
!-- Omdat de identiteit groep verder geen verantwoording heeft, komt deze niet mee in het onderzoek (er kan obv voorkomensleutel niet naar verwezen worden)
!-- Ingeval er via de gba conversie een mutatie doorkomt op een object met een standaard en identiteitsgroep
!-- dan worden in het mutatie bericht enkel de gegevens geleverd voor onderzoek die betrekking hebben op de standaard groep
!-- Dit is niet correct, eigenlijk zou het hele object in onderzoek gezet moeten worden.
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek gelijk aan expected/DELTAOND/29.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 30 DELTAONDC20T10_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC20T10_xls
When voor persoon 501849257 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/30.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 31 DELTAONDC20T110_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC20T110_xls
When voor persoon 720950089 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/31.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 32 DELTAONDC20T120_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC20T120_xls
When voor persoon 411738665 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek gelijk aan expected/DELTAOND/32.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 33 DELTAONDC20T130_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC20T130_xls
When voor persoon 411738665 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/33.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 34 DELTAONDC20T140_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC20T140_xls
When voor persoon 411738665 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/34.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 35 DELTAONDC20T20_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC20T20_xls
When voor persoon 707778633 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/35.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 36 DELTAONDC20T30_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC20T30_xls
When voor persoon 236685545 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/36.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 37 DELTAONDC20T40_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC20T40_xls
When voor persoon 759320329 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor gegevenInOnderzoek,onderzoek gelijk aan expected/DELTAOND/37.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 38 DELTAONDC20T50_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC20T50_xls
When voor persoon 567240393 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor gegevenInOnderzoek,onderzoek gelijk aan expected/DELTAOND/38.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 39 DELTAONDC20T60_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC20T60_xls
When voor persoon 287201673 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/39.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 40 DELTAONDC20T70_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC20T70_xls
When voor persoon 466644425 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/40.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 41 DELTAONDC20T80_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC20T80_xls
When voor persoon 532949481 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/41.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 42 DELTAONDC20T90_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC20T90_xls
When voor persoon 823703241 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor gegevenInOnderzoek,onderzoek gelijk aan expected/DELTAOND/42.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 43 DELTAONDC30T10_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC30T10_xls
When voor persoon 271650825 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/43.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 44 DELTAONDC30T20_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC30T20_xls
When voor persoon 685605577 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/44.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 45 DELTAONDC30T30_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC30T30_xls
When voor persoon 758085321 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/45.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 46 DELTAONDC30T40_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC30T40_xls
When voor persoon 932945545 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/46.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 47 DELTAONDC30T50_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC30T50_xls
When voor persoon 888141385 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/47.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 48 DELTAONDC30T60_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC30T60_xls
When voor persoon 493059593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/48.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 49 DELTAONDC30T70_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC30T70_xls
When voor persoon 144412561 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/49.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 50 DELTAONDC30T80_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC30T80_xls
When voor persoon 754964681 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/50.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 51 DELTAONDC30T90_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC30T90_xls
When voor persoon 201573337 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/51.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 52 DELTAONDC40T10_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC40T10_xls
When voor persoon 460452265 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/52.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 53 DELTAONDC40T20_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC40T20_xls
When voor persoon 887143817 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/53.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 54 DELTAONDC40T30_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC40T30_xls
When voor persoon 917131721 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/54.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 55 DELTAONDC40T40_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC40T40_xls
When voor persoon 281817625 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/55.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 56 DELTAONDC40T50_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC40T50_xls
When voor persoon 651919113 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/56.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 57 DELTAONDC40T60_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC40T60_xls
When voor persoon 890581769 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/57.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 58 DELTAONDC40T70_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC40T70_xls
When voor persoon 264774553 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/58.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 59 DELTAONDC40T80_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC40T80_xls
When voor persoon 541531049 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/59.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 60 DELTAONDC40T90_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC40T90_xls
When voor persoon 186954761 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/60.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 61 DELTAONDC50T10_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC50T10_xls
When voor persoon 225680865 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/61.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 62 DELTAONDC50T20_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC50T20_xls
When voor persoon 295159625 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/62.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 63 DELTAONDC50T30_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC50T30_xls
When voor persoon 255294761 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/63.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 64 DELTAONDC50T40_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC50T40_xls
When voor persoon 992467913 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/64.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 65 DELTAONDC50T50_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC50T50_xls
When voor persoon 374128649 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/65.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 66 DELTAONDC50T70_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC50T70_xls
When voor persoon 733622537 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/66.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 67 DELTAONDC50T90_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC50T90_xls
When voor persoon 888141385 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor gegevenInOnderzoek gelijk aan expected/DELTAOND/67.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 68 DELTAONDC70T10_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC70T10_xls
When voor persoon 575351913 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/68.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 69 DELTAONDC70T20_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC70T20_xls
When voor persoon 303899177 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/69.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 70 DELTAONDC70T30_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC70T30_xls
When voor persoon 678462409 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/70.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 71 DELTAONDC70T40_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC70T40_xls
When voor persoon 226394761 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/71.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 72 DELTAONDC70T50_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC70T50_xls
When voor persoon 927010185 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/72.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 73 DELTAONDC70T60_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC70T60_xls
When voor persoon 848808137 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/73.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 74 DELTAONDC70T70_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC70T70_xls
When voor persoon 169430273 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/74.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 75 DELTAONDC70T80_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC70T80_xls
When voor persoon 169430273 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/75.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 76 DELTAONDC70T90_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC70T90_xls
When voor persoon 226394761 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/76.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 77 DELTAONDC80T10_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC80T10_xls
When voor persoon 854820425 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/77.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 78 DELTAONDC80T20_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC80T20_xls
When voor persoon 854820425 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/78.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 79 DELTAONDC80T30_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC80T30_xls
When voor persoon 270433417 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/79.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 80 DELTAONDC80T40_xls
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC80T40_xls
When voor persoon 854820425 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor onderzoek,gegevenInOnderzoek gelijk aan expected/DELTAOND/80.xml voor expressie //brp:lvg_synVerwerkPersoon
