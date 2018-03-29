Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R1466
@sleutelwoorden         voltrekkingHuwelijkInNederland,TjieWah,VHNL02C20T30
@usecase                UCS-BY.HG

Narrative:
R1466 Gemeente geboorte moet geldig zijn op datum geboorte

Scenario: R1466 Persoon.Datum geboorte voor Gemeente.Dateindegel
          LT: VHNL02C20T30



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Piet.xls
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls

When voer een bijhouding uit VHNL02C20T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
And is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C20T30.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 159247913 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 422531881 wel als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 5398948626 uit database en vergelijk met expected VHNL02C20T30-persoon1.xml
Then lees persoon met anummer 1868196961 uit database en vergelijk met expected VHNL02C20T30-persoon2.xml
