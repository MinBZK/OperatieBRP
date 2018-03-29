Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R2146
@usecase                UCS-BY.HG

Narrative:
Land-gebied geboorte in Geboorte is verplicht

Scenario: Land of gebied geboorte in geboorte is leeg
          LT: AGNL01C530T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGNL/Marjan.xls

When voer een bijhouding uit AGNL01C530T10.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/AGNL/expected/AGNL01C530T10.txt

Then is in de database de persoon met bsn 221087849 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
