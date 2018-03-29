Meta:
@status                 Klaar
@sleutelwoorden         voltrekkingHuwelijkInNederland VHNL01C100T20
@usecase                UCS-BY.HG

Narrative:
R1636 (BRAL2111) In een H/GP-relatie zijn twee partners betrokken

Scenario:   In een H-relatie zijn 3 partners betrokken,
            LT: VHNL01C100T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby_met_his_rel.xls
Given enkel initiele vulling uit bestand /LO3PL/Piet.xls

When voer een bijhouding uit VHNL01C100T20.xml namens partij 'Gemeente BRP 1'

Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL01C100T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then lees persoon met anummer 1868196961 uit database en vergelijk met expected /testcases/bijhouding/VHNL/expected/VHNL01C100T20-persoon1.xml
Then lees persoon met anummer 5398948626 uit database en vergelijk met expected /testcases/bijhouding/VHNL/expected/VHNL01C100T20-persoon2.xml









