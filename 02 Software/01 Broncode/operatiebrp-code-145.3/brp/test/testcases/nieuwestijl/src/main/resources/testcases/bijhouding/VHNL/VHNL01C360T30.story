Meta:
@status                 Klaar
@regels                 R1838
@usecase                UCS-BY.HG

Narrative: R1838 In een bijhouding moet ReferentieID verwijzen naar CommunicatieID

Scenario: Bij bronnen komt referentieid wel overeen met communicatieid maar type niet
          LT : VHNL01C360T30

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL/VHNL01C360T10-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C360T10-danny.xls

When voer een bijhouding uit VHNL01C360T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL01C360T30.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 696451657 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 941592777 niet als PARTNER betrokken bij een HUWELIJK

