Meta:
@status             Klaar
@usecase            SL.0.PA
@sleutelwoorden     Selectie

Narrative:

Test voor het plaatsen vanuit selectie op een vervallen afnemerindicatie

Scenario:   1. Plaats afnemerindicatie bij Persoon door afnemer Gemeente Standaard
            LT: R1266_LT03
            UC: SA.0.PA
            Verwacht resultaat: Afnemer indicatie geplaatst, vul bericht aan afnemer


Given alle selectie personen zijn verwijderd
Given selectiepersonen uit bestand /LO3PL/Anne_met_Historie.xls

Given verzoek voor leveringsautorisatie 'SelectieMetVerwijderingAfnInd' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/SL0VA_Selectiedienst_met_verwijdering_afnemerindicatie/Requests/1_Plaats_Verwijder_Afnemerindicatie_scenario_1.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

When alle berichten zijn geleverd

Then is er een volledigbericht ontvangen voor leveringsautorisatie SelectieMetVerwijderingAfnInd

Then is er voor persoon met bsn 590984809 en leveringautorisatie SelectieMetVerwijderingAfnInd en partij Gemeente Standaard en soortDienst PLAATSING_AFNEMERINDICATIE een afnemerindicatie geplaatst

Scenario:   2. Verwijder afnemerindicatie bij Persoon
            LT: R1266_LT03
            UC: SA.0.VA
            Verwacht resultaat: Afnemer indicatie bij persoon voor partij verwijderd, geen vul bericht aan afnemer

Given verzoek voor leveringsautorisatie 'SelectieMetVerwijderingAfnInd' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/SL0VA_Selectiedienst_met_verwijdering_afnemerindicatie/Requests/1_Plaats_Verwijder_Afnemerindicatie_scenario_2.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

When alle berichten zijn geleverd
Then zijn er geen berichten ontvangen

Scenario:   3.  Selectie met verwijdering afnemerindicatie
                LT: R2591_LT04
                Verwacht resultaat:
                Persoon heeft een vervallen afnemerindicatie, valt binnen de selectie.
                Geen afnemerindicatie verwijderd
                Geen volledig bericht

Given een selectierun met de volgende selectie taken:
|datplanning 	|status      | dienstSleutel
|vandaag           |Uitvoerbaar     | selectiemetverwijderingafnemerindicatie

When start selectie run
And wacht tot selectie run gestart
And wacht maximaal 2 minuten tot selectie run klaar

Then zijn de volgende resultaat files aanwezig voor selectietaak met dienstsleutel 'selectiemetverwijderingafnemerindicatie' en datumuitvoer 'vandaag':
|type                         |aantal
|Resultaatset totalen         |==1

!-- Voor gemeente Standaard is geen actueel en geldig voorkomen van persoon.afnemerindicatie
Then in brp heeft select indag,dataanvmaterieleperiode,dateindevolgen from autaut.persafnemerindicatie where pers=(select id from kern.pers where bsn='590984809') AND levsautorisatie=(select id from autaut.levsautorisatie where naam='SelectieMetVerwijderingAfnInd') AND partij=32002 de volgende gegevens:
| veld                      | waarde |
| indag                     | false  |
| dataanvmaterieleperiode   | NULL   |
| dateindevolgen            | NULL   |

When alle berichten zijn geleverd
Then zijn er geen berichten ontvangen





