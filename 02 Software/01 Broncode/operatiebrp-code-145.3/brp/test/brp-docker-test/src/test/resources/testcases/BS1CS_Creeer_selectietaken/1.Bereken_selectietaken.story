Meta:
@status Klaar
@driverSoort phantomjs
@binaryPath phantomjs.binary
@closeDriver true

Narrative:
Als beheerder wil ik de gecreerde taken kunnen zien voor een dienst welke binnen het zoekbereik liggen


Scenario:  1. Opgeven van overzichtsperiode bij in te plannen selecties is verplicht
              LT:R2810_LT01, R2810_LT02, R2810_LT03

Given er is een verbinding met de applicatie
!-- R2810_LT01
When begindatum is aangepast naar ''
And einddatum is aangepast naar ''
Then is de zoek button niet klikbaar

!-- R2810_LT02
When begindatum is aangepast naar '01-06-2017'
And einddatum is aangepast naar ''
Then is de zoek button niet klikbaar

!-- R2810_LT03
When begindatum is aangepast naar ''
And einddatum is aangepast naar '01-09-2017'
Then is de zoek button niet klikbaar


Scenario:  2. Er worden geen selectie taken gevonden
              LT: R2809_LT01
              DOEL: Vaststellen dat er een melding getoond wordt indien er op basis van de berekende selectie datum en de taak status geen selectie taken zijn om te tonen


Given er is een verbinding met de applicatie
When begindatum is aangepast naar 01-01-2010
And einddatum is aangepast naar 01-03-2010
And er op zoeken wordt geklikt
Then wacht tot meldingstekst 'Er zijn geen in te plannen selecties gevonden.' aanwezig is
Then is de melding met tekst 'Er zijn geen in te plannen selecties gevonden.' aanwezig

Scenario:  3. De opgegeven einddatum mag niet voor de begin datum liggen
              LT: R2811_LT01


Given er is een verbinding met de applicatie
When begindatum is aangepast naar 01-03-2017
And einddatum is aangepast naar 01-01-2017
And er op zoeken wordt geklikt
Then wacht tot meldingstekst 'De opgegeven einddatum mag niet voor de begindatum liggen.' aanwezig is
Then is de melding met tekst 'De opgegeven einddatum mag niet voor de begindatum liggen.' aanwezig

Scenario:  4. De gevraagde overzichtsperiode mag maximaal een jaar zijn
              LT: R2825_LT01
              DOEL: Vaststellen dat indien de opgegeven periode waarvoor selectie taken opgevraagd worden groter is dan 1 jaar, er een melding wordt gegeven aan de gebruiker


Given er is een verbinding met de applicatie
When begindatum is aangepast naar 01-01-2017
And einddatum is aangepast naar 01-01-2018
And er op zoeken wordt geklikt
Then wacht tot meldingstekst 'De geselecteerde selectieperiode is te groot (maximum is 12 maanden).' aanwezig is
Then is de melding met tekst 'De geselecteerde selectieperiode is te groot (maximum is 12 maanden).' aanwezig

Scenario: 5. Melding als er na filtering geen selectie taken worden gevonden
             LT: R2812_LT01
             DOEL: Vaststellen dat als er een zoek criteria ingegeven wordt waarvoor geen selectie taken voldoen er een melding getoond wordt aan de gebruiker

Given er is een verbinding met de applicatie
When er op zoeken wordt geklikt
Then zijn er 3 selectietaken aanwezig
When voor kolom dienstid de filterwaarde AAA wordt opgegeven
Then is de melding met tekst 'Er zijn geen in te plannen selecties gevonden met de gegeven extra zoekcriteria.' aanwezig
Then zijn er 0 selectietaken aanwezig

Scenario:  6. Het overzicht bevat de correcte kolommen
              LT: BS1RI_LT01
              DOEL: Vaststellen dat de juiste kolommen aanwezig zijn voor het selectie taken overzicht

Given er is een verbinding met de applicatie
When er op zoeken wordt geklikt
Then is kolom ID aanwezig in het taken overzicht
Then is kolom Dienst aanwezig in het taken overzicht
Then is kolom Toegang aanwezig in het taken overzicht
Then is kolom Stelsel aanwezig in het taken overzicht
Then is kolom Partij aanwezig in het taken overzicht
Then is kolom Eerste Selectiedatum aanwezig in het taken overzicht
Then is kolom Interval aanwezig in het taken overzicht
Then is kolom Eenheid Interval aanwezig in het taken overzicht
Then is kolom Soort aanwezig in het taken overzicht
Then is kolom Berekende selectiedatum aanwezig in het taken overzicht
Then is kolom Datum Planning aanwezig in het taken overzicht