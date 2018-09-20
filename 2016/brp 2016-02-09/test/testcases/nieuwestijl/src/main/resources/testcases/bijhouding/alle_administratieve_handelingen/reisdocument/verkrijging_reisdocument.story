Meta:
@status                 Klaar
@sleutelwoorden         verkrijgingReisdocument

Narrative:
Reisdocument, verkrijging reisdocument met de actie registratie reisdocument

Scenario: 1. Verkrijging Nederlandse identiteitskaart

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Tiel.txt
Given bijhoudingsverzoek voor partij 'Gemeente Tiel'

Given de personen 826933129,526521673,126408609 zijn verwijderd
Given de standaardpersoon Sandy met bsn 126408609 en anr 8912385298 zonder extra gebeurtenissen

Given administratieve handeling van type verkrijgingReisdocument , met de acties registratieReisdocument
And testdata uit bestand verkrijging_reisdocument_01.yml
And extra waardes:
| SLEUTEL                                                                                                         | WAARDE              |
| verkrijgingReisdocument.acties.registratieReisdocument.datumAanvangGeldigheid                                   | ${vandaag(0,0,0)}   |
| verkrijgingReisdocument.acties.registratieReisdocument.persoon.objectSleutel                                    | 126408609           |
| verkrijgingReisdocument.acties.registratieReisdocument.persoon.reisdocumenten.reisdocument.soortCode            | NI                  |
| verkrijgingReisdocument.acties.registratieReisdocument.persoon.reisdocumenten.reisdocument.nummer               | 123456789           |
| verkrijgingReisdocument.acties.registratieReisdocument.persoon.reisdocumenten.reisdocument.autoriteitVanAfgifte | 1704                |
| verkrijgingReisdocument.acties.registratieReisdocument.persoon.reisdocumenten.reisdocument.datumIngangDocument  | ${vandaag(0,0,0)}   |
| verkrijgingReisdocument.acties.registratieReisdocument.persoon.reisdocumenten.reisdocument.datumEindeDocument   | ${vandaag(+5,0,+1)} |
| verkrijgingReisdocument.acties.registratieReisdocument.persoon.reisdocumenten.reisdocument.datumUitgifte        | ${vandaag(0,0,+1)}  |


When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Then in kern select srt, nr from kern.persreisdoc where pers = (select id from kern.pers where bsn = 126408609) de volgende gegevens:
| veld | waarde    |
| srt  | 13        |
| nr   | 123456789 |

Then in kern select id, oms from kern.srtnlreisdoc where code = 'NI' de volgende gegevens:
| veld | waarde                       |
| id   | 13                           |
| oms  | Nederlandse identiteitskaart |

