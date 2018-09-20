Meta:
@status                 Klaar
@sleutelwoorden         wijzigingNaamgebruik,naamGeslacht

Narrative: Administrative handeling wijziging naamgebruik, met de actie registratie naamgebruik

Scenario: 1. Persoon krijgt een ander naamgebruik

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Tiel.txt
Given bijhoudingsverzoek voor partij 'Gemeente Tiel'

Given de personen 826933129,526521673,274801401 zijn verwijderd
Given de standaardpersoon Sandy met bsn 274801401 en anr 8949478674 zonder extra gebeurtenissen

Given administratieve handeling van type wijzigingNaamgebruik , met de acties registratieNaamgebruik
And testdata uit bestand wijziging_naamgebruik_01.yml
And extra waardes:
| SLEUTEL                                                                                  | WAARDE    |
| wijzigingNaamgebruik.acties.registratieNaamgebruik.persoon.objectSleutel                 | 274801401 |
| wijzigingNaamgebruik.acties.registratieNaamgebruik.persoon.naamgebruik.indicatieAfgeleid | N         |
| wijzigingNaamgebruik.acties.registratieNaamgebruik.persoon.naamgebruik.voornamen         | Linda     |
| wijzigingNaamgebruik.acties.registratieNaamgebruik.persoon.naamgebruik.voorvoegsel       | de        |
| wijzigingNaamgebruik.acties.registratieNaamgebruik.persoon.naamgebruik.geslachtsnaamstam | Mol       |

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Then in kern select voornamennaamgebruik,voorvoegselnaamgebruik,geslnaamstamnaamgebruik from kern.pers where bsn = 274801401 de volgende gegevens:
| veld                    | waarde |
| voornamennaamgebruik    | Linda  |
| voorvoegselnaamgebruik  | de     |
| geslnaamstamnaamgebruik | Mol    |
