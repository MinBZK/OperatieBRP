Meta:

@status             Klaar
@usecase            BV.0.GM, BV.1.GM
@sleutelwoorden     Geef Medebewoner van Persoon
@regels             R1585

Narrative:
De cijfers (v.l.n.r.) a[0] t/m a[9] van het Administratienummer moeten voldoen aan het volgende voorschrift:

a[0] <> 0
a[i] <> a[i+1]
a[0]+a[1]+.+a[9] gedeeld door 11 geeft rest 0 of 5
(1*a[0])+(2*a[1])+(4*a[2])+.+(512*a[9]) is deelbaar door 11

Scenario: 1.    Test met geldig ANR om persoon te zoeken
                LT: R1585_LT03
                Verwacht resultaat:
                - Geslaagd

Given leveringsautorisatie uit autorisatie/GeefMedebewoners
Given personen uit specials:specials/Jan_xls

Given verzoek geef medebewoners van persoon:
|key|value
|leveringsautorisatieNaam|'Geef Medebewoners'
|zendendePartijNaam|'Gemeente Utrecht'
|administratienummer|'5398948626'

Then heeft het antwoordbericht verwerking Geslaagd


Scenario: 2.    Test met ONgeldig ANR om persoon te zoeken
                LT: R1585_LT04
                Verwacht resultaat:
                - Geslaagd

Given leveringsautorisatie uit autorisatie/GeefMedebewoners
Given personen uit specials:specials/Jan_xls

Given verzoek geef medebewoners van persoon:
|key|value
|leveringsautorisatieNaam|'Geef Medebewoners'
|zendendePartijNaam|'Gemeente Utrecht'
|administratienummer|'1543726322'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1585     | Het opgegeven administratienummer is niet geldig.