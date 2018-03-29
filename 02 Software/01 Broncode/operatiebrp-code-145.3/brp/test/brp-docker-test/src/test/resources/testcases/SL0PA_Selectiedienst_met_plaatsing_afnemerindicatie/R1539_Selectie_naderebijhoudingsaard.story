Meta:
@status             Klaar
@sleutelwoorden     Selectie

Scenario:   1.  Nadere bijhoudingsaard foutief
                LT: R1539_LT25
                Verwacht resultaat:
                - geen afnemerindicatie geplaatst


Given alle selectie personen zijn verwijderd
!-- Nadere bijhouding foutief mag niet geleverd worden R1539
And selectiepersonen uit bestand /LO3PL/Jan_naderebijhoudingsaard_fout.xls

Given een selectierun met de volgende selectie taken:
|datplanning 	|status      | dienstSleutel
|vandaag       |Uitvoerbaar | ToegangSelectieMetPlaatsenVolledigBericht/DienstSelectieMetPlaatsenVolledigBericht

When start selectie run
And wacht tot selectie run gestart
And wacht maximaal 2 minuten tot selectie run klaar

Then is er voor persoon met bsn 606417801 en leveringautorisatie SelectieMetPlaatsingAfnemerindicatieEnVolledigBericht en partij Gemeente Standaard geen afnemerindicatie aanwezig

When alle berichten zijn geleverd
Then zijn er geen berichten ontvangen

Scenario:   2.  Nadere bijhoudingsaard overleden
                LT: R1539_LT24
                Verwacht resultaat:
                - Afnemerindicatie geplaatst


Given alle selectie personen zijn verwijderd

And selectiepersonen uit bestand /LO3PL/Jan_naderebijhoudingsaard_overleden.xls

Given een selectierun met de volgende selectie taken:
|datplanning 	|status      | dienstSleutel
|vandaag       |Uitvoerbaar | ToegangSelectieMetPlaatsenVolledigBericht/DienstSelectieMetPlaatsenVolledigBericht

When start selectie run
And wacht tot selectie run gestart
And wacht maximaal 2 minuten tot selectie run klaar

Then is er voor persoon met bsn 951688777 en leveringautorisatie SelectieMetPlaatsingAfnemerindicatieEnVolledigBericht en partij Gemeente Standaard en soortDienst SELECTIE een afnemerindicatie geplaatst

When alle berichten zijn geleverd

Then is er een volledigbericht ontvangen voor leveringsautorisatie SelectieMetPlaatsingAfnemerindicatieEnVolledigBericht
