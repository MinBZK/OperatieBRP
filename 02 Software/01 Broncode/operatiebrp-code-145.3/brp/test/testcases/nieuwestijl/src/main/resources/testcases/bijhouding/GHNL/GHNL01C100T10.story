Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R2146
@usecase                UCS-BY.HG

Narrative:
R2146 Land/gebied geboorte in Geboorte is verplicht

Scenario: R2146 Land/gebied geboorte leeg bij Onbekend Persoon
          LT: GHNL01C100T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL-Mila.xls

When voer een GBA bijhouding uit GHNL01C100T10.xml namens partij 'Migratievoorziening'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/GHNL/expected/GHNL01C100T10.txt

Then lees persoon met anummer 3528125729 uit database en vergelijk met expected GHNL01C100T10-persoon1.xml
