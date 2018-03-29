Meta:
@epic               Goedpad Synchroniseer Persoon
@status             Klaar
@usecase            SA.0.SP

Narrative:
Synchroniseer persoon:
Als afnemer wil ik een afnemerverzoek tot het synchroniseren van een persoon afhandelen,
met dienst 'Mutatielevering op basis van doelbinding' of 'Mutatielevering op basis van afnemerindicatie',
zodat de afnemer voor de opgegeven Persoon een Volledig bericht ontvangt

Scenario:   1   Verzoek synchronisatie persoon, met dienst mutatielevering op basis van doelbinding en
                Datum einde volgen is gevuld met 1 dag > systeemdatum
            LT: R1262_LT01, R1268_LT03, R1268_LT04, R1269_LT04, R1270_LT06, R1270_LT09, R1335_LT02, R1339_LT02, R1347_LT01, R1613_LT02, R1615_LT01, R1982_LT01, R1587_LT05

            AL.1.AV Afhandelen Verzoek AL.1.AB:
            LT: R1268_LT03 - Verwacht resultaat: Ingaand bericht gearchiveerd volgens de regels en bsn geprotocolleerd
            LT: R1268_LT04 - Verwacht resultaat: Uitgaand bericht (synchroon) gearchiveerd
            LT: R1269_LT04 - Verwacht resultaat: Persoonsreferentie inkomend bericht wel opnemen in ber.pers tabel
            LT: R1270_LT06 - Verwacht resultaat: Persoonsreferentie synchroon response bericht niet opnemen staat nl niet in response bericht
            LT: R1270_LT09 - Verwacht resultaat: Persoonsreferentie lever bericht wel opgenomen in ber.pers tabel

            AL.1.AV Afhandelen Verzoek AL.1.XV: Geen testgevallen
            AL.1.AV Afhandelen Verzoek AL.1.AU: Geen testgevallen
            AL.1.AV Afhandelen Verzoek LV.1.AL:
            LT: R1262_LT01 - De gevraagde dienst synchroniseer persoon datum ingang kleiner dan systeemdatum, datum einde is groter dan systeemdatum
            SA.1.SP.CA Controleer Autorisatie:
            SA.1.SP.CI Controleer Inhoud:

            SA.1.SP Synchroniseer persoon:
            R1339_LT02 - Verwacht resultaat: Geen verstrekkingsbeperking aanwezig voor verzoekende afnemer, er wordt gevolg gegeven aan het verzoek
            R1347_LT01 - Verwacht resultaat: De afnemer moet voor de dienst Synchronisatie persoon ook een leveringsautorisatie hebben voor de dienst 'Mutatielevering op basis van doelbinding'

            LV.0.MB Maak BRP bericht LV.0.MB.VB: geen testgevallen

            SA.0.LM/SA.0.AA Attendering met plaatsen afnemerindicatie:
            R1335_LT02 - Verwacht resultaat: Geen afnemerindicatie geplaatst

            LV.1.PB Protocolleer bericht:
            R1613_LT02 - Verwacht resultaat: Zie R1615_LT01
            R1615_LT01 - Verwacht resultaat: Protocolleringsgegevens worden als volgt samengesteld:
                                             Leveringsaantekening.Toegang leveringsautorisatie = Toegang leveringsautorisatie.ID waarvoor verstrekt wordt
                                             Leveringsaantekening.Dienst = Dienst.ID van de geleverde Dienst (1)
                                             Leveringsaantekening.Datum/tijd klaarzetten levering = Datum\tijd systeem van ophalen gegevens(Persoon en Autorisatie) uit de database
                                             Leveringsaantekening.Datum materieel selectie = 'leeg'
                                             Leveringsaantekening.Datum aanvang materiële periode resultaat = R1617 - Afleiding Datum aanvang materiele periode resultaat
                                             Leveringsaantekening.Datum einde materiële periode resultaat = R1618 - Afleiding Datum einde materiele periode resultaat
                                             Leveringsaantekening.Datum/tijd aanvang formele periode resultaat = R1619 - Afleiding Datum/tijd aanvang formele periode resultaat
                                             Leveringsaantekening.Datum/tijd einde formele periode resultaat = R1620 - Afleiding Datum/tijd einde formele periode resultaat
                                             Leveringsaantekening.Administratieve handeling = 'leeg'
                                             Leveringsaantekening.Soort synchronisatie = "Volledigbericht"(2)
            R1982_LT01 - Verwacht resultaat: Bij een geldig verzoek via Synchroniseer persoon volgt een volledig bericht

            AL.1.LE Leveren AL.1.VE: Geen testgevallen

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

Given persoonsbeelden uit specials:specials/Jan_xls
!-- R1262_LT01
Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding



Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then is het verzoek correct gearchiveerd

!--  R1268_LT03
!-- Then ingaand bericht is gearchiveerd met referentienummer 00000000-0000-0000-0000-123456789123
!-- Then in ber heeft select q.* from ber.ber q left join ber.berpers qp on (q.id = qp.ber) left join kern.pers p on (qp.pers = p.id) where p
!-- .bsn=606417801 and q.zendendepartij=348 and q.srt=64 de volgende gegevens:
!-- | veld                  | waarde                               |
!-- | srt                   | 64                                   |
!-- | richting              | 1                                    |
!-- | admhnd                | NULL                                 |
!-- |-- data              --|-- <wordt gecheckt in aparte stap>  --|
!-- | antwoordop            | NULL                                 |
!-- | zendendepartij        | 348                                  |
!-- | zendendesysteem       | AFNEMERSYSTEEM                       |
!-- | ontvangendepartij     | NULL                                 |
!-- | ontvangendesysteem    | NULL                                 |
!-- | referentienr          | 00000000-0000-0000-0000-123456789123 |
!-- | crossreferentienr     | NULL                                 |
!-- |-- tsverzending      --|-- <wordt gecheckt in aparte stap>  --|
!-- |-- tsontv            --|-- <wordt gecheckt in aparte stap>  --|
!-- | verwerkingswijze      | NULL                                 |
!-- | rol                   | NULL                                 |
!-- | srtsynchronisatie     | NULL                                 |
!-- | -- levsautorisatie   --|-- <wordt gecheckt in aparte stap>  --|
!-- |-- dienst            --|-- <wordt gecheckt in aparte stap>  --|
!-- | verwerking            | NULL                                 |
!-- | bijhouding            | NULL                                 |
!-- | hoogstemeldingsniveau | NULL                                 |

!-- Then is het synchrone verzoek correct gearchiveerd
!-- Then tijdstipverzending in bericht is correct gearchiveerd
!-- Then tijdstipontvangst is actueel
!-- Then leveringautorisatie is gelijk in archief

!-- R1269_L04
!-- Then in ber heeft select kern.pers.bsn from ber.berpers left join ber.ber on (berpers.ber = ber.id) left join kern.pers on (berpers.pers = pers.id) where ber.referentienr='00000000-0000-0000-0000-123456789123' and ber.srt='64' de volgende gegevens:
!-- | veld      | waarde    |
!-- | bsn       | 606417801 |


Then heeft het antwoordbericht verwerking Geslaagd
Then heeft in het antwoordbericht 'zendendePartij' in 'stuurgegevens' de waarde '199903'
Then heeft in het antwoordbericht 'zendendeSysteem' in 'stuurgegevens' de waarde 'BRP'
Then is in antwoordbericht de aanwezigheid van 'referentienummer' in 'stuurgegevens' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'tijdstipVerzending' in 'stuurgegevens' nummer 1 ja

!-- R1982_LT01
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken

!--  R1268_LT04
!-- Then in ber heeft select * from ber.ber where crossreferentienr='00000000-0000-0000-0000-123456789123' and srt=65 de volgende gegevens:
!-- | veld                  | waarde                               |
!-- | srt                   | 65                                   |
!-- | richting              | 2                                    |
!-- | admhnd                | NULL                                 |
!-- |-- data              --|-- <wordt gecheckt in aparte stap>  --|
!-- |-- antwoordop        --|-- <wordt gecheckt in aparte stap>  --|
!-- | zendendepartij        | 2001                                 |
!-- | zendendesysteem       | BRP                                  |
!-- | ontvangendepartij     | NULL                                 |
!-- | ontvangendesysteem    | NULL                                 |
!-- |-- referentienr      --|-- <wordt gecheckt in aparte stap>  --|
!-- | crossreferentienr     | 00000000-0000-0000-0000-123456789123 |
!-- |-- tsverzending      --|-- <wordt gecheckt in aparte stapt> --|
!-- | tsontv                | NULL                                 |
!-- | verwerkingswijze      | NULL                                 |
!-- | rol                   | NULL                                 |
!-- | srtsynchronisatie     | NULL                                 |
!-- |-- levsautorisatie   --|-- <wordt gecheckt in aparte stap>  --|
!-- | dienst                | NULL                                 |
!-- | verwerking            | 1                                    |
!-- | bijhouding            | NULL                                 |
!-- | hoogstemeldingsniveau | 1                                    |
!-- Then controleer dat alle asynchroon ontvangen berichten correct gearchiveerd zijn
!-- Then bestaat er een antwoordbericht voor referentie 00000000-0000-0000-0000-123456789123
!-- Then referentienr is gelijk
!-- Then tijdstipverzending in bericht is correct gearchiveerd
!-- Then leveringautorisatie is gelijk in archief
!--  R1270_LT06
!-- Then bestaat er geen voorkomen in berpers tabel voor crossreferentie 00000000-0000-0000-0000-123456789123 en srt 65
!-- R1268_LT06
!-- Then in ber heeft select * from ber.ber where srt=23 de volgende gegevens:
!-- | veld                  | waarde                               |
!-- | srt                   | 23                                   |
!-- | richting              | 2                                    |
!-- | admhnd                | NULL                                 |
!-- |-- data              --|-- <wordt gecheckt in aparte stap>  --|
!-- | antwoordop            | NULL                                 |
!-- | zendendepartij        | 2001                                 |
!-- | zendendesysteem       | BRP                                  |
!-- | ontvangendepartij     | 348                                  |
!-- | ontvangendesysteem    | Leveringsysteem                      |
!-- |-- referentienr      --|-- <wordt gecheckt in aparte stap>  --|
!-- | crossreferentienr     | NULL                                 |
!-- |-- tsverzending      --|-- <wordt gecheckt in aparte stap>  --|
!-- | tsontv                | NULL                                 |
!-- | verwerkingswijze      | NULL                                 |
!-- | rol                   | 1                                    |
!-- | srtsynchronisatie     | 2                                    |
!-- |-- levsautorisatie   --|-- <wordt gecheckt in aparte stap>  --|
!-- |-- dienst            --|-- <wordt gecheckt in aparte stap>  --|
!-- | verwerking            | NULL                                 |
!-- | bijhouding            | NULL                                 |
!-- | hoogstemeldingsniveau | NULL                                 |
!-- Then controleer dat alle asynchroon ontvangen berichten correct gearchiveerd zijn
!-- Then referentienr is gelijk
!-- Then tijdstipverzending in bericht is correct gearchiveerd
!-- Then leveringautorisatie is gelijk in archief
!-- Then dienstid is gelijk in archief

!-- R1270_LT09
!-- Then in ber heeft select kern.pers.bsn from ber.berpers left join ber.ber on (berpers.ber = ber.id) left join kern.pers on (berpers.pers = pers.id)
!-- where ber.srt='23' de volgende gegevens:
!-- | veld      | waarde    |
!-- | bsn       | 606417801 |

!-- R1613_LT02, R1615_LT01
!-- Then is asynchroon bericht geprotocolleerd voor de toeganglevsautorisatie, tsklaarzettenlev en persoon 606417801 en soortdienst Synchronisatie
!-- persoon en soortSynchronisatie Volledigbericht NULL en dataanvangMaterieleperiode NULL en dateindematerieleperiode NULL en tsaanvformeleperioderes NU en tseindeformeleperioderes NU
