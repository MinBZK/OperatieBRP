Meta:
@status                 Klaar
@regels                 R2146
@usecase                UCS-BY.HG
@sleutelwoorden         Foutief

Narrative:
Land of Gebied geboorte in geboorte is verplicht

Scenario: Land-gebied in geboorte wordt leeg gelaten
          LT: GHNL03C70T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL-Mila.xls

When voer een GBA bijhouding uit GHNL03C70T10.xml namens partij 'Migratievoorziening'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/GHNL/expected/GHNL03C70T10.txt

Then is in de database de persoon met bsn 956803593 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 189975945 niet als PARTNER betrokken bij een HUWELIJK




