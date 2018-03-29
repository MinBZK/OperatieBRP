Meta:
@status                 Klaar
@sleutelwoorden         Fout

Narrative:
Testen van expecteds


Scenario:   1. DB init scenario om uitgangssituatie te zetten
            preconditie

Given alle personen zijn verwijderd

Scenario:   2. inhoud expected responsebericht verschilt
            LT: FOUT01C10T10
            
Given enkel initiele vulling uit bestand /LO3PL-FOUT/FOUT01C10T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-FOUT/FOUT01C10T10-002.xls

When voer een bijhouding uit FOUT01C10T10.xml namens partij 'Gemeente BRP 1'

Then is het antwoordbericht gelijk aan /testcases/bijhouding/FOUT/expected/FOUT01C10T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R


Scenario:   3. Query resultaat verschilt
            postconditie

Then in kern heeft select statuslev from kern.admhnd where partij = 27012 and tslev is null de volgende gegevens:
| veld                      | waarde |
| statuslev                 | 2      |

Scenario:   4. Inhoud expected opgeslagen persoon verschilt
            postconditie

Then lees persoon met anummer 1868196961 uit database en vergelijk met expected FOUT01C10T10-persoon1.xml

Scenario:   5. Expected opgeslagen persoon niet aanwezig
            postconditie

Then lees persoon met anummer 5398948626 uit database en vergelijk met expected FOUT01C10T10-persoon2.xml




