Meta:
@status             Klaar
@usecase            BV.0.GM
@sleutelwoorden     Geef medebewoners
@regels             R2392

Narrative:
Als gebruiker wil ik medebewoners van een persoon / of bag identificatie kunnen opvragen
zodat ik inzicht krijg in de medebewoners.

Scenario:   1.  Het opgegeven identiteitsnummer levert meer dan 1 identificatiecode nummeraanduiding
            LT: R2392_LT04
            2 personen op hetzelfde adres worden ingeladen. Anne_met_Historie en Anne_met_Historie2_xls
            1 persoon wordt ingeladen welke een andere bag sleutel heeft.
            Verwacht resultaat:
            Verwerking Foutief
            R2392 - Adresidentificatie is herleidbaar tot meer dan één adres


Given leveringsautorisatie uit autorisatie/GeefMedebewoners
Given personen uit specials:specials/Anne_met_Historie_xls, specials:specials/Anne_met_Historie2_xls, specials:specials/Anne_Bakker_GBA_Bijhouding_Verhuizing_xls

Given verzoek geef medebewoners van persoon:
|key|value
|leveringsautorisatieNaam|'Geef Medebewoners'
|zendendePartijNaam|'Gemeente Utrecht'
|postcode|'1422RZ'
|woonplaatsnaam|'Uithoorn'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                                  |
| R2392 | Adresidentificatie is herleidbaar tot meer dan één adres |


