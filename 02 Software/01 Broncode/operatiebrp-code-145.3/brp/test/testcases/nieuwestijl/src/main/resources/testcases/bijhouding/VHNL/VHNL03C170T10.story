Meta:
@auteur                 jozon
@status                 Klaar
@regels                 R2146
@sleutelwoorden         voltrekkingHuwelijkInNederland, VHNL03C170T10
@usecase                UCS-BY.HG

Narrative:
Land-gebied geboorte in Geboorte is verplicht

Scenario: Land of gebied geboorte in geboorte is leeg
          LT: VHNL03C170T10



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls

When voer een bijhouding uit VHNL03C170T10.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL03C170T10.txt

Then is in de database de persoon met bsn 422531881 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 1868196961 uit database en vergelijk met expected VHNL03C170T10-persoon1.xml






