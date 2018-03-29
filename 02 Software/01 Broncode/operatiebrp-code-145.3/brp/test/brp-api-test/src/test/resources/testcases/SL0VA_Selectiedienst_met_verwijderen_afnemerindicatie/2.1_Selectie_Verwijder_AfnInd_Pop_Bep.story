Meta:

@status             Klaar
@usecase            SL.1.VA.CAA, SL.1.VA.VVA, SL.0.VA
@regels             R1342, R2591, R2592, R2594
@sleutelwoorden     Selectie Verwijder afnemerindicatie

Narrative:
Selectie met verwijderen afnemerindicatie
- Controle op geen selectieresultaatset met persoongegevens is handmatig uitgevoerd
- Controle op WEL selectieresultaatset met TOTALEN is handmatig uitgevoerd

- Totale populatiebeperking
•	Toegang Leveringsautorisatie.Nadere populatiebeperking NIET IN TEST WANT DAN OOK GEEN AFNEMERINDICATIE GEPLAATST
•	Leveringsautorisatie.Populatiebeperking NIET IN TEST WANT DAN OOK GEEN AFNEMERINDICATIE GEPLAATST
•	Dienstbundel.Nadere populatiebeperking WEL IN TEST
•	Dienst.Nadere selectiecriterium WEL IN TEST

-   BSN lijst
•	Moet op de lijst staan IN TEST

- Verstrekkingsbeperking
•	Bij verwijderen afnemerindiatie met verstrekkingsbeperking geen volledig bericht, wel verwijderd


|Personen
| nr | Naam                                | Burgerservicenummer | Afnemerindicatie       | Binnen BSN lijst | Binnen PopBep dienst | Binnen PopBep dienstbundel | Verstrekkingsbeperking |
| 1  | Anne_met_Historie                   | 590984809           | Ja                     | Ja               | Ja                   | Ja                         | Nee                    |
| 2  | Anne_met_Historie2                  | 986096969           | Ja                     | Ja               | Ja                   | Nee                        | Nee                    |
| 3  | Jan                                 | 606417801           | Ja                     | Ja               | Nee                  | Ja                         | Nee                    |
| 4  | Anne_Bakker                         | 595891305           | Ja                     | Nee              | Ja                   | Ja                         | Nee                    |
| 5  | Jamie_Hervestiging                  | 427389033           | Ja, maar andere partij | Ja               | Ja                   | Ja                         | Nee                    |
| 6  | Libby-gebdat-deels-onbekend1        | 422531881           | Nee                    | Ja               | Ja                   | Ja                         | Nee                    |
| 7  | PersoonKrijgtVerstrekkingsBeperking | 270433417           | Ja                     | Ja               | Ja                   | Ja                         | Ja                     |

Scenario: 1.    Selectie met verwijderen afnemerindicatie
                LT: R1342_LT01, R2591_LT01, R2591_LT03, R2592_LT01, R2592_LT02, R2592_LT03, R2594_LT01, R2059_LT01, R2059_LT02, R2059_LT06, R2591_LT07, R2673_LT03
                Verwacht resultaat:
                - R1342_LT01 Afnemerindicatie verwijderd
                - R2591_LT01 Volledig bericht met adm.hand Verwijderen afnemerindicatie
                - R2591_LT07 Afnemerindicatie verwijderd, geen volledig bericht
                - R2591_LT03 Persoon valt buiten de pop. beperking
                - R2592_LT01 Aanwezige afnemerindicatie wordt verwijderd
                - R2592_LT02 Persoon heeft geen afnemerindicatie, verwerking wordt gestopt voor person
                - R2592_LT03 Onjuiste combinatie Leveringsautorisatie, partij, persoon voor afnemerindicatie, verwerking wordt gestopt voor person
                - R2594_LT01 Verwijderen afnemerindicatie geslaagd
                - R2059_LT01 Persoon voldoet aan populatiebeperking, afnemerindicatie kan verwijderd worden
                - R2059_LT02 Persoon voldoet niet aan populatiebeperking dienstbundel, afnemerindicatie niet verwijderd
                - R2059_LT06 Persoon voldoet niet aan populatiebeperking dienst, afnemerindicatie niet verwijderd
                - R2673_LT03 Handmatige controle Registreer de daadwerkelijke selectiedatum in Selectietaak.Datum uitvoer

Given leveringsautorisatie uit autorisatie/SelectieVerwijderAfnIndPopBep
Given personen uit specials:specials/Anne_met_Historie_xls, specials:specials/Anne_met_Historie2_xls, specials:specials/Jan_xls, specials:specials/Anne_Bakker_xls, specials:specials/Jamie_Hervestiging_xls, specials:specials/Libby-gebdat-deels-onbekend1_xls, specials:VerstrekkingsBeperking/PersoonKrijgtVerstrekkingsBeperking_xls,

Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg|tsReg|datumEindeVolgen
|590984809|SelectieVerwijderenAfnIndPopBep|'KUC033-PartijVerstrekkingsbeperking'|30|2015-07-28 T16:11:21Z|2016-12-12 T16:11:21Z

Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg|tsReg|datumEindeVolgen
|986096969|SelectieVerwijderenAfnIndPopBep|'KUC033-PartijVerstrekkingsbeperking'|30|2015-07-28 T16:11:21Z|2016-12-12 T16:11:21Z

Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg|tsReg|datumEindeVolgen
|606417801|SelectieVerwijderenAfnIndPopBep|'KUC033-PartijVerstrekkingsbeperking'|30|2015-07-28 T16:11:21Z|2016-12-12 T16:11:21Z

Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg|tsReg|datumEindeVolgen
|595891305|SelectieVerwijderenAfnIndPopBep|'KUC033-PartijVerstrekkingsbeperking'|30|2015-07-28 T16:11:21Z|2016-12-12 T16:11:21Z

Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg|tsReg|datumEindeVolgen
|427389033|SelectieVerwijderenAfnIndPopBep|'Gemeente Utrecht'|30|2015-07-28 T16:11:21Z|2016-12-12 T16:11:21Z

Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg|tsReg|datumEindeVolgen
|270433417|SelectieVerwijderenAfnIndPopBep|'KUC033-PartijVerstrekkingsbeperking'|30|2015-07-28 T16:11:21Z|2016-12-12 T16:11:21Z

Given selectielijsten per dienst:
|selectietaak | soortIdentificatie  | identificatienummers                                              | dienstSleutel                                                     |
|1   | Burgerservicenummer | 270433417 , 422531881, 427389033, 606417801, 986096969, 590984809 | KUC033SelectieVerwijderAfnIndPopBep/SelectieVerwijderAfnIndPopBep |

Given een selectierun met de volgende selectie taken:
| id | datplanning | status      | indsellijstgebruiken | dienstSleutel                                                     |
| 1  | vandaag     | Uitvoerbaar | true                 | KUC033SelectieVerwijderAfnIndPopBep/SelectieVerwijderAfnIndPopBep |

When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar

!-- Persoon 7
!-- R1342_LT01, R2059_LT01, R2591_LT07
!-- Verstrekkingsbeperking, dus verwijderd, geen volledig bericht
Then is er voor persoon met bsn 270433417 en leveringautorisatie SelectieVerwijderenAfnIndPopBep en partij KUC033-PartijVerstrekkingsbeperking de afnemerindicatie verwijderd

!-- Persoon 6
!-- R2592_LT02
!-- Nooit een afnemerindicatie gehad, dus ook niet verwijderd of geplaats
Then is er voor persoon met bsn 422531881 en leveringautorisatie SelectieVerwijderenAfnIndPopBep en partij KUC033-PartijVerstrekkingsbeperking geen afnemerindicatie geplaatst

!-- Persoon 5
!-- R2592_LT03
!-- Afnemerindicatie is van een andere partij, dus nooit geplaats via deze leveringsautorisatie, partij, en persoon combinatie
Then is er voor persoon met bsn 427389033 en leveringautorisatie SelectieVerwijderenAfnIndPopBep en partij KUC033-PartijVerstrekkingsbeperking de afnemerindicatie niet verwijderd

!-- Persoon 4
!-- Niet op BSN lijst, dus weggefilterd voor verwijdering
Then is er voor persoon met bsn 595891305 en leveringautorisatie SelectieVerwijderenAfnIndPopBep en partij KUC033-PartijVerstrekkingsbeperking de afnemerindicatie niet verwijderd

!-- Persoon 3
!-- R2059_LT06
!-- Niet binnen nadere selectie criterium, dus weggefilterd voor verwijdering
Then is er voor persoon met bsn 606417801 en leveringautorisatie SelectieVerwijderenAfnIndPopBep en partij KUC033-PartijVerstrekkingsbeperking de afnemerindicatie niet verwijderd

!-- Persoon 2
!-- R2059_LT02
!-- dienstbundel pop bep wordt bij verwijdering afnemerindicatie buiten beschouwing gelaten, dus niet weggefilterd voor verwijdering
Then is er voor persoon met bsn 986096969 en leveringautorisatie SelectieVerwijderenAfnIndPopBep en partij KUC033-PartijVerstrekkingsbeperking de afnemerindicatie verwijderd

!-- Persoon 1
!-- R2059_LT01
!-- R2592_LT01
!-- Verwijder afnemerindicatie en lever volledig bericht
Then is er voor persoon met bsn 590984809 en leveringautorisatie SelectieVerwijderenAfnIndPopBep en partij KUC033-PartijVerstrekkingsbeperking de afnemerindicatie verwijderd
!-- R2591_LT01
When het volledigbericht voor leveringsautorisatie SelectieVerwijderenAfnIndPopBep is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan Expecteds/Story_2_scenario_1.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 3. Persoon met afnemerindicatie datum einde volgen in het verleden
             LT: R2591_LT06
             Verwacht resultaat: Afnemerindicatie verwijderd, bericht aan afnemer


Given leveringsautorisatie uit autorisatie/SelectieMetVerwijderenVolledigBericht
Given personen uit specials:specials/Jan_xls

Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam    | partijNaam         | datumEindeVolgen | datumAanvangMaterielePeriode | tsReg                 | dienstId |
| 606417801 | 'SelectieVerwijderenAfnInd' | 'Gemeente Utrecht' | 2015-01-01       | 2010-01-01                   | 2014-01-01 T00:00:00Z | 2        |

Given een selectierun met de volgende selectie taken:
| id | datplanning | status      | dienstSleutel                         |
| 1  | vandaag     | Uitvoerbaar | SelectieMetVerwijderenVolledigBericht |

When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then is er voor persoon met bsn 606417801 en leveringautorisatie SelectieVerwijderenAfnInd en partij Gemeente Utrecht de afnemerindicatie verwijderd
When het volledigbericht voor leveringsautorisatie SelectieVerwijderenAfnInd is ontvangen en wordt bekeken

Scenario: 4. Persoon voldoet op SELECTIE_DATUM binnen de Totale populatie bepaling
             LT:
             Uitwerking: Persoon voldoet aan nadere selectie criterium op moment van selectie datum
             Expressie: JAAR(SELECTIE_DATUM() - ^0/0/0) > JAAR(Persoon.Afnemerindicatie.DatumEindeVolgen - ^0/0/0)
             Verwacht resultaat: Persoon heeft datum einde volgen < selectie datum, Afnemerindicatie verwijderd bij persoon die binnen pop bep valt, geen bericht aan afnemer

Given leveringsautorisatie uit autorisatie/SelectieMetNadereSelectieCriterium
Given persoonsbeelden uit oranje:DELTAVERS13/DELTAVERS13C10T20_xls

!-- Persoon met einddatum uitsluiting kiesrecht voldoet aan totale pop. bepaling
Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam    | partijNaam         | datumEindeVolgen | datumAanvangMaterielePeriode | tsReg                 | dienstId |
| 629228425 | 'SelectieVerwijderenAfnInd' | 'Gemeente Utrecht' | 2015-01-01       |                              | 2014-01-01 T00:00:00Z | 2        |

Given een selectierun met de volgende selectie taken:
| id | datplanning | status      | dienstSleutel                         |
| 1  | vandaag     | Uitvoerbaar | SelectieMetVerwijderenVolledigBericht |

When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then is er voor persoon met bsn 629228425 en leveringautorisatie SelectieVerwijderenAfnInd en partij Gemeente Utrecht de afnemerindicatie verwijderd
When het volledigbericht voor leveringsautorisatie SelectieVerwijderenAfnInd is ontvangen en wordt bekeken

Scenario: 5. Persoon voldoet op SELECTIE_DATUM niet binnen de Totale populatie bepaling
             LT:
             Uitwerking: Nadere selectie criterium sluit persoon uit van de populatie op basis van peil moment
             Expressie: JAAR(SELECTIE_DATUM() - ^0/0/0) > JAAR(Persoon.Afnemerindicatie.DatumEindeVolgen - ^0/0/0)
             Verwacht resultaat: Persoon heeft datum einde volgen > selectie datum, dus valt buiten de selectie criteria

Given leveringsautorisatie uit autorisatie/SelectieMetNadereSelectieCriterium
Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_PartnerschapBuitenland_xls

!-- Persoon zonder einddatum uitsluiting kiesrecht voldoet aan totale pop. bepaling
Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam    | partijNaam         | datumEindeVolgen | datumAanvangMaterielePeriode | tsReg                 | dienstId |
| 595891305 | 'SelectieVerwijderenAfnInd' | 'Gemeente Utrecht' | 2035-01-01       |                              | 2014-01-01 T00:00:00Z | 2        |


Given een selectierun met de volgende selectie taken:
| id | datplanning | status      | dienstSleutel                         |
| 1  | vandaag     | Uitvoerbaar | SelectieMetVerwijderenVolledigBericht |

When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then is er voor persoon met bsn 595891305 en leveringautorisatie SelectieVerwijderenAfnInd en partij Gemeente Utrecht de afnemerindicatie niet verwijderd
Then is er geen synchronisatiebericht voor leveringsautorisatie SelectieVerwijderenAfnInd
