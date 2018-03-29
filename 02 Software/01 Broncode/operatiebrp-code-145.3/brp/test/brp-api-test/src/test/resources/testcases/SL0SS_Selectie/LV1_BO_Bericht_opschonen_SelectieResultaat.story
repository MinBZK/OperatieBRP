Meta:
@status             Klaar
@sleutelwoorden     Selectie
@usecase            LV.1.BO

Narrative:
1.	Bericht consistent maken (LV.1.BO.CM)
Deze filtering betreft voornamelijk het verwijderen van elementen die niet (meer) relevant zijn (door vorige filterstappen).
•	Er worden geen onderzoeken geleverd die geen enkele verwijzing overhouden.
•	R1551: Alleen acties die verantwoording vormen voor inhoudelijke- of onderzoeksgroepen worden meegeleverd.
•	R1552: Alleen administratieve handelingen waarnaar daadwerkelijk wordt verwezen worden als verantwoording  geleverd.
•	R1561: Een bericht bevat alleen onderzoeken die verwijzen naar ontbrekende, aanwezige en geautoriseerde gegevens.
•	R1563: Geen lege onderzoeken leveren
•	R2015: Alleen bronnen waarnaar daadwerkelijk wordt verwezen worden meegeleverd.

2.	Bericht structuur opschonen (LV.1.BO.SO)
Deze filterstap betreft het opschonen van bericht structuur elementen.
•	R1980: Een BRP bericht bevat geen lege containers.
•	R1622: Het systeem logt de situatie “bericht bevat geen gegevens”

3.	Gegevens in bericht sorteren (LV.1.BO.GS)
Gegevens in het bericht worden gesorteerd. Er zijn regels voor:
•	R1804: het sorteren van verantwoording in een bericht;
•	R1805: de volgorde van meervoudig voorkomende objecten in een bericht;
•	R1806: de volgorde historie in een bericht.


Scenario: 1 Bericht consistent maken en opschonen
             LT: R1551_LT01, R1551_LT02, 1551_LT03, R1552_LT01, R1551_LT02, R1561_LT01, R1563_LT01, R1563_LT02, R2015_LT01, R2015_LT02, R1980_LT01, R1804_LT01, R1806_LT02
             Verwacht resultaat:
                 - Onderzoek geleverd voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding Haarlem
                 - Onderzoek NIET geleverd voor leveringsautorisatie Geen autorisatie op attributen van adres


Given leveringsautorisatie uit aut/Geen_autorisatie_op_adres_GeenFMV, aut/Geen_autorisatie_op_adres_welFMV, aut/SelectieAutWaar
Given een selectierun met de volgende selectie taken:
| id | datplanning | status  | dienstSleutel                                                                               |
| 1  | vandaag     | Uitvoerbaar | Geen_autorisatie_op_adres_GeenFMV |
| 2  | vandaag     | Uitvoerbaar | Geen_autorisatie_op_adres_welFMV |
| 3  | vandaag     | Uitvoerbaar | SelectieAutWaar

Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen
Then resultaat files aanwezig voor selectietaak '2' en datumplanning 'vandaag' met '1' personen
Then resultaat files aanwezig voor selectietaak '3' en datumplanning 'vandaag' met '1' personen

!-- R1561: Onderzoek NIET AANWEZIG als deze op basis attribuut autorsatie gefilterd wordt
!-- R1563: Geen lege onderzoeken leveren
!-- R1980: Adres object niet aanwezig want attributen en FMV niet geautoriseerd

And zijn de volgende resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag':
|volgnummer    |pad
|1             |expecteds/LV_1_BO/LV1BO_Scenario1_zonderOnderzoek.xml

!-- R1551: Alleen Acties die verantwoording vormen voor inhoudelijke-of onderzoeksgroepen meeleveren
!-- R1552: Alleen de Administratieve handelingen waarnaar daadwerkelijk wordt verwezen als verantwoording leveren.
!-- R2015: Alleen bronnen waarnaar daadwerkelijk wordt verwezen meeleveren
And zijn de volgende resultaat files aanwezig voor selectietaak '2' en datumplanning 'vandaag':
|volgnummer    |pad
|1             |expecteds/LV_1_BO/LV1BO_Scenario1_zonderOnderzoek_welFMV.xml

!-- R1805: Volgorde van meervoudig voorkomende objecten in een bericht (meerdere voorkomens van 'voornaam' gesorteerd op volgnummer)
!-- R1806: Volgorde historie in bericht
And zijn de volgende resultaat files aanwezig voor selectietaak '3' en datumplanning 'vandaag':
|volgnummer    |pad
|1             |expecteds/LV_1_BO/LV1BO_Scenario1_metOnderzoek.xml

Scenario: 2 Sortering voorkomens (historisch en meerdere actuele) en tonen van verantwoording in selectieresultaat
            LT: R1552, R1804, R2015

Given leveringsautorisatie uit aut/SelectieAutWaar, /levering_autorisaties_nieuw/R1552/Geen_autorisatie_op_huwelijk
Given een selectierun met de volgende selectie taken:
| id | datplanning | status  | dienstSleutel                                                              |
| 1  | vandaag     | Uitvoerbaar | SelectieAutWaar            |
| 2  | vandaag     | Uitvoerbaar | Geen_autorisatie_op_huwelijk |

Given persoonsbeelden uit BIJHOUDING:VHNL06C10T10/De_actuele_Persoon.Samengestelde_naam_wo/dbstate003
When de selectie wordt gestart in single-threaded mode
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '2' personen
Then resultaat files aanwezig voor selectietaak '2' en datumplanning 'vandaag' met '2' personen

!-- R1552: Initiele vulling en Huwelijk in verantwoording
!-- R1804: Sortering verantwoording in een bericht
!-- R2015: Alleen bronnen waarnaar daadwerkelijk wordt verwezen meeleveren
!-- Zelfde handeling op 2 personen, maar bij Danny geen bronnen aanwezig omtrent registratie naamgebruik en verklaring
!-- Zelfde handeling bij beide peronen op zelfde manier gesorteerd
And zijn de volgende resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag':
|volgnummer    |pad
|1             |expecteds/LV_1_BO/LV1BO_Scenario2_seltaak1.xml

!-- Geen autorisatie voor huwelijk, administratieve handeling van het huwelijk valt daarom in zijn geheel weg
And zijn de volgende resultaat files aanwezig voor selectietaak '2' en datumplanning 'vandaag':
|volgnummer    |pad
|1             |expecteds/LV_1_BO/LV1BO_Scenario2_seltaak2.xml
