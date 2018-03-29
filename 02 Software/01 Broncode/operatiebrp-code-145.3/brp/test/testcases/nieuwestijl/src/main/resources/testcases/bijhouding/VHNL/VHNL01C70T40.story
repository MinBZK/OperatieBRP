Meta:
@auteur                 tjlee
@status                 Klaar
@sleutelwoorden         Geslaagd
@regels                 R1625
@usecase                UCS-BY.HG

Narrative:
R1625 Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: R1625 alles is ingevuld
          LT: VHNL01C70T40



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C70-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C70-danny.xls

When voer een bijhouding uit VHNL01C70T40.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
And is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL01C70T40.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 558376617 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 156960849 wel als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 1686982546 uit database en vergelijk met expected VHNL01C70T40-persoon1.xml
Then lees persoon met anummer 3814796818 uit database en vergelijk met expected VHNL01C70T40-persoon2.xml
