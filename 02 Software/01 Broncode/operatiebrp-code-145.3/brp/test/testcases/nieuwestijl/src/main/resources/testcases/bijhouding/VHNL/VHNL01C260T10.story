Meta:
@status                 Klaar
@regels                 R2181
@usecase                UCS-BY.HG

Narrative:
Registratie aanvang huwelijk in NL tussen I-I en I-I

Scenario:   R2181 Objectsleutel ingevuld en geen groepen ingevuld.
            LT: VHNL01C260T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C260T10-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C260T10-danny.xls

When voer een bijhouding uit VHNL01C260T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL01C260T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 808875401 wel als PARTNER betrokken bij een HUWELIJK