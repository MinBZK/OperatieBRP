Meta:
@status                 Klaar
@regels                 R2428
@usecase                UCS-BY.HG

Narrative: R2428 Een opgegeven gedeblokkeerde melding moet verwijzen naar een regel die als deblokkeerbaar is vastgelegd

Scenario: Opgegeven gedeblokkeerde melding komt voor in de stamtabel Regel maar niet als "Deblokkeerbaar"
          LT : VHNL02C880T20

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL/VHNL02_reg_gesl_nm-Libby.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL02_reg_gesl_nm-Piet.xls

When voer een bijhouding uit VHNL02C880T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C880T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 690020041 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 373230217 niet als PARTNER betrokken bij een HUWELIJK

