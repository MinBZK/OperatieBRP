Meta:
@status             Klaar
@sleutelwoorden     Selectie
@regels             R2556, R2627

Narrative:
Als beheerder wil ik aan kunnen geven:
- Hoeveel personen er maximaal opgenomen mogen worden in een selectie resultaat bestand
- Wat de maximale grootte is van het selectie resultaat bestand in Mb's

Scenario: 1. Selectie run max personen per resultaat bestand kleiner dan resultaatset
             LT: R2556_LT02, R2560_LT01
            Verwacht Resultaat:
            - 2 resultaat bestanden met de volledige resultaatset
            - Resultaat bestanden afgebroken op hele personen
            - Resultaat bestand kleiner dan maximale bestandsgrootte

Given leveringsautorisatie uit aut/SelectieMaxAantalPersKleinerdanset
Given een selectierun met de volgende selectie taken:
|id |datplanning | status    |peilmommaterieelresultaat|peilmomformeelresultaat | dienstSleutel |
|1  |vandaag      | Uitvoerbaar |20170101                          |2017-01-01T00:00:00Z  | SelectieMaxAantalPersKleinerdanset |

Given personen uit specials:specials/Anne_met_Historie_xls, specials:specials/Anne_met_Historie2_xls, specials:specials/Anne_Bakker_GBA_Bijhouding_Verhuizing_xls, specials:specials/Jan_xls,

Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|595891305|2008-12-31 T23:59:00Z
|590984809|2008-12-31 T23:59:00Z
|986096969|2008-12-31 T23:59:00Z
|595891305|2008-12-31 T23:59:00Z
|606417801|2008-12-31 T23:59:00Z

When de selectie wordt gestart in single-threaded mode
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '4' personen in '3' resultaatbestanden

!-- R2560_LT01
And is het totalenbestand voor selectietaak '1' en datumplanning 'vandaag' gelijk aan 'expecteds/SL_1_MSP_Maak_Selectieresultaat_1_Resultaatset_totalen.xml'

!-- Geen controle op volledige expected omdat sortering wisseld
!-- And zijn de volgende resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag':
!--  |volgnummer    |pad
!--  |1             |expecteds/Scenario1_Resultaatset_personen_1.xml
!--  |2             |expecteds/Scenario1_Resultaatset_personen_2.xml

Scenario: 2. Selectie run max personen per resultaat is gelijk aan maximum
             LT: R2556_LT04
            Verwacht Resultaat:
            - 1 resultaat bestanden met de volledige resultaatset


Given leveringsautorisatie uit aut/SelectieMaxAantalPersGelijkaanset
Given een selectierun met de volgende selectie taken:
|id |datplanning | status    |peilmommaterieelresultaat|peilmomformeelresultaat | dienstSleutel |
|1  |vandaag      | Uitvoerbaar |20170101                          |2017-01-01T00:00:00Z  | SelectieMaxAantalPersGelijkaanset |

Given personen uit specials:specials/Anne_met_Historie_xls, specials:specials/Anne_met_Historie2_xls, specials:specials/Anne_Bakker_GBA_Bijhouding_Verhuizing_xls, specials:specials/Jan_xls,
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|595891305|2008-12-31 T23:59:00Z
|590984809|2008-12-31 T23:59:00Z
|986096969|2008-12-31 T23:59:00Z
|595891305|2008-12-31 T23:59:00Z
|606417801|2008-12-31 T23:59:00Z

When de selectie wordt gestart in single-threaded mode
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '4' personen in '2' resultaatbestanden
