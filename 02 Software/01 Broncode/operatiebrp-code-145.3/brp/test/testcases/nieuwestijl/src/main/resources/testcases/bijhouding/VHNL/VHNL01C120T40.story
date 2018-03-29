Meta:
@auteur                 tjlee
@status                 Klaar
@sleutelwoorden         Geslaagd
@regels                 R1867
@usecase                UCS-BY.HG

Narrative:
R1867 Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: R1867 (BRBY0403) Partner mag niet onder curatele staan
          LT: VHNL01C120T40

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL/VHNL01C120T40-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C120T40-danny.xls

When voer een bijhouding uit VHNL01C120T40.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL01C120T40.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then lees persoon met anummer 7262803730 uit database en vergelijk met expected /testcases/bijhouding/VHNL/expected/VHNL01C120T40-persoon1.xml
Then lees persoon met anummer 1957631570 uit database en vergelijk met expected /testcases/bijhouding/VHNL/expected/VHNL01C120T40-persoon2.xml
