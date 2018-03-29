Meta:
@status             Klaar
@usecase            AL.1.AV
@regels             ARCHSYN

Narrative:
Als beheerder wil ik bij een binnenkomend bericht dat deze wordt gearchiveerd
zodra er een response bericht wordt verstuurd
Wil ik dat het uitgaande bericht wordt gearchiveerd

Scenario:   1   Verzoek synchronisatie persoon, met dienst mutatielevering op basis van doelbinding en
            LT: R1262_LT01, R1268_LT03, R1268_LT04, R1268_LT06, R1269_LT04, R1270_LT06, R1270_LT12, R1335_LT02, R1339_LT02, R1347_LT01, R1403_LT03, R1982_LT01, R1587_LT05
            Verzoek synchronisatie persoon, met dienst mutatielevering op basis van doelbinding en
            Datum einde volgen is gevuld met 1 dag > systeemdatum

            AL.1.AV Afhandelen Verzoek AL.1.AB:
            LT: R1268_LT03 - Verwacht resultaat: Ingaand bericht gearchiveerd volgens de regels en bsn geprotocolleerd
            LT: R1268_LT04 - Verwacht resultaat: Uitgaand bericht (synchroon) gearchiveerd
            LT: R1269_LT04 - Verwacht resultaat: Persoonsreferentie inkomend bericht niet opnemen in ber.pers tabel
            LT: R1270_LT06 - Verwacht resultaat: Persoonsreferentie synchroon response bericht niet opnemen staat nl niet in response bericht
            LT: R1270_LT12 - Verwacht resultaat: Persoonsreferentie lever bericht wel opgenomen in ber.pers tabel

            LT: R1262_LT01 - De gevraagde dienst synchroniseer persoon datum ingang kleiner dan systeemdatum, datum einde is groter dan systeemdatum

            SA.0.SP Synchroniseer persoon:
            R1339_LT02 - Verwacht resultaat: Geen verstrekkingsbeperking aanwezig voor verzoekende afnemer, er wordt gevolg gegeven aan het verzoek
            R1347_LT01 - Verwacht resultaat: De afnemer moet voor de dienst Synchronisatie persoon ook een leveringsautorisatie hebben voor de dienst 'Mutatielevering op basis van doelbinding'
            R1403_LT03 - Verwacht resultaat: Er bestaat een persoon met het opgegeven burgerservicenummer, er wordt geleverd.

            SA.0.LM/SA.0.AA Attendering met plaatsen afnemerindicatie:
            R1335_LT02 - Verwacht resultaat: Geen afnemerindicatie geplaatst

            R1982_LT01 - Verwacht resultaat: Bij een geldig verzoek via Synchroniseer persoon volgt een volledig bericht

             Verwacht resultaat: Antwoordbericht
                Met vulling:
                - Zendende partij
                - Zendende systeem
                - referentienummer
                - tijdstip
                - abonnementnaam
                - burgerservicenummer
            Verwacht resultaat:Leveringsbericht
                Met vulling:
                -  Soort bericht = Volledigbericht
                -  Persoon = De betreffende Persoon uit het bericht
                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd
                -  Abonnement = Het Abonnement waarbinnen de Dienst wordt geleverd

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls
!-- R1262_LT01

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/SA1SP_Synchroniseer_Persoon/Requests/3._Synchroniseer_Persoon_Story_2.0.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then heeft in het antwoordbericht 'zendendePartij' in 'stuurgegevens' de waarde '199903'
Then heeft in het antwoordbericht 'zendendeSysteem' in 'stuurgegevens' de waarde 'BRP'
Then is in antwoordbericht de aanwezigheid van 'referentienummer' in 'stuurgegevens' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'tijdstipVerzending' in 'stuurgegevens' nummer 1 ja

!--  R1268_LT03
Then is er gearchiveerd met de volgende gegevens:
| veld                  | waarde                           |
| srt                    | 64                                   |
| richting              | 1                                    |
| admhnd                | NULL                                 |
| zendendepartij        | 32002                                  |
| zendendesysteem       | AFNEMERSYSTEEM                       |
| ontvangendepartij     | NULL                                 |
| referentienr          | 00000000-0000-0000-0000-123456789123 |
| crossreferentienr     | NULL                                 |
| verwerkingswijze      | NULL                                 |
| rol                   | NULL                                 |
| srtsynchronisatie     | NULL                                 |
| verwerking            | NULL                                 |
| bijhouding            | NULL                                 |
| hoogstemeldingsniveau | NULL                                 |

Then is het synchrone verzoek correct gearchiveerd
Then tijdstipverzending in bericht is correct gearchiveerd
Then tijdstipontvangst is actueel
Then leveringautorisatie is gelijk in archief

!-- R1269_L04 persoon niet opnemen bij binenkomende verzoeken in de ber.pers tabel
Then bestaat er geen voorkomen in berpers tabel voor referentie 00000000-0000-0000-0000-123456789123 en srt lvg_synGeefSynchronisatiePersoon

!-- R1982_LT01
When alle berichten zijn geleverd

Then is er een volledigbericht ontvangen voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding

!--  R1268_LT04
Then is er gearchiveerd met de volgende gegevens:
| veld                  | waarde                               |
| srt                   | 65                                   |
| richting              | 2                                    |
| admhnd                | NULL                                 |
| zendendepartij        | 2001                                 |
| zendendesysteem       | BRP                                  |
| ontvangendepartij     | 32002                                  |
| crossreferentienr     | 00000000-0000-0000-0000-123456789123 |
| tsontv                | NULL                                 |
| verwerkingswijze      | NULL                                 |
| rol                   | NULL                                 |
| srtsynchronisatie     | NULL                                 |
| dienst                | NULL                                 |
| verwerking            | 1                                    |
| bijhouding            | NULL                                 |
| hoogstemeldingsniveau | 1                                    |

Then referentienr is gelijk
Then tijdstipverzending in bericht is correct gearchiveerd
Then leveringautorisatie is gelijk in archief

!--  R1270_LT06  antwoordbericht wordt gearchiveerd in berpers tabel
Then is er gearchiveerd met de volgende gegevens:
| veld | waarde    |
| bsn  | 606417801 |
| srt  | 65        |


!-- R1268_LT06
Then is er gearchiveerd met de volgende gegevens:
| veld                  | waarde          |
| srt                   | 23              |
| richting              | 2               |
| admhnd                | NULL            |
| zendendepartij        | 2001            |
| zendendesysteem       | BRP             |
| ontvangendepartij     | 32002             |
| crossreferentienr     | NULL            |
| tsontv                | NULL            |
| verwerkingswijze      | NULL            |
| rol                   | 1               |
| srtsynchronisatie     | 2               |
| verwerking            | NULL            |
| bijhouding            | NULL            |
| hoogstemeldingsniveau | NULL            |

Then referentienr is gelijk
Then tijdstipverzending in bericht is correct gearchiveerd
Then dienstid is gelijk in archief
Then leveringautorisatie is gelijk in archief

!-- R1270_LT12
Then is er gearchiveerd met de volgende gegevens:
| veld | waarde    |
| bsn  | 606417801 |
| srt  | 23        |

!-- R1613_LT02, R1615_LT01
Then is er voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding en partij Gemeente Standaard geprotocolleerd met de volgende gegevens:
| veld                     | waarde                 |
| bsn                      | 606417801              |
| soortDienst              | Synchronisatie persoon |
| soortSynchronisatie      | Volledigbericht        |
| dataanvmaterieleperiode  | NULL                   |
| tseindeformeleperioderes | NULL                   |
| tsaanvformeleperioderes  | NULL                   |
| tseindeformeleperioderes | NU                     |


Scenario:   2.1 Plaats afnemerindicatie op Libby zonder datum aanvang mat. per. en op Piet met datum aanvang mat. per.
            LT: Preconditie voor volgende scenario's
            Verwacht resultaat:
            - Afnemerindicatie geplaatst

Given alle personen zijn verwijderd
Given personen uit bestanden:
|filenaam
|/LO3PL/Libby.xls
|/LO3PL/Piet.xls

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/SA1SP_Synchroniseer_Persoon/Requests/Prot/Plaats_Afnemerindicatie_Piet.xml

Then heeft het antwoordbericht verwerking Geslaagd
And is het antwoordbericht xsd-valide

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/SA1SP_Synchroniseer_Persoon/Requests/Prot/Plaats_Afnemerindicatie_Libby_Scenario_3.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

Scenario:   2.2 Protocollering van asynchroon bericht na synchroniseer persoon (Piet)
            LT: R1615_LT02, R1617_LT04
            Verwacht resultaat: synchroniseer persoon bericht geprotocolleerd

            Leveringsaantekening.Toegang leveringsautorisatie = Toegang leveringsautorisatie.ID (R1615_LT02)
            Leveringsaantekening.Dienst = Dienst.ID van de geleverde Dienst (R1615_LT01)
            Leveringsaantekening.Datum/tijd klaarzetten levering = Datum\tijd van klaar zetten bericht (R1615_LT02)
            Leveringsaantekening.Datum materieel selectie = 'leeg' (R1616_LT02)
            Leveringsaantekening.Datum aanvang materiële periode resultaat = afnemerindicatie.datum aanvang mat. per. (R1617_LT04)
            Leveringsaantekening.Datum einde materiële periode resultaat = 'leeg' (R1618_LT03)
            Leveringsaantekening.Datum/tijd aanvang formele periode resultaat = 'leeg' (R1619_LT06)
            Leveringsaantekening.Datum/tijd einde formele periode resultaat = Datum\tijd van klaar zetten bericht (R1620_LT03)
            Leveringsaantekening.Administratieve handeling = 'Leeg' (R1615_LT02)
            Leveringsaantekening.Soort synchronisatie = 2 (volledig bericht)
            Leveringsaantekening.Scope patroon = 'leeg' (R1613_LT02)

            Persoon.Leveringsaantekening \ Persoon.Tijdstip laatste wijziging persoon = gevuld (Tijdstip verwerking adm. hand.) (R2236_LT03)

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/SA1SP_Synchroniseer_Persoon/Requests/Prot/Synchoniseer_Persoon_Scenario_2.xml

Then heeft het antwoordbericht verwerking Geslaagd
And is het antwoordbericht xsd-valide

When alle berichten zijn geleverd

Then is er een volledigbericht ontvangen voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie

!-- R1617_LT04
Then is er voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie en partij Gemeente Standaard geprotocolleerd met de volgende gegevens:
| veld                        | waarde                 |
| bsn                         | 159247913              |
| dataanvmaterieleperioderes  | 20160207               |
| dateindematerieleperioderes | NULL                   |
| tsaanvformeleperioderes     | NULL                   |
| tseindeformeleperioderes    | NU                     |
| soortSynchronisatie         | Volledigbericht        |
| scopepatroon                | NULL                   |
| soortDienst                 | Synchronisatie persoon |
| dataanvmaterieleperiode     | NULL                   |

Scenario:   2.3 Protocollering van asynchroon bericht na synchroniseer persoon, pers afn ind datum aanvang mat per is 'Leeg' (Libby)
            LT: R1617_LT03
            Verwacht resultaat: synchroniseer persoon bericht geprotocolleerd

            Leveringsaantekening.Toegang leveringsautorisatie = Toegang leveringsautorisatie.ID (R1615_LT02)
            Leveringsaantekening.Dienst = Dienst.ID van de geleverde Dienst (R1615_LT01)
            Leveringsaantekening.Datum/tijd klaarzetten levering = Datum\tijd van klaar zetten bericht (R1615_LT02)
            Leveringsaantekening.Datum materieel selectie = 'leeg' (R1616_LT02)
            Leveringsaantekening.Datum aanvang materiële periode resultaat = 'leeg' (R1617_LT03)
            Leveringsaantekening.Datum einde materiële periode resultaat = 'leeg' (R1618_LT03)
            Leveringsaantekening.Datum/tijd aanvang formele periode resultaat = 'leeg' (R1619_LT06)
            Leveringsaantekening.Datum/tijd einde formele periode resultaat = Datum\tijd van klaar zetten bericht (R1620_LT03)
            Leveringsaantekening.Administratieve handeling = 'Leeg' (R1615_LT02)
            Leveringsaantekening.Soort synchronisatie = 2 (volledig bericht)
            Leveringsaantekening.Scope patroon = 'leeg' (R1613_LT02)

            Persoon.Leveringsaantekening \ Persoon.Tijdstip laatste wijziging persoon = gevuld (Tijdstip verwerking adm. hand.) (R2236_LT03)

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/SA1SP_Synchroniseer_Persoon/Requests/Prot/Synchroniseer_Persoon_Scenario_4.xml
Then heeft het antwoordbericht verwerking Geslaagd
And is het antwoordbericht xsd-valide

When alle berichten zijn geleverd

Then is er een volledigbericht ontvangen voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie

!-- R1617_LT03
Then is er voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie en partij Gemeente Standaard geprotocolleerd met de volgende gegevens:
| veld                        | waarde                 |
| bsn                         | 422531881              |
| dataanvmaterieleperioderes  | NULL                   |
| dateindematerieleperioderes | NULL                   |
| tsaanvformeleperioderes     | NULL                   |
| tseindeformeleperioderes    | NU                     |
| soortSynchronisatie         | Volledigbericht        |
| scopepatroon                | NULL                   |
| soortDienst                 | Synchronisatie persoon |
| dataanvmaterieleperiode     | NULL                   |
