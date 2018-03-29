Meta:
@sprintnummer           78
@epic                   PDT & aanpassen SW Leveren tbv productiedata 3.1
@status                 Klaar
@sleutelwoorden         GeconverteerdeDataTest

Narrative: Leveren categorie 5 (Huwelijk/geregistreerd partnerschap) mutatieberichten


Scenario: 01 DELTAVERS05C10T10	Element 81.10 wijkt af tussen IST-OUD.actueel en IST-Nieuw.actueel
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T10_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel1/01.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 02 DELTAVERS05C10T20 Element 82.20 wijkt af tussen IST-OUD.actueel en IST-Nieuw.actueel
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T20_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel1/02.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 03 DELTAVERS05C10T30 Element 83.10 wijkt af tussen IST-OUD.actueel en IST-Nieuw.actueel
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T30_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel1/Scenario3.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 04 DELTAVERS05C10T40 Element 84.10 wijkt af tussen IST-OUD.actueel en IST-Nieuw.actueel
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T40_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel1/04.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 05 DELTAVERS05C10T50 Element 86.10 wijkt af tussen IST-OUD.actueel en IST-Nieuw.actueel
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T50_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel1/05.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 06 DELTAVERS05C10T60 Element 85.10 wijkt af tussen IST-OUD.actueel en IST-Nieuw.actueel
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T60_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel1/06.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 07 DELTAVERS05C10T70 Element 02.10 wijkt af tussen IST-OUD.actueel en IST-Nieuw.actueel
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T70_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel1/07.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 08 DELTAVERS05C10T80 Element 81.10 wijkt af tussen IST-OUD.actueel en IST-Nieuw.actueel
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T80_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel1/08.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 09 DELTAVERS05C10T90 Element 85.10 wijkt af tussen IST-OUD.actueel en IST-Nieuw.actueel
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T90_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel1/09.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 10 DELTAVERS05C10T100 Element 02.10 wijkt af tussen IST-OUD.actueel en IST-Nieuw.actueel
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T100_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel1/10.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 11 DELTAVERS05C10T110 Element 82.10 en 86.10 wijkt af tussen IST-OUD.actueel en IST-Nieuw.actueel
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T110_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel1/11.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 12 DELTAVERS05C10T120 Element 81.20 wijkt af tussen IST-OUD.actueel en IST-Nieuw.actueel in 2 stapels
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T120_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel1/12.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 13 DELTAVERS05C10T130 Precies dezelfde PL
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T130_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel1/13.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 14 DELTAVERS05C10T140 Verwijderen Huwelijk (WALG)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T140_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel1/14.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 15 DELTAVERS05C10T150 Toevoegen huwelijk
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T150_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel1/15.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 16 DELTAVERS05C10T160 Leeg met Leeg
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T160_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel1/16.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 17 DELTAVERS05C10T170 Element 81.10 wijkt af tussen IST-OUD.actueel en IST-Nieuw.actueel in 2 stapels (HIS)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T170_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel1/17.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 18 DELTAVERS05C10T180 IST-OUD.HIS en IST-Nieuw.actueel vergelijken, voornaam ist.nieuw.act = voornaam ist.oud.hist
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T180_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel1/18.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 19 DELTAVERS05C10T190 2 dezelfde stapel in IST.OUD.act verg 1 stapel
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T190_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel1/19.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 20 DELTAVERS05C10T200 IST.OUD.actueel in andere stapel
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T200_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel1/20.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 21 DELTAVERS05C10T210 IST.OUD.act omzetting en IST.Nieuw.act omzetting + ontbinding
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T210_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel1/21.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 22 DELTAVERS05C10T220 IST.OUD.act omzetting en IST.Nieuw.act omzetting (precies zelfde)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T220_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel1/22.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 23 DELTAVERS05C10T230 IST.OUD.act dubbele omz. (p/h/p) en IST.nieuw.act dubbele omz + ontb
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T230_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel1/23.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 24 DELTAVERS05C10T240 IST.OUD.act dubbele omz. (p/h/p) en IST.nieuw.act omz + ontb
             LT:

!-- FIXME uitzoeken waarom er een huwelijk ipv partnerschap terug komt
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T240_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht ongeacht elementvolgorde voor partner gelijk aan expected/categorie05_deel1/24.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 25 DELTAVERS05C10T250 IST.OUD.act. Omz (p/h) en IST.nieuw.act dubbele omz (p/h/p)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T250_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel1/25.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 26 DELTAVERS05C10T260 IST.OUD.act omz (p/h) en IST.nieuw.act omzetting + ontbinding zelfde datums
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T260_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel1/26.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 27 DELTAVERS05C10T270 IST.OUD.act huwelijk en IST.nieuw huwelijk + ontbinding
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T270_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel1/27.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 28 DELTAVERS05C10T280 IST.OUD.act omzetting en IST.Nieuw.act omzetting + ontbinding + andere voornaam in H
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T280_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel1/28.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 29 DELTAVERS05C10T290 Omzetting
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T290_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel1/Scenario29.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 30 DELTAVERS05C10T300 Omzetting + achternaamswijziging CAT01
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T300_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie05_deel1/Scenario30.xml voor expressie //brp:lvg_synVerwerkPersoon

