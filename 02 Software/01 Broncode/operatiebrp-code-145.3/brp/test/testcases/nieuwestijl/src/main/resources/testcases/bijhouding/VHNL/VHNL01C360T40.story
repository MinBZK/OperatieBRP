Meta:
@status                 Klaar
@regels                 R1838
@usecase                UCS-BY.HG

Narrative: R1838 In een bijhouding moet ReferentieID verwijzen naar CommunicatieID

Scenario: Bij gedeblokkeerdeMelding komt referentieid wel overeen met communicatieid  maar type niet
            LT : VHNL01C360T40

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL/VHNL01C360T40-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C360T40-danny.xls

When voer een bijhouding uit VHNL01C360T40.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL01C360T40.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 761390601 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 470497257 wel als PARTNER betrokken bij een HUWELIJK

