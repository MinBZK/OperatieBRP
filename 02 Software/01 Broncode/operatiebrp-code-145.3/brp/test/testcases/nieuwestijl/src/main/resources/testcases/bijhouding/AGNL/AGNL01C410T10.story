Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R2039
@usecase                UCS-BY.HG

Narrative:
R2039 Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: R2039 Woonplaatsnaam aanvang relatie mag alleen gevuld zijn als ook Gemeente gevuld is
          LT: AGNL01C410T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGNL/Victor.xls
Given enkel initiele vulling uit bestand /LO3PL-AGNL/Marjan.xls

When voer een bijhouding uit AGNL01C410T10.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/AGNL/expected/AGNL01C410T10.txt
