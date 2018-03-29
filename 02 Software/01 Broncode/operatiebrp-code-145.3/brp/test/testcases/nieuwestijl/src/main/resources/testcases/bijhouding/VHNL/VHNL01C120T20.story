Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R1867
@sleutelwoorden         voltrekkingHuwelijkInNederland,TjieWah,VHNL01C120T20
@usecase                UCS-BY.HG

Narrative:
R1867 Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: R1867 (BRBY0403) 7 Partner mag niet onder curatele staan
          LT: VHNL01C120T20

Gemeente BRP 1

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL/VHNL01C120T20-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C120T20-danny.xls

When voer een bijhouding uit VHNL01C120T20.xml namens partij 'Gemeente Tiel'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL01C120T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 905457353 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 7262803730 uit database en vergelijk met expected /testcases/bijhouding/VHNL/expected/VHNL01C120T20-persoon1.xml
Then lees persoon met anummer 1957631570 uit database en vergelijk met expected /testcases/bijhouding/VHNL/expected/VHNL01C120T20-persoon2.xml
