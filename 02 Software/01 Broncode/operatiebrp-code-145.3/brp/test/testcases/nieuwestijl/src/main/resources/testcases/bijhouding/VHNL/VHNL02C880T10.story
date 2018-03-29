Meta:
@status                 Klaar
@regels                 R2428
@usecase                UCS-BY.HG

Narrative: R2428 Een opgegeven gedeblokkeerde melding moet verwijzen naar een regel die als deblokkeerbaar is vastgelegd

Scenario: Opgegeven gedeblokkeerde melding komt niet voor in de stamtabel Regel
          LT : VHNL02C880T10

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL/VHNL02C880T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL02C880T10-002.xls

When voer een bijhouding uit VHNL02C880T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C880T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 636550217 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 315026649 niet als PARTNER betrokken bij een HUWELIJK

