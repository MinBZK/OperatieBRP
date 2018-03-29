Meta:

@status             Klaar
@usecase            SL.1.HFS
@sleutelwoorden     Selectie
@regels             R2225


Narrative:
Alleen materiële voorkomens worden geleverd tot en met "(Selectie) Peilmoment materieel resultaat" zoals bekend in het systeem op "(Selectie) Peilmoment formeel resultaat".
Indien één of beide peilmomenten leeg zijn (of niet voorkomen in het bericht) dan wordt 'Systeemdatum' (R2016) als peilmoment(en) gebruikt.
Indien er sprake is van Datum (deels) onbekend (R1273) dan moet de R1283 - Vergelijken (partiële) datums uitgevoerd worden.

Scenario: 1.    Historievorm = Materieel, Peilmoment materieel resultaat is datum x (2016-01-01), Peilmoment Formeel resultaat is datum y (2015-12-31)
                LT: R2225_LT01
                Verwacht resultaat:
                - Utrecht in bericht
                - Oosterhout in bericht
                Uitwerking:
                - Uithoorn sinds 2016-01-02
                - Den Haag sinds 2016-01-01
                - Utrecht sinds  2015-21-31
                - Oosterhout sinds 2010

!-- Selectie met historievorm 'Materieel'
Given leveringsautorisatie uit aut/SelectieHistorievormMaterieel
Given een selectierun met de volgende selectie taken:
|id |datplanning | status    |peilmommaterieelresultaat |peilmomformeelresultaat |dienstSleutel |
|1  |vandaag     | Uitvoerbaar   |20160101                  |2015-12-31T23:00:00Z   |SelectieHistorievormMaterieel |

Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|595891305|2008-12-31 T23:59:00Z

When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen

Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Utrecht']' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Oosterhout']' een node aanwezig in de selectieresultaat persoonbestanden

Then zijn de volgende resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag':
|volgnummer    |pad
|1             |expecteds/expected_R2225/01.xml

Scenario: 2.    Historievorm = Materieel, Peilmoment materieel resultaat is datum x (2016-01-01), Peilmoment Formeel resultaat is datum y (2016-01-01)
                LT: R2225_LT02
                Verwacht resultaat:
                - Den Haag in bericht
                - Utrecht in bericht
                - Oosterhout in bericht
                Uitwerking:
                - Uithoorn sinds 2016-01-02
                - Den Haag sinds 2016-01-01
                - Utrecht sinds  2015-21-31
                - Oosterhout sinds 2010

!-- Selectie met historievorm 'Materieel'
Given leveringsautorisatie uit aut/SelectieHistorievormMaterieel
Given een selectierun met de volgende selectie taken:
|id |datplanning | status    |peilmommaterieelresultaat |peilmomformeelresultaat |dienstSleutel |
|1  |vandaag     | Uitvoerbaar   |20160101                  |2016-01-01T23:00:00Z   |SelectieHistorievormMaterieel |

Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|595891305|2008-12-31 T23:59:00Z

When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen

Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Utrecht']' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Oosterhout']' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:naamOpenbareRuimte[text()='Spui']' een node aanwezig in de selectieresultaat persoonbestanden

Then zijn de volgende resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag':
|volgnummer    |pad
|1             |expecteds/expected_R2225/02.xml

Scenario: 3.    Historievorm = Materieel, Peilmoment materieel resultaat is datum x (2016-01-01), Peilmoment Formeel resultaat is datum y (2016-01-02)
                LT: R2225_LT03
                Verwacht resultaat:
                - Den Haag in bericht
                - Utrecht in bericht
                - Oosterhout in bericht
                Uitwerking:
                - Uithoorn sinds 2016-01-02
                - Den Haag sinds 2016-01-01
                - Utrecht sinds  2015-21-31
                - Oosterhout sinds 2010

!-- Selectie met historievorm 'Materieel'
Given leveringsautorisatie uit aut/SelectieHistorievormMaterieel
Given een selectierun met de volgende selectie taken:
|id |datplanning | status    |peilmommaterieelresultaat |peilmomformeelresultaat |dienstSleutel |
|1  |vandaag     | Uitvoerbaar   |20160101                  |2016-01-02T23:00:00Z   |SelectieHistorievormMaterieel |

Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|595891305|2008-12-31 T23:59:00Z


When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen

Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Utrecht']' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Oosterhout']' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:naamOpenbareRuimte[text()='Spui']' een node aanwezig in de selectieresultaat persoonbestanden

Then zijn de volgende resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag':
|volgnummer    |pad
|1             |expecteds/expected_R2225/03.xml

Scenario: 4.    Historievorm = Materieel, Peilmoment materieel resultaat is LEEG, Peilmoment Formeel resultaat is datum y (2015-12-31)
                LT: R2225_LT04
                Verwacht resultaat:
                - Utrecht in bericht
                - Oosterhout in bericht
                Uitwerking:
                - Uithoorn sinds 2016-01-02
                - Den Haag sinds 2016-01-01 (naamOpenbareRuimte = Spui)
                - Utrecht sinds  2015-21-31
                - Oosterhout sinds 2010

!-- Selectie met historievorm 'Materieel'
Given leveringsautorisatie uit aut/SelectieHistorievormMaterieel
Given een selectierun met de volgende selectie taken:
|id |datplanning | status    |peilmomformeelresultaat |dienstSleutel |
|1  |vandaag     | Uitvoerbaar   |2015-12-31T23:00:00Z   |SelectieHistorievormMaterieel |

Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|595891305|2008-12-31 T23:59:00Z


When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen

Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Utrecht']' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Oosterhout']' een node aanwezig in de selectieresultaat persoonbestanden

Then zijn de volgende resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag':
|volgnummer    |pad
|1             |expecteds/expected_R2225/04.xml

Scenario: 5.    Historievorm = Materieel, Peilmoment materieel resultaat is LEEG, Peilmoment Formeel resultaat is datum y (2016-01-01)
                LT: R2225_LT05
                Verwacht resultaat:
                - Den Haag in bericht
                - Utrecht in bericht
                - Oosterhout in bericht
                Uitwerking:
                - Uithoorn sinds 2016-01-02
                - Den Haag sinds 2016-01-01 (naamOpenbareRuimte = Spui)
                - Utrecht sinds  2015-21-31
                - Oosterhout sinds 2010

!-- Selectie met historievorm 'Materieel'
Given leveringsautorisatie uit aut/SelectieHistorievormMaterieel
Given een selectierun met de volgende selectie taken:
|id |datplanning | status    |peilmomformeelresultaat |dienstSleutel |
|1  |vandaag     | Uitvoerbaar   |2016-01-01T23:00:00Z   |SelectieHistorievormMaterieel |

Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|595891305|2008-12-31 T23:59:00Z

When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen

Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Utrecht']' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Oosterhout']' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:naamOpenbareRuimte[text()='Spui']' een node aanwezig in de selectieresultaat persoonbestanden

Then zijn de volgende resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag':
|volgnummer    |pad
|1             |expecteds/expected_R2225/05.xml

Scenario: 6.1   Historievorm = Materieel, Peilmoment materieel resultaat is LEEG, Peilmoment Formeel resultaat is datum y (2016-01-02)
                LT: R2225_LT06
                Verwacht resultaat:
                - Uithoorn in bericht
                - Den Haag in bericht
                - Utrecht in bericht
                - Oosterhout in bericht
                Uitwerking:
                - Uithoorn sinds 2016-01-02
                - Den Haag sinds 2016-01-01 (naamOpenbareRuimte = Spui)
                - Utrecht sinds  2015-21-31
                - Oosterhout sinds 2010

!-- Selectie met historievorm 'Materieel'
Given leveringsautorisatie uit aut/SelectieHistorievormMaterieel
Given een selectierun met de volgende selectie taken:
|id |datplanning | status    |peilmomformeelresultaat |dienstSleutel |
|1  |vandaag     | Uitvoerbaar   |2016-01-02T23:00:00Z   |SelectieHistorievormMaterieel |

Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|595891305|2008-12-31 T23:59:00Z

When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen

Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Uithoorn']' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Utrecht']' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Oosterhout']' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:naamOpenbareRuimte[text()='Spui']' een node aanwezig in de selectieresultaat persoonbestanden

Then zijn de volgende resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag':
|volgnummer    |pad
|1             |expecteds/expected_R2225/06.xml

Scenario: 6.2   Historievorm = Materieel, Peilmoment materieel resultaat is LEEG, Peilmoment Formeel resultaat is datum y (2016-01-02)
                LT: R2225_LT07
                Verwacht resultaat:
                - Uithoorn  in bericht (op basis van TsReg)
                - Den Haag in bericht
                - Utrecht in bericht
                - Oosterhout in bericht
                Uitwerking:
                - Uithoorn sinds 2016-01-02 (Tsreg = 2016-01-03)
                - Den Haag sinds 2016-01-01 (naamOpenbareRuimte = Spui)
                - Utrecht sinds  2015-21-31
                - Oosterhout sinds 2010

!-- Selectie met historievorm 'Materieel'
Given leveringsautorisatie uit aut/SelectieHistorievormMaterieel
Given een selectierun met de volgende selectie taken:
|id |datplanning | status    |peilmomformeelresultaat |dienstSleutel |
|1  |vandaag     | Uitvoerbaar   |2016-01-02T23:00:00Z   |SelectieHistorievormMaterieel |

Given persoonsbeelden uit specials:specials/Anne_met_Historie2TsReg_xls
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|986096969|2008-12-31 T23:59:00Z

When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen

Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Utrecht']' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Oosterhout']' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:naamOpenbareRuimte[text()='Spui']' een node aanwezig in de selectieresultaat persoonbestanden

Then zijn de volgende resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag':
|volgnummer    |pad
|1             |expecteds/expected_R2225/07.xml

Scenario: 7.    Historievorm = Materieel, Peilmoment materieel resultaat is datum x (31-12-2015), Peilmoment Formeel resultaat is LEEG
                LT: R2225_LT08
                Uitwerking:
                - Verhuizing naar Utrecht  op 31-12-2015
                - Verhuizing naar Rotterdam op 01-01-2016
                - Verhuizing naar Uithoorn op 02-01-2016
                Verwacht Resultaat
                - Oosterhout in het bericht
                - Utrecht in het bericht
                - Rotterdam in het bericht
                - Uithoorn niet in het bericht

Given leveringsautorisatie uit aut/SelectieHistorievormMaterieel
Given een selectierun met de volgende selectie taken:
|id |datplanning | status    |peilmommaterieelresultaat |dienstSleutel |
|1  |vandaag     | Uitvoerbaar   |20151231                  |SelectieHistorievormMaterieel |

Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|595891305|2008-12-31 T23:59:00Z

When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen

Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Utrecht']' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Oosterhout']' een node aanwezig in de selectieresultaat persoonbestanden

Then zijn de volgende resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag':
|volgnummer    |pad
|1             |expecteds/expected_R2225/08.xml

Scenario: 8.    Historievorm = Materieel, Peilmoment materieel resultaat is datum x (01-01-2016), Peilmoment Formeel resultaat LEEG
                LT: R2225_LT09
                Uitwerking:
                - Oorspronkelijke woonplaats Oosterhout
                - Verhuizing naar Utrecht  op 31-12-2015
                - Verhuizing naar Rotterdam op 01-01-2016
                - Verhuizing naar Uithoorn op 02-01-2016
                Verwacht Resultaat
                - Oosterhout in het bericht
                - Utrecht in het bericht
                - Rotterdam en Uithoorn niet in het bericht

!-- Selectie met historievorm 'Materieel'
Given leveringsautorisatie uit aut/SelectieHistorievormMaterieel
Given een selectierun met de volgende selectie taken:
|id |datplanning | status    |peilmommaterieelresultaat |dienstSleutel |
|1  |vandaag     | Uitvoerbaar   |20160101                  |SelectieHistorievormMaterieel |

Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|595891305|2008-12-31 T23:59:00Z

When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen

Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Utrecht']' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Oosterhout']' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:naamOpenbareRuimte[text()='Spui']' een node aanwezig in de selectieresultaat persoonbestanden

Then zijn de volgende resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag':
|volgnummer    |pad
|1             |expecteds/expected_R2225/09.xml

Scenario: 9.   Historievorm = Materieel, Peilmoment materieel resultaat is datum x (02-01-2016), Peilmoment Formeel resultaat is LEEG
                LT: R2225_LT10
                Uitwerking:
                - Oorspronkelijke woonplaats Oosterhout
                - Verhuizing naar Utrecht  op 31-12-2015
                - Verhuizing naar Rotterdam op 01-01-2016
                - Verhuizing naar Uithoorn op 02-01-2016
                Verwacht Resultaat
                - Oosterhout in het bericht
                - Utrecht in het bericht
                - Rotterdam in het bericht
                - Uithoorn in het bericht

!-- Selectie met historievorm 'Materieel'
Given leveringsautorisatie uit aut/SelectieHistorievormMaterieel
Given een selectierun met de volgende selectie taken:
|id |datplanning | status    |peilmommaterieelresultaat |dienstSleutel |
|1  |vandaag     | Uitvoerbaar   |20160102                  |SelectieHistorievormMaterieel |

Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|595891305|2008-12-31 T23:59:00Z

When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen

Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Uithoorn']' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Utrecht']' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Oosterhout']' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:naamOpenbareRuimte[text()='Spui']' een node aanwezig in de selectieresultaat persoonbestanden

Then zijn de volgende resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag':
|volgnummer    |pad
|1             |expecteds/expected_R2225/10.xml

Scenario: 10.   Historievorm = Materieel, Peilmoment materieel resultaat is LEEG, Peilmoment Formeel resultaat is LEEG
                LT: R2225_LT11
                Uitwerking:
                - Oorspronkelijke woonplaats Oosterhout
                - Verhuizing naar Utrecht  op 31-12-2015
                - Verhuizing naar Rotterdam op 01-01-2016
                - Verhuizing naar Uithoorn op 02-01-2016
                Verwacht Resultaat
                - Oosterhout in het bericht
                - Utrecht in het bericht
                - Rotterdam in het bericht
                - Uithoorn in het bericht

!-- Selectie met historievorm 'Materieel'
Given leveringsautorisatie uit aut/SelectieHistorievormMaterieel
Given een selectierun met de volgende selectie taken:
|id |datplanning | status    |dienstSleutel |
|1  |vandaag     | Uitvoerbaar   |SelectieHistorievormMaterieel |

Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|595891305|2008-12-31 T23:59:00Z

When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen

Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Uithoorn']' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Utrecht']' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Oosterhout']' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:naamOpenbareRuimte[text()='Spui']' een node aanwezig in de selectieresultaat persoonbestanden

Then zijn de volgende resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag':
|volgnummer    |pad
|1             |expecteds/expected_R2225/11.xml

Scenario:  11.  Historievorm = Materieel, Peilmoment materieel resultaat is 2016-01-01, Peilmoment Formeel resultaat is LEEG
                LT: R2225_LT13, R2225_LT14
                Verwacht resultaat:
                - Uithoorn              NIET in bericht
                - Den Haag              in bericht
                - Utrecht in bericht    in bericht
                - Oosterhout            in bericht
                Uitwerking:
                - Uithoorn sinds 2016-02-00 tot heden
                - Den Haag sinds 2016-01-00 tot 2016-02-00 (naamOpenbareRuimte = Spui)
                - Oosterhout sinds 2010-12-31 tot 2016-01-00
                - Utrecht sinds  0000-00-00 tot 2010-12-31

!-- Selectie met historievorm 'Materieel'
Given leveringsautorisatie uit aut/SelectieHistorievormMaterieel
Given een selectierun met de volgende selectie taken:
|id |datplanning | status    |peilmommaterieelresultaat |dienstSleutel |
|1  |vandaag     | Uitvoerbaar   |20160101                  |SelectieHistorievormMaterieel |

Given persoonsbeelden uit specials:specials/Anne_met_Historie_geheel_onb_dat_xls

When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen

Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Utrecht']' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Oosterhout']' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:naamOpenbareRuimte[text()='Spui']' een node aanwezig in de selectieresultaat persoonbestanden

Then zijn de volgende resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag':
|volgnummer    |pad
|1             |expecteds/expected_R2225/12.xml
