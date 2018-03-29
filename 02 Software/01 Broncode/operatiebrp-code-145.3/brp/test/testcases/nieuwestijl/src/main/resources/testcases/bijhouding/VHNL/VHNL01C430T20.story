Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative:
R2512 Objectsleutel die verwijst naar betrokkenheid moet verwijzen naar betrokkenheid met de juiste rol

Scenario:   Objectsleutel verwijst naar betrokkenheid met niet de juiste rol (K ipv P)
            LT: VHNL01C430T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C430T20-001.xls

When voer een bijhouding uit VHNL01C430T20a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Given pas laatste relatie van soort 1 aan tussen persoon 829675401 en persoon 590788681 met relatie id 2000101 en betrokkenheid id 2000102
Given pas laatste relatie van soort 3 aan tussen persoon 829675401 en persoon 632934761 met relatie id 2000103 en betrokkenheid id 2000104


When voer een bijhouding uit VHNL01C430T20b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL01C430T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

