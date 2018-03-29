Meta:
@status                 Klaar
@regels                 R2181
@usecase                UCS-BY.HG

Narrative:
Registratie aanvang huwelijk in NL tussen I-I en I-I

Scenario:   R2181 Geen objectsleutel ingevuld en wel groepen ingevuld behalve identificatienummers.
            LT: VHNL01C260T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C260T30.xls

When voer een bijhouding uit VHNL01C260T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL01C260T30.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 283914361 wel als PARTNER betrokken bij een HUWELIJK