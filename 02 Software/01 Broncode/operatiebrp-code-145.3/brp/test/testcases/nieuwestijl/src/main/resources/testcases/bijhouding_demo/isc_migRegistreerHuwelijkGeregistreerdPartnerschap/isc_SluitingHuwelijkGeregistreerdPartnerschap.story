Meta:
@sprintnummer           82
@epic                   POC Bijhouding
@auteur                 dihoe
@status                 Onderhanden

Narrative:
    *** Het notificatiebericht werkt vanaf release Carice. Voor release Bernadette deze test op Onderhanden. ***
    Als GBA-bijhouder wil ik dat BRP een GBA-huwelijk wel of niet vastlegt afhankelijk van de stand van de autofiat.

Scenario: 1. Sluiting huwelijk in Nederland, vanuit ISC, bezien vanuit Sandy. Utrecht is de bijhoudingsgemeente van Sandy en heeft de autofiat AAN. BRP
legt het huwelijk vast.

Given leveringsautorisatie uit /levering_autorisaties/postcode_gebied_heemstede_2100-2129_en_afnemerindicatie

Gemeente BRP 1, /bijhoudingsautorisaties/Gemeente_Alkmaar.txt,
/bijhoudingsautorisaties/Gemeente_Utrecht.txt

Given bijhoudingsverzoek voor partij 'Gemeente Tiel'

Given voor gemeente Utrecht autofiat aan staat
Given de personen 931665929,826933129,526521673 zijn verwijderd
And de standaardpersoon Sandy met bsn 931665929 en anr 9091384082 zonder extra gebeurtenissen

And administratieve handeling van type gBASluitingHuwelijkGeregistreerdPartnerschap , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap, registratieNaamVoornaam
And testdata uit bestand isc_SluitingHuwelijkGeregistreerdPartnerschap_01.yml
And testdata uit bestand isc_SluitingHuwelijkGeregistreerdPartnerschap_02.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
And hebben in het antwoordbericht de attributen in de groepen de volgende waardes:
| groep                | attribuut           | verwachteWaardes |
| stuurgegevens        | zendendePartij      | 199903           |
| stuurgegevens        | zendendeSysteem     | BRP              |
| resultaat            | verwerking          | Geslaagd         |
| resultaat            | bijhouding          | Verwerkt         |
| identificatienummers | burgerservicenummer | 931665929        |
And heeft het antwoordbericht 1 groep 'gBASluitingHuwelijkGeregistreerdPartnerschap'
And is in de database de persoon met bsn 931665929 wel als PARTNER betrokken bij een HUWELIJK

When het notificatiebericht voor partij 028101 is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waarde:
| groep                | attribuut           | verwachteWaardes |
| parameters           | redenNotificatie    | Automatische fiat|

Scenario: 2. Sluiting huwelijk in Nederland, vanuit ISC, bezien vanuit Sandy. Utrecht is de bijhoudingsgemeente van Sandy en heeft de autofiat uit. BRP
legt het huwelijk NIET vast.

Given voor gemeente Utrecht autofiat uit staat
Given de personen 931665929,826933129,526521673 zijn verwijderd
And de standaardpersoon Sandy met bsn 931665929 en anr 9091384082 zonder extra gebeurtenissen

And administratieve handeling van type gBASluitingHuwelijkGeregistreerdPartnerschap , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap, registratieNaamVoornaam
And testdata uit bestand isc_SluitingHuwelijkGeregistreerdPartnerschap_01.yml
And testdata uit bestand isc_SluitingHuwelijkGeregistreerdPartnerschap_02.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
And hebben in het antwoordbericht de attributen in de groepen de volgende waardes:
| groep                | attribuut           | verwachteWaardes |
| stuurgegevens        | zendendePartij      | 199903           |
| stuurgegevens        | zendendeSysteem     | BRP              |
| resultaat            | verwerking          | Geslaagd         |
| resultaat            | bijhouding          | Verwerkt         |

And heeft het antwoordbericht 1 groep 'gBASluitingHuwelijkGeregistreerdPartnerschap'
And is in de database de persoon met bsn 931665929 niet als PARTNER betrokken bij een HUWELIJK
