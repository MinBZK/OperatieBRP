Meta:
@auteur                 fuman
@status                 Klaar
@regels                 R2235
@usecase                UCS-BY.HG

Narrative:
R2235 Partij in Document moet verwijzen naar bestaande Partij

Scenario: De partij verwijst niet naar een bestaande partij, partij 999901
          LT: VHNL02C850T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls
Given enkel initiele vulling uit bestand /LO3PL/Piet.xls

When voer een bijhouding uit VHNL02C850T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
And is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C850T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 422531881 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 159247913 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 1868196961 uit database en vergelijk met expected VHNL02C850T10-persoon1.xml
Then lees persoon met anummer 5398948626 uit database en vergelijk met expected VHNL02C850T10-persoon2.xml
