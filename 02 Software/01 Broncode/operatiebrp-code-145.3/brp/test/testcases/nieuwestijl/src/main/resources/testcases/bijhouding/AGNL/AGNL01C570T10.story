Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R2171
@usecase                UCS-BY.HG

Narrative:
Datum geboorte in Geboorte is verplicht

Scenario: Datum geboorte in Geboorte is verplicht andere velden in Geboorte gevuld
          LT: AGNL01C570T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGNL/Marjan.xls

When voer een bijhouding uit AGNL01C570T10.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/AGNL/expected/AGNL01C570T10.txt

Then is in de database de persoon met bsn 221087849 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
