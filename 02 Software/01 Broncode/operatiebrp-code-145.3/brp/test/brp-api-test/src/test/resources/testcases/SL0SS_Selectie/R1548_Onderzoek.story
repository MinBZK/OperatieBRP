Meta:
@status             Klaar
@sleutelwoorden     Selectie
@regels             R1546, R1548, R1353

Narrative:


De attributen Datum/tijd registratie en Datum/tijd verval van een 'Inhoudelijke groep' (R1540) of van een 'Onderzoeksgroep' (R1543)
mogen alleen worden opgenomen in het te leveren resultaat als er bij de Dienst waarvoor geleverd wordt een corresponderend voorkomen
bestaat van Dienstbundel \ Groep met Dienstbundel \ Groep.Formele historie? = 'Ja'.

NB: Nadere aanduiding verval en bijhouding beëindigd? worden (indien gevuld) bij de levering van een vervallen voorkomen altijd meegeleverd.


Scenario:   1.      Selectie met formele historie
                    LT: R1548_LT05
                    Verwacht resultaat:
                    - TijdstipRegistratie bij onderzoek

Given leveringsautorisatie uit aut/SelectieMetFormeleHistorie
Given een selectierun met de volgende selectie taken:
|id | datplanning | status               | dienstSleutel                                                 |
|1  | vandaag     | Uitvoerbaar              | SelectieMetFormeleHistorie |

Given persoonsbeelden uit specials:/MaakBericht/R1805_Anne_Bakker_Meerdere_Onderzoeken_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen

And zijn de volgende resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag':
|volgnummer    |pad
|1             |expecteds/R1548_Scenario1.xml

Scenario:   2.      Selectie zonder formele historie
                    LT: R1548_LT06
                    Verwacht resultaat:
                    - Geen TijdstipRegistratie bij onderzoek

Given leveringsautorisatie uit aut/SelectieZonderFormeleHistorie
Given een selectierun met de volgende selectie taken:
|id | datplanning | status               | dienstSleutel                                                 |
|1  | vandaag     | Uitvoerbaar              | SelectieZonderFormeleHistorie |

Given personen uit specials:specials/Anne_met_Historie_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen

And zijn de volgende resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag':
|volgnummer    |pad
|1             |expecteds/R1548_Scenario2.xml
