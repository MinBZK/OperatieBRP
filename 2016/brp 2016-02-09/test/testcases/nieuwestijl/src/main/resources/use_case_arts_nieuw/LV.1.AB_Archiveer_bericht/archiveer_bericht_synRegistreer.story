Meta:
@sprintnummer           91
@epic                   Archiveer bericht
@auteur                 miuij
@jiraIssue              TEAMBRP-4592, TEAMBRP-4809
@status                 Bug
@regels                 R1268, R1269, R1270



Narrative:
Als beheerder wil ik bij een binnenkomend bericht van soort lvg_synRegistreer dat deze wordt gearchiveerd
zodra er een response bericht wordt verstuurd
Wil ik dat het uitgaande bericht van soort lvg_synRegistreer wordt gearchiveerd


Scenario:   1. Plaatsen afnemerindicatie goedpad, inkomend en synchroon responsebericht worden gearchiveerd
            Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.AB: R1268_L07, R1268_L08, R1269_L06, R1270_08
            Verwacht resultaat:
            Verwacht resultaat: 1. Inkomende bericht van type Onderhoud afnemersindicatie wordt gearchiveerd
                                2. Persoon in inkomend bericht is gearchiveerd in ber.pers
                                3. Synchroon responsebericht wordt gearchiveerd
                                4. Geen record van response bericht wordt gearchiveerd.
                                TEAMBRP-4592 - zendende partij is nu nog id ipv code (expected value nog aanpassen in scenario)
                                TEAMBRP-4809 - leveringsautorisatie wordt niet correct gevuld

Given de personen 299054457, 743274313, 606417801 zijn verwijderd
And de standaardpersoon UC_Kruimeltje met bsn 606417801 en anr 1383746930 zonder extra gebeurtenissen
Given de database is aangepast met: delete from ber.ber where ber.referentienr='00000000-0000-0000-0000-000000001230' or ber.crossreferentienr='00000000-0000-0000-0000-000000001230'
Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie

And testdata uit bestand 1.Plaats_Afnemerindicatie_goedpad_02.yml
When het bericht wordt verstuurd
Then ingaand bericht is gearchiveerd met referentienummer 00000000-0000-0000-0000-000000001230
Then in ber select q.* from ber.ber q left join ber.berpers qp on (q.id = qp.ber) left join kern.pers p on (qp.pers = p.id) where p.bsn=606417801 and q.zendendepartij=178 and q.srt=40 de volgende gegevens:
| veld                  | waarde                               |
| srt                   | 40                                   |
| richting              | 1                                    |
| admhnd                | NULL                                 |
|-- data              --|-- <wordt gecheckt in aparte stap>  --|
| antwoordop            | NULL                                 |
| zendendepartij        | 178                                  |
| zendendesysteem       | BRP                                  |
| ontvangendepartij     | NULL                                 |
| ontvangendesysteem    | NULL                                 |
| referentienr          | 00000000-0000-0000-0000-000000001230 |
| crossreferentienr     | NULL                                 |
|-- tsverzending      --|-- <wordt gecheckt in aparte stap>  --|
|-- tsontv            --|-- <wordt gecheckt in aparte stap>  --|
| verwerkingswijze      | NULL                                 |
| rol                   | NULL                                 |
| srtsynchronisatie     | NULL                                 |
|-- levsautorisatie   --|-- <wordt gecheckt in aparte stap>  --|
| dienst                | NULL                                 |
| verwerking            | NULL                                 |
| bijhouding            | NULL                                 |
| hoogstemeldingsniveau | NULL                                 |

Then is het synchrone verzoek correct gearchiveerd
Then tijdstipverzending in bericht is correct gearchiveerd
Then tijdstipontvangst is actueel
!-- Then leveringautorisatie is gelijk in archief

Then heeft het antwoordbericht verwerking Geslaagd

!-- R1269_L06
Then in ber select kern.pers.bsn from ber.berpers left join ber.ber on (berpers.ber = ber.id) left join kern.pers on (berpers.pers = pers.id) where ber.referentienr='00000000-0000-0000-0000-000000001230' and ber.srt='40' de volgende gegevens:
| veld      | waarde    |
| bsn       | 606417801 |


When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then uitgaand bericht is gearchiveerd met referentienummer 00000000-0000-0000-0000-000000001230
Then in ber select * from ber.ber where crossreferentienr='00000000-0000-0000-0000-000000001230' and srt=41 de volgende gegevens:
| veld                  | waarde                               |
| srt                   | 41                                   |
| richting              | 2                                    |
| admhnd                | NULL                                 |
|-- data              --|-- <wordt gecheckt in aparte stap>  --|
|-- antwoordop        --|-- <wordt gecheckt in aparte stap>  --|
| zendendepartij        | 2001                                 |
| zendendesysteem       | BRP                                  |
| ontvangendepartij     | NULL                                 |
| ontvangendesysteem    | NULL                                 |
|-- referentienr      --|-- <wordt gecheckt in aparte stap>  --|
| crossreferentienr     | 00000000-0000-0000-0000-000000001230 |
|-- tsverzending      --|-- <wordt gecheckt in aparte stap>  --|
| tsontv                | NULL                                 |
| verwerkingswijze      | NULL                                 |
| rol                   | NULL                                 |
| srtsynchronisatie     | NULL                                 |
|-- levsautorisatie   --|-- <wordt gecheckt in aparte stap>  --|
| dienst                | NULL                                 |
| verwerking            | 1                                    |
| bijhouding            | NULL                                 |
| hoogstemeldingsniveau | 1                                    |

Then controleer dat alle asynchroon ontvangen berichten correct gearchiveerd zijn
Then bestaat er een antwoordbericht voor referentie 00000000-0000-0000-0000-000000001230
Then referentienr is gelijk
Then tijdstipverzending in bericht is correct gearchiveerd
!-- Then leveringautorisatie is gelijk in archief

Then is het bericht xsd-valide

!-- R1270_08
Then bestaat er geen voorkomen in berpers tabel voor crossreferentie 00000000-0000-0000-0000-000000001230  en srt 41


Scenario:   2. Plaatsen afnemerindicatie foutpad, inkomend en synchroon responsebericht worden gearchiveerd
            Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.AB: R1269_L11
            Verwacht resultaat:
            Verwacht resultaat: 1. Inkomende bericht van type Onderhoud afnemersindicatie wordt gearchiveerd
                                2. Persoon in inkomend bericht is niet bekend en wordt niet opgenomen in berpers
                                TEAMBRP-4592 - zendende partij is nu nog id ipv code (expected value nog aanpassen in scenario)



Given de database is aangepast met: delete from ber.ber where ber.referentienr='00000000-0000-0000-0000-000000001230' or ber.crossreferentienr='00000000-0000-0000-0000-000000001230'
Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given de cache is herladen
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 1.Plaats_Afnemerindicatie_foutpad.yml

When het bericht wordt verstuurd

Then ingaand bericht is gearchiveerd met referentienummer 00000000-0000-0000-0000-000000001230
Then in ber select * from ber.ber where zendendepartij='178' and referentienr='00000000-0000-0000-0000-000000001230' and srt='40' de volgende gegevens:
| veld                  | waarde                               |
| srt                   | 40                                   |
| richting              | 1                                    |
| admhnd                | NULL                                 |
|-- data              --|-- <wordt gecheckt in aparte stap>  --|
| antwoordop            | NULL                                 |
| zendendepartij        | 178                                  |
| zendendesysteem       | BRP                                  |
| ontvangendepartij     | NULL                                 |
| ontvangendesysteem    | NULL                                 |
| referentienr          | 00000000-0000-0000-0000-000000001230 |
| crossreferentienr     | NULL                                 |
|-- tsverzending      --|-- <wordt gecheckt in aparte stap>  --|
|-- tsontv            --|-- <wordt gecheckt in aparte stap>  --|
| verwerkingswijze      | NULL                                 |
| rol                   | NULL                                 |
| srtsynchronisatie     | NULL                                 |
|-- levsautorisatie   --|-- <wordt gecheckt in aparte stap>  --|
| dienst                | NULL                                 |
| verwerking            | NULL                                 |
| bijhouding            | NULL                                 |
| hoogstemeldingsniveau | NULL                                 |

Then is het synchrone verzoek correct gearchiveerd
Then tijdstipverzending in bericht is correct gearchiveerd
Then tijdstipontvangst is actueel
!-- Then leveringautorisatie is gelijk in archief

!-- R1269_11
Then bestaat er geen voorkomen in berpers tabel voor referentie 00000000-0000-0000-0000-000000001230 en srt 40