Meta:
@auteur                 tjlee
@status                 Klaar
@sleutelwoorden         Geslaagd
@regels                 R1571
@usecase                UCS-BY.HG

Narrative:
R1571 Er mag slechts 1 voorkomen van geslachtsnaamcomponent aanwezig zijn

Scenario: 2 voorkomens van geslachtsnaamcomponent verdeel over 2 personen
          LT: VHNL02C60T20



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C30T30-Libby.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C30T30-Piet.xls

When voer een bijhouding uit VHNL02C60T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C60T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 546921097 wel als PARTNER betrokken bij een HUWELIJK

