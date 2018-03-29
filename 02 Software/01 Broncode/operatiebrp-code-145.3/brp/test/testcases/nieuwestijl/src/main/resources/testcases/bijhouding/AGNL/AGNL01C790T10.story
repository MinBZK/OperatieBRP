Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R2166
@sleutelwoorden         Geslaagd, AGNL01C790T10
@usecase                UCS-BY.HG

Narrative:
R2166 Stam in Persoon.Geslachtsnaamcomponent niet aanwezig

Scenario:   R2166 Personen Marjan en Victor gaan trouwen, stam in Persoon.Geslachtsnaamcomponent niet aanwezig
            LT: AGNL01C790T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGNL/Marjan.xls
Given enkel initiele vulling uit bestand /LO3PL-AGNL/Victor.xls

When voer een bijhouding uit AGNL01C790T10.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/AGNL/expected/AGNL01C790T10.txt
