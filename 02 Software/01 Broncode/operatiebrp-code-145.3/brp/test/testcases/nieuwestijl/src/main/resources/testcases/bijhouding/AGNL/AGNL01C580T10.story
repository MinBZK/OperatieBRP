Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R2172
@usecase                UCS-BY.HG

Narrative:
R2172 Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: R2172 Geslachtsaanduiding in Geslachtsaanduiding is ingevuld
          LT: AGNL01C580T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGNL/Marjan.xls
Given enkel initiele vulling uit bestand /LO3PL-AGNL/Victor.xls

When voer een bijhouding uit AGNL01C580T10.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/AGNL/expected/AGNL01C580T10.txt
