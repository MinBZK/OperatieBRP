Meta:
@status                 Klaar
@usecase                UCS-BY.HG


Narrative: R2511 Objectsleutel die verwijst naar relatie moet verwijzen naar relatie van juiste soort

Scenario:   Objectsleutel verwijst naar relatie van niet de juiste soort (FRB)
            LT: VHNL01C420T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C420T20-001.xls

Given pas laatste relatie van soort 3 aan tussen persoon 662714441 en persoon 522831497 met relatie id 70000001 en betrokkenheid id 70000001

When voer een bijhouding uit VHNL01C420T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL01C420T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R












