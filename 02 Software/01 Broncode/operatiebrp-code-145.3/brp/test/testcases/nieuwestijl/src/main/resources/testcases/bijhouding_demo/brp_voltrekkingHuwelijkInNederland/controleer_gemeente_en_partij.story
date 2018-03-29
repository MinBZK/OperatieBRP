Meta:
@status               Onderhanden
@sleutelwoorden       exampletest
@regels               BRAL2104,R1861x

Narrative:
H/GP mag alleen door betrokken gemeente worden geregistreerd

Scenario: 1. Voltrekking huwelijk in Nederland, controleer partij en gemeente

Gemeente BRP 1
Given bijhoudingsverzoek voor partij 'Gemeente Tiel'

Given de personen 826933129,526521673,141901317,631512457,140678177,698938057 zijn verwijderd
Given de standaardpersoon Sandy met bsn 140678177 en anr 3679783698 zonder extra gebeurtenissen
Given de standaardpersoon Danny met bsn 698938057 en anr 7049482514 zonder extra gebeurtenissen

Given administratieve handeling van type voltrekkingHuwelijkInNederland , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap
And testdata uit bestand controleer_gemeente_en_partij_01.yml
And extra waardes:
| SLEUTEL                                                                                                                        | WAARDE     |
| voltrekkingHuwelijkInNederland.partijCode                                                                                      | <partij>   |
| voltrekkingHuwelijkInNederland.acties.registratieAanvangHuwelijkGeregistreerdPartnerschap.huwelijk.relatie.gemeenteAanvangCode | <gemeente> |

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking <verwerking>

Examples:
| #testsituatie                                | <partij> | gemeente | verwerking |
| partij gelijk aan relatie.gemeente.aanvang   | 017401   | 0174     | Geslaagd   |
| partij ongelijk aan relatie.gemeente.aanvang | 036101   | 0174     | Foutief    |


