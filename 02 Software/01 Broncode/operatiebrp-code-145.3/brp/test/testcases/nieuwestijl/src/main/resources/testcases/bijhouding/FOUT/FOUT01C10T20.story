Meta:
@status                 Klaar
@sleutelwoorden         Fout

Narrative:
Testen van expecteds


Scenario:   1. DB init scenario om uitgangssituatie te zetten
            preconditie

Given alle personen zijn verwijderd

Scenario:   2. Expected responsebericht niet aanwezig
            LT: FOUT01C10T20
            
Given enkel initiele vulling uit bestand /LO3PL-FOUT/FOUT01C10T20-001.xls
Given enkel initiele vulling uit bestand /LO3PL-FOUT/FOUT01C10T20-002.xls

When voer een bijhouding uit FOUT01C10T10.xml namens partij 'Gemeente BRP 1'

Then is het antwoordbericht gelijk aan /testcases/bijhouding/FOUT/expected/FOUT01C10T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R



