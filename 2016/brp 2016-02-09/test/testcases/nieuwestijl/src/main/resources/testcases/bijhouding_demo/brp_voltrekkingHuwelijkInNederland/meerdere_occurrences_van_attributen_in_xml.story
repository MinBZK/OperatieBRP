Meta:
@status                 Onderhanden
@sleutelwoorden         demon

Narrative:
Huwelijk en geregistreerd partnerschap, met administratieve handeling Voltrekking huwelijk in Nederland,
en actie registratie aanvang huwelijk geregistreerd partnerschap

Scenario: Personen Sandy Olsson en Danny Zuko gaan trouwen, extra waardes (bsn) wordt toegevoegd dmv example

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Tiel.txt
Given bijhoudingsverzoek voor partij 'Gemeente Tiel'

Given de personen 826933129,526521673,141901317,631512457,651712713,176434057 zijn verwijderd
Given de standaardpersoon Sandy met bsn 651712713 en anr 2595909394 zonder extra gebeurtenissen
Given de standaardpersoon Danny met bsn 176434057 en anr 9567294738 zonder extra gebeurtenissen

And administratieve handeling van type voltrekkingHuwelijkInNederland , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap
And testdata uit bestand meerdere_occurrences_van_attributen_in_xml_01.yml
And extra waardes:
| SLEUTEL                                                                                                                                      | WAARDE |
| voltrekkingHuwelijkInNederland.acties.registratieAanvangHuwelijkGeregistreerdPartnerschap.huwelijk.(betrokkenheden)[0].persoon.objectSleutel | <bsn1> |
| voltrekkingHuwelijkInNederland.acties.registratieAanvangHuwelijkGeregistreerdPartnerschap.huwelijk.(betrokkenheden)[1].persoon.objectSleutel | <bsn2> |

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking <verwerking>

Examples:
| #testsituatie                 | <bsn1>    | <bsn2>    | verwerking |
| bsn nrs kloppen               | 176434057 | 651712713 | Geslaagd   |
| volgorde bsn nrs zijn onjuist | 651712713 | 176434057 | Geslaagd   |


