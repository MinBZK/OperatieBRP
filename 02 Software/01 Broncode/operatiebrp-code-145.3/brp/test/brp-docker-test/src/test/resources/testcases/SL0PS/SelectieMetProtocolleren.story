Meta:
@status             Klaar
@usecase            SL.0.US
@regels             R2662, R2663, R2664, R2665, R2568, R1617, R1618, R1619, R1620
@sleutelwoorden     Selectie met protocolleren

Narrative:
Het te protocolleren bestand van de selectietaak wordt verwerkt in de protocolleringsadministratie  van de centrale voorzieningen.
De status van de selectietaak wordt gezet naar ‘Protocollering in uitvoering’.
Tijdens de verwerking van de protocollering wordt de voortgang van de protocollering van de selectietaak in de beheerapplicatie weergegeven
bij de betreffende selectietaak. Tevens heeft de beheerder in de beheerapplicatie de mogelijkheid om het protocolleren van een
specifieke selectietaak te pauzeren en te hervatten.



Scenario:   1.  Controleer dat de er geprotocolleerd wordt na de selectie
            LT: R2662_LT01, R2663_LT01, R2568_LT01, R1617_LT12, R1617_LT13, R1618_LT06, R1618_LT07, R1619_LT07, R1620_LT04
            Verwacht resultaat:
            - R2568_LT01:   Leveringsaantekening.Toegang leveringsautorisatie = Toegang leveringsautorisatie.ID waarvoor verstrekt wordt
            - R2568_LT01:   Leveringsaantekening.Dienst = Dienst.ID van de geleverde Dienst
            - R2568_LT01:   Administratieve handeling = leeg, Soort synchronisatie = leeg EN Scope patroon = leeg
            - R2568_LT01:   Overige vulling volgens R1617, R1618, R1619, R1620
            - R1617_LT12:   Leveringsaantekening.Datum aanvang materiële periode resultaat = LEEG
            - R1617_LT13:   Leveringsaantekening.Datum aanvang materiële periode resultaat = Peilmoment materieel resultaat
            - R1618_LT06:   Leveringsaantekening.Datum einde materiële periode = LEEG
            - R1618_LT07:   Leveringsaantekening.Datum einde materiële periode = Selectietaak.Peilmoment materieel resultaat + 1 dag
            - R1619_LT07:   Leveringsaantekening.Datum/tijd aanvang formele periode resultaat = LEEG
            - R1619_LT09:   Leveringsaantekening.Datum/tijd aanvang formele periode resultaat = Selectietaak.Peilmoment formeel resultaat
            - R1620_LT04:   Leveringsaantekening.Datum/tijd einde formele periode resultaat = Selectietaak.Datum uitvoer
            - R1620_LT05:   Leveringsaantekening.Datum/tijd einde formele periode resultaat = Selectietaak.Peilmoment formeel resultaat
            Uitwerking:
            Taak 1 maakt gebruik van leveringsautorisatie SelectiePopulatieBeperkingUithoorn
            Is een selectie met een populatiebepering op woonplaats Uithoorn, Anne_met_Historie voldoet hieraan.
            Taak 2 maakt gebruik van leveringsautorisatie Selectie
            Is een selectie op geboortedatum 19600821 Kim en Kanye voldoen hieraan.
            Status van de taak is te protocolleren
            Bestand voor de te protocolleren taak is gevonden
            Protocolleren wordt gestart dmv een step, vervolgens inhoudelijke check

Given alle selectie personen zijn verwijderd
Given selectie personen uit bestanden:
| filenaam                     |
| /LO3PL/R2286/Kim.xls         |
| /LO3PL/R2286/Kanye.xls       |
| /LO3PL/Anne_met_Historie.xls |

Given alle selectietaken zijn verwijderd
Given een selectierun met de volgende selectie taken:
| datplanning | status      | peilmommaterieelresultaat | peilmomformeelresultaat | dienstSleutel         |
| vandaag     | Uitvoerbaar | 20151231                  | nu                      | Selectiemetpeilmoment |
| vandaag     | Uitvoerbaar |                           |                         | selectiebundel1       |

When start selectie run
And wacht tot selectie run gestart
And wacht maximaal 2 minuten tot selectie run klaar

Then zijn de volgende resultaat files aanwezig voor selectietaak met dienstsleutel 'selectiebundel1' en datumuitvoer 'vandaag':
| type                  | aantal |
| Resultaatset personen | >=1    |
| Resultaatset totalen  | ==1    |
| Controlebestand       | ==1    |

Then zijn de volgende resultaat files aanwezig voor selectietaak met dienstsleutel 'Selectiemetpeilmoment' en datumuitvoer 'vandaag':
| type                  | aantal |
| Resultaatset personen | >=1    |
| Resultaatset totalen  | ==1    |

Given de beheerder aangeeft dat de volgende selectietaken geprotocolleerd dienen te worden:
| dienstSleutel         |
| selectiebundel1       |
| Selectiemetpeilmoment |

Given protocolleren voor selecties wordt gestart
Given wacht maximaal 2 minuten tot protocolleren voor selecties klaar

Then is de selectie geprotocolleerd met 2 leveringsaantekeningen en 3 personen

!-- inhoudelijke check voor selectie taak 2 selectiebundel1 R2568_LT01, R1617_LT12, R1618_LT06, R1619_LT07, R1620_LT04
Then is er voor leveringsautorisatie Selectie en partij Gemeente Standaard geprotocolleerd met de volgende gegevens:
| veld                        | waarde    |
| bsn                         | 606417801 |
| tsklaarzettenlev            | NU        |
| dataanvmaterieleperioderes  | NULL      |
| dateindematerieleperioderes | NULL      |
| tsaanvformeleperioderes     | NULL      |
| tseindeformeleperioderes    | VANDAAG   |
| admhnd                      | NULL      |
| soortSynchronisatie         | NULL      |
| scopepatroon                | NULL      |
| soortDienst                 | Selectie  |

!-- inhoudelijke check voor selectie taak 1 Selectiemetpeilmoment R2568_LT01, R1617_LT13, R1618_LT07, R1619_LT09, R1620_LT05
Then is er voor leveringsautorisatie SelectiePopBepUithoorn en partij Gemeente Standaard geprotocolleerd met de volgende gegevens:
| veld                        | waarde    |
| bsn                         | 590984809 |
| soortDienst                 | Selectie  |
| tsklaarzettenlev            | NU        |
| dataanvmaterieleperioderes  | 20151231  |
| dateindematerieleperioderes | 20160101  |
| tsaanvformeleperioderes     | NU        |
| tseindeformeleperioderes    | NU        |
| admhnd                      | NULL      |
| soortSynchronisatie         | NULL      |
| scopepatroon                | NULL      |

!-- R2568 (R1619/R1620) Datum/tijd  Peilmoment formeel resultaat controle
Then zijn de volgende selectie taken correct geprotocolleerd:
| dienstSleutel         |
| selectiebundel1       |
| Selectiemetpeilmoment |


Scenario:   2.  Controleer dat de er NIET geprotocolleerd wordt na de selectie
            LT: R2662_LT02
            Uitwerking:
            Status van de taak is ongelijk aan Te protocolleren

Given alle selectietaken zijn verwijderd
Given een selectierun met de volgende selectie taken:
| datplanning | status      | dienstSleutel   |
| vandaag     | Uitvoerbaar | selectiebundel1 |

Given alle selectie personen zijn verwijderd
Given selectie personen uit bestanden:
| filenaam               |
| /LO3PL/R2286/Kim.xls   |
| /LO3PL/R2286/Kanye.xls |

When start selectie run
And wacht tot selectie run gestart
And wacht maximaal 2 minuten tot selectie run klaar

Given protocolleren voor selecties wordt gestart
Given wacht maximaal 2 minuten tot protocolleren voor selecties klaar

Then is de selectie geprotocolleerd met 0 leveringsaantekeningen en 0 personen


Scenario:   3.  Selectie met historievorm Materieel, formeel peilmoment GEVULD
                LT: R1617_LT14,R1619_LT10
                Verwacht resultaat:
                - R1617_LT14:   Leveringsaantekening.Datum aanvang materiële periode resultaat = LEEG
                - R1619_LT10:   Leveringsaantekening.Datum/tijd aanvang formele periode resultaat = Selectietaak.Peilmoment formeel resultaat

Given alle selectie personen zijn verwijderd
Given selectie personen uit bestanden:
| filenaam                     |
| /LO3PL/R2286/Kim.xls         |
| /LO3PL/R2286/Kanye.xls       |
| /LO3PL/Anne_met_Historie.xls |

Given alle selectietaken zijn verwijderd
Given een selectierun met de volgende selectie taken:
| datplanning | status      | peilmommaterieelresultaat | peilmomformeelresultaat | dienstSleutel                 |
| vandaag     | Uitvoerbaar | 20151231                  | nu                      | SelectieHistorievormMaterieel |

When start selectie run
And wacht tot selectie run gestart
And wacht maximaal 2 minuten tot selectie run klaar

Given de beheerder aangeeft dat de volgende selectietaken geprotocolleerd dienen te worden:
| dienstSleutel                 |
| SelectieHistorievormMaterieel |

Given protocolleren voor selecties wordt gestart
Given wacht maximaal 2 minuten tot protocolleren voor selecties klaar


!-- inhoudelijke check voor selectie taak 1 SelectieHisMaterieel R1617_LT14, R1619_LT10
Then is er voor leveringsautorisatie SelectieHisMaterieel en partij Gemeente Standaard geprotocolleerd met de volgende gegevens:
| veld                       | waarde |
| dataanvmaterieleperioderes | NULL   |
| tsaanvformeleperioderes    | NU     |


Scenario:   4.  Selectie met historievorm Materieel, formeel peilmoment LEEG
                LT: R1619_LT08
                Verwacht resultaat:
                - R1619_LT08:   Leveringsaantekening.Datum/tijd aanvang formele periode resultaat = gebruikte systeemdatum bij de betreffende Selectietaak.Peilmoment formeel resultaat

Given alle selectie personen zijn verwijderd
Given selectie personen uit bestanden:
| filenaam                     |
| /LO3PL/R2286/Kim.xls         |
| /LO3PL/R2286/Kanye.xls       |
| /LO3PL/Anne_met_Historie.xls |

Given alle selectietaken zijn verwijderd
Given een selectierun met de volgende selectie taken:
| datplanning | status      | peilmommaterieelresultaat | peilmomformeelresultaat | dienstSleutel                 |
| vandaag     | Uitvoerbaar | 20151231                  |                         | SelectieHistorievormMaterieel |

When start selectie run
And wacht tot selectie run gestart
And wacht maximaal 2 minuten tot selectie run klaar

Given de beheerder aangeeft dat de volgende selectietaken geprotocolleerd dienen te worden:
| dienstSleutel                 |
| SelectieHistorievormMaterieel |

Given protocolleren voor selecties wordt gestart
Given wacht maximaal 2 minuten tot protocolleren voor selecties klaar

!-- R2678
!-- Indien bij de Selectietaak een historiefilter is toegepast waarbij de systeemdatum is gebruikt als peilmoment materieel en/of peilmoment formeel
!-- dan
!-- registreer de gebruikte systeemdatum bij de betreffende Selectietaak.Peilmoment materieel resultaat en/of bij Selectietaak.Peilmoment formeel resultaat

Then is er voor leveringsautorisatie SelectieHisMaterieel en partij Gemeente Standaard geprotocolleerd met de volgende gegevens:
| veld                     | waarde |
| tsaanvformeleperioderes  | NU     |
| tseindeformeleperioderes | NU     |


Scenario:   5.  Selectie met historievorm MaterieelFormeel, formeel peilmoment GEVULD
                LT: R1619_LT11
                Verwacht resultaat:
                - R1619_LT11:   Leveringsaantekening.Datum/tijd aanvang formele periode resultaat = LEEG

Given alle selectie personen zijn verwijderd
Given selectie personen uit bestanden:
| filenaam                     |
| /LO3PL/R2286/Kim.xls         |
| /LO3PL/R2286/Kanye.xls       |
| /LO3PL/Anne_met_Historie.xls |

Given alle selectietaken zijn verwijderd
Given een selectierun met de volgende selectie taken:
| datplanning | status      | peilmommaterieelresultaat | peilmomformeelresultaat | dienstSleutel                        |
| vandaag     | Uitvoerbaar | 20151231                  | nu                      | SelectieHistorievormMaterieelFormeel |

When start selectie run
And wacht tot selectie run gestart
And wacht maximaal 2 minuten tot selectie run klaar

Given de beheerder aangeeft dat de volgende selectietaken geprotocolleerd dienen te worden:
| dienstSleutel                        |
| SelectieHistorievormMaterieelFormeel |

Given protocolleren voor selecties wordt gestart
Given wacht maximaal 2 minuten tot protocolleren voor selecties klaar


!-- inhoudelijke check voor selectie taak 1 SelectieHisMaterieelFormeel R1619_LT11
!-- historievorm materieel-formeel, dus Leveringsaantekening.Datum/tijd aanvang formele periode resultaat niet vullen ('leeg').
Then is er voor leveringsautorisatie SelectieHisMaterieelFormeel en partij Gemeente Standaard geprotocolleerd met de volgende gegevens:
| veld                    | waarde |
| tsaanvformeleperioderes | NULL   |
