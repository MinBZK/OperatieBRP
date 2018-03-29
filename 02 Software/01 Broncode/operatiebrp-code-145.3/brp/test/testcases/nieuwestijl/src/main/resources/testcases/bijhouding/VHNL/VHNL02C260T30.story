Meta:
@auteur                 jozon
@status                 Klaar
@regels                 R1849
@sleutelwoorden         voltrekkingHuwelijkInNederland, VHNL02C260T30
@usecase                UCS-BY.HG

Narrative:
R1849 Aktenummer verplicht bij Nederlandse registerakten en leeg bij andere documentsoorten

Scenario: Aktenummer is niet gevuld bij Nederlandse akte
          LT: VHNL02C260T30



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls

When voer een bijhouding uit VHNL02C260T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C260T30.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 422531881 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 1868196961 uit database en vergelijk met expected VHNL02C260T30-persoon1.xml
