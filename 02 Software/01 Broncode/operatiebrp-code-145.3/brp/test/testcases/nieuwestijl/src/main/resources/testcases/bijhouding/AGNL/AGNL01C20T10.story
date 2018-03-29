Meta:
@auteur                 jozon
@status                 Klaar
@regels                 R1849
@sleutelwoorden         AGNL01C20T10
@usecase                UCS-BY.HG

Narrative: Aktenummer verplicht bij Nederlandse registerakten en leeg bij andere documentsoorten

Scenario:   1. DB init scenario om uitgangssituatie te zetten
            preconditie

Given voer enkele update uit: INSERT INTO kern.srtactiebrongebruik (srtactie, srtadmhnd, srtdoc, dataanvgel, dateindegel)
                              VALUES ('61','20','39','19000101','99991231');

Given maak bijhouding caches leeg

Scenario: 2. R1849 Aktenummer is gevuld bij niet-Nederlandse akte
             LT: AGNL01C20T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGNL/Marjan.xls

When voer een bijhouding uit AGNL01C20T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AGNL/expected/AGNL01C20T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 221087849 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

Scenario:   3. DB reset scenario om de aangepaste data weer terug te zetten
            postconditie
Given de database is aangepast met: DELETE FROM kern.srtactiebrongebruik
                                    WHERE srtadmhnd='20' and srtdoc='39'

Given maak bijhouding caches leeg
