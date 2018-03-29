Meta:
@status Onderhanden
@driverSoort chrome
@binaryPath chrome.binary
@closeDriver false

Narrative:
Als beheerder wil ik de details van een selectietaak kunnen raadplegen en muteren.

Scenario:   1. Raadplegen details selectietaak
            LT: BS.1.RI_001_LT01, BS.1.RI_001_LT02
            Doel:
            Vaststellen dat de juiste details worden getoond bij de selectie taak
            Uitwerking:
            De detailgegevens van een selectietaak bestaan uit:
            •	Gegevens m.b.t. de selectiedienst
            •	Gegevens m.b.t. de selectietaak
            •	Historische gegevens m.b.t. de status van de selectietaak

Given er is een verbinding met de applicatie

When begindatum is aangepast naar 01-01-2017
And einddatum is aangepast naar 31-12-2017
When er op zoeken wordt geklikt
And de volgende checkboxen worden aangevinkt:
|id
|opnieuw-te-plannen
Then is er 1 selectietaak aanwezig
When de details van de eerste taak worden bekeken
!-- Then worden in het detailoverzicht de selectietaakgegevens getoond
Then worden de volgende taakgegevens getoond:
| veld                  | waarde             |
| Selectiesoort:        | Standaard selectie |
| Eerste selectiedatum: | 01-06-2017         |
| Datum planning:       | 01-06-2017         |

Then worden in het detailoverzicht de dienstgegevens getoond

Dienst ID:
1
Selectiecriterium:
WAAR
Historievorm:
Selectieresultaat controleren?:
Ja
Max. aantal personen in selectiebestand:
1
Max. grootte selectiebestand:
Verzend volledig bericht bij aanpassen afnemerindicatie?:
Nee
Leverwijze selectie:


Then worden in het detailoverzicht de toeganggegevens getoond

