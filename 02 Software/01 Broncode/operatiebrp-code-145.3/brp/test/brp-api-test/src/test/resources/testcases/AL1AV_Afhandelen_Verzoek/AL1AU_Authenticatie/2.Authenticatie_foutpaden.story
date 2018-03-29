Meta:
@status             Klaar
@usecase            AL.1.AV
@regels             R1257, R1258, R2052, R2120, R2121, R2122, R2016, R2050, R2101, R2129
@sleutelwoorden     Authenticatie


Narrative:
Als BRP wil ik dat alleen geautoriseerde afnemers een dienst kunnen bevragen
zodat de levering plaats vind aan alleen geautoriseerde afnemers

Scenario:  1.   De toegang leveringsautorisatie bestaat, combinatie ondertekenaar, transporteur onjuist
                LT: R1257_LT02
                Verwacht resultaat:
                1. Foutief bericht met de melding: "Authenticatie: Combinatie ondertekenaar en transporteur onjuist"

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/R1257_combinatie_ondertekenaar_transporteur_onjuist
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'model autorisatie voor bevraging'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|ondertekenaar|'College'
|transporteur|'Gemeente Utrecht'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING
| R2343 | De combinatie ondertekenaar en transporteur is onjuist.

Then is er een autorisatiefout gelogd met regelcode R1257

Scenario:  2.   De toegang leveringsautorisatie bestaat, combinatie ondertekenaar, transporteur onjuist, Plaats afnemerindicatie
                LT: R1257_LT02
                Verwacht resultaat:
                1. Foutief bericht met de melding: "Authenticatie: Combinatie ondertekenaar en transporteur onjuist"

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/R1257_combinatie_ondertekenaar_transporteur_onjuist_PA
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'model autorisatie voor bevraging'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|ondertekenaar|'College'
|transporteur|'Gemeente Utrecht'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING
| R2343 | De combinatie ondertekenaar en transporteur is onjuist.

Then is er een autorisatiefout gelogd met regelcode R1257

Scenario:   3.  De toegang leveringsautorisatie bestaat, ondertekenaar en transporteur zijn leeg en rol is niet opgegeven,
                LT: R1258_LT05
                toegangleveringsautorisatie datum ingang is groter dan systeemdatum
                Verwacht resultaat:
                1. Foutief bericht met de melding: "De toegang autorisatie is niet geldig"

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/R1258_toegangleveringsautorisatie_datumingang_morgen
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING
| R2343    | De toegang leveringsautorisatie is niet geldig.

Then is er een autorisatiefout gelogd met regelcode R1258

Scenario:   4.  De toegang leveringsautorisatie bestaat, ondertekenaar en transporteur zijn leeg en rol is niet opgegeven,
                LT: R1258_LT06
                toegangleveringsautorisatie datum ingang is kleiner dan systeemdatum, datum einde is gelijk aan de systeemdatum
                Verwacht resultaat:
                1. Foutief bericht met de melding: "De toegang autorisatie is niet geldig"

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/R1258_toegangleveringsautorisatie_datumeinde_vandaag
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING
| R2343    | De toegang leveringsautorisatie is niet geldig.

Then is er een autorisatiefout gelogd met regelcode R1258

Scenario:   5.  De toegang leveringsautorisatie bestaat, ondertekenaar en transporteur zijn leeg en rol is niet opgegeven,
                LT: R1258_LT07
                toegangleveringsautorisatie datum ingang is kleiner dan systeemdatum, datum einde is kleiner dan de systeemdatum
                Verwacht resultaat:
                1. Foutief bericht met de melding: "De toegang autorisatie is niet geldig"

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/R1258_toegangleveringsautorisatie_datumeinde_gisteren
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING
| R2343    | De toegang leveringsautorisatie is niet geldig.

Then is er een autorisatiefout gelogd met regelcode R1258

Scenario:   6.  De toegang leveringsautorisatie bestaat, ondertekenaar is onjuist en transporteur is onjuist en rol is niet opgegeven
                LT: R2120_LT03
                De leveringsautorisatie wordt geupdate zodat er een transporteur en ondertekenaar worden meegegeven in dit geval
                de gemeente Haarlem in het request worden de juiste OIN's meegegeven voor gemeente Haarlem. In de yml wordt een
                partijcode meegegeven van gemeente Tiel wat onjuist is tov de leveringsautorisatie.
                1. Foutief bericht met de melding "De gebruikte authenticatie is niet bekend"

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/Bewerker_autorisatie
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Bewerker autorisatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|ondertekenaar|'Gemeente Haarlem'
|transporteur|'Gemeente Haarlem'
|zendendePartijCode|28101

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING
| R2343    | De gebruikte authenticatie is niet bekend.

Then is er een autorisatiefout gelogd met regelcode R2120

Scenario:   7.  De toegang leveringsautorisatie bestaat, ondertekenaar en transporteur zijn gevuld en rol is foutief
                LT: R2120_LT04
                Rol Bijhoudingsorgaan Minister wordt meegegeven in de yml in de leveringsautorisatie wordt rol 1 gebruikt.
                Verwacht resultaat:
                1. Foutief bericht met de melding: "De gebruikte authenticatie is niet bekend"
                Ondertekenaar en transporteur is hier niet zo relevant, het niet bestaande van de ministerrol is relevant.

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit /levering_autorisaties_nieuw/oin_gelijk_geautoriseerde_ongelijk_ondertektransporteur
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|rolNaam|'Bijhoudingsorgaan Minister'
|ondertekenaar|'Gemeente Olst'
|transporteur|'Gemeente Olst'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING
| R2343    | De gebruikte authenticatie is niet bekend.

Then is er een autorisatiefout gelogd met regelcode R2120

Scenario:   8.  De toegang leveringsautorisatie bestaat niet, ondertekenaar en transporteur zijn gevuld en rol is nvt
                LT: R2120_LT05
                De meegegeven leveringsautorisatieId (99999) bestaat niet
                Verwacht resultaat:
                1. Foutief bericht met de melding: "De gebruikte authenticatie is niet bekend"

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit /levering_autorisaties_nieuw/oin_gelijk_geautoriseerde_ongelijk_ondertektransporteur
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|rolNaam|'Bijhoudingsorgaan Minister'
|leveringsautorisatieId|1

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING
| R2343    | De gebruikte authenticatie is niet bekend.

Then is er een autorisatiefout gelogd met regelcode R2120
