Meta:
@sprintnummer           91
@epic                   Archiveer bericht
@auteur                 miuij
@jiraIssue              TEAMBRP-4592, TEAMBRP-4682
@status                 Bug
@regels                 R1268, R1269, R1270



Narrative:
Als beheerder wil ik bij een binnenkomend bericht van soort lvg_synGeef dat deze wordt gearchiveerd
Na levering van het bericht
Wil ik dat het uitgaande bericht van soort lvg_synGeef wordt gearchiveerd

Scenario:   Synchroniseer persoon goed pad, inkomend en uitgaand bericht worden gearchiveerd
            Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.AB:   R1268_L03, R1268_L04, R1268_L06, R1269_L04, R1270_L06
            Verwacht resultaat:
            1. Synchronisatie gearchiveerd Vulling voor inkomend bericht:
            2. Persoon in bericht is gearchiveerd in ber.pers
            3. Synchronisatie gearchiveerd Vulling voor uitgaand bericht:
            4. Geen persoon gearchiveerd in ber.pers
            5. Leverings bericht wordt gearchiveerd:
            6. Persoon uit leveringsbericht is gearchiveerd in ber.pers
            TEAMBRP-4592 - zendende partij is nu nog id ipv code (expected value nog aanpassen in scenario)
            TEAMBRP-4682 - rol wordt niet gevuld (expected value nu uit gecomment)


Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de personen 803697417, 968282441, 412670409 zijn verwijderd
Given de database is aangepast met: delete from ber.ber where ber.referentienr='0000000A-3000-7000-0000-000000000000'
Given de database is aangepast met: delete from ber.ber where ber.crossreferentienr='0000000A-3000-7000-0000-000000000000'
Given de standaardpersoon UC_Timmy met bsn 412670409 en anr 7157893906 zonder extra gebeurtenissen
Given de database is aangepast met: update autaut.dienst set dateinde='${vandaagsql(0,0,1)}'
Given de cache is herladen
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 1.2_Synchroniseer_Persoon_1.yml
When het bericht wordt verstuurd

Then ingaand bericht is gearchiveerd met referentienummer 0000000A-3000-7000-0000-000000000000
Then in ber select q.* from ber.ber q left join ber.berpers qp on (q.id = qp.ber) left join kern.pers p on (qp.pers = p.id) where p.bsn=412670409 and q.zendendepartij=348 and q.srt=64 de volgende gegevens:
| veld                  | waarde                               |
| srt                   | 64                                   |
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
|-- rol               --|-- NULL                             --|
| srtsynchronisatie     | NULL                                 |
|-- levsautorisatie   --|-- <wordt gecheckt in aparte stap>  --|
| dienst                | NULL                                 |
| verwerking            | NULL                                 |
| bijhouding            | NULL                                 |
| hoogstemeldingsniveau | NULL                                 |

Then is het synchrone verzoek correct gearchiveerd
Then tijdstipverzending in bericht is correct gearchiveerd
Then tijdstipontvangst is actueel
Then leveringautorisatie is gelijk in archief
!-- BUG TEAMBRP-4682 rol wordt niet gevuld.

!-- R1269_L04
Then in ber select kern.pers.bsn from ber.berpers left join ber.ber on (berpers.ber = ber.id) left join kern.pers on (berpers.pers = pers.id) where ber.referentienr='0000000A-3000-7000-0000-000000000000' and ber.srt='64' de volgende gegevens:
| veld      | waarde    |
| bsn       | 412670409 |

Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then in ber select * from ber.ber where crossreferentienr='0000000A-3000-7000-0000-000000000000' and srt=65 de volgende gegevens:
| veld                  | waarde                               |
| srt                   | 65                                   |
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
|-- tsverzending      --|-- <wordt gecheckt in aparte stapt> --|
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
Then bestaat er een antwoordbericht voor referentie 0000000A-3000-7000-0000-000000000000
Then referentienr is gelijk
Then tijdstipverzending in bericht is correct gearchiveerd
Then leveringautorisatie is gelijk in archief


!-- R1270_L06
Then bestaat er geen voorkomen in berpers tabel voor crossreferentie 0000000A-3000-7000-0000-000000000000 en srt 65


Then in ber select * from ber.ber where crossreferentienr='0000000A-3000-7000-0000-000000000000' and srt=23 de volgende gegevens:
| veld                  | waarde                               |
| srt                   | 23                                   |
| richting              | 2                                    |
| admhnd                | NULL                                 |
|-- data              --|-- <wordt gecheckt in aparte stap>  --|
| antwoordop            | NULL                                 |
| zendendepartij        | 2001                                 |
| zendendesysteem       | BRP                                  |
| ontvangendepartij     | 348                                  |
| ontvangendesysteem    | Leveringsysteem                      |
|-- referentienr      --|-- <wordt gecheckt in aparte stap>  --|
| crossreferentienr     | 0000000A-3000-7000-0000-000000000000 |
|-- tsverzending      --|-- <wordt gecheckt in aparte stap>  --|
| tsontv                | NULL                                 |
| verwerkingswijze      | NULL                                 |
|-- rol               --|-- 1                                --|
| srtsynchronisatie     | 2                                    |
|-- levsautorisatie   --|-- <wordt gecheckt in aparte stap>  --|
|-- dienst            --|-- <wordt gecheckt in aparte stap>  --|
| verwerking            | NULL                                 |
| bijhouding            | NULL                                 |
| hoogstemeldingsniveau | NULL                                 |


Then controleer dat alle asynchroon ontvangen berichten correct gearchiveerd zijn
Then referentienr is gelijk
Then tijdstipverzending in bericht is correct gearchiveerd
Then leveringautorisatie is gelijk in archief
Then dienstid is gelijk in archief
!-- TEAMBRP-4682 - rol wordt niet gevuld (expected value nu uit gecomment)

!-- R1270_L09
Then in ber select kern.pers.bsn from ber.berpers left join ber.ber on (berpers.ber = ber.id) left join kern.pers on (berpers.pers = pers.id) where ber.crossreferentienr='0000000A-3000-7000-0000-000000000000' and ber.srt='23' de volgende gegevens:
| veld      | waarde    |
| bsn       | 412670409 |


Scenario:   Synchroniseer persoon fout pad, check uitgaand bericht meerdere foutmeldingen juiste hoogste melding wordt gearchiveerd
            leveringsautorisatie en personen worden gebruikt uit bovenstaand scenario
            Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.AB: R1268_L05
            Verwacht resultaat:
            1. Synchronisatie gearchiveerd Vulling voor uitgaand bericht met meerdere foutmeldingen is gearchiveerd met juiste hoogste melding
            2. Er is geen persoon gearchiveerd in de ber.pers tabel

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de database is aangepast met: delete from ber.ber where ber.referentienr='0000000A-3000-7000-0000-000000000000'
Given de database is aangepast met: delete from ber.ber where ber.crossreferentienr='0000000A-3000-7000-0000-000000000000'
Given de database is aangepast met: update autaut.levsautorisatie set populatiebeperking='ONWAAR' where naam = 'Geen pop.bep. levering op basis van doelbinding'
Given de cache is herladen

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 1.2_Synchroniseer_Persoon_1.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'
Then heeft in het antwoordbericht 'melding' in 'melding' de waarde 'De opgegeven persoon valt niet te synchroniseren binnen de opgegeven leveringsautorisatie.'

Then uitgaand bericht is gearchiveerd met referentienummer 0000000A-3000-7000-0000-000000000000
Then in ber select * from ber.ber where crossreferentienr='0000000A-3000-7000-0000-000000000000' and srt=65 de volgende gegevens:
| veld                  | waarde                               |
| srt                   | 65                                   |
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
|-- tsverzending      --|-- <wordt geccheckt in aparte stap> --|
| tsontv                | NULL                                 |
| verwerkingswijze      | NULL                                 |
| rol                   | NULL                                 |
| srtsynchronisatie     | NULL                                 |
|-- levsautorisatie   --|-- <wordt geccheckt in aparte stap> --|
| dienst                | NULL                                 |
| verwerking            | 2                                    |
| bijhouding            | NULL                                 |
| hoogstemeldingsniveau | 5                                    |

Then controleer dat alle asynchroon ontvangen berichten correct gearchiveerd zijn
Then bestaat er een antwoordbericht voor referentie 0000000A-3000-7000-0000-000000000000
Then referentienr is gelijk
Then tijdstipverzending in bericht is correct gearchiveerd
Then leveringautorisatie is gelijk in archief

Then bestaat er geen voorkomen in berpers tabel voor crossreferentie 0000000A-3000-7000-0000-000000000000 en srt 65
