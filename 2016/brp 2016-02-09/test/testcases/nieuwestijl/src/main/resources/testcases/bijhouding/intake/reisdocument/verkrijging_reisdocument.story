Meta:
@status                 Klaar
@sleutelwoorden         verkrijgingReisdocument,intaketest

Narrative:
Reisdocument, verkrijging reisdocument met de actie registratie reisdocument

Scenario: 1. Het hele response bericht wordt gecontroleerd, en ook een aantal waardes in de database.

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Tiel.txt
Given bijhoudingsverzoek voor partij 'Gemeente Tiel'

Given de personen 826933129,526521673,554861513 zijn verwijderd
Given de standaardpersoon Sandy met bsn 554861513 en anr 4543046930 zonder extra gebeurtenissen

Given administratieve handeling van type verkrijgingReisdocument , met de acties registratieReisdocument
And testdata uit bestand verkrijging_reisdocument_01.yml
When het bericht wordt verstuurd

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/intake/reisdocument/expected_reisdocument_berichten/expected_verkrijging_reisdocument_scenario_1.xml voor expressie //brp:bhg_rsdRegistreerReisdocument_R

Then in kern select srt, nr from kern.persreisdoc where pers = (select id from kern.pers where bsn = 554861513) de volgende gegevens:
| veld | waarde    |
| srt  | 20        |
| nr   | 123456789 |

Then in kern select id, oms from kern.srtnlreisdoc where code = 'PN' de volgende gegevens:
| veld | waarde             |
| id   | 20                 |
| oms  | Nationaal paspoort |

