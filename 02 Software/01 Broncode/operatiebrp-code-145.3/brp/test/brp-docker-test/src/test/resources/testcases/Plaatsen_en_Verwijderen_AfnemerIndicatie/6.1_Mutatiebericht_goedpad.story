Meta:
@status             Klaar
@usecase            LV.1.PB, AL.1.VZ
@regels             R1270
@sleutelwoorden     Archiveer bericht, Verzenden

Narrative:
Als beheerder van het BRP systeem
wil ik dat bij uitgaande berichten de personen in het bericht (excl betrokkenen) worden gearchiveerd
zodat kan worden nagegaan voor welke personen er is geleverd.

Scenario:   1.1  Plaats afnemerindicatie op Libby (preconditie 1.2)
Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_BRP_1.txt
Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls
Given enkel initiele vulling uit bestand /LO3PL/Piet.xls

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/Plaatsen_en_Verwijderen_AfnemerIndicatie/Requests/8._Plaats_afnemerindicatie_Story_6.1.xml


Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide


Scenario:   1.2  Huwelijk voor Libby zorgt voor een mutatiebericht
            LT: R1270_LT13, R1268_LT10
            Verwacht resultaat:
            Persoon in ber.berpers tabel weggeschreven na uitgaand mutatiebericht.

!-- bijhouding huwelijk
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Standaard'
When voer een bijhouding uit /testcases/BRP_INTEGRATIE_TEST/IT_VHNL/VHNL04C10T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

When alle berichten zijn geleverd
Then is er een mutatiebericht ontvangen voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie

!-- R1270_LT13, archivering van persoon in mutatiebericht
Then is er gearchiveerd met de volgende gegevens:
| veld         | waarde                                        |
| bsn          | 422531881                                     |
| soortDienst  | Mutatielevering op basis van afnemerindicatie |
| berichtSoort | lvg_synVerwerkPersoon                         |

And is de administratieve handeling voor persoon 422531881 correct gearchiveerd
