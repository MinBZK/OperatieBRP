Meta:
@sprintnummer           78
@epic                   PDT & aanpassen SW Leveren tbv productiedata 3.1
@status                 Klaar
@sleutelwoorden         GeconverteerdeDataTest

Narrative: Leveren categorie 9 mutatieberichten

Scenario: 01 DELTAVERS09C10T10 Toevoeging eerste kind
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS09/DELTAVERS09C10T10_xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie09/01.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 02 DELTAVERS09C10T20 Toevoegen 2 Kinderen (eerst geen Kind)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS09/DELTAVERS09C10T20_xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie09/02.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 03 DELTAVERS09C10T30 Kind toevoegen (Als tweede kind)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS09/DELTAVERS09C10T30_xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie09/03.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 04 DELTAVERS09C10T30a Kind toevoegen (Als tweede kind)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS09/DELTAVERS09C10T30a_xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie09/04.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 05 DELTAVERS09C10T40 Kind rij toevoegd waarbij de bestaande actuele stapel een 85.10 heeft met standaardwaarde
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS09/DELTAVERS09C10T40_xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie09/05.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 06 DELTAVERS09C10T50 KIND historie toevoegen op een WALG manier
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS09/DELTAVERS09C10T50_xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie09/06.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 07 DELTAVERS09C10T60 2 Kinderen toevoegen (Als tweede en derde kind, met in totaal 3 PL'en)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS09/DELTAVERS09C10T60_xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie09/07.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 08 DELTAVERS09C10T70 Hist. Kind rij toevoegd waarbij de bestaande actuele stapel een 85.10 heeft met standaardwaarde
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS09/DELTAVERS09C10T80_xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie09/08.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 09 DELTAVERS09C10T80 Onterecht opgevoerde Kind toch weer opgevoerd
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS09/DELTAVERS09C10T80_xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie09/09.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 10 DELTAVERS09C20T10 Kindgegevens wijzigen (voornaam wijzigt) met historie
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS09/DELTAVERS09C20T10_xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie09/10.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 11 DELTAVERS09C20T20 Kindgegevens corrigeren (voornaam wijzigt) historie wordt onjuist
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS09/DELTAVERS09C20T20_xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie09/11.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 12 DELTAVERS09C20T30 Kindgegevens wijzigen (voornaam wijzigt) op een WALG manier
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS09/DELTAVERS09C20T30_xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie09/12.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 13 DELTAVERS09C20T40 Kindgegevens wijzigen (voornaam wijzigt 2x) met historie
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS09/DELTAVERS09C20T40_xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie09/13.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 14 DELTAVERS09C20T50 Kindgegevens wijzigen van tweede Kind (actualisering)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS09/DELTAVERS09C20T50_xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie09/14.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 15 DELTAVERS09C20T60 Kindgegevens wijzigen van tweede Kind (WALG)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS09/DELTAVERS09C20T60_xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie09/15.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 16 DELTAVERS09C20T70 Kindgegevens wijzigen van tweede Kind (WALG)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS09/DELTAVERS09C20T70_xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie09/16.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 17 DELTAVERS09C30T10 Actualiseren onterecht Kind en er blijven geen kinderen over (correctie na ten onrechte opgevoerde kind)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS09/DELTAVERS09C30T10_xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie09/17.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 18 DELTAVERS09C30T20 Acutaliseren onterecht Kind en er blijft 1 kind over
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS09/DELTAVERS09C30T20_xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie09/18.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 19 DELTAVERS09C30T30 KIND verwijderd op een WALG manier
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS09/DELTAVERS09C30T30_xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie09/19.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 20 DELTAVERS09C30T40 KIND verwijderd op een WALG manier (met meerdere kinderen) Kind 1 verwijderen
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS09/DELTAVERS09C30T40_xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie09/20.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 21 DELTAVERS09C30T50 KIND verwijderd op een WALG manier (met meerdere kinderen) Kind 2 verwijderen
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS09/DELTAVERS09C30T50_xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie09/21.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 22 DELTAVERS09C30T60 Stapel volgorde gewijzgd
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS09/DELTAVERS09C30T60_xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie09/22.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 23 DELTAVERS09C30T70 Stapel volgorde gewijzgd en 1 Kind wordt verwijderd (WALG)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS09/DELTAVERS09C30T70_xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie09/23.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 24 DELTAVERS09C30T80 Stapel volgorde gewijzgd en 1 Kind wordt verwijderd (WALG)
Meta:
@regels     ter_beoordeling,R2015

Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS09/DELTAVERS09C30T80_xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie09/24m.xml voor expressie //brp:lvg_synVerwerkPersoon

Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|205409593
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie09/24v.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 25 DELTAVERS09C30T90 Stapel volgorde gewijzgd en 1 Kind wordt verwijderd (WALG)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS09/DELTAVERS09C30T90_xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie09/25.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 26 DELTAVERS09C40T10 Element 81.10 wijkt af tussen IST-OUD.actueel en IST-Nieuw.actueel
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS09/DELTAVERS09C40T10_xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie09/26.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 27 DELTAVERS09C40T20 Element 82.10 wijkt af tussen IST-OUD.actueel en IST-Nieuw.actueel
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS09/DELTAVERS09C40T20_xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie09/27.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 28 DELTAVERS09C40T30 Element 83.10 wijkt af tussen IST-OUD.actueel en IST-Nieuw.actueel
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS09/DELTAVERS09C40T30_xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie09/28.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 29 DELTAVERS09C40T40 Element 84.10 wijkt af tussen IST-OUD.actueel en IST-Nieuw.actueel
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS09/DELTAVERS09C40T40_xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie09/29.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 30 DELTAVERS09C40T50 Element 86.10 wijkt af tussen IST-OUD.actueel en IST-Nieuw.actueel
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS09/DELTAVERS09C40T50_xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie09/30.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 31 DELTAVERS09C40T60 Element 85.10 wijkt af tussen IST-OUD.actueel en IST-Nieuw.actueel
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS09/DELTAVERS09C40T60_xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie09/31.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 32 DELTAVERS09C40T70 Element 02.10 wijkt af tussen IST-OUD.actueel en IST-Nieuw.actueel
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS09/DELTAVERS09C40T70_xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie09/32.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 33 DELTAVERS09C40T80 Element 81.10 wijkt af tussen IST-OUD.actueel en IST-Nieuw.actueel
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS09/DELTAVERS09C40T80_xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie09/33.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 34 DELTAVERS09C40T90 Element 85.10 wijkt af tussen IST-OUD.actueel en IST-Nieuw.actueel
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS09/DELTAVERS09C40T90_xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie09/34.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 35 DELTAVERS09C40T100 Element 02.10 wijkt af tussen IST-OUD.actueel en IST-Nieuw.actueel
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS09/DELTAVERS09C40T100_xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie09/35.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 36 DELTAVERS09C40T110 Element 82.10 en 86.10 wijkt af tussen IST-OUD.actueel en IST-Nieuw.actueel
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS09/DELTAVERS09C40T110_xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie09/36.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 37 DELTAVERS09C40T120 Element 83.30 wijkt af tussen IST-OUD.actueel en IST-Nieuw.actueel in 2 stapels
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS09/DELTAVERS09C40T120_xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie09/37.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 38 DELTAVERS09C40T130 Precies dezelfde PL
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS09/DELTAVERS09C40T130_xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie09/38.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 39 DELTAVERS09C40T160 Leeg met Leeg
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS09/DELTAVERS09C40T160_xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie09/39.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 40 DELTAVERS09C40T170 Element 81.10 wijkt af tussen IST-OUD.actueel en IST-Nieuw.actueel in 2 stapels (HIS)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS09/DELTAVERS09C40T170_xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie09/40.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 41 DELTAVERS09C40T180 Element 81.10 wijkt af tussen IST-OUD.HIS en IST-Nieuw.actueel
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS09/DELTAVERS09C40T180_xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie09/41.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 42 DELTAVERS09C40T190 2 dezelfde stapel in IST.OUD.act verg 1 stapel
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS09/DELTAVERS09C40T190_xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie09/42.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 43 DELTAVERS09C40T200 IST.OUD.actueel in andere stapel
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS09/DELTAVERS09C40T200_xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie09/43.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 44 DELTAVERS09C50T10 Toevoeging CAT11
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS09/DELTAVERS09C50T10_xls
When voor persoon 205409593 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie09/44.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 45 DELTAVERS09C60T10 Toevoeging/correctie historie kind???
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS09/DELTAVERS09C60T10_xls
When voor persoon 836242889 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie09/45.xml voor expressie //brp:lvg_synVerwerkPersoon
