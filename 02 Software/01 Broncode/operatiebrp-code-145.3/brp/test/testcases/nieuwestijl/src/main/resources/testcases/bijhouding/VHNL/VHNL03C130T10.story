Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R2164
@sleutelwoorden         voltrekkingHuwelijkInNederland,TjieWah,VHNL03C130T10
@usecase                UCS-BY.HG

Narrative:
R2164 Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: R2164 Gemeente geboorte verwijst niet naar bestaand stamgegeven
          LT: VHNL03C130T10



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL03C130-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL03C130-danny.xls

When voer een bijhouding uit VHNL03C130T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
And is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL03C130T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 558376617 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 156960849 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 1686982546 uit database en vergelijk met expected VHNL03C130T10-persoon1.xml
Then lees persoon met anummer 3814796818 uit database en vergelijk met expected VHNL03C130T10-persoon2.xml
