Meta:
@status             Klaar
@usecase            SA.0.AT, SA.0.LM, LV.1.PB
@sleutelwoorden     Attendering, Lever mutaties
@regels             ATTARCH

Narrative:
Aanmaken volledigbericht.
Indien door een administratieve handeling één of meerdere personen in de doelgroep van een abonnement
met een geldige dienst Attendering van de afnemer vallen en er wordt voor die persoon of personen voldaan aan het attenderingscriterium,
dan wordt een volledig bericht voor die persoon of personen aangemaakt.

Attendering met plaatsing 2 maal plaatsen met check of dit R1402 oplevert wordt ook gedaan in
story testcases/Plaatsen_en_Verwijderen_AfnemerIndicatie/1_Plaats_Verwijder_Afnemerindicatie.story

Scenario: 1.    Er wordt een huwelijk tussen 2 personen voltrokken via een BRP bijhouding, waarbij de personen aan het attenderingscriterium en de pop. bep. voldoen
                LT: R1268_LT10, R1614_LT01, R1988_LT01
                Verwacht resultaat: zie hieronder

                LV.1.PB:
                Volledig bericht door administratieve handeling
                LT: R1613_LT02, R1616_LT02, R1617_LT08, R1618_LT03, R1619_LT06, R1620_LT03, R2236_LT02
                Verwacht resultaat:
                Leveringsaantekening.Toegang leveringsautorisatie = Toegang leveringsautorisatie.ID (R1614)
                Leveringsaantekening.Dienst = Dienst.ID van de geleverde Dienst (R1614)
                Leveringsaantekening.Datum/tijd klaarzetten levering = Datum\tijd van klaar zetten bericht (R1614)
                Leveringsaantekening.Datum materieel selectie = 'leeg' (R1616)
                Leveringsaantekening.Datum aanvang materiële periode resultaat = 'leeg' (R1617)
                Leveringsaantekening.Datum einde materiële periode resultaat = 'leeg' (R1618)
                Leveringsaantekening.Datum/tijd aanvang formele periode resultaat = 'leeg' (R1619)
                Leveringsaantekening.Datum/tijd einde formele periode resultaat = Datum\tijd van klaar zetten bericht (R1620)
                Leveringsaantekening.Administratieve handeling = id van de administratieve handeling die geleverd wordt (R1614)
                Leveringsaantekening.Soort synchronisatie = 2 (volledig bericht)
                Leveringsaantekening.Scope patroon = 'leeg' (R1613)

                Persoon.Leveringsaantekening \ Persoon.Tijdstip laatste wijziging persoon = gevuld (Tijdstip verwerking administratieve handeling) (R2236)
                AL.1.AB - Archiveer bericht
                Uitgaand bericht, syn_verwerk_persoon
                LT: R1268_LT10
                Verwacht resultaat: archivering bericht

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls
Given enkel initiele vulling uit bestand /LO3PL/Piet.xls

When voer een bijhouding uit /testcases/BRP_INTEGRATIE_TEST/IT_VHNL/VHNL04C10T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

!-- Via een brp bijhouding wordt een huwelijk voltrokken. Hierdoor dient er geattendeerd te worden op GEWIJZIGD(HUWELIJKEN(oud), HUWELIJKEN(nieuw), [datum_aanvang])
When alle 5 berichten zijn geleverd

Then is er een volledigbericht ontvangen voor leveringsautorisatie Attendering met pop bep en attenderingscriterium

!-- R1988_LT01
Then in kern heeft select statuslev from kern.admhnd where partij=(select id from kern.partij where naam='Gemeente BRP 1') and tslev is not null de volgende gegevens:
| veld      | waarde |
| statuslev | 4   |

!-- R1268_LT10 Archivering van asynchrone volledig bericht
Then is er gearchiveerd met de volgende gegevens:
| veld                  | waarde          |
| bsn                   | 159247913       |
| srt                   | 23              |
| richting              | 2               |
| zendendepartij        | 2001            |
| zendendesysteem       | BRP             |
| ontvangendepartij     | 32002           |
| tsontv                | NULL            |
| verwerkingswijze      | NULL            |
| rol                   | 1               |
| srtsynchronisatie     | 2               |
| verwerking            | NULL            |
| bijhouding            | NULL            |
| hoogstemeldingsniveau | NULL            |
| crossreferentienr     | NULL            |

!-- Archivering van het bijhoudingsplan
Then is er gearchiveerd met de volgende gegevens:
| veld                  | waarde            |
| ontvangendepartij     | 27021             |
| srtsynchronisatie     | null              |
| hoogstemeldingsniveau | null              |
| crossreferentienr     | null              |
| rol                   | null              |
| verwerking            | null              |
| levsautorisatie       | null              |
| srt                   | 144               |
| zendendesysteem       | BRP               |
| dienst                | null              |
| bijhouding            | null              |
| richting              | 2                 |
| zendendepartij        | 3                 |
| tsontv                | null              |

Then referentienr is gelijk
Then tijdstipverzending in bericht is correct gearchiveerd
Then dienstid is gelijk in archief
Then leveringautorisatie is gelijk in archief

!-- Controle op protocollering
!-- R1614_LT01, R1616_LT02, R1617_LT08, R1618_LT03, R1619_LT06, R1620_LT03, R2236_LT02

Then is de administratieve handeling voor bsn 159247913 correct geprotocolleerd

Then is er voor leveringsautorisatie Attendering met pop bep en attenderingscriterium en partij Gemeente Standaard geprotocolleerd met de volgende gegevens:
| veld                        | waarde          |
| bsn                         | 159247913       |
| soortDienst                 | Attendering     |
| soortSynchronisatie         | Volledigbericht |
| dataanvmaterieleperioderes  | NULL            |
| dateindematerieleperioderes | NULL            |
| tsaanvformeleperioderes     | NULL            |
| tseindeformeleperioderes    | NU              |

Scenario: 2.    Meerdere attenderingsdiensten aanwezig die een afnemerindicatie plaatsen bij de persoon
                LT:
                Verwacht resultaat: meerdere afnemerindicaties aanwezig bij persoon

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls
Given enkel initiele vulling uit bestand /LO3PL/Piet.xls

When voer een bijhouding uit /testcases/BRP_INTEGRATIE_TEST/IT_VHNL/VHNL04C10T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

When alle berichten zijn geleverd

Then is er een volledigbericht ontvangen voor leveringsautorisatie Attendering met plaatsing autorisatie 1
Then is er een volledigbericht ontvangen voor leveringsautorisatie Attendering met plaatsing autorisatie 2
