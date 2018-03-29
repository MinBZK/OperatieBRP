Meta:
@status             Klaar
@usecase            LV.1.PB, AL.1.VZ
@regels             R1614, R1615, R1616, R1617, R1618, R1619, R1620, R2236
@sleutelwoorden     Protocoleer bericht, Verzenden, Plaats Afnemerindicatie

Narrative:
Een protocollering wordt vastgelegd door:
Het vastleggen van een voorkomen van Leveringsaantekening, zoals beschreven in
R1614 - Vaststelling protocolleringsgegevens bij verstrekking op administratieve handeling en
R1615 - Vaststelling protocolleringsgegevens bij verstrekking door systeemactie.
R2236 - Het registeren van een voorkomen van Leveringsaantekening \ Persoon voor elke geleverde Persoon

            Libby BSN = 422531881
            Piet BSN = 159247913

Scenario:   1.1 Plaats afnemerindicatie op Libby (opbouw test voor 1.2)
            LT: R1613_LT02, R1615_LT01, R1616_LT02, R1617_LT02, R1618_LT03, R1619_LT06, R1620_LT03, R2236_LT03
            Verwacht resultaat: Afnemer indicatie geplaatst, correcte protocollering van vulbericht als gevolg van het plaatsen
            van de afnemerindicatie.

            Leveringsaantekening.Toegang leveringsautorisatie = Toegang leveringsautorisatie.ID (R1615_LT01)
            Leveringsaantekening.Dienst = Dienst.ID van de geleverde Dienst (R1615_LT01)
            Leveringsaantekening.Datum/tijd klaarzetten levering = Datum\tijd van klaar zetten bericht (R1615_LT01)
            Leveringsaantekening.Datum materieel selectie = 'leeg' (R1616_LT02)
            Leveringsaantekening.Datum aanvang materiële periode resultaat = afnemerindicatie.datum aanvang mat. per. (R1617_LT02)
            Leveringsaantekening.Datum einde materiële periode resultaat = 'Leeg' (R1618_LT03)
            Leveringsaantekening.Datum/tijd aanvang formele periode resultaat = 'leeg' (R1619_LT06)
            Leveringsaantekening.Datum/tijd einde formele periode resultaat = Datum\tijd van klaar zetten bericht (R1620_LT03)
            Leveringsaantekening.Administratieve handeling = 'Leeg' (R1615_LT01)
            Leveringsaantekening.Soort synchronisatie = 2 (volledig bericht)
            Leveringsaantekening.Scope patroon = 'leeg' (R1613_LT02)

            Persoon.Leveringsaantekening \ Persoon.Tijdstip laatste wijziging persoon = gevuld (Tijdstip verwerking adm. hand.) (R2236_LT03)

Given alle personen zijn verwijderd
Given personen uit bestanden:
|filenaam
|/LO3PL/Libby.xls
|/LO3PL/Piet.xls


Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/Plaatsen_en_Verwijderen_AfnemerIndicatie/Requests/Plaats_Afnemerindicatie_Libby.xml


Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

When alle berichten zijn geleverd

Then is er een volledigbericht ontvangen voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie

Then is er voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie en partij Gemeente Standaard geprotocolleerd met de volgende gegevens:
| veld                        | waarde                     |
| bsn                         | 422531881                  |
| dataanvmaterieleperioderes  | 20160131                   |
| dateindematerieleperioderes | NULL                       |
| tsaanvformeleperioderes     | NULL                       |
| tseindeformeleperioderes    | NU                         |
| admhnd                      | NULL                       |
| soortSynchronisatie         | Volledigbericht            |
| scopepatroon                | NULL                       |
| soortDienst                 | Plaatsing afnemerindicatie |

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/Plaatsen_en_Verwijderen_AfnemerIndicatie/Requests/Plaats_Afnemerindicatie_Piet.xml

Then heeft het antwoordbericht verwerking Geslaagd
And is het antwoordbericht xsd-valide

When alle berichten zijn geleverd

Then is er een volledigbericht ontvangen voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie

Then is er voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie en partij Gemeente Standaard geprotocolleerd met de volgende gegevens:
| veld                        | waarde                     |
| bsn                         | 159247913                  |
| dataanvmaterieleperioderes  | 20160207                   |
| dateindematerieleperioderes | NULL                       |
| tsaanvformeleperioderes     | NULL                       |
| tseindeformeleperioderes    | NU                         |
| admhnd                      | NULL                       |
| soortSynchronisatie         | Volledigbericht            |
| scopepatroon                | NULL                       |
| soortDienst                 | Plaatsing afnemerindicatie |

Scenario: 1.2   Huwelijk Libby en Piet, op beide een afnemerindicatie, dus beide leveren, dus beide protocolleren
                LT: R1613_LT02, R1614_LT02, R1616_LT02, R1617_LT08, R1618_LT03, R1619_LT06, R1620_LT03, R2236_LT02
                Verwacht Resultaat: Zowel Libby als Piet worden geprotocoleerd volgens de regels van R2236:
                Leveringsaantekening.Toegang leveringsautorisatie = Toegang leveringsautorisatie.ID (R1614)
                Leveringsaantekening.Dienst = Dienst.ID van de geleverde Dienst (R1614)
                Leveringsaantekening.Datum/tijd klaarzetten levering = Datum\tijd van klaar zetten bericht (R1614)
                Leveringsaantekening.Datum materieel selectie = 'leeg' (R1616)
                Leveringsaantekening.Datum aanvang materiële periode resultaat = 'leeg' (R1617)
                Leveringsaantekening.Datum einde materiële periode resultaat = 'leeg' (R1618)
                Leveringsaantekening.Datum/tijd aanvang formele periode resultaat = 'leeg' (R1619)
                Leveringsaantekening.Datum/tijd einde formele periode resultaat = Datum\tijd van klaar zetten bericht (R1620)
                Leveringsaantekening.Administratieve handeling = id van de administratieve handeling die geleverd wordt (R1614)
                Leveringsaantekening.Soort synchronisatie = 1 (mutatie bericht)
                Leveringsaantekening.Scope patroon = 'leeg' (R1613)

                Persoon.Leveringsaantekening \ Persoon.Tijdstip laatste wijziging persoon = gevuld (Tijdstip verwerking administratieve handeling) (R2236

!-- Step nodig om levautorisatie id in de cache te laden, anders gaat de controle op protocollering niet goed
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Standaard'

When voer een bijhouding uit /testcases/BRP_INTEGRATIE_TEST/IT_VHNL/VHNL04C10T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

When alle berichten zijn geleverd

Then is er een mutatiebericht ontvangen voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie

Then is de administratieve handeling voor bsn 422531881 correct geprotocolleerd
Then is de administratieve handeling voor bsn 159247913 correct geprotocolleerd

Then is er voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie en partij Gemeente Standaard geprotocolleerd met de volgende gegevens:
| veld                        | waarde                                        |
| bsn                         | 422531881                                     |
| dataanvmaterieleperioderes  | NULL                                          |
| dateindematerieleperioderes | NULL                                          |
| tsaanvformeleperioderes     | NULL                                          |
| tseindeformeleperioderes    | NU                                            |
| soortSynchronisatie         | Mutatiebericht                                |
| scopepatroon                | NULL                                          |
| soortDienst                 | Mutatielevering op basis van afnemerindicatie |
| dataanvmaterieleperiode     | 20160131                                      |

!-- R1617_LT02
Then is er voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie en partij Gemeente Standaard geprotocolleerd met de volgende gegevens:
| veld                        | waarde                                        |
| bsn                         | 159247913                                     |
| dataanvmaterieleperioderes  | NULL                                          |
| dateindematerieleperioderes | NULL                                          |
| tsaanvformeleperioderes     | NULL                                          |
| tseindeformeleperioderes    | NU                                            |
| soortSynchronisatie         | Mutatiebericht                                |
| scopepatroon                | NULL                                          |
| soortDienst                 | Mutatielevering op basis van afnemerindicatie |
| dataanvmaterieleperiode     | 20160207                                      |

Scenario:   2. Plaats afnemerindicatie op Libby zonder datum aanvang mat. per.
            LT: R1617_LT01
            Verwacht resultaat: Afnemer indicatie geplaatst, correcte protocollering van vulbericht als gevolg van het plaatsen
            van de afnemerindicatie.

            Leveringsaantekening.Toegang leveringsautorisatie = Toegang leveringsautorisatie.ID (R1615_LT01)
            Leveringsaantekening.Dienst = Dienst.ID van de geleverde Dienst (R1615_LT01)
            Leveringsaantekening.Datum/tijd klaarzetten levering = Datum\tijd van klaar zetten bericht (R1615_LT01)
            Leveringsaantekening.Datum materieel selectie = 'leeg' (R1616_LT02)
            Leveringsaantekening.Datum aanvang materiële periode resultaat = 'Leeg' (R1617_LT01)
            Leveringsaantekening.Datum einde materiële periode resultaat = 'leeg' (R1618_LT03)
            Leveringsaantekening.Datum/tijd aanvang formele periode resultaat = 'leeg' (R1619_LT06)
            Leveringsaantekening.Datum/tijd einde formele periode resultaat = Datum\tijd van klaar zetten bericht (R1620_LT03)
            Leveringsaantekening.Administratieve handeling = 'Leeg' (R1615_LT01)
            Leveringsaantekening.Soort synchronisatie = 2 (volledig bericht)
            Leveringsaantekening.Scope patroon = 'leeg' (R1613_LT02)

            Persoon.Leveringsaantekening \ Persoon.Tijdstip laatste wijziging persoon = gevuld (Tijdstip verwerking adm. hand.) (R2236_LT03)

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/Plaatsen_en_Verwijderen_AfnemerIndicatie/Requests/Plaats_Afnemerindicatie_Libby_Scenario_3.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

When alle berichten zijn geleverd

Then is er een volledigbericht ontvangen voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie

!-- Datum aanvang mat. per. niet gevuld bij protocollering als deze niet is gevuld in plaats afn. ind.

!-- R1617_LT01
!-- Story: ROOD-530
Then is er voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie en partij Gemeente Standaard geprotocolleerd met de volgende gegevens:
| veld                        | waarde                     |
| bsn                         | 422531881                  |
| dataanvmaterieleperioderes  | NULL                       |
| dateindematerieleperioderes | NULL                       |
| tsaanvformeleperioderes     | NULL                       |
| tseindeformeleperioderes    | NU                         |
| soortSynchronisatie         | Volledigbericht            |
| scopepatroon                | NULL                       |
| soortDienst                 | Plaatsing afnemerindicatie |
| dataanvmaterieleperiode     | NULL                       |
