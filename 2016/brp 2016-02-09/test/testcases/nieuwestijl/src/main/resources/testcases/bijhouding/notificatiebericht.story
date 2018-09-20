Meta:
@epic                   POC Bijhouding
@jiraIssue              TEAMBRP-4134
@status                 Onderhanden
@sleutelwoorden         notificatiebericht

Narrative:
    *** Het notificatiebericht werkt vanaf release Carice. Voor release Bernadette deze test op Onderhanden. ***
    Als GBA-bijhouder wil ik dat BRP een GBA-huwelijk wel of niet vastlegt afhankelijk van de stand van de autofiat.

Scenario: 1. Woongemeente is Utrecht, er wordt een ISC huwelijk ingeschoten. Reden notificatie in notiticatiebericht is afhankelijk van de stand van de autofiat.

Given leveringsautorisatie uit /levering_autorisaties/postcode_gebied_heemstede_2100-2129_en_afnemerindicatie

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Tiel.txt, /bijhoudingsautorisaties/Gemeente_Alkmaar.txt,
/bijhoudingsautorisaties/Gemeente_Utrecht.txt

Given bijhoudingsverzoek voor partij 'Gemeente Tiel'

Given voor gemeente Utrecht autofiat <autofiat> staat
Given de personen 931665929,826933129,526521673 zijn verwijderd
And de standaardpersoon Sandy met bsn 931665929 en anr 9091384082 zonder extra gebeurtenissen

And administratieve handeling van type gBASluitingHuwelijkGeregistreerdPartnerschap , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap
And testdata uit bestand notificatiebericht_01.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het notificatiebericht voor partij 028101 is ontvangen en wordt bekeken

Then heeft 'redenNotificatie' in 'parameters' de waardes '<waarde>'

Examples:
| #testsituatie                                   | autofiat | waarde            |
| autofiat staat aan, BRP legt huwelijk vast      | aan      | Automatische fiat |
| autofiat staat uit, BRP legt huwelijk niet vast | uit      | Opnieuw indienen  |
