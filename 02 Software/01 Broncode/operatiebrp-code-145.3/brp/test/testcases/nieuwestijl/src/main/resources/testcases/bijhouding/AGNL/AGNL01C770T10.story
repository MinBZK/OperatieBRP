Meta:
@auteur                 tjlee
@status                 Klaar
@sleutelwoorden         Foutief
@regels                 R2156
@usecase                UCS-BY.HG

Narrative:
R2156 Personen Marjan en Victor gaan trouwen, Adelijke titel niet in stamtabel

Scenario:   R2156 Adelijke titel komt niet voor in de stamtabel
            LT: AGNL01C770T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGNL/Marjan.xls
Given enkel initiele vulling uit bestand /LO3PL-AGNL/Victor.xls

When voer een bijhouding uit AGNL01C770T10.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/AGNL/expected/AGNL01C770T10.txt

Then is in de database de persoon met bsn 221087849 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 110477200 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
