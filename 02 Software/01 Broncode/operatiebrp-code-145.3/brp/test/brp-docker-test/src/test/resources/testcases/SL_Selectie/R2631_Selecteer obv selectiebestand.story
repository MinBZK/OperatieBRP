Meta:
@status             Klaar
@sleutelwoorden     Selectie

Scenario:   1. Succesvolle selectierun met selectiebestand
            Uitwerking:
            Een selectie op geboortedatum 19600821 Kim, Kanye
            1 resultaten : Kim (Alleen bsn van Kim zit in selectielijst)


Given een selectierun met de volgende selectie taken:
|datplanning   |status      | indsellijstgebruiken | dienstSleutel
|vandaag       |Uitvoerbaar     | true | selectiebundel1

Given selectielijsten per dienst:
|soortIdentificatie           | identificatienummers       | dienstSleutel
|Burgerservicenummer| 606417801 , 123456789  | selectiebundel1

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
|Resultaatset personen        |==1
|Resultaatset totalen         |==1
