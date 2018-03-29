Meta:
@status             Klaar
@usecase            BV.0.GD
@sleutelwoorden     Geef Details Persoon
@regels             R2529

Narrative:

Objecttype Gedeblokkeerde melding als onderdeel van de verantwoordingsgroep wordt samengesteld op basis van
Administratieve handeling \ Gedeblokkeerde regel.Regel en de Gedeblokkeerde melding.Melding uit de
Gedeblokkeerde melding waarnaar de Administratieve handeling \ Gedeblokkeerde regel.Regel verwijst.

Scenario: 1.    Leveren van Gedeblokkeerde melding voor partij met partijrol bijhouder
                LT: R2529_LT01
                Verwacht resultaat:
                - Gedeblokkeerde melding in het bericht

Given leveringsautorisatie uit autorisatie/GeefDetailsPersoon_Bijhouder

Given persoonsbeelden uit BIJHOUDING:VHNL01C360T40/Bij_gedeblokkeerdeMelding_komt_referenti/dbstate003
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geef details persoon bijhouder'
|zendendePartijNaam|'College'
|rolNaam|'Bijhoudingsorgaan College'
|bsn|761390601
Then heeft het antwoordbericht verwerking Geslaagd

Then is er voor xpath //brp:gedeblokkeerdeMelding een node aanwezig in het antwoord bericht
Then is het antwoordbericht gelijk aan expected_R2529/Scenario_1.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 2.    Leveren van Gedeblokkeerde melding voor partij met partijrol minister
                LT: R2529_LT02
                Verwacht resultaat:
                - Gedeblokkeerde melding in het bericht

Given leveringsautorisatie uit autorisatie/GeefDetailsPersoon_Minister

Given persoonsbeelden uit BIJHOUDING:VHNL01C360T40/Bij_gedeblokkeerdeMelding_komt_referenti/dbstate003
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geef details persoon minister'
|zendendePartijNaam|'Minister'
|rolNaam|'Bijhoudingsorgaan Minister'
|bsn|761390601
Then heeft het antwoordbericht verwerking Geslaagd

Then is er voor xpath //brp:gedeblokkeerdeMelding een node aanwezig in het antwoord bericht
Then is het antwoordbericht gelijk aan expected_R2529/Scenario_2.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 3.    Leveren van Gedeblokkeerde melding voor partij met partijrol afnemer
                LT: R2529_LT03
                Verwacht resultaat:
                - Gedeblokkeerde melding NIET in het bericht

Given leveringsautorisatie uit autorisatie/GeefDetailsPersoon_Afnemer
Given persoonsbeelden uit BIJHOUDING:VHNL01C360T40/Bij_gedeblokkeerdeMelding_komt_referenti/dbstate003

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geef details persoon afnemer'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|761390601

Then heeft het antwoordbericht verwerking Geslaagd

Then is er voor xpath //brp:gedeblokkeerdeMelding geen node aanwezig in het antwoord bericht
Then is het antwoordbericht gelijk aan expected_R2529/Scenario_3.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 4.    Geen gedeblokkeerde melding aanwezig voor partij met partijrol bijhouder
                LT: R2529_LT04
                Verwacht resultaat:
                - Gedeblokkeerde melding NIET in het bericht

Given leveringsautorisatie uit autorisatie/GeefDetailsPersoon_Bijhouder

Given persoonsbeelden uit BIJHOUDING:VHNL01C360T40/Bij_gedeblokkeerdeMelding_komt_referenti/dbstate001
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geef details persoon bijhouder'
|zendendePartijNaam|'College'
|rolNaam|'Bijhoudingsorgaan College'
|bsn|761390601
Then heeft het antwoordbericht verwerking Geslaagd

Then is er voor xpath //brp:gedeblokkeerdeMelding geen node aanwezig in het antwoord bericht
Then is het antwoordbericht gelijk aan expected_R2529/Scenario_4.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R