Meta:

@status             Klaar
@usecase            SL.1.HFS
@sleutelwoorden     Selectie
@regels             R2609


Narrative:
Indien Dienst.Historievorm selectie, Selectietaak.Peilmoment formeel resultaat en
Selectietaak.Peilmoment materieel resultaat leeg zijn,
dan wordt het historiefilter niet toegepast op groepen en objecten. Anders wordt het historiefilter als volgt toegepast:
Indien Dienst.Historievorm selectie de waarde "Leeg" heeft of leeg is, dan R2224 - Historiefilter historievorm = "Leeg" en R2279 - Historiefilter op objecten uitvoeren;
Indien Dienst.Historievorm selectie de waarde "Materieel" heeft, dan R2225 - Historiefilter historievorm = "Materieel" en R2279 - Historiefilter op objecten uitvoeren;
Indien Dienst.Historievorm selectie de waarde "MaterieelFormeel" heeft, dan R2226 - Historiefilter historievorm = "MaterieelFormeel" en R2279 - Historiefilter op objecten uitvoeren
IN DEZE STORY ALLEEN HISTORIEVORM LEEG GETEST, DE REST ZIT IN R2224, R2225, R2226

Scenario:   1. Selectie met historie vorm 'lEEG'
            LT: R2609_LT01, R2279_LT01, R2279_LT02,
            Historievorm: Leeg
            peilmomentMaterieelResultaat: Leeg
            peilmomentFormeelResultaat: Leeg
            Verwacht resultaat:
            - Enkel de voorkomens die geldig zijn op de systeemdatum zijn aanwezig in het antwoord bericht.
            - Onderzoek wel in bericht

!-- | WOONPLAATS    | DAG      | DEG      |                                   |
!-- | Utrecht       | 19760401 | 20100101 |                                   |
!-- | 's-Gravenhage | 20100101 | 20150101 |                                   |
!-- | Uithoorn      | 20150101 | NULL     | <-- Huidig adres want DEG is NULL |

!-- Selectie met historievorm 'Leeg'
Given leveringsautorisatie uit aut/SelectieHistorievormLeeg
Given een selectierun met de volgende selectie taken:
|id |datplanning | status               | dienstSleutel                                             |
|1  |vandaag     | Uitvoerbaar              | SelectieHistorievormLeeg |

Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then zijn de volgende resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag':
|volgnummer    |pad
|1             |expecteds/expected_R2609/01.xml


Scenario:   2. Selectie met historie vorm Leeg en peilmomentMaterieelResultaat gevuld
            LT: R2224_LT02, R2279_LT04
            Historievorm: Leeg
            peilmomentMaterieelResultaat: 2010-01-01
            peilmomentFormeelResultaat: Leeg (dus enkel voorkomens geldig op systeemdatum)
            Verwacht resultaat:
            - Alle voorkomens waarvoor geldt dat (DAG <= 2010-01-01 EN DEG > 2010-01-01)
                    EN
              (tijdstipregistratie < systeemdatum EN tijdstip verval = leeg)
            - Object onderzoek weggefilterd (R2279_LT04)

!-- | WOONPLAATS    | DAG      | DEG      |                                   |
!-- | Utrecht       | 19760401 | 20100101 |                                   |
!-- | 's-Gravenhage | 20100101 | 20150101 |                                   |
!-- | Uithoorn      | 20150101 | NULL     | <-- Huidig adres want DEG is NULL |

!-- Selectie met historievorm 'Leeg'
Given leveringsautorisatie uit aut/SelectieHistorievormLeeg
Given een selectierun met de volgende selectie taken:
|id |datplanning | status    |peilmommaterieelresultaat |dienstSleutel |
|1  |vandaag     | Uitvoerbaar   |20100101                  |SelectieHistorievormLeeg |

Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls
When de selectie wordt gestart
And wacht 20 seconden tot selectie run klaar
Then zijn de volgende resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag':
|volgnummer    |pad
|1             |expecteds/expected_R2609/02.xml

Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:naamOpenbareRuimte[text()='Spui']' een node aanwezig in de selectieresultaat persoonbestanden

Scenario: 3.    Historievorm = Leeg, Peilmoment materieel resultaat is datum x (2016-01-01), Peilmoment Formeel resultaat is datum y (2016-01-01)
                LT: R2224_LT03, R2678_LT05
                Verwacht resultaat:
                - Den Haag in bericht
                Uitwerking:
                - Uithoorn sinds 2016-01-02
                - Den Haag sinds 2016-01-01 (naamOpenbareRuimte = Spui)
                - Utrecht sinds  2015-12-31
                - Oosterhout sinds 2010

!-- Selectie met historievorm 'Leeg'
Given leveringsautorisatie uit aut/SelectieHistorievormLeeg
Given een selectierun met de volgende selectie taken:
|id |datplanning | status    |peilmommaterieelresultaat |peilmomformeelresultaat |dienstSleutel |
|1  |vandaag     | Uitvoerbaar   |20160101                  |2016-01-01T23:00:00Z   |SelectieHistorievormLeeg |

Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|595891305|2008-12-31 T23:59:00Z

When de selectie wordt gestart
And wacht 30 seconden tot selectie run klaar
Then zijn de volgende resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag':
|volgnummer    |pad
|1             |expecteds/expected_R2609/03.xml

Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:naamOpenbareRuimte[text()='Spui']' een node aanwezig in de selectieresultaat persoonbestanden

Scenario: 4.    Historievorm = Leeg, Peilmoment materieel resultaat is datum x (2015-12-31), Peilmoment Formeel resultaat is datum y (2015-12-31)
                LT: R2224_LT04
                Verwacht resultaat:
                - Utrecht in bericht
                Uitwerking:
                - Uithoorn sinds 2016-01-02
                - Den Haag sinds 2016-01-01
                - Utrecht sinds  2015-21-31
                - Oosterhout sinds 2010

!-- Selectie met historievorm 'Leeg'
Given leveringsautorisatie uit aut/SelectieHistorievormLeeg
Given een selectierun met de volgende selectie taken:
|id |datplanning | status    |peilmommaterieelresultaat |peilmomformeelresultaat |dienstSleutel |
|1  |vandaag     | Uitvoerbaar   |20151231                  |2015-12-31T23:00:00Z   |SelectieHistorievormLeeg |

Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|595891305|2008-12-31 T23:59:00Z

When de selectie wordt gestart
And wacht 30 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Utrecht']' een node aanwezig in de selectieresultaat persoonbestanden

Scenario:   5. Selectie met historie vorm Leeg en peilmomentMaterieelResultaat gevuld  / peilmomentFormeelResultaat  = LEEG, PL bevat deels onbekende datum
            LT: R2224_LT05
            Historievorm: Leeg
            peilmomentMaterieelResultaat: 2016-01-31
            peilmomentFormeelResultaat: Leeg
            Verwacht resultaat:
            - Bartoklaan en Spui in het bericht
!-- Geldig voorkomens van adres op peilmoment materieel resultaat 2016-01-31
!-- Adres 4: Bertram 157,   Datum aanvang geldigheid = 2016-02-00
!-- Adres 3: Spui 43,       datum aanvang geldigheid = 2016-01-00   datum einde geldigheid = 2016-02-00
!-- Adres 2: Neude 11,      Datum aanvang geldigheid = 2016-00-00   Datum einde geldigheid = 2016-01-00
!-- Adres 1: Bartoklaan 11, Datum aanvang geldigheid = 2010-12-31   Datum einde geldigheid = 2016-00-00

!-- Selectie met historievorm 'Leeg'
Given leveringsautorisatie uit aut/SelectieHistorievormLeeg
Given een selectierun met de volgende selectie taken:
|id |datplanning | status    |peilmommaterieelresultaat |dienstSleutel |
|1  |vandaag     | Uitvoerbaar   |20160131                  |SelectieHistorievormLeeg |

Given persoonsbeelden uit specials:specials/Anne_met_Historie_gedeeltelijk_onb_dat_xls
When de selectie wordt gestart
And wacht 30 seconden tot selectie run klaar
Then zijn de volgende resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag':
|volgnummer    |pad
|1             |expecteds/expected_R2609/05.xml

Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:naamOpenbareRuimte[text()='Spui']' een node aanwezig in de selectieresultaat persoonbestanden

Scenario:   6. Selectie met historie vorm Leeg en peilmomentMaterieelResultaat gevuld  / peilmomentFormeelResultaat  = LEEG, PL bevat deels onbekende datum
            LT: R2224_LT06
            Historievorm: Leeg
            peilmomentMaterieelResultaat: 2016-02-01
            peilmomentFormeelResultaat: Leeg
            Verwacht resultaat:
            - Spui en Bertram in het bericht
!-- Geldig voorkomens van adres op peilmoment materieel resultaat 2016-02-01
!-- Adres 4: Bertram 157,   Datum aanvang geldigheid = 2016-02-00
!-- Adres 3: Spui 43,       datum aanvang geldigheid = 2016-01-00   datum einde geldigheid = 2016-02-00
!-- Adres 2: Neude 11,      Datum aanvang geldigheid = 2016-00-00   Datum einde geldigheid = 2016-01-00
!-- Adres 1: Bartoklaan 11, Datum aanvang geldigheid = 2010-12-31   Datum einde geldigheid = 2016-00-00

Given leveringsautorisatie uit aut/SelectieHistorievormLeeg
Given een selectierun met de volgende selectie taken:
|id |datplanning | status    |peilmommaterieelresultaat |dienstSleutel |
|1  |vandaag     | Uitvoerbaar   |20160201                 |SelectieHistorievormLeeg |

Given persoonsbeelden uit specials:specials/Anne_met_Historie_gedeeltelijk_onb_dat_xls
When de selectie wordt gestart
And wacht 30 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen

Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:naamOpenbareRuimte[text()='Bertram']' een node aanwezig in de selectieresultaat persoonbestanden

Scenario:   7. Selectie met historie vorm Leeg, PL bevat geheel onbekende datum
            LT: R2224_LT07
            Historievorm: Leeg
            peilmomentMaterieelResultaat: 2005-01-01
            peilmomentFormeelResultaat: Leeg
            Verwacht resultaat: Enkel de actuele voorkomens bij de persoon worden geleverd.

Given leveringsautorisatie uit aut/SelectieHistorievormLeeg
Given een selectierun met de volgende selectie taken:
|id |datplanning | status    |peilmommaterieelresultaat |dienstSleutel |
|1  |vandaag     | Uitvoerbaar   |20050101                 |SelectieHistorievormLeeg |

Given persoonsbeelden uit oranje:DELTAVERS11/DELTAVERS11C10T110_xls
When de selectie wordt gestart
And wacht 30 seconden tot selectie run klaar
Then zijn de volgende resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag':
|volgnummer    |pad
|1             |expecteds/expected_R2609/07.xml

Scenario:   8. Selectie met historie vorm Leeg en peilmomentFormeelResultaat gevuld
            LT: R2224_LT08, R2279_LT05
            Historievorm: Leeg
            peilmomentMaterieelResultaat: Leeg
            peilmomentFormeelResultaat: 2009-12-31
            Verwacht resultaat:
            - Object adres weggefilterd, want historie vorm Leeg (R2279_LT05)

!-- | WOONPLAATS    | DAG      | DEG      |                                   |
!-- | Utrecht       | 19760401 | 20100101 |                                   |
!-- | 's-Gravenhage | 20100101 | 20150101 |                                   |
!-- | Uithoorn      | 20150101 | NULL     | <-- Huidig adres want DEG is NULL |

!-- Selectie met historievorm 'Leeg'
Given leveringsautorisatie uit aut/SelectieHistorievormLeeg
Given een selectierun met de volgende selectie taken:
|id |datplanning | status    |peilmomformeelresultaat |dienstSleutel |
|1  |vandaag     | Uitvoerbaar   |2009-12-31T23:00:00Z   |SelectieHistorievormLeeg |

Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|590984809|2008-12-31 T23:59:00Z


When de selectie wordt gestart
And wacht 30 seconden tot selectie run klaar
Then zijn de volgende resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag':
|volgnummer    |pad
|1             |expecteds/expected_R2609/08.xml

Scenario:   9. Selectie met historie vorm Leeg en peilmomentFormeelResultaat gevuld
            LT: R2224_LT09, R2279_LT05
            Historievorm: Leeg
            peilmomentMaterieelResultaat: Leeg
            peilmomentFormeelResultaat: 2010-01-01
            Verwacht resultaat:
            - Object adres weggefilterd, want historie vorm Leeg (R2279_LT05)

!-- | WOONPLAATS    | DAG      | DEG      |                                   |
!-- | Utrecht       | 19760401 | 20100101 |                                   |
!-- | 's-Gravenhage | 20100101 | 20150101 |                                   |
!-- | Uithoorn      | 20150101 | NULL     | <-- Huidig adres want DEG is NULL |

!-- Selectie met historievorm 'Leeg'
Given leveringsautorisatie uit aut/SelectieHistorievormLeeg
Given een selectierun met de volgende selectie taken:
|id |datplanning | status    |peilmomformeelresultaat |dienstSleutel |
|1  |vandaag     | Uitvoerbaar   |2016-10-16T23:59:59Z   |SelectieHistorievormLeeg |

Given persoonsbeelden uit BIJHOUDING:VHNL04C10T10/Personen_Libby_Thatcher_(Ingeschrevene-I/dbstate003
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|422531881|2008-12-31 T23:59:00Z
|159247913|2008-12-31 T23:59:00Z

When de selectie wordt gestart in single-threaded mode
And wacht 30 seconden tot selectie run klaar

Then zijn de volgende resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag':
|volgnummer    |pad
|1             |expecteds/expected_R2609/09.xml
