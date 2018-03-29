Meta:
@status                 Klaar
@regels                 R2159
@sleutelwoorden         voltrekkingHuwelijkInNederland,registratieNaamgebruik,TjieWah,AGNL01C880T10
@usecase                UCS-BY.HG

Narrative:
R2159 Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap, registratieNaamgebruik

Scenario: R2159 Persoon.Adellijke titel naamgebruik verwijst niet naar een stamgegeven in Adellijke titel.
          LT: AGNL01C880T10

Gemeente BRP 1

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-AGNL/Marjan.xls
Given enkel initiele vulling uit bestand /LO3PL-AGNL/Victor.xls

When voer een bijhouding uit AGNL01C880T10.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/AGNL/expected/AGNL01C880T10.txt

Then is in de database de persoon met bsn 221087849 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

