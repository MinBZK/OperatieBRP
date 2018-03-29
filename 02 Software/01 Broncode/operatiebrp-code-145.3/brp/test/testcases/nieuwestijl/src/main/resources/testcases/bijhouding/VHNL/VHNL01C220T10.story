Meta:
@status                 Klaar
@sleutelwoorden         voltrekkingHuwelijkInNederland VHNL01C220T10
@usecase                UCS-BY.HG

Narrative: R2145  Geslachtsnaamstam is verplicht bij Niet-ingeschrevene of Onbekend Persoon

Scenario:   Geslachtsnaamstam leeg bij Onbekend Persoon
            LT: VHNL01C220T10



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls

When voer een bijhouding uit VHNL01C220T10.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL01C220T10.txt

Then lees persoon met anummer 1868196961 uit database en vergelijk met expected VHNL01C220T10-persoon1.xml











