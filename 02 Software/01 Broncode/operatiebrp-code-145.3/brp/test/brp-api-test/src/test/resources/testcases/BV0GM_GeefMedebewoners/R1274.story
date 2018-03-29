Meta:

@status             Klaar
@usecase            BV.0.GM, BV.1.GM
@sleutelwoorden     Geef Medebewoners van Persoon
@regels             R1274

Narrative:
De waarde van datum moet in de combinatie van jaar, maand en dag geldig zijn binnen de Gregoriaanse kalender.

Scenario: 1.    Test met ongeldige datum als peilmoment
                LT: R1274_LT72
                Verwacht resultaat:
                - Foutief R1274: 	De opgegeven datum is geen geldige kalenderdatum.

Given leveringsautorisatie uit autorisatie/GeefMedebewoners
Given personen uit specials:specials/Jan_xls

Given verzoek geef medebewoners van persoon:
|key|value
|leveringsautorisatieNaam|'Geef Medebewoners'
|zendendePartijNaam|'Gemeente Utrecht'
|peilmomentMaterieel|'2015-02-29'
|burgerservicenummer|'606417801'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1274     | De opgegeven datum is geen geldige kalenderdatum.


