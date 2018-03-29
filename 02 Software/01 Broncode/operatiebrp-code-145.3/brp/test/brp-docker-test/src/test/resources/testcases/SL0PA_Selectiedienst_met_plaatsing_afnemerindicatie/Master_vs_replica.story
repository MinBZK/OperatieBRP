Meta:
@status             Klaar
@sleutelwoorden     Selectie

Narrative:
Selectie van personen dient plaats te vinden op de Replica. Wanneer er voor de selectie een mutatie doorgevoerd wordt op de master,
gaat de selectie door op basis van de replica en wordt de afnemerindicatie geplaatst.


Scenario:   1.  Selectie van personen dient plaats te vinden op de Replica. Wanneer er voor de selectie een mutatie doorgevoerd wordt op de master,
                gaat de selectie door op basis van de replica en wordt de afnemerindicatie geplaatst.
                Verwacht resultaat:
                In het volledig bericht komen de gegevens terug zoals deze op de replica bekend waren
                Dus is het huwelijk nog niet verwerkt

Given alle selectie personen zijn verwijderd
Given selectie personen uit bestanden:
|filenaam
|/LO3PL/Piet.xls
|/LO3PL/Libby.xls

When voer een bijhouding uit /testcases/BRP_INTEGRATIE_TEST/IT_VHNL/VHNL04C10T10.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

Given een selectierun met de volgende selectie taken:
|datplanning | status              | dienstSleutel                           |
|vandaag     | Uitvoerbaar         | ToegangSelectieMetPlaatsenAfnemerindicatie/DienstSelectieMetPlaatsenAfnemerindicatie |

When start selectie run
And wacht tot selectie run gestart
And wacht maximaal 2 minuten tot selectie run klaar

Then zijn de volgende resultaat files aanwezig voor selectietaak met dienstsleutel 'ToegangSelectieMetPlaatsenAfnemerindicatie/DienstSelectieMetPlaatsenAfnemerindicatie' en datumuitvoer 'vandaag':
|type                         |aantal
|Resultaatset totalen         |==1

Then is er voor persoon met bsn 422531881 en leveringautorisatie SelectieMetPlaatsenAfnInd en partij Gemeente Standaard en soortDienst SELECTIE een afnemerindicatie geplaatst
When alle berichten zijn geleverd
Then is er een volledigbericht ontvangen voor leveringsautorisatie SelectieMetPlaatsenAfnInd
!-- Controle dat bijhouding huwelijk nog niet op de pl is verwerkt voor het plaatsen van de afnemerindicatie
Then heeft het bericht 0 groepen 'huwelijk'