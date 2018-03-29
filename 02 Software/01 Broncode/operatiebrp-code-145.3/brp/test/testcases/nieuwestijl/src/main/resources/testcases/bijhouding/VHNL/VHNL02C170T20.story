Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R1680
@usecase                UCS-BY.HG

Narrative:
R1680 Geslachtnaamstam verplicht als naamgebruik niet wordt afgeleid (buiten scope)

Scenario: R1680 Persoon.Naamgebruik afgeleid heeft de waarde Nee en Persoon.Geslachtsnaamstam naamgebruik heeft een waarde
          LT: VHNL02C170T20

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL/VHNL02C170T20-Libby.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL02C170T20-Piet.xls

When voer een bijhouding uit VHNL02C170T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C170T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 933694921 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 298778361 wel als PARTNER betrokken bij een HUWELIJK
