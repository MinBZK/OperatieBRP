Meta:
@status             Klaar
@usecase            AL.1.AB
@regels             R1268, R1269, R1270
@sleutelwoorden     Archiveer bericht


Narrative:
Als beheerder wil ik bij een binnenkomend bericht van soort lvg_bvg dat deze wordt gearchiveerd
zodra er een response bericht wordt verstuurd
Wil ik dat het uitgaande bericht van soort lvg_bvg wordt gearchiveerd


Scenario:   1. Archiveren van bericht met type lvg_bvg GeefDetailsPersoon
            LT: R1268_LT01, R1268_LT02, R1269_LT01, R1270_LT01, R1316_LT06, R1617_LT07, R2236_LT01
            Verwacht resultaat:
            R1268_LT01: Inkomend bericht van type lvg_bvg wordt gearchiveerd
            R1269_LT01: Inkomend bericht geef details persoon wordt niet opgenomen in de ber.pers tabel
            R1268_LT02: Uitgaand bericht van type lvg_bvg wordt gearchiveerd
            R1316_LT06: Scope patroon leeg
            R1617_LT07: Historievorm materieel en Datum aanvang materiële periode resultaat LEEG
            R2236_LT01: Leveringsaantekening \ Persoon.Tijdstip laatste wijziging persoon gevuld = Persoon.Tijdstip laatste wijziging

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Abo GeefDetailsPersoon' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0GD_Geef_Details_Persoon/verzoek/1._Geef_Details_Persoon_Story_1.xml


!-- Ingaand bericht wordt gearchiveerd R1268_LT01
!-- Then ingaand bericht is gearchiveerd met referentienummer 00000000-0000-0000-0000-123456789000
Then is er gearchiveerd met de volgende gegevens:
| veld                  | waarde                               |
| srt                   | 71                                   |
| richting              | 1                                    |
| admhnd                | NULL                                 |
| zendendepartij        | 32002                                |
| zendendesysteem       | AFNEMERSYSTEEM                       |
| ontvangendepartij     | NULL                                 |
| referentienr          | 00000000-0000-0000-0000-123456789000 |
| crossreferentienr     | NULL                                 |
| verwerkingswijze      | NULL                                 |
| rol                   | 1                                    |
| srtsynchronisatie     | NULL                                 |
| verwerking            | NULL                                 |
| bijhouding            | NULL                                 |
| hoogstemeldingsniveau | NULL                                 |

Then is het synchrone verzoek correct gearchiveerd
Then tijdstipverzending in bericht is correct gearchiveerd
Then tijdstipontvangst is actueel
Then leveringautorisatie is gelijk in archief
Then dienstid is gelijk in archief

!-- aangepaste R1269_LT01 geen pers opnemen in ber.pers tabel
Then bestaat er geen voorkomen in berpers tabel voor referentie 00000000-0000-0000-0000-123456789000 en srt lvg_bvgGeefDetailsPersoon


!-- Verzonden uitgaand bericht wordt gearchiveerd R1268_LT02
Then heeft het antwoordbericht verwerking Geslaagd
!-- Then uitgaand bericht is gearchiveerd met referentienummer 00000000-0000-0000-0000-123456789000
Then is er gearchiveerd met de volgende gegevens:
| veld                  | waarde                               |
| srt                   | 72                                   |
| richting              | 2                                    |
| admhnd                | NULL                                 |
| zendendepartij        | 2001                                 |
| zendendesysteem       | BRP                                  |
| ontvangendepartij     | 32002                                |
| crossreferentienr     | 00000000-0000-0000-0000-123456789000 |
| verwerkingswijze      | NULL                                 |
| rol                   | NULL                                 |
| srtsynchronisatie     | NULL                                 |
| dienst                | NULL                                 |
| verwerking            | 1                                    |
| bijhouding            | NULL                                 |
| hoogstemeldingsniveau | 1                                    |


Then bestaat er een antwoordbericht voor referentie 00000000-0000-0000-0000-123456789000
Then referentienr is gelijk
Then tijdstipverzending in bericht is correct gearchiveerd
Then tijdstipontvangst is actueel



!-- Deze step doet niets voor bevraging, omdat daar geen leveringsautorisatieIdentificatie in het bericht voorkomt.
!--Then leveringautorisatie is gelijk in archief

!-- R1270_LT01
Then is er voor leveringsautorisatie Abo GeefDetailsPersoon en partij Gemeente Standaard geprotocolleerd met de volgende gegevens:
| veld                        | waarde               |
| bsn                         | 606417801            |
| soortDienst                 | Geef details persoon |
| dataanvmaterieleperioderes  | NULL                 |
| dateindematerieleperioderes | MORGEN               |
| tsaanvformeleperioderes     | NULL                 |
| tseindeformeleperioderes    | NU                   |
| admhnd                      | NULL                 |
| soortSynchronisatie         | NULL                 |
| scopepatroon                | NULL                 |


Scenario:   2A. verstrekking van persoonsgegevens vindt plaats als directe reactie op een bevraging van een afnemer
            Dienst: Geef Details Persoon (met scoping)
            LT: R1613_LT01, R2236_LT01
            Verwacht resultaat: Protocolering met vulling

            Leveringsaantekening.Toegang leveringsautorisatie = Toegang leveringsautorisatie.ID waarvoor verstrekt wordt
            Leveringsaantekening.Dienst = Dienst.ID van de geleverde Dienst (volgt uit opgegeven de parameters van het verzoekbericht)
            Leveringsaantekening.Datum/tijd klaarzetten levering = Datum\tijd systeem van ophalen gegevens(Persoon en Autorisatie) uit de database
            Leveringsaantekening.Datum materieel selectie = 'leeg'
            Leveringsaantekening.Datum aanvang materiële periode resultaat = Bericht.peilmomentMaterieelResultaat
            Leveringsaantekening.Datum einde materiële periode resultaat = Bericht.peilmomentMaterieelResultaat + 1 dag
            Leveringsaantekening.Datum/tijd aanvang formele periode resultaat = 'leeg'
            Leveringsaantekening.Datum/tijd einde formele periode resultaat = Datum\tijd systeem van ophalen gegevens
            Leveringsaantekening.Administratieve handeling = 'leeg'
            Leveringsaantekening.Soort synchronisatie = 'leeg'
            Leveringsaantekening.Scope patroon = '1'

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Bewerker autorisatie' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0GD_Geef_Details_Persoon/verzoek/Geef_Details_Persoon_Scoping_2.1.xml

Then heeft het antwoordbericht verwerking Geslaagd

!-- R1613_LT01
!-- Then in prot heeft select * from prot.levsaantek where id=(select max(levsaantek) from prot.levsaantekpers where pers=(select id from kern.pers where bsn=606417801)) and scopepatroon is not null de volgende gegevens:
Then is er voor leveringsautorisatie Bewerker autorisatie en partij Gemeente Standaard geprotocolleerd met de volgende gegevens:
| veld                        | waarde               |
| bsn                         | 606417801            |
| dataanvmaterieleperioderes  | VANDAAG              |
| dateindematerieleperioderes | MORGEN               |
| admhnd                      | NULL                 |
| srtsynchronisatie           | NULL                 |
| scopepatroon                | 1                    |
| soortDienst                 | Geef details persoon |
| tsaanvformeleperioderes      | NU                   |
| tseindeformeleperioderes     | NU                   |

Scenario:   2B. verstrekking van persoonsgegevens vindt plaats als directe reactie op een bevraging van een afnemer
            LT: R1613_LT01
            Dienst: Geef Details Persoon (met scoping)

Given verzoek voor leveringsautorisatie 'Bewerker autorisatie' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0GD_Geef_Details_Persoon/verzoek/Geef_Details_Persoon_Scoping_2.2.xml

Then heeft het antwoordbericht verwerking Geslaagd

!-- R1613_LT01
!-- Then in prot heeft select * from prot.levsaantek where id=(select max(levsaantek) from prot.levsaantekpers where pers=(select id from kern.pers where bsn=606417801)) and scopepatroon is not null de volgende gegevens:
Then is er voor leveringsautorisatie Bewerker autorisatie en partij Gemeente Standaard geprotocolleerd met de volgende gegevens:
| veld                        | waarde               |
| bsn                         | 606417801            |
| admhnd                      | NULL                 |
| srtsynchronisatie           | NULL                 |
| scopepatroon                | 2                    |
| soortDienst                 | Geef details persoon |
| dataanvmaterieleperioderes  | VANDAAG              |
| dateindematerieleperioderes | MORGEN               |
| tsaanvformeleperioderes     | NU                   |
| tseindeformeleperioderes    | NU                   |

!-- Uitgecomment omdat tsaanvformeleperioderes niet te controleren is, maar in de api test ook getest.

Scenario:   3. verstrekking van persoonsgegevens vindt plaats als directe reactie op een bevraging van een afnemer
            Dienst: Geef Details Persoon
            LT: R1617_LT06
            Verwacht resultaat: Protocolering met vulling

            Leveringsaantekening.Datum aanvang materiële periode resultaat = LEEG

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Bewerker autorisatie' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0GD_Geef_Details_Persoon/verzoek/Geef_Details_Persoon_3.xml

Then heeft het antwoordbericht verwerking Geslaagd

!-- R1617_LT06
!-- Then in prot heeft select * from prot.levsaantek where id=(select levsaantek from prot.levsaantekpers where pers=(select id from kern.pers where bsn=606417801)) de volgende gegevens:
!-- Then in prot heeft select dataanvmaterieleperiode from prot.levsaantekpers where pers=(select id from kern.pers where bsn=606417801) de volgende gegevens:

Then is er voor leveringsautorisatie Bewerker autorisatie en partij Gemeente Standaard geprotocolleerd met de volgende gegevens:
| veld                       | waarde    |
| bsn                        | 606417801 |
| dataanvmaterieleperioderes | NULL      |
| dataanvmaterieleperiode    | NULL      |

Scenario:   4. verstrekking van persoonsgegevens vindt plaats als directe reactie op een bevraging van een afnemer
            Dienst: Geef Details Persoon
            LT: R1617_LT07
            Verwacht resultaat: Protocolering met vulling

            Leveringsaantekening.Datum aanvang materiële periode resultaat = LEEG

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Bewerker autorisatie' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0GD_Geef_Details_Persoon/verzoek/Geef_Details_Persoon_4.xml

Then heeft het antwoordbericht verwerking Geslaagd

!-- R1617_LT07
!-- Then in prot heeft select * from prot.levsaantek where id=(select levsaantek from prot.levsaantekpers where pers=(select id from kern.pers where bsn=606417801)) de volgende gegevens:
!-- Then in prot heeft select dataanvmaterieleperiode from prot.levsaantekpers where pers=(select id from kern.pers where bsn=606417801) de volgende gegevens:

Then is er voor leveringsautorisatie Bewerker autorisatie en partij Gemeente Standaard geprotocolleerd met de volgende gegevens:
| veld                       | waarde    |
| bsn                        | 606417801 |
| dataanvmaterieleperioderes | NULL      |
| dataanvmaterieleperiode    | NULL      |


Scenario:   5. verstrekking van persoonsgegevens vindt plaats als directe reactie op een bevraging van een afnemer
            Dienst: Geef Details Persoon
            LT: R1617_LT09
            Verwacht resultaat: Protocolering met vulling

            Leveringsaantekening.Datum aanvang materiële periode resultaat = LEEG

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Bewerker autorisatie' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0GD_Geef_Details_Persoon/verzoek/Geef_Details_Persoon_5.xml

Then heeft het antwoordbericht verwerking Geslaagd

Then is er voor leveringsautorisatie Bewerker autorisatie en partij Gemeente Standaard geprotocolleerd met de volgende gegevens:
| veld                        | waarde               |
| bsn                         | 606417801            |
| soortDienst                 | Geef details persoon |
| dataanvmaterieleperioderes  | VANDAAG              |
| dateindematerieleperioderes | MORGEN               |
| dataanvmaterieleperiode     | NULL                 |
| tsaanvformeleperioderes     | NU                   |
| tseindeformeleperioderes    | NU                   |
| admhnd                      | NULL                 |
| srtsynchronisatie           | NULL                 |