Meta:
@auteur                 jozon
@status                 Klaar
@regels                 R1849
@sleutelwoorden         voltrekkingHuwelijkInNederland, VHNL02C260T10
@usecase                UCS-BY.HG

Narrative:
Aktenummer verplicht bij Nederlandse registerakten en leeg bij andere documentsoorten

Scenario: 1. DB init
          preconditie

Given voer enkele update uit: INSERT INTO kern.srtactiebrongebruik (srtactie, srtadmhnd, srtdoc, dataanvgel, dateindegel)
                              VALUES ('32','18','39','19000101','99991231');

Given maak bijhouding caches leeg

Scenario: 2. R1849 Aktenummer is gevuld bij niet-Nederlandse akte
          LT: VHNL02C260T10


Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls

When voer een bijhouding uit VHNL02C260T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C260T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 422531881 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 1868196961 uit database en vergelijk met expected VHNL02C260T10-persoon1.xml

Scenario: 3. DB reset
          postconditie
Given de database is aangepast met: DELETE FROM kern.srtactiebrongebruik
                                    where srtadmhnd ='18' and srtdoc ='39'

Given maak bijhouding caches leeg
