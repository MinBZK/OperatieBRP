Meta:

@status             Klaar
@usecase            SL.0.US
@sleutelwoorden     Uitvoeren Selectie

Narrative:
Autorisatieregels op dit moment afgetest volgens implementatie op dezelfde wijze als
autorisatie voor overige diensten. De uitgecommente regels zijn de vervangende regels,
mocht autorisatie voor selectie eventueel specifiek gemaakt worden

R1258: De toegang leveringsautorisatie uit een verzoekbericht moet geldig zijn
!-- R2647: De toegang leveringsautorisatie van de selectietaak moet geldig zijn

R1261: De leveringsautorisatie uit een verzoekbericht moet geldig zijn
!-- R2648: De leveringsautorisatie waarvoor de selectietaak wordt uitgevoerd moet geldig zijn

R1262: De gevraagde dienst moet geldig zijn

R1263: De leveringsautorisatie mag niet geblokkeerd zijn

R1264: De gevraagde dienst mag niet geblokkeerd zijn

R2052: De toegang leveringsautorisatie mag niet geblokkeerd zijn

R2056: De dienstbundel van de gevraagde dienst mag niet geblokkeerd zijn

R2239: De dienstbundel waarin de gevraagde dienst is opgenomen moet geldig zijn

R2242: De geautoriseerde voor de toegang leveringsautorisatie moet een geldige partij zijn
!-- R2650: De geautoriseerde partij moet geldig zijn

R2245: De combinatie partij en rol voor de toegang leveringsautorisatie in het verzoekbericht moet geldig zijn
!-- R2649: De combinatie partij en rol van de toegang leveringsautorisatie moet geldig zijn

R2258: Dienstbundels met onvolledig geconverteerde populatiebeperkingen buiten beschouwing laten

R2524: Het stelsel van de leveringsautorisatie moet BRP zijn als de partij op het BRP-stelsel aangesloten is

!-- R2574: Aanmaken logregel na autorisatie fout bij selecties

!-- In deze story van alle autorisatie regels 1 foutgeval om te zien of de regels voor de dienst geimplementeerd zijn

Scenario:   1.  toegangleveringsautorisatie datum ingang is groter dan systeemdatum
                LT: R1258_LT05, R2572_LT04, R2574_LT01
                Verwacht resultaat:
                - R1258 gelogd

Given leveringsautorisatie uit aut/AutorisatieLevering/R1258_toegangleveringsautorisatie_datumingang_morgen

Given een selectierun met de volgende selectie taken:
| id | datplanning | status      | dienstSleutel     |
| 1  | vandaag     | Uitvoerbaar | ToegangA/DienstB |

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar

Then is er een autorisatiefout gelogd met regelcode R1258

Scenario:   2.  Leveringsautorisatie is niet geldig, datumingang ligt na de systeemdatum
                LT: R1261_LT04
                Verwacht resultaat:
                - R1261 gelogd

Given leveringsautorisatie uit aut/AutorisatieLevering/R1261_leveringsautorisatie_morgen_geldig
Given een selectierun met de volgende selectie taken:
| id | datplanning | status      | dienstSleutel     |
| 1  | vandaag     | Uitvoerbaar | ToegangA/DienstB |

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar

Then is er een autorisatiefout gelogd met regelcode R1261

Scenario:   3.  Dienst is niet geldig, datumingang ligt na de systeemdatum
                LT: R1262_LT30, R2057_LT30
                Verwacht resultaat:
                - R1262 gelogd

Given leveringsautorisatie uit aut/AutorisatieLevering/R1262_dienst_morgen_geldig
Given een selectierun met de volgende selectie taken:
| id | datplanning | status      | dienstSleutel     |
| 1  | vandaag     | Uitvoerbaar | ToegangA/DienstB |

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar

Then is er een autorisatiefout gelogd met regelcode R1262

Scenario:   4.  Leveringsautorisatie is geblokkeerd
                LT: R1263_LT02
                Verwacht resultaat:
                - R1263 gelogd

Given leveringsautorisatie uit aut/AutorisatieLevering/R1263_LeveringsautorisatieGeblokkeerd
Given een selectierun met de volgende selectie taken:
| id | datplanning | status      | dienstSleutel     |
| 1  | vandaag     | Uitvoerbaar | ToegangA/DienstB |

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar

Then is er een autorisatiefout gelogd met regelcode R1263

Scenario:   5.  Dienst is geblokkeerd
                LT: R1264_LT26, R2560_LT02
                Verwacht resultaat:
                - R1264 gelogd

Given leveringsautorisatie uit aut/AutorisatieLevering/R1264_DienstGeblokkeerd
Given een selectierun met de volgende selectie taken:
| id | datplanning | status      | dienstSleutel     |
| 1  | vandaag     | Uitvoerbaar | ToegangA/DienstB |

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar

Then is er een autorisatiefout gelogd met regelcode R1264

Then is status transitie:
| selectieTaakId | statusTransitie |
| 1              | [UITVOERBAAR, AUTORISATIE_GEWIJZIGD]     |

Scenario:   6.  ToegangLeveringsautorisatie is geblokkeerd
                LT: R2052_LT02
                Verwacht resultaat:
                - R2052 gelogd

Given leveringsautorisatie uit aut/AutorisatieLevering/R2052_ToegangLeveringsautorisatieGeblokkeerd
Given een selectierun met de volgende selectie taken:
| id | datplanning | status      | dienstSleutel     |
| 1  | vandaag     | Uitvoerbaar | ToegangA/DienstB |

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar

Then is er een autorisatiefout gelogd met regelcode R2052

Scenario:   7.  Dienstbundel geblokkeerd
                LT: R2056_LT26
                Verwacht resultaat:
                - R2056 gelogd

Given leveringsautorisatie uit aut/AutorisatieLevering/R2056_DienstbundelGeblokkeerd
Given een selectierun met de volgende selectie taken:
| id | datplanning | status      | dienstSleutel     |
| 1  | vandaag     | Uitvoerbaar | ToegangA/DienstB |

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar

Then is er een autorisatiefout gelogd met regelcode R2056

Scenario:   8.  Dienstbundel ongeldig
                LT: R2239_LT05
                Verwacht resultaat:
                - R2239 gelogd


Given leveringsautorisatie uit aut/AutorisatieLevering/R2239_DienstbundelOngeldig
Given een selectierun met de volgende selectie taken:
| id | datplanning | status      | dienstSleutel     |
| 1  | vandaag     | Uitvoerbaar | ToegangA/DienstB |

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar

Then is er een autorisatiefout gelogd met regelcode R2239

Scenario:   9.  Partij is ongeldig
                LT: R2242_LT05
                Verwacht resultaat:
                - R2242 gelogd

Given leveringsautorisatie uit aut/AutorisatieLevering/R2242_Partij_Ongeldig
Given een selectierun met de volgende selectie taken:
| id | datplanning | status      | dienstSleutel     |
| 1  | vandaag     | Uitvoerbaar | ToegangA/DienstB |

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar

Then is er een autorisatiefout gelogd met regelcode R2242


Scenario:   10. partijrol ongeldig
                LT: R2245_LT04
                Verwacht resultaat:
                - R2245 gelogd

Given leveringsautorisatie uit aut/AutorisatieLevering/R2245_PartijrolOngeldig
Given een selectierun met de volgende selectie taken:
| id | datplanning | status      | dienstSleutel     |
| 1  | vandaag     | Uitvoerbaar | ToegangA/DienstB |

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar

Then is er een autorisatiefout gelogd met regelcode R2245

Scenario:   11. Datum Overgang BRP groter dan systeemdatum
                LT: R2524_LT06
                Verwacht resultaat:
                - R2524 gelogd

Given leveringsautorisatie uit aut/AutorisatieLevering/R2524_DatumOvergangBRP
Given een selectierun met de volgende selectie taken:
| id | datplanning | status      | dienstSleutel     |
| 1  | vandaag     | Uitvoerbaar | ToegangA/DienstB |

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar


Then is er een autorisatiefout gelogd met regelcode R2524