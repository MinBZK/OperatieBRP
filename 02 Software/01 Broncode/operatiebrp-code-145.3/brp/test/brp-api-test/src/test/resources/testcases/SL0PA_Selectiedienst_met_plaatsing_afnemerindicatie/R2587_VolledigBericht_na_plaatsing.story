Meta:
@status             Klaar
@sleutelwoorden     Selectie



Narrative:
Indien bij de Dienst is ingesteld dat Dienst.Verzend volledig bericht bij wijziging afnemerindicatie na selectie? is gelijk aan 'Ja'
EN
De registratie van een Persoon \ Afnemerindicatie op een Persoon was succesvol
dan wordt een Volledig bericht met die Persoon geleverd aan de betreffende Afnemer (Partij) voor de Leveringsautorisatie behorende bij de Persoon \ Afnemerindicatie.
Opmerking:
De Administratieve handeling in het Volledig bericht is in dit geval Plaatsing afnemerindicatie.


Scenario:   1. Volledig bericht na succesvol plaatsing afnemerindicatie
            LT: R2587_LT01, R1544_LT01
            Succesvol plaatsen van een afnemerindicatie bij een ingeschreven persoon, psuedo personen geen afnemerindicatie geplaatst
            Resultaat:
            Voor Anne Bakker wordt succesvol een afnemerindicatie geplaatst met datum aanvang materiele periode is leeg en er wordt een volledig bericht verstuurd.
            Peilmomenten van de selectie taak en historie vorm van de selectiedienst zijn niet vna invloed op het volledig bericht

Given leveringsautorisatie uit aut/SelectieAutPlaatsAfnemerindicatie, aut/SelectieAutPlaatsAfnemerindicatieZonderFMVenAdres
Given een selectierun met de volgende selectie taken:
| id | datplanning | status      | peilmommaterieelresultaat | peilmomformeelresultaat | dienstSleutel                               |
| 1  | vandaag     | Uitvoerbaar | 20160101                  | 2015-12-31T23:00:00Z   | SelectieAutPlaatsAfnemerindicatie           |
| 2  | vandaag     | Uitvoerbaar |                           |                         | SelectieAutPlaatsAfnemerindicatieSteekProef |


Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar

Then is het totalenbestand voor selectietaak '1' en datumplanning 'vandaag' gelijk aan 'expecteds/expected_R2587_scenario1_resultaatset_totalen.xml'
And is er voor persoon met bsn 595891305 en leveringautorisatie SelectieLeveringautorisatie1 en partij Gemeente Utrecht een afnemerindicatie geplaatst
And is er voor persoon met bsn 595891305 en leveringautorisatie SelectieLeveringautorisatie2 en partij Gemeente Utrecht een afnemerindicatie geplaatst

!-- pseudo personen -- geen afnemerindicatie plaatsen
And is er voor persoon met bsn 265748185 en leveringautorisatie SelectieLeveringautorisatie1 en partij Gemeente Utrecht geen afnemerindicatie geplaatst
And is er voor persoon met bsn 773201993 en leveringautorisatie SelectieLeveringautorisatie1 en partij Gemeente Utrecht geen afnemerindicatie geplaatst
And is er voor persoon met bsn 632934761 en leveringautorisatie SelectieLeveringautorisatie1 en partij Gemeente Utrecht geen afnemerindicatie geplaatst
And is er voor persoon met bsn 823826697 en leveringautorisatie SelectieLeveringautorisatie1 en partij Gemeente Utrecht geen afnemerindicatie geplaatst

When het volledigbericht voor leveringsautorisatie SelectieLeveringautorisatie1 is ontvangen en wordt bekeken
!-- R1544_LT01 Datum aanvang materiele periode niet gevuld, dus bericht niet begrensd
Then is het synchronisatiebericht gelijk aan expecteds/expected_volledigbericht_scenario1.xml voor expressie //brp:lvg_synVerwerkPersoon
!-- Administratieve handeling moet zijn Plaatsing afnemerindicatie
Then is er voor xpath //brp:synchronisatie[contains(brp:soortNaam, 'Plaatsing afnemerindicatie')] een node aanwezig in het levering bericht

When het volledigbericht voor leveringsautorisatie SelectieLeveringautorisatie2 is ontvangen en wordt bekeken
!-- Bericht gefilterd volgens autorisatie regels en regels voor opschoning (ie. onderzoek bijv niet aanwezig in bericht)
Then is het synchronisatiebericht gelijk aan expecteds/expected_volledigbericht_scenario1B.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario:   2. Geen Volledig bericht na succesvol plaatsen afnemerindicatie
            LT: R2587_LT02
            Afnemerindicatie succesvol geplaatst, geen volledig bericht verstuurd, dit is ingesteld in de leveringsautorisatie
            Resultaat:
            Voor Anne Bakker wordt wel een afnemerindicatie geplaatst, geen volledig bericht.


Given leveringsautorisatie uit aut/SelectieAutPlaatsAfnemerindicatie_zonderVolledigBericht
Given een selectierun met de volgende selectie taken:
| id | datplanning | status  | dienstSleutel                                                 |
| 1  | vandaag     | Uitvoerbaar | SelectieAutPlaatsAfnemerindicatie_zonderVolledigBericht |

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar

Then is het totalenbestand voor selectietaak '1' en datumplanning 'vandaag' gelijk aan 'expecteds/expected_R2587_scenario2_resultaatset_totalen.xml'
Then is er voor persoon met bsn 595891305 en leveringautorisatie SelectieLeveringautorisatieZonderVolledigBericht en partij Gemeente Utrecht een afnemerindicatie geplaatst
Then is er geen synchronisatiebericht voor leveringsautorisatie SelectieLeveringautorisatieZonderVolledigBericht

Scenario:   3. Geen Volledig bericht plaatsen afnemerindicatie mislukt afnemerindicatie bestaat al
            LT: R2587_LT03
            Afnemerindicatie kan niet geplaatst worden omdat er al een afnemerindicatie bij de persoon geplaatst is.
            Resultaat:
            Persoon wel meegeteld in de totalen, afnemerindicatie mislukt omdat er al 1 bestaat
            Geen volledig bericht geleverd.

Given leveringsautorisatie uit aut/SelectieAutPlaatsAfnemerindicatie_zonderVolledigBericht
Given een selectierun met de volgende selectie taken:
| id | datplanning | status  | dienstSleutel                                                 |
| 1  | vandaag     | Uitvoerbaar | SelectieAutPlaatsAfnemerindicatie_zonderVolledigBericht |

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg|datumEindeVolgen
|595891305|SelectieLeveringautorisatieZonderVolledigBericht|'Gemeente Utrecht'|1|2016-07-28 T16:11:21Z|20150805

When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar

!-- Persoon wordt wel meegeteld in de resultaatset_totalen
Then is het totalenbestand voor selectietaak '1' en datumplanning 'vandaag' gelijk aan 'expecteds/expected_R2587_scenario3_resultaatset_totalen.xml'

Then is er voor persoon met bsn 595891305 en leveringautorisatie SelectieLeveringautorisatieZonderVolledigBericht en partij Gemeente Utrecht geen afnemerindicatie geplaatst
Then is er geen synchronisatiebericht voor leveringsautorisatie SelectieLeveringautorisatieZonderVolledigBericht






