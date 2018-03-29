Meta:
@status             Klaar
@sleutelwoorden     Selectie

Narrative:
Selecties met maximale bestandsgrootte ingesteld.
Check of de maximale bestandsgrootte wordt aangehouden
Check of de maximale bestandsgrootte wordt aangehouden indien max aantal personen is ingesteld
Check of de maximaal aantal personen wordt aangehouden indien de maximale bestandsgrootte is ingesteld wordt al gecheckt in

Scenario: 1. Selectie met maximale bestandsgrootte op 1 mb.
            LT: R2556_LT03
            Uitwerking:
            100 Personen worden ingeladen om het selectie resultaatbestand groter dan 1 mb te maken
            Het bestand selectieresultaatset personen wordt groter dan 1mb en wordt opgesplits in meerdere bestanden nl 4.
            De selectieresultaatset personen mogen niet groter zijn dan 1 mb.
            De stap Then resultaat files aanwezig doet de checkt op de maximale grootte
            De stap single-threaded mode zorgt ervoor dat er niet gelijk al meerdere bestanden worden aangemaakt.
            Check op voor de controle op de bestandsgrootte moet nog worden toegevoegd.


Given leveringsautorisatie uit aut/SelectieMaxBestandsGrootte
Given een selectierun met de volgende selectie taken:
|id |datplanning | status               | dienstSleutel                                                 |
|1  |vandaag      | Uitvoerbaar         | Selectiemaxbestandsgrootte |

Given personen uit specials:specials/Anne_met_Historie_xls
Given bulk mode actief met 100 personen

When de selectie wordt gestart in single-threaded mode
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '100' personen in '4' resultaatbestanden


Scenario: 2. Selectie met maximale bestandsgrootte op 1 mb en aantal personen op max 50
            LT: R2556_LT03
            Uitwerking:
            100 Personen worden ingeladen om het selectie resultaatbestand groter dan 1 mb te maken
            Het bestand selectieresultaatset personen wordt groter dan 1mb en wordt opgesplits in meerdere bestanden nl 4.
            De selectieresultaatset personen mogen niet groter zijn dan 1 mb.
            De stap Then resultaat files aanwezig doet de checkt op de maximale grootte
            Het aantal personen in het bestand mogen maximaal 50 zijn, echter zou dit de bestandsgrootte overschrijven
            Deze test checkt of het maximaal aantal personen niet zorgt voor een overwrite van de bestandsgrootte
            De stap single-threaded mode zorgt ervoor dat er niet gelijk al meerdere bestanden worden aangemaakt.
            Check op voor de controle op de bestandsgrootte moet nog worden toegevoegd.

Given leveringsautorisatie uit aut/SelectieMaxBestandsGrootte_enPersonen50
Given een selectierun met de volgende selectie taken:
|id |datplanning | status               | dienstSleutel                                                 |
|1  |vandaag      | Uitvoerbaar         | Selectiemaxbestandsgrootteenpersonen50 |

Given personen uit specials:specials/Anne_met_Historie_xls
Given bulk mode actief met 100 personen

When de selectie wordt gestart in single-threaded mode
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '100' personen in '4' resultaatbestanden


Scenario: 3. Selectie met maximale bestandsgrootte op 10 mb en aantal personen op max 5000
            LT: R2556_LT03
            Uitwerking:
            Zelfde situatie als scenario 2, maar met grotere aantallen

!-- Selectie run lijkt niet afgerond te worden binnen de gegeven tijd, geeft een assertion error...

Given leveringsautorisatie uit aut/SelectieMaxBestandsGrootte_enPersonen5000
Given een selectierun met de volgende selectie taken:
|id |datplanning | status               | dienstSleutel                                                 |
|1  |vandaag      | Uitvoerbaar         | Selectiemaxbestandsgrootteenpersonen5000 |

Given personen uit specials:specials/Anne_met_Historie_xls
Given bulk mode actief met 10000 personen

When de selectie wordt gestart in single-threaded mode
And wacht 120 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '10000' personen in '27' resultaatbestanden




