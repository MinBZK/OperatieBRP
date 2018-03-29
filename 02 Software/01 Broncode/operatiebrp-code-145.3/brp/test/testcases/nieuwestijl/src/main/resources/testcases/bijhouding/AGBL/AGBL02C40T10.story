Meta:
@status                 Klaar
@regels                 R1855
@usecase                UCS-BY.HG

Narrative: R1855 Land en gebied aanvang moet een waarde hebben bij aanvang van een H/GP

Scenario: Land en gebied aanvang code is niet gevuld
          LT: AGBL02C40T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGBL/AGBL-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AGBL/AGBL-002.xls

When voer een bijhouding uit AGBL02C40T10.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/AGBL/expected/AGBL02C40T10.txt

Then is in de database de persoon met bsn 480112745 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 195517441 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP






