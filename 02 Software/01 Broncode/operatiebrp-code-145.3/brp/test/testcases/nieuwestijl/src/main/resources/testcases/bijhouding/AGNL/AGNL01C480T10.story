Meta:
@status                 Klaar
@regels                 R1569
@usecase                UCS-BY.HG

Narrative:
R1569 Geslachtsaanduiding staat niet in de stamtabel

Scenario:   R1569 De geslachtsaanduiding staat niet in de stamtabel
            LT: AGNL01C480T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGNL/Marjan.xls

When voer een bijhouding uit AGNL01C480T10.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/AGNL/expected/AGNL01C480T10.txt

Then is in de database de persoon met bsn 221087849 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
