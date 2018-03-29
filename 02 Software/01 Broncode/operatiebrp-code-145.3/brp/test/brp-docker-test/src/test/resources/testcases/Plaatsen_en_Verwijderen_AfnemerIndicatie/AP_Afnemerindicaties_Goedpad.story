Meta:
@status             Klaar
@usecase            SA.0.PA, SA.1.PA.CA, SA.1.PA.CI
@sleutelwoorden     Plaats afnemerindicatie, verwijder afnemerindicatie, Archivering + Protocollering + Verzenden
@regels             PLAVERAFNIND

Narrative:
Deze test is gemaakt om te kijken of de integratie van gba naar BRP goed verloopt
Tegelijkertijd worden de testgevallen die in de nieuwe tool niet afgedekt kunnen worden hier getest.
Dit heeft betrekking op archivering en levering (en later protocolering) waar de database voor nodig is

Scenario: 1.1   Er wordt een persoon vanuit het GBA ingeladen en een afnemerindicatie geplaatst.
                LT: R1268_LT07, R1268_LT08, R1269_LT06, R1408_LT01, R1266_LT01, R1410_LT01, R1612_LT01, R1995_LT01, R1997_LT01, R1997_LT03, R1985_LT01, R1270_LT10, R1538_LT01, R1268_LT08

                AL.1.AV Afhandelen Verzoek AL.1.AB:
                LT: R1268_LT07, R1269_LT06
                Verwacht resultaat: Ingaand bericht gearchiveerd volgens de regels en bsn niet gearchiveerd in ber.pers

                SA.0.PA Plaats afnemerindicatie
                LT: R1408_LT01
                Verwacht resultaat: Persoon \ Afnemerindicatie.Standaard.Dienst inhoud wordt gevuld met de Dienst.ID van de Dienst waarbinnen de betreffende afnemerindicatie wordt aangemaakt.
                EN Persoon \ Afnemerindicatie.Leveringsautorisatie wordt voor een BRP bericht overgenomen uit de parameters van het bericht (Bericht.Leveringsautorisatie)

                AL.1.AV Afhandelen Verzoek AL.1.MR Maak Resultaatbericht en AL.1.VR Verzend resultaatbericht
                LT: R1266_LT01, R1410_LT01
                Verwacht resultaat:
                1. Synchroon responsebericht
                Met vulling:
                -  Verwerking = geslaagd
                -  Hoogste meldings niveau = Geen
                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd
                2. XSD valide asynchroon resoponsebericht
                3. Verwerking geslaagd

                AL.1.VZ Verzenden
                LT: R1612_LT01, R1995_LT01, R1997_LT01, R1997_LT03
                Verwacht resulaat:
                2. Ingaand en uitgaand bericht worden gearchiveerd (R1997)

                AL.1.VZ Verzending AL.1.VE Verzend bericht
                BRP bericht, gegarandeerd leveren op afleverpunt
                LT: R1985_LT01
                Verwacht resultaat: Stelsel wordt bepaald op BRP, er wordt geleverd via BRP.

                AL.1.AB - Archiveer bericht
                LT: R1268_LT08, R1270_LT10
                Verwacht resultaat:
                1. Bericht vastgelegd volgens regels
                2. bijgehouden persoon vastgelegd in berpers (R1270)


Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Anne_met_Historie.xls
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/Plaatsen_en_Verwijderen_AfnemerIndicatie/Requests/5._Plaats_Afnemerindicatie_Story_4.1.xml


!-- R1410_LT01
Then heeft het antwoordbericht verwerking Geslaagd

!-- R1266_LT01
Then heeft in het antwoordbericht 'zendendePartij' in 'stuurgegevens' de waarde '199903'
Then heeft in het antwoordbericht 'zendendeSysteem' in 'stuurgegevens' de waarde 'BRP'
Then is in antwoordbericht de aanwezigheid van 'referentienummer' in 'stuurgegevens' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'crossReferentienummer' in 'stuurgegevens' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'tijdstipVerzending' in 'stuurgegevens' nummer 1 ja

!-- R1410_LT01
Then heeft in het antwoordbericht 'verwerking' in 'resultaat' de waarde 'Geslaagd'
Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Geen'

Then heeft in het antwoordbericht 'partijCode' in 'plaatsingAfnemerindicatie' de waarde '900002'
Then is in antwoordbericht de aanwezigheid van 'tijdstipRegistratie' in 'plaatsingAfnemerindicatie' nummer 1 ja
Then heeft in het antwoordbericht 'burgerservicenummer' in 'plaatsingAfnemerindicatie' de waarde '606417801'

When alle berichten zijn geleverd

Then is er een volledigbericht ontvangen voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie

!-- R1997_LT01
!-- R1408_LT01: Persoon \ Afnemerindicatie.Leveringsautorisatie wordt voor een BRP bericht overgenomen uit de parameters van het bericht (Bericht.Leveringsautorisatie)
Then is er voor persoon met bsn 606417801 en leveringautorisatie Geen pop.bep. levering op basis van afnemerindicatie en partij Gemeente Standaard en soortDienst PLAATSING_AFNEMERINDICATIE een afnemerindicatie geplaatst

!-- R1268_LT07 : Ingaand bericht wordt gearchiveerd
Then is er gearchiveerd met de volgende gegevens:
| veld                   | waarde                               |
| srt                    | 40                                   |
| richting               | 1                                    |
| admhnd                 | NULL                                 |
|-- data              -- | -- <wordt gecheckt in aparte stap> --|
| zendendepartij         | 32002                                |
| zendendesysteem        | BRP                                  |
| ontvangendepartij      | NULL                                 |
| referentienr           | 00000000-0000-0000-0000-999999991230 |
| crossreferentienr      | NULL                                 |
|-- tsverzending      -- | -- <wordt gecheckt in aparte stap> --|
|-- tsontv            -- | -- <wordt gecheckt in aparte stap> --|
| verwerkingswijze       | NULL                                 |
| rol                    | NULL                                 |
| srtsynchronisatie      | NULL                                 |
|-- levsautorisatie   -- | -- <wordt gecheckt in aparte stap> --|
|-- dienst            -- | -- <wordt gecheckt in aparte stap> --|
| verwerking             | NULL                                 |
| bijhouding             | NULL                                 |
| hoogstemeldingsniveau  | NULL                                 |

Then is het synchrone verzoek correct gearchiveerd
Then tijdstipverzending in bericht is correct gearchiveerd
Then tijdstipontvangst is actueel
Then leveringautorisatie is gelijk in archief

!-- R1997_LT03 Uitgaand bericht wordt gearchiveerd
!-- R1268_LT08
Then is er gearchiveerd met de volgende gegevens:
| veld                   | waarde                               |
| srt                    | 41                                   |
| richting               | 2                                    |
| admhnd                 | NULL                                 |
|-- data              -- | -- <wordt gecheckt in aparte stap> --|
| zendendepartij         | 2001                                 |
| zendendesysteem        | BRP                                  |
| ontvangendepartij      | 32002                                |
|-- referentienr      -- | -- <wordt gecheckt in aparte stap> --|
| crossreferentienr      | 00000000-0000-0000-0000-999999991230 |
| tsontv                 | NULL                                 |
| verwerkingswijze       | NULL                                 |
| rol                    | NULL                                 |
| srtsynchronisatie      | NULL                                 |
|-- levsautorisatie   -- | -- <wordt gecheckt in aparte stap> --|
| dienst                 | NULL                                 |
| verwerking             | 1                                    |
| bijhouding             | NULL                                 |
| hoogstemeldingsniveau  | 1                                    |

Then bestaat er een antwoordbericht voor referentie 00000000-0000-0000-0000-999999991230
Then referentienr is gelijk
Then tijdstipverzending in bericht is correct gearchiveerd
Then leveringautorisatie is gelijk in archief

!-- R1269_LT06 persoon vanuit inkomend verzoek niet archiveren in de ber.pers tabel.
Then bestaat er geen voorkomen in berpers tabel voor referentie 00000000-0000-0000-0000-999999991230 en srt lvg_synRegistreerAfnemerindicatie

!-- Then in autaut heeft select naam from autaut.levsautorisatie where id = (select levsautorisatie from ber.ber where referentienr = '00000000-0000-0000-0000-999999991230' and zendendepartij = '348') de volgende gegevens:
| !-- | veld | waarde                                               |
| !-- | naam | Geen pop.bep. levering op basis van afnemerindicatie |

!-- R1408_LT01: Persoon \ Afnemerindicatie.Standaard.Dienst inhoud wordt gevuld met de Dienst.ID (1)
!-- Van de 4 diensten is plaatsten afnemerindicatie de eerste, daardoor ID = 1, betekent dienstinhoud = plaatsen afnemerindicatie
Then in autaut heeft select srt from autaut.dienst where id = (select dienstinh from autaut.his_persafnemerindicatie where persafnemerindicatie=(select id from autaut.persafnemerindicatie where pers=(select id from kern.pers where bsn = '606417801'))) de volgende gegevens:
| veld | waarde |
| srt  | 3      |

!-- Archivering: R1270_LT10 voor responsebericht
Then is er gearchiveerd met de volgende gegevens:
| veld                     | waarde                                               |
| bsn                      | 606417801                                            |
| srt                      | 41                                                   |
| crossreferentienr        | 00000000-0000-0000-0000-999999991230                 |
| leveringsautorisatieNaam | Geen pop.bep. levering op basis van afnemerindicatie |


!-- R1612_LT01, R1995_LT01, R1616_LT02, R1619_LT06, R1620_LT03 & R1996_LT01 check protocollering

Then is er voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie en partij Gemeente Standaard geprotocolleerd met de volgende gegevens:
| veld                        | waarde                     |
| bsn                         | 606417801                  |
| soortDienst                 | Plaatsing afnemerindicatie |
| soortSynchronisatie         | Volledigbericht            |
| dataanvmaterieleperioderes  | 20160131                   |
| dateindematerieleperioderes | NULL                       |
| tsaanvformeleperioderes     | NULL                       |
| tseindeformeleperioderes    | NU                         |



Scenario: 1.2   Voor de persoon Jan wordt een afnemerindicatie verwijderd
                LT: R1409_LT01
                Verwacht resultaat:
                Dienstverval wordt vastgelegd Afnemerindicatie.Dienstverval wordt gevuld met DienstID)*
                Tijdstip vervallen invullen voor BRP Intern

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/Plaatsen_en_Verwijderen_AfnemerIndicatie/Requests/6._Verwijder_Afnmerindicatie_Story_4.1.xml

Then heeft het antwoordbericht verwerking Geslaagd

When alle berichten zijn geleverd
Then zijn er geen berichten ontvangen

!-- Controle op database verwijderen afnemerindicatie (dienstverval gevuld)
!-- R1409_LT01
Then in kern heeft select bsn from kern.pers where id=(select pers from autaut.persafnemerindicatie where id=(select pe.persafnemerindicatie from autaut.his_persafnemerindicatie pe where dienstverval is not null)) de volgende gegevens:
| veld | waarde    |
| bsn  | 606417801 |

!-- Controleer dat de dienstverval verwijst naar het id van de dienst id van verwijderen afnemerindicatie
Then in autaut heeft select srt from autaut.dienst where id = (select dienstverval from autaut.his_persafnemerindicatie where persafnemerindicatie = (select id from autaut.persafnemerindicatie where pers = (select id from kern.pers where bsn = '606417801'))) de volgende gegevens:
| veld | waarde |
| srt  | 20     |
