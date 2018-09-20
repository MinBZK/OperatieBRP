Meta:
@status                 Klaar
@sleutelwoorden         wijzigingGeslachtsnaam,naamGeslacht

Narrative: Administrative handeling wijziging geslachtsnaam, met de actie registratie naam geslacht

Scenario: 1. Persoon krijgt een ander geslachtsnaam

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Tiel.txt
Given bijhoudingsverzoek voor partij 'Gemeente Tiel'

Given de personen 826933129,526521673,228496433 zijn verwijderd
Given de standaardpersoon Sandy met bsn 228496433 en anr 4271965202 zonder extra gebeurtenissen

Given administratieve handeling van type wijzigingGeslachtsnaam , met de acties registratieNaamGeslacht
And testdata uit bestand wijziging_geslachtsnaam_01.yml
And extra waardes:
| SLEUTEL                                                                                          | WAARDE    |
| wijzigingGeslachtsnaam.acties.registratieNaamGeslacht.persoon.objectSleutel                      | 228496433 |
| wijzigingGeslachtsnaam.acties.registratieNaamGeslacht.persoon.(geslachtsnaamcomponenten)[0].stam | Jansen    |

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Then in kern select geslnaamstamnaamgebruik from kern.pers where bsn = 228496433 de volgende gegevens:
| veld                    | waarde |
| geslnaamstamnaamgebruik | Jansen |
