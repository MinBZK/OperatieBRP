Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R1677
@sleutelwoorden         voltrekkingHuwelijkInNederland,registratieNaamgebruik,TjieWah,VHNL02C150T20
@usecase                UCS-BY.HG

Narrative:
R1677 Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap, registratieNaamgebruik

Scenario: R1677 Persoon.Naamgebruik afgeleid heeft de waarde Ja en Persoon.Predicaat naamgebruik heeft een waarde
          LT: VHNL02C150T20



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL02C150-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL02C150-danny.xls

When voer een bijhouding uit VHNL02C150T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
And is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C150T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 558376617 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 156960849 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 1686982546 uit database en vergelijk met expected VHNL02C150T20-persoon1.xml
Then lees persoon met anummer 3814796818 uit database en vergelijk met expected VHNL02C150T20-persoon2.xml
