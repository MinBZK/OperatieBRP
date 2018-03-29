Meta:
@status             Klaar
@sleutelwoorden     Selectie
@regels             R2631

Narrative:
In het bestand kan of een lijst met Administratienummers of een lijst met Burgerservicenummers opgenomen zijn.
De personen die geselecteerd worden voor deze selectiedienst moeten voorkomen op deze lijst.
Het bestand is een CSV bestand met in de kopregel of de tekst Administratienummer of Burgerservicenummer. Daaronder een lijst met de betreffende nummers.
De aanwezigheid van een selectiebestand is optioneel, wanneer er geen bestand aanwezig is voor de betreffende Dienst dan wordt deze stap overgeslagen.

Test analyse:
| nr | Bestandsformaat | Kolom kopregel      | Identificatie nummer | Pers in lijst | Tot. pop. beperking | Soort selectie     | Verwacht resultaat                                                            |
| 1  | CSV             | Burgerservicenummer | geldig BSN           | JA            | WAAR                | Standaard selectie | Persoon opgenomen in selectie resultaat bestand                               |
| 1  | CSV             | Burgerservicenummer | geldig BSN           | JA            | ONWAAR              | Standaard selectie | Persoon NIET opgenomen in selectie resultaat bestand                          |
| 1  | CSV             | Burgerservicenummer | ONGELDIG BSN         | NVT           | NVT                 | Standaard selectie | Persoon NIET opgenomen in selectie resultaat bestand                          |
| 1  | CSV             | Burgerservicenummer | LEGE WAARDE          | NVT           | NVT                 | Standaard selectie | Persoon NIET opgenomen in selectie resultaat bestand                          |
| 2  | CSV             | Burgerservicenummer | geldig BSN           | NEE           | WAAR                | Standaard selectie | Persoon komt niet voor op lijst, niet opgenomen in selectie resultaat bestand |
| 3  | CSV             | Administratienummer | geldig ANR           | JA            | WAAR                | Standaard selectie | Persoon opgenomen in selectie resultaat bestand
| 4  | CSV             | Administratienummer | geldig ANR           | NEE           | WAAR                | Standaard selectie | Persoon NIET opgenomen in selectie resultaat bestand
| 5  | CSV             | INVALIDE            | geldig ANR           | JA            | WAAR                | Standaard selectie | Persoon NIET opgenomen in selectie resultaat bestand
| 6  | CSV             | Burgerservicenummer | geldig BSN           | JA            | WAAR                | Selectie met plaatsing | Persoon NIET opgenomen in selectie resultaat bestand, wel afn. indicatie geplaatst

Scenario: 1. Selectie op basis van BSN in selectiebestand : bsn aanwezig in selectiebestand
             LT: R2631_LT01, R2631_LT02, R2631_LT03, R2631_LT04
             NB: Lijst identificatienummers met lege waarde, identificatie nummers van pseudo personen, ongeldige bsn's, ANR, dubbele BSN
             Verwacht resultaat: Selectie taak 1: Persoon voldoet aan totale populatie beperking en komt voor in selectie bestand
                                 Selectie taak 2: Persoon voldoet niet totale populatie beperking en komt wel voor in selectie bestand

Given leveringsautorisatie uit aut/SelectieAutWaar, aut/SelectieAutTPBOnwaar
!-- eerste taak levert 1 persoon op obv opgegeven lijst
!-- tweede taak levert geen persoon op obv de opgegven lijst, omdat persoon buiten de populatie van de autorisatie valt
Given een selectierun met de volgende selectie taken:
|id | datplanning | status  |indsellijstgebruiken | dienstSleutel                                                 |
| 1  | vandaag     | Uitvoerbaar | true | SelectieAutWaar |
| 2  | vandaag     | Uitvoerbaar | true | SelectieAutTPBOnwaar |

Given selectielijsten per dienst:
|selectietaak   | soortIdentificatie   | identificatienummers                                                  | dienstSleutel                                                 |
|1  | Burgerservicenummer | 1, 595891305, , 595891305, 773201993, 265748185, 632934761, 823826697, 5941970194 | SelectieAutWaar |
|2  | Burgerservicenummer | 1, 595891305, , 595891305, 773201993, 265748185, 632934761, 823826697, 5941970194 | SelectieAutTPBOnwaar |

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen
Then resultaat files aanwezig voor selectietaak '2' en datumplanning 'vandaag' met '0' personen


Scenario: 2. Selectie op basis van BSN in selectiebestand : bsn NIET aanwezig in selectiebestand
             LT: R2631_LT05
             Verwacht resultaat: Persoon valt binnen populatie beperking, maar komt niet voor in selectie bestand

Given leveringsautorisatie uit aut/SelectieAutWaar
Given een selectierun met de volgende selectie taken:
| id | datplanning | status  |indsellijstgebruiken | dienstSleutel                                                 |
| 1  | vandaag     | Uitvoerbaar | true | SelectieAutWaar |

Given selectielijsten per dienst:
|selectietaak   | soortIdentificatie   | identificatienummers  | dienstSleutel                                                 |
1   | Burgerservicenummer | 123456789 , 987654321, 773201993, 265748185| SelectieAutWaar |

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '0' personen


Scenario: 3. Selectie op basis van ANR in selectiebestand : anr aanwezig in selectiebestand
             LT: R2631_LT06
             Verwacht resultaat: persoon in resultaatfiles

Given leveringsautorisatie uit aut/SelectieAutWaar
Given een selectierun met de volgende selectie taken:
| id | datplanning | status  |indsellijstgebruiken | dienstSleutel                                                 |
| 1  | vandaag     | Uitvoerbaar | true    | SelectieAutWaar |

Given selectielijsten per dienst:
|selectietaak   | soortIdentificatie   | identificatienummers   | dienstSleutel                                                 |
|1  | Administratienummer | 123456789 , 5941970194 | SelectieAutWaar |

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen



Scenario: 4. Selectie op basis van ANR in selectiebestand : anr NIET aanwezig in selectiebestand
             LT: R2631_LT07
             Verwacht resultaat: persoon in resultaatfiles

Given leveringsautorisatie uit aut/SelectieAutWaar
Given een selectierun met de volgende selectie taken:
| id | datplanning | status  |indsellijstgebruiken | dienstSleutel                                                 |
| 1  | vandaag     | Uitvoerbaar | true    | SelectieAutWaar |

Given selectielijsten per dienst:
|selectietaak   | soortIdentificatie   | identificatienummers  | dienstSleutel                                                 |
|1  | Administratienummer | 123456789 , 987654321 | SelectieAutWaar |

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '0' personen


Scenario: 5. Selectie op basis van BSN in selectiebestand : header is invalide.
             LT: R2631_LT08
                        NB: Nog niet gespecificeerd, maar waarschijnlijk wordt toevoegen van selectielijsten via beheer applicatie gedaan en daar
                        gevalideerd, m.a.w invalide headers zouden nooit moeten voorkomen.
                        Verwacht resultaat: selectielijst wordt niet geevalueerd, geen filtering van personen in resultaatfiles

Given leveringsautorisatie uit aut/SelectieAutWaar
Given een selectierun met de volgende selectie taken:
| id | datplanning | status  | indsellijstgebruiken | dienstSleutel                                                 |
| 1  | vandaag     | Uitvoerbaar | true |  SelectieAutWaar |

Given selectielijsten per dienst:
|selectietaak   | soortIdentificatie | identificatienummers   | dienstSleutel                                                 |
|1  | Invalideheader     | 123456789 , 5941970194 | SelectieAutWaar |

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen


Scenario: 6. Selectie met plaatsing op basis van BSN in selectiebestand : bsn aanwezig in selectiebestand
             LT: R2631_LT09
             Verwacht resultaat: Geen persoon resultaat bestand, afnemer indicatie geplaatst bij persoon, volledig bericht verzonden aan afnemer

Given leveringsautorisatie uit aut/SelectieMetPlaatsenAfnemerindicatie

Given een selectierun met de volgende selectie taken:
|id |datplanning | status               |indsellijstgebruiken | dienstSleutel                                                 |
|1  |vandaag      | Uitvoerbaar            |true | SelectieMetPlaatsenAfnemerindicatie/Selectie |

Given selectielijsten per dienst:
|selectietaak   | soortIdentificatie   | identificatienummers | dienstSleutel                                                   |
|1  | Burgerservicenummer | 595891305            | SelectieMetPlaatsenAfnemerindicatie/Selectie |

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '0' personen

Then is er voor persoon met bsn 595891305 en leveringautorisatie SelectieMetPlaatsenAfnInd en partij Gemeente Utrecht een afnemerindicatie geplaatst
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie SelectieMetPlaatsenAfnInd is ontvangen en wordt bekeken
