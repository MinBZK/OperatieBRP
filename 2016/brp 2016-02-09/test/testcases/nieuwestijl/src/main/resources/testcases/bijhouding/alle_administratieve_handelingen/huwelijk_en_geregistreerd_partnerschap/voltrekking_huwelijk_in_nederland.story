Meta:
@status                 Klaar
@sleutelwoorden         voltrekkingHuwelijkInNederland

Narrative:
Huwelijk en geregistreerd partnerschap, met administratieve handeling Voltrekking huwelijk in Nederland,
en acties registratie aanvang huwelijk geregistreerd partnerschap en registratie naamgebruik

Scenario: Personen Sandy Olsson en Danny Zuko gaan trouwen, controleer naamgebruik

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Tiel.txt
Given bijhoudingsverzoek voor partij 'Gemeente Tiel'

Given de personen 826933129,526521673,141901317,631512457,182119865,951375945 zijn verwijderd
Given de standaardpersoon Sandy met bsn 182119865 en anr 5371089682 zonder extra gebeurtenissen
Given de standaardpersoon Danny met bsn 951375945 en anr 1375139474 zonder extra gebeurtenissen

And administratieve handeling van type voltrekkingHuwelijkInNederland , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap, registratieNaamgebruik
And testdata uit bestand voltrekking_huwelijk_in_nederland_01.yml

And testdata uit bestand voltrekking_huwelijk_in_nederland_02.yml
And extra waardes:
| SLEUTEL                                                                                            | WAARDE        |
| voltrekkingHuwelijkInNederland.acties.registratieNaamgebruik.persoon.naamgebruik.code              | <code>        |
| voltrekkingHuwelijkInNederland.acties.registratieNaamgebruik.persoon.naamgebruik.indicatieAfgeleid | <indAfgeleid> |

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking <verwerking>

Then is in de database de persoon met bsn 182119865 wel als PARTNER betrokken bij een HUWELIJK
Then in kern select geslnaamstamnaamgebruik from kern.pers where bsn=182119865 de volgende gegevens:
| veld                    | waarde     |
| geslnaamstamnaamgebruik | <waardeDb> |


Examples:
| #testsituatie                                                              | <code> | <indAfgeleid> | verwerking | <waardeDb>  |
| Sandy krijgt de geslachtsnaam van haar partner Danny                       | P      | J             | Geslaagd   | Zuko        |
| Sandy behoudt haar eigen naam plus de geslachtsnaam van haar partner Danny | N      | J             | Geslaagd   | Zuko-Olsson |

