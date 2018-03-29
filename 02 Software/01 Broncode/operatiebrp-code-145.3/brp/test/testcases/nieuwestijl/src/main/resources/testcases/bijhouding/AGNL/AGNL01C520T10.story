Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R2145
@sleutelwoorden         voltrekkingHuwelijkInNederland AGNL01C520T10
@usecase                UCS-BY.HG

Narrative:
R2145  Geslachtsnaamstam is verplicht bij Niet-ingeschrevene of Onbekend Persoon

Scenario:   R2145 Geslachtsnaamstam leeg bij Onbekend Persoon
            LT: AGNL01C520T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGNL/Marjan.xls

When voer een bijhouding uit AGNL01C520T10.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/AGNL/expected/AGNL01C520T10.txt
