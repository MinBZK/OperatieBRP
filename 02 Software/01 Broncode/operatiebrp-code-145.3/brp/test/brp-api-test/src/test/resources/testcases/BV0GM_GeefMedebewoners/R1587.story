Meta:
@status             Klaar
@usecase            BV.0.GM
@sleutelwoorden     Geef medebewoners
@regels             R1587

Narrative:
Als gebruiker wil ik medebewoners van een persoon / of bag identificatie kunnen opvragen
zodat ik inzicht krijg in de medebewoners.


Scenario:   1.  Het burgerservicenummer moet voldoen aan het voorschrift, en leveringsautorisatie bevat dienst
            LT: R1587_LT09, R2130_LT21
            BSN voldoet.
            Verwacht resultaat: Verwerking geslaagd, persoon wordt geleverd.

Given leveringsautorisatie uit autorisatie/GeefMedebewoners
Given personen uit specials:specials/Anne_met_Historie_xls

Given verzoek geef medebewoners van persoon:
|key|value
|leveringsautorisatieNaam|'Geef Medebewoners'
|zendendePartijNaam|'Gemeente Utrecht'
|burgerservicenummer|'590984809'

Then heeft het antwoordbericht verwerking Geslaagd

Scenario:   2.  Het burgerservicenummer voldoet niet aan het voorschrift
            LT: R1587_LT10
            BSN Voldoet niet
            Verwacht resultaat: Foutmelding R1587 - Het opgegeven BSN is niet geldig.

Given leveringsautorisatie uit autorisatie/GeefMedebewoners
Given personen uit specials:specials/Anne_met_Historie_xls

Given verzoek geef medebewoners van persoon:
|key|value
|leveringsautorisatieNaam|'Geef Medebewoners'
|zendendePartijNaam|'Gemeente Utrecht'
|burgerservicenummer|'123456789'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                                                                             |
| R1587 | Het opgegeven BSN is niet geldig. |
