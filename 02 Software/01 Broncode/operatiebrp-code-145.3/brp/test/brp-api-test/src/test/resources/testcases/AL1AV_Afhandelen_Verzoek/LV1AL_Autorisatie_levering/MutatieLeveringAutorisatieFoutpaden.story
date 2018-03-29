Meta:
@status             Klaar
@usecase            LV.1.AL
@regels             R2239, R2242, R2245
@sleutelwoorden     Autorisatie Levering

Narrative:
Als BRP wil ik dat alleen geautoriseerde afnemers toegang hebben tot de BRP


Scenario:   1. R2239 Ongeldige Dienstbundel
               LT: R2239_LT04
               Verwacht Resultaat:
               Foutieve situatie aangetroffen; verwerking blokkeert
               Meldingstekst:	De dienstbundel is niet geldig.

Given leveringsautorisatie uit autorisatie/R2239/R2239
Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht voor leveringsautorisatie R2239 Geldigheid Dienstbundel

Scenario:   2.  R2242 Ongeldige Partij
                LT: R2242_LT05
                Verwacht resultaat:

                Foutieve situatie aangetroffen; verwerking blokkeert
                Geen mutatie bericht ontvangen


Given leveringsautorisatie uit autorisatie/R2242_GeldigheidPartij/R2242_MutatieLevering.txt
Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht voor leveringsautorisatie R2242 Mutatie levering ongeldige partij

Scenario:   3.  R2245 Ongeldige PartijRol
                LT: R2245_LT04
                Verwacht resultaat:

                Foutieve situatie aangetroffen; verwerking blokkeert
                Geen mutatie bericht ontvangen


Given leveringsautorisatie uit autorisatie/R2245_GeldigheidPartijRol/2245_MutatieLevering.txt
Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht voor leveringsautorisatie R2245 Ongeldige partijrol mutatielevering
