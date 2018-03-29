Meta:
@auteur                 tjlee
@status                 Klaar
@sleutelwoorden         Geslaagd
@regels                 R2169
@usecase                UCS-BY.HG

Narrative:
R2169 Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: R2169 Namenreeks in Samengestelde naam is opgegeven
          LT: VHNL03C180T20



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL03C180-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL03C180-danny.xls

When voer een bijhouding uit VHNL03C180T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL03C180T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 558376617 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 156960849 wel als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 1686982546 uit database en vergelijk met expected VHNL03C180T20-persoon1.xml
Then lees persoon met anummer 3814796818 uit database en vergelijk met expected VHNL03C180T20-persoon2.xml
