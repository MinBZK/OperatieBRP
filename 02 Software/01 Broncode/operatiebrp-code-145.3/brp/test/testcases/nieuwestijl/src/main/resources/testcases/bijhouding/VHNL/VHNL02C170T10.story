Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R1680
@usecase                UCS-BY.HG

Narrative:
R1680 Geslachtnaamstam verplicht als naamgebruik niet wordt afgeleid

Scenario: R1680 Persoon.Naamgebruik afgeleid heeft de waarde Nee en Persoon.Geslachtsnaamstam naamgebruik heeft geen waarde
          LT: VHNL02C170T10

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL/VHNL02_reg_gesl_nm-Libby.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL02_reg_gesl_nm-Piet.xls

When voer een bijhouding uit VHNL02C170T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C170T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 690020041 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 373230217 niet als PARTNER betrokken bij een HUWELIJK
