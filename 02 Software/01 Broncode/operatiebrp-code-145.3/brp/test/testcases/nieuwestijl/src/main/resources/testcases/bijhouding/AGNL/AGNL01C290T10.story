Meta:
@auteur                 fuman
@status                 Klaar
@regels                 R1854
@usecase                UCS-BY.HG

Narrative:
R1854 Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: R1854 De Datum aanvang is niet gevuld bij huwelijk
          LT: AGNL01C290T10



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGNL/Victor.xls

When voer een bijhouding uit AGNL01C290T10.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/AGNL/expected/AGNL01C290T10.txt


