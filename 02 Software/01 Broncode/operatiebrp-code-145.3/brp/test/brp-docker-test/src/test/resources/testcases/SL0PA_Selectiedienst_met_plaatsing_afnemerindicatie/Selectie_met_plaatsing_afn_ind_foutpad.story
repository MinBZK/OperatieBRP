Meta:
@status             Klaar
@sleutelwoorden     Selectie met plaatsing afnemerindicatie

Narrative:

Indien bij de Dienst waarvoor geldt dat Dienst.Soort selectie is gelijk aan 'Selectiedienst met plaatsing afnemerindicatie'
het plaatsen van de afnemerindicatie mislukt, dient dit in de functionele logging te worden opgenomen.

Scenario:   1. Selectie met plaatsing afnemerindicatie foutpad
            LT: R2593_LT01, R2588_LT03
            Uitwerking:
            Persoon heeft al een afnmerindicatie op partij en leveringsautorisatie
            Persoon wordt wel opgenomen in resultaatset totalen
            Geen afnemerindicatie geplaatst
            R2593 - Log regel weggeschreven (met melding van R1402 reeds bestaande afnemerindicatie)
            R2588 - plaats afnemerindicatie na selectie foutpad


Given een selectierun met de volgende selectie taken:
|datplanning 	|status      | dienstSleutel
|vandaag       |Uitvoerbaar | ToegangSelectieMetPlaatsenVolledigBericht/DienstSelectieMetPlaatsenVolledigBericht

And alle selectie personen zijn verwijderd
And selectiepersonen uit bestand /LO3PL/R2286/Kim.xls

!-- afnemerindicatie plaatsen voor Kim met gelijke partij en leveringsautoristatie
Given verzoek voor leveringsautorisatie 'SelectieMetPlaatsingAfnemerindicatieEnVolledigBericht' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/SL0PA_Selectiedienst_met_plaatsing_afnemerindicatie/Requests/3_Plaats_Afnemerindicatie_Kim_Gemeente_Standaard.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

When start selectie run
And wacht tot selectie run gestart
And wacht maximaal 2 minuten tot selectie run klaar

Then zijn de volgende resultaat files aanwezig voor selectietaak met dienstsleutel 'ToegangSelectieMetPlaatsenVolledigBericht/DienstSelectieMetPlaatsenVolledigBericht' en datumuitvoer 'vandaag':
|type                         |aantal
|Resultaatset totalen         |==1


Then is er voor persoon met bsn 606417801 en leveringautorisatie SelectieMetPlaatsingAfnemerindicatieEnVolledigBericht en partij Gemeente Standaard en soortDienst PLAATSING_AFNEMERINDICATIE een afnemerindicatie geplaatst
Then is er een logregel gelogd met regel R1402 in container SELECTIE_AFNEMERINDICATIE
