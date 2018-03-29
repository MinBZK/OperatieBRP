Meta:
@status                 Klaar
@jiraIssue              ROOD-969
@sleutelwoorden         Geslaagd
@usecase                UCS-BY.HG

Narrative:
Integratie test om te bepalen dat een bijhouding van het type voltrekking huwelijk in nederland een mutatiebericht levert aan BRP Afnemers

Scenario:   1. Er wordt een huwelijk tussen 2 ingezetenen geregistreerd, er wordt een mutatie levering verzonden aan de geautoriseerde afnemer(s)
            LT: R2062_LT01, R1268_LT11, VHNL04C10T10
            Verwacht resultaat: Notificatie bericht voor de gemeente die als bijhoudingspartij geldt voor de personen
                                1 Mutatielevering voor 2 personen Libby en Piet, geen naamsgebruik of andere nevenactiviteiten.


Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls
Given enkel initiele vulling uit bestand /LO3PL/Piet.xls

When voer een bijhouding uit /testcases/BRP_INTEGRATIE_TEST/IT_VHNL/VHNL04C10T10.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

!-- Bijhouding wordt ingestuurd door Gemeente BRP 1
!-- Bijhoudingspartij bij beide personen = Gemeente BRP 3 (Code = 507022)
!-- Gemeente aanvang code voor huwelijk = 7112 ("Gemeente BRP 1")
!-- Dus moet er een notificatie verzonden worden aan Gemeente BRP 3 (omdat de bijhoudingspartij voor beide personen anders is dan de gemeente waarin het huwelijk is voltrokken)


!-- Ophalen van de mutatie leveringen nav het huwelijk
When alle berichten zijn geleverd

Then zijn er per type bericht de volgende aantallen ontvangen:
| type                  | aantal
| volledigbericht       | 1
| mutatiebericht        | 1
| notificatiebericht    | 2


Then is er een mutatiebericht ontvangen voor partij Gemeente Standaard en voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding
And is het ontvangen bericht voor expressie //brp:lvg_synVerwerkPersoon gelijk aan /testcases/BRP_INTEGRATIE_TEST/IT_VHNL/Expected_MutatieBerichten/expected_scenario_1.xml
And is er een volledigbericht ontvangen voor leveringsautorisatie Attendering met plaatsing en expressies

!-- Regressie controle op de bijhoudingsnotificatie
And is er een notificatiebericht ontvangen voor partij Gemeente BRP 3
And is het ontvangen bericht voor expressie //brp:bhg_sysVerwerkBijhoudingsplan gelijk aan /testcases/BRP_INTEGRATIE_TEST/IT_VHNL/Expected_MutatieBerichten/expected_bijh_not_scenario_1.xml

!-- Controle op de archivering van het notificatie bericht
Then is er gearchiveerd met de volgende gegevens:
| veld                   | waarde                               |
| srt                    | 144                                  |
| richting               | 2                                    |
| zendendepartij         | 3                                    |
| zendendesysteem        | BRP                                  |
| ontvangendepartij      | 27021                               |
| tsontv                 | NULL                                 |
| verwerkingswijze       | 1                                    |
| rol                    | NULL                                 |
| srtsynchronisatie      | NULL                                 |
| levsautorisatie        | NULL                                 |
| dienst                 | NULL                                 |
| verwerking             | NULL                                 |
| bijhouding             | NULL                                 |
| hoogstemeldingsniveau  | NULL                                 |


Scenario:   2. Er wordt een huwelijk tussen 2 ingezetenen geregistreerd met nevenactie registratieGeslachtsnaam, er wordt een mutatie levering verzonden aan de geautoriseerde afnemer(s)
            LT: VHNL05C10T10
            Verwacht resultaat: 1 mutatiebericht voor 2 personen
            Bij Libby de toevoeging geslachtsnaamstam Jansen en veval geslachtsnaamstam Thatcher (naamswijziging)
            Libby trouwt met Piet Jansen, en krijgt de naam Jansen-Jansen
            Bij Pet Jansen wordt de naam Jansen-Jansen (piet is een vrouw) en heeft naamgebruik V.


Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C30T10-Libby.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C30T10-Piet.xls

When voer een bijhouding uit /testcases/BRP_INTEGRATIE_TEST/IT_VHNL/VHNL05C10T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

When alle berichten zijn geleverd

Then is er een mutatiebericht ontvangen voor partij Gemeente Standaard en voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding
And is het ontvangen bericht voor expressie //brp:lvg_synVerwerkPersoon gelijk aan /testcases/BRP_INTEGRATIE_TEST/IT_VHNL/Expected_MutatieBerichten/expected_scenario_2.xml


Scenario: 3. Er wordt een huwelijk tussen 2 ingezetenen geregistreerd met nevenactie registratieNaamgebruik, er wordt een mutatie levering verzonden aan de geautoriseerde afnemer(s)
          Naamgebruik van Danny wordt meegegeven in het bijhoudings bericht, omdat het een expliciete bijhouding is wordt hiervan wel een toevoeging en verval van aangemaakt ondankt
          dat het dezelfde naam blijft. Naamgebruik wordt dus ook geleverd.
          LT: VHNL06C10T10

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL/VHNL06C10-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL06C10-danny.xls

When voer een bijhouding uit /testcases/BRP_INTEGRATIE_TEST/IT_VHNL/VHNL06C10T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

When alle berichten zijn geleverd

Then is er een mutatiebericht ontvangen voor partij Gemeente Standaard en voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding
And is het ontvangen bericht voor expressie //brp:lvg_synVerwerkPersoon gelijk aan /testcases/BRP_INTEGRATIE_TEST/IT_VHNL/Expected_MutatieBerichten/expected_scenario_3.xml
