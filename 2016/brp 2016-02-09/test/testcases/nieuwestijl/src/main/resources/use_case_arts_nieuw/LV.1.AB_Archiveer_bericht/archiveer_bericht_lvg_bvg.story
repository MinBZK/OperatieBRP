Meta:
@sprintnummer           91
@epic                   Archiveer bericht
@auteur                 miuij
@jiraIssue              TEAMBRP-4592, TEAMBRP-4682
@status                 Bug
@regels                 R1268, R1269, R1270



Narrative:
Als beheerder wil ik bij een binnenkomend bericht van soort lvg_bvg dat deze wordt gearchiveerd
zodra er een response bericht wordt verstuurd
Wil ik dat het uitgaande bericht van soort lvg_bvg wordt gearchiveerd


Scenario:   1. Archiveren van bericht met type lvg_bvg
            Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.AB: R1268_L01, R1268_L02, R1269_l01, R1270_L09
            Verwacht resultaat:
            Verwacht resultaat: 1. Inkomende bericht van type lvg_bvg wordt gearchiveerd
                                2. Persoon uit inkomend bericht wordt gearchiveerd in ber.pers
                                3. Uitgaand bericht van type lvg_bvg wordt gearchiveerd
                                4. Geen record voor uitgaand bericht gearchiveerd in ber.pers
                                TEAMBRP-4592 - zendende partij is nu nog id ipv code (expected value nog aanpassen in scenario)
                                TEAMBRP-4682 - veld rol wordt niet gevuld (rol nu uit gecomment)


Given de personen 299054457, 743274313, 606417801 zijn verwijderd
And de standaardpersoon UC_Kruimeltje met bsn 606417801 en anr 1383746930 zonder extra gebeurtenissen
Given de database is aangepast met: delete from ber.ber where ber.referentienr='0000000A-3000-7000-0000-000000000000' or ber.crossreferentienr='0000000A-3000-7000-0000-000000000000'
Given leveringsautorisatie uit /levering_autorisaties/abo_geef_details_persoon
Given de cache is herladen
Given verzoek voor leveringsautorisatie 'Abo GeefDetailsPersoon' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_bvgGeefDetailsPersoon
And testdata uit bestand 1.3_GeefDetailsPersoon.yml

When het bericht wordt verstuurd

Then ingaand bericht is gearchiveerd met referentienummer 0000000A-3000-7000-0000-000000000000
Then in ber select * from ber.ber where ber.referentienr='0000000A-3000-7000-0000-000000000000' and ber.zendendepartij=348 and ber.srt=71 de volgende gegevens:
| veld                  | waarde                               |
| srt                   | 71                                   |
| richting              | 1                                    |
| admhnd                | NULL                                 |
|-- data              --|-- <wordt gecheckt in aparte stap>  --|
| antwoordop            | NULL                                 |
| zendendepartij        | 348                                  |
| zendendesysteem       | AFNEMERSYSTEEM                       |
| ontvangendepartij     | NULL                                 |
| ontvangendesysteem    | NULL                                 |
| referentienr          | 0000000A-3000-7000-0000-000000000000 |
| crossreferentienr     | NULL                                 |
|-- tsverzending      --|-- <wordt gecheckt in aparte stap>  --|
|-- tsontv            --|-- <wordt gecheckt in aparte stap>  --|
| verwerkingswijze      | NULL                                 |
|-- rol               --|-- Afnemer                          --|
| srtsynchronisatie     | NULL                                 |
|-- levsautorisatie   --|-- <wordt gecheckt in aparte stap>  --|
| dienst                | 3                                    |
| verwerking            | NULL                                 |
| bijhouding            | NULL                                 |
| hoogstemeldingsniveau | NULL                                 |

Then is het synchrone verzoek correct gearchiveerd
Then tijdstipverzending in bericht is correct gearchiveerd
Then tijdstipontvangst is actueel
Then leveringautorisatie is gelijk in archief

!-- R1269_L01
Then in ber select kern.pers.bsn from ber.berpers left join ber.ber on (berpers.ber = ber.id) left join kern.pers on (berpers.pers = pers.id) where ber.referentienr='0000000A-3000-7000-0000-000000000000' and ber.srt='71' de volgende gegevens:
| veld      | waarde    |
| bsn       | 606417801 |

!-- todo check onderstaande data
!-- BUG TEAMBRP-4682 veld rol wordt niet gevuld.

Then heeft het antwoordbericht verwerking Geslaagd
Then uitgaand bericht is gearchiveerd met referentienummer 0000000A-3000-7000-0000-000000000000
Then in ber select * from ber.ber where ber.crossreferentienr='0000000A-3000-7000-0000-000000000000' and ber.zendendepartij=2001 and ber.srt=72 de volgende gegevens:
| veld                  | waarde                               |
| srt                   | 72                                   |
| richting              | 2                                    |
| admhnd                | NULL                                 |
|-- data              --|-- <wordt gecheckt in aparte stap>  --|
|-- antwoordop        --|-- <wordt gecheckt in aparte stap>  --|
| zendendepartij        | 2001                                 |
| zendendesysteem       | BRP                                  |
| ontvangendepartij     | NULL                                 |
| ontvangendesysteem    | NULL                                 |
|-- referentienr      --|-- <wordt gecheckt in aparte stap>  --|
| crossreferentienr     | 0000000A-3000-7000-0000-000000000000 |
|-- tsverzending      --|-- <wordt gecheckt in aparte stap>  --|
|-- tsontv            --|-- <wordt gecheckt in aparte stap>  --|
| verwerkingswijze      | NULL                                 |
| rol                   | NULL                                 |
| srtsynchronisatie     | NULL                                 |
|-- levsautorisatie   --|-- <wordt gecheckt in aparte stap>  --|
| dienst                | NULL                                 |
| verwerking            | 1                                    |
| bijhouding            | NULL                                 |
| hoogstemeldingsniveau | 1                                    |

Then controleer dat alle asynchroon ontvangen berichten correct gearchiveerd zijn
Then bestaat er een antwoordbericht voor referentie 0000000A-3000-7000-0000-000000000000
Then referentienr is gelijk
Then tijdstipverzending in bericht is correct gearchiveerd
Then tijdstipontvangst is actueel
Then leveringautorisatie is gelijk in archief

!-- R1270_09
Then in ber select kern.pers.bsn from ber.berpers left join ber.ber on (berpers.ber = ber.id) left join kern.pers on (berpers.pers = pers.id) where ber.ontvpartij='365'  and ber.srt='23' de volgende gegevens:
| veld      | waarde    |
| bsn       | 606417801 |
