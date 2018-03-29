Meta:
@status                 Klaar
@regels                 R1839
@usecase                UCS-BY.HG

Narrative: R1839 Gedeblokkeerde meldingen moeten in verwerking daadwerkelijk optreden

Scenario: Opgegeven gedeblokkeerde melding treedt op met verkeerd referentieiID
          LT : VHNL02C890T20

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL/VHNL02C890T20-001.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL02C890T20-002.xls

When voer een bijhouding uit VHNL02C890T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C890T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 690020041 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 373230217 niet als PARTNER betrokken bij een HUWELIJK

