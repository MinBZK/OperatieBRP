Meta:
@status             Klaar
@sleutelwoorden     Expressietaal

Narrative:
Testen voor het valideren van de verschillende functies in de expressie taal.
De functies die getest worden:
- Datum Functies (DAG, MAAND, JAAR, AANTAL_DAGEN, VANDAAG, SELECTIE_DATUM, DATUM, LAATSTE_DAG)

Scenario: 01 Datum functie DAG
             LT:
             Expressie: DAG(Persoon.Geboorte.Datum)-1=0

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
Given leveringsautorisatie uit autorisatie/functie_DAG

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Expressietaal'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|595891305

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 02 Datum functie MAAND
             LT:
             Expressie: MAAND(Persoon.Geboorte.Datum)-4 = 0

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
Given leveringsautorisatie uit autorisatie/functie_MAAND

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Expressietaal'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|595891305

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 03 Datum functie JAAR
             LT:
             Expressie: JAAR(Persoon.Geboorte.Datum)+1 > 1975

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
Given leveringsautorisatie uit autorisatie/functie_JAAR

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Expressietaal'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|595891305

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 04 Datum functie AANTAL_DAGEN
             LT:
             Expressie: DAG(Persoon.Geboorte.Datum) < AANTAL_DAGEN(1976,4)

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
Given leveringsautorisatie uit autorisatie/functie_AANTAL_DAGEN

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Expressietaal'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|595891305

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 05 Datum functie VANDAAG
             LT:
             Expressie: Persoon.Inschrijving.Datum A< VANDAAG(-18)
             Verwacht resultaat: WAAR


Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
Given leveringsautorisatie uit autorisatie/functie_VANDAAG

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Expressietaal'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|595891305

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 06 Datum functie DATUM
             LT:
             Expressie: DATUM(1970,0,0) = DATUM(1969,11,30)

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
Given leveringsautorisatie uit autorisatie/functie_DATUM

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Expressietaal'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|595891305

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 07 Datum functie LAATSTE_DAG
             LT:
             Expressie: LAATSTE_DAG(1999) A= 1999/12/31

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
Given leveringsautorisatie uit autorisatie/functie_LAATSTE_DAG

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Expressietaal'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|595891305

Then heeft het antwoordbericht verwerking Geslaagd


Scenario: 08a. Datum functie SELECTIE_DATUM evalueert tot ONWAAR
            LT:

!-- Expressie: JAAR(SELECTIE_DATUM() - ^3/0/0) <1900

Given leveringsautorisatie uit autorisatie/functie_SELECTIEDATUM_ONWAAR
Given een selectierun met de volgende selectie taken:
|id |datplanning | status               | dienstSleutel                                                 |
|1  |vandaag      | Uitvoerbaar         | functie_SELECTIEDATUM_ONWAAR |

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '0' personen in '1' resultaatbestanden


Scenario: 08b. Datum functie SELECTIE_DATUM evalueert tot WAAR
                LT:

!-- Expressie:JAAR(SELECTIE_DATUM() - ^3/0/0) >1900

Given leveringsautorisatie uit autorisatie/functie_SELECTIEDATUM_WAAR
Given een selectierun met de volgende selectie taken:
|id |datplanning | status               | dienstSleutel                                                 |
|1  |vandaag      | Uitvoerbaar         | functie_SELECTIEDATUM_WAAR |

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen
