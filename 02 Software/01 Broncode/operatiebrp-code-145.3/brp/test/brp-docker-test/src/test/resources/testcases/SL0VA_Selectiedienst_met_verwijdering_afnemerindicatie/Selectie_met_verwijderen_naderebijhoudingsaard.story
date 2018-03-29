Meta:
@status             Klaar
@usecase            SL.0.PA
@sleutelwoorden     Selectie

Narrative:

Test voor het verwijderen vanuit selectie op een geldige afnemerindicatie bij een persoon met een naderebijhoudingsaard ongelijk aan acuteel, in dit geval gewist.

Scenario:   1. Plaats afnemerindicatie bij Persoon door afnemer Gemeente Standaard
            LT: R1266_LT03, R2591_LT05
            UC: SA.0.PA
            Verwacht resultaat: Afnemer indicatie geplaatst, vul bericht aan afnemer
            Selectie met verwijdering afnemerindicatie
            LT: R2591_LT05
            Verwacht resultaat:
            Persoon heeft een geldige afnemerindicatie, valt binnen de selectie.
            Persoon heeft een naderebijhoudingsaard gewist
            Geen afnmerindicatie verwijderen
            Geen volledig bericht

Given alle selectie personen zijn verwijderd
And selectiepersonen uit bestand /LO3PL/R2286/Kim.xls

Given verzoek voor leveringsautorisatie 'SelectieMetVerwijderingAfnInd' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/SL0VA_Selectiedienst_met_verwijdering_afnemerindicatie/Requests/3_Plaats_Afnemerindicatie_Kim_Gemeente_Standaard.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

When alle berichten zijn geleverd

Then is er een volledigbericht ontvangen voor leveringsautorisatie SelectieMetVerwijderingAfnInd
Then is er voor persoon met bsn 606417801 en leveringautorisatie SelectieMetVerwijderingAfnInd en partij Gemeente Standaard en soortDienst PLAATSING_AFNEMERINDICATIE een afnemerindicatie geplaatst

Given de selectiedatabase is aangepast met: update kern.pers set naderebijhaard=9 where bsn='606417801';
And de selectiedatabase is aangepast met: update kern.his_persbijhouding set naderebijhaard=9 where pers=(select id from kern.pers where bsn='606417801');

Given de database is aangepast met: update kern.pers set naderebijhaard=9 where bsn='606417801';
And de database is aangepast met: update kern.his_persbijhouding set naderebijhaard=9 where pers=(select id from kern.pers where bsn='606417801');

Given een selectierun met de volgende selectie taken:
|datplanning 	|status      | dienstSleutel
|vandaag           |Uitvoerbaar     | selectiemetverwijderingafnemerindicatie

When start selectie run

And wacht tot selectie run gestart
And wacht maximaal 2 minuten tot selectie run klaar

Then zijn de volgende resultaat files aanwezig voor selectietaak met dienstsleutel 'selectiemetverwijderingafnemerindicatie' en datumuitvoer 'vandaag':
|type                         |aantal
|Resultaatset totalen         |==1

!-- Voor gemeente Standaard is een actueel en geldig voorkomen van persoon.afnemerindicatie, afnemerindicatie is niet verwijderd
Then in autaut heeft select indag,dataanvmaterieleperiode,dateindevolgen from autaut.persafnemerindicatie where pers=(select id from kern.pers where bsn='606417801') AND levsautorisatie=(select id from autaut.levsautorisatie where naam='SelectieMetVerwijderingAfnInd') AND partij=32002 de volgende gegevens:
| veld                      | waarde |
| indag                     | true   |
| dataanvmaterieleperiode   | NULL   |
| dateindevolgen            | NULL   |

!-- Er is enkel een volledig bericht voor de aanvankelijk geplaatste afnemerindicatie.
When alle berichten zijn geleverd
Then zijn er per type bericht de volgende aantallen ontvangen:
| type                  | aantal
| volledigbericht       | 1
