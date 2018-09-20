Meta:
@status                 Klaar
@sleutelwoorden         verkrijgingVreemdeNationaliteit,intaketest

Narrative:
Nationaliteit, administratieve handeling verkrijging vreemde nationaliteit, met actie registratie nationaliteit naam

Scenario: 1. Verkregen nationaliteit moet een vreemde zijn (nation = 2 is Nederlandse)

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Tiel.txt
Given bijhoudingsverzoek voor partij 'Gemeente Tiel'

Given de personen 826933129,526521673,139122333 zijn verwijderd
Given de standaardpersoon Sandy met bsn 139122333 en anr 4939702546 zonder extra gebeurtenissen

And administratieve handeling van type verkrijgingVreemdeNationaliteit , met de acties registratieNationaliteitNaam
And testdata uit bestand verkrijging_vreemde_nationaliteit_01.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/intake/nationaliteit/expected_nationaliteit_berichten/expected_verkrijging_vreemde_nationaliteit_scenario_1.xml voor expressie //brp:bhg_natRegistreerNationaliteit_R

Then in kern select nation from kern.persnation where pers = (select id from kern.pers where bsn = 139122333) and nation <> 2 de volgende gegevens:
| veld   | waarde |
| nation | 117    |


Then in kern select id, naam from kern.nation where code = 204 de volgende gegevens:
| veld | waarde   |
| id   | 117      |
| naam | Canadese |

