Meta:
@status                 Klaar
@sleutelwoorden         wijzigingVoornaam,naamGeslacht

Narrative: Administrative handeling wijziging voornaam, met de actie registratie voornaam

Scenario: 1. Persoon krijgt een ander voornaam

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Tiel.txt
Given bijhoudingsverzoek voor partij 'Gemeente Tiel'

Given de personen 826933129,526521673,419587081 zijn verwijderd
Given de standaardpersoon Sandy met bsn 419587081 en anr 8076765970 zonder extra gebeurtenissen

Given administratieve handeling van type wijzigingVoornaam , met de acties registratieVoornaam
And testdata uit bestand wijziging_voornaam_01.yml
And extra waardes:
| SLEUTEL                                                                        | WAARDE    |
| wijzigingVoornaam.acties.registratieVoornaam.persoon.objectSleutel             | 419587081 |
| wijzigingVoornaam.acties.registratieVoornaam.persoon.(voornamen)[0].volgnummer | 1         |
| wijzigingVoornaam.acties.registratieVoornaam.persoon.(voornamen)[0].naam       | Mary      |

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Then in kern select voornamennaamgebruik from kern.pers where bsn = 419587081 de volgende gegevens:
| veld                 | waarde |
| voornamennaamgebruik | Mary   |
