Meta:
@status             Klaar
@sleutelwoorden     Selectie
@regels             R2613, R2614

Narrative:
R2613
Indien Dienst.Selectieresultaat controleren? is gelijk aan 'Ja'

maak dan een controlebestand met de volgende waarden in de bestandsnaam:

Selectiedatum
DienstID
SelectietaakID

R2614:
Het controlebestand van een selectie dient gevuld te worden met 50 willekeurig gekozen geselecteerde personen waarvoor geldt dat:

de populatie waaruit de 50 geselecteerde personen willekeurig wordt gekozen betreft het hele selectieresultaat

EN

het persoonsbeeld dat geplaatst wordt dient een exacte kopie te zijn van het persoonsbeeld dat in de selectieresultaatset is geplaatst.


Scenario:   3.      Selectie, Indien Dienst.IndSelresultaatControleren true; dan wordt er een controlebestand gemaakt
                    LT: R2613_LT01, R2614_LT02
                    Verwacht resultaat:
                    -controlebestand bestand wel aanwezig, met 50 personen

Given leveringsautorisatie uit aut/SelectieAutWaarControlebestand
Given een selectierun met de volgende selectie taken:
| id | datplanning | status  | dienstSleutel                                                 |
| 1  | vandaag     | Uitvoerbaar | SelectieAutWaarControlebestand |

Given personen uit specials:specials/Anne_met_Historie_xls
Given bulk mode actief met 100 personen

When de selectie wordt gestart
And wacht 120 seconden tot selectie run klaar
Then zijn er de volgende controlebestanden:
| selectieTaakId | aanwezig | aantalPersonen |
| 1              | ja       | 50             |

Scenario:   4.      Selectie, Indien Dienst.IndSelresultaatControleren true EN selectie soort = selectie met plaatsing
                    dan wordt er geen controlebestand gemaakt
                    LT: R2613_LT02, R2614_LT03, R2614_LT01
                    Verwacht resultaat:
                    -controlebestand bestand niet aanwezig

Given leveringsautorisatie uit aut/SelectieMetPlaatsenAfnemerindicatieControleBestand

Given een selectierun met de volgende selectie taken:
| id | datplanning | status  | dienstSleutel                                                   |
| 1  | vandaag     | Uitvoerbaar | SelectieMetPlaatsenAfnemerindicatieControleBestand/Selectie |

Given selectielijsten per dienst:
|selectietaak| soortIdentificatie   | identificatienummers | dienstSleutel                                                   |
|1| Burgerservicenummer | 595891305            | SelectieMetPlaatsenAfnemerindicatieControleBestand/Selectie |

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '0' personen

Then zijn er de volgende controlebestanden:
| selectieTaakId | aanwezig | aantalPersonen |
| 1              | nee      | -              |

Then is er voor persoon met bsn 595891305 en leveringautorisatie SelectieMetPlaatsenAfnInd en partij Gemeente Utrecht een afnemerindicatie geplaatst
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie SelectieMetPlaatsenAfnInd is ontvangen en wordt bekeken

