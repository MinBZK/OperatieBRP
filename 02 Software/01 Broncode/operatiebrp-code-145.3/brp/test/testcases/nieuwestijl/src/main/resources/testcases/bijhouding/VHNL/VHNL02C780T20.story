Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: Omschrijving niet gevuld bij Nederlandse registerakten

Scenario: niet NLse akte Omschrijving gevuld
          LT: VHNL02C780T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL02C780T20.xls

When voer een bijhouding uit VHNL02C780T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C780T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 297457913 wel als PARTNER betrokken bij een HUWELIJK






