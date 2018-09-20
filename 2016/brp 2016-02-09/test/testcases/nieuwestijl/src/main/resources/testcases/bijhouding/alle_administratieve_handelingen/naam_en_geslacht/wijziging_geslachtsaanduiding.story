Meta:
@status                 Klaar
@sleutelwoorden         wijzigingGeslachtsaanduiding,naamGeslacht

Narrative: Administrative handeling wijziging geslachtsaanduiding, met de actie registratie naam geslacht

Scenario: 1. Persoon krijgt een ander geslachtsaanduiding

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Tiel.txt
Given bijhoudingsverzoek voor partij 'Gemeente Tiel'

Given de personen 826933129,526521673,772627721 zijn verwijderd
Given de standaardpersoon Sandy met bsn 772627721 en anr 7626373906 zonder extra gebeurtenissen

Given administratieve handeling van type wijzigingGeslachtsaanduiding , met de acties registratieNaamGeslacht
And testdata uit bestand wijziging_geslachtsaanduiding_01.yml
And extra waardes:
| SLEUTEL                                                                                      | WAARDE    |
| wijzigingGeslachtsaanduiding.acties.registratieNaamGeslacht.persoon.objectSleutel            | 772627721 |
| wijzigingGeslachtsaanduiding.acties.registratieNaamGeslacht.persoon.geslachtsaanduiding.code | M         |


When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Then in kern select geslachtsaand from kern.pers where bsn=772627721 de volgende gegevens:
| veld          | waarde |
| geslachtsaand | 1      |
