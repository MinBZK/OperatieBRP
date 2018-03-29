Meta:
@status             Klaar
@sleutelwoorden     Selectie

Scenario:   1. Succesvolle selectierun met ingeschrevenen en checks op nadere bijhoudingsaard foutief niet leveren
            LT: R1539_LT23, R2666_LT01, R2692_LT01, R1262_LT29, R1264_LT27, R2056_LT27, R2678_LT01, R2723_LT02
            Uitwerking:
            Een selectie op geboortedatum 19600821 Kim, Kanye
            2 resultaten Kim en Kanye
            Status van Uitvoerbaar naar te controleren


Given alle selectietaken zijn verwijderd
Given een selectierun met de volgende selectie taken:
|datplanning   |status      | dienstSleutel
|vandaag       |Uitvoerbaar     | selectiebundel1

And alle selectie personen zijn verwijderd
Given selectie personen uit bestanden:
|filenaam
|/LO3PL/R2286/Kim.xls
|/LO3PL/R2286/Kanye.xls

When start selectie run
And wacht tot selectie run gestart
And wacht maximaal 2 minuten tot selectie run klaar

Then zijn de volgende resultaat files aanwezig voor selectietaak met dienstsleutel 'selectiebundel1' en datumuitvoer 'vandaag':
|type                         |aantal
|Resultaatset personen        |>=1
|Resultaatset totalen         |==1
|Controlebestand              |==1

!-- Controle op status wijziging R2692_LT01, controle op vullen TsREG voor nieuwe status en TsVerval voor oude status handmatig uitgevoerd
Then heeft de actuele status rij de volgende waarden:
|dienstSleutel|status|gewijzigddoor
|selectiebundel1|CONTROLEREN|Systeem


Scenario:   2. Run met taak met correcte dienst en verkeerde dienst

Given alle selectietaken zijn verwijderd
Given een selectierun met de volgende selectie taken:
|datplanning   |status      | dienstSleutel
|vandaag       |Uitvoerbaar     | selectiebundelfout1
|vandaag       |Uitvoerbaar     | selectiebundel1

When start selectie run
And wacht tot selectie run gestart
And wacht maximaal 2 minuten tot selectie run klaar

Then heeft de actuele status rij de volgende waarden:
|dienstSleutel|status|gewijzigddoor
|selectiebundel1|CONTROLEREN|Systeem
|selectiebundelfout1|AUTORISATIE_GEWIJZIGD|Systeem

Then heeft de actuele selectietaak rij de volgende waarden:
|dienstSleutel|datuitvoer
|selectiebundel1|vandaag
|selectiebundelfout1|
