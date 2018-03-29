Meta:
@auteur                 tjlee
@regels                 R2157
@status                 Klaar
@sleutelwoorden         Geslaagd, AGNL01C780T10
@usecase                UCS-BY.HG

Narrative:
R2157 Predicaat titel komt niet voor in de stamtabel

Scenario:   R2157 Personen Marjan en Victor gaan trouwen, Predicaat titel niet in stamtabel
            LT: AGNL01C780T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGNL/Marjan.xls
Given enkel initiele vulling uit bestand /LO3PL-AGNL/Victor.xls

When voer een bijhouding uit AGNL01C780T10.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/AGNL/expected/AGNL01C780T10.txt

Then is in de database de persoon met bsn 221087849 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 110477200 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
