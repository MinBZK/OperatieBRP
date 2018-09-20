Meta:
@status                 Klaar
@sleutelwoorden         verhuizingBinnengemeentelijk

Narrative:
Verblijf en adres, administratieve handeling verhuizing binnengemeentelijk, met de actie registratie adres

Scenario: 1. Een persoon is verhuisd naar een nieuwe gemeente

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Tiel.txt
Given bijhoudingsverzoek voor partij 'Gemeente Tiel'

Given de personen 826933129,526521673,932167561 zijn verwijderd
Given de standaardpersoon Sandy met bsn 932167561 en anr 6280461074 zonder extra gebeurtenissen

And administratieve handeling van type verhuizingBinnengemeentelijk , met de acties registratieAdres
And testdata uit bestand verhuizing_binnengemeentelijk_01.yml
And extra waardes:
| SLEUTEL                                                                                    | WAARDE    |
| verhuizingBinnengemeentelijk.acties.registratieAdres.persoon.objectSleutel                 | 932167561 |
| verhuizingBinnengemeentelijk.acties.registratieAdres.persoon.adressen.adres.huisnummer     | 50        |
| verhuizingBinnengemeentelijk.acties.registratieAdres.persoon.adressen.adres.postcode       | 3067GB    |
| verhuizingBinnengemeentelijk.acties.registratieAdres.persoon.adressen.adres.woonplaatsnaam | Rotterdam |

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Then in kern select huisnr, postcode, wplnaam from kern.persadres where pers = (select id from kern.pers where bsn = 932167561) de volgende gegevens:
| veld     | waarde    |
| huisnr   | 50        |
| postcode | 3067GB    |
| wplnaam  | Rotterdam |

