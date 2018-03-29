Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R2032
@sleutelwoorden         voltrekkingHuwelijkInNederland,TjieWah,VHNL02C560T30
@usecase                UCS-BY.HG

Narrative:
R2032 Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: R2032 Bij geboorte in Land/gebied is Nederland zijn geen buitenlandse locatiegegevens toegestaan
          LT: VHNL02C560T30

Gemeente BRP 1

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL02C560T30-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL02C560T30-danny.xls

When voer een bijhouding uit VHNL02C560T30.xml namens partij 'Gemeente Tiel'

Then heeft het antwoordbericht verwerking Foutief
And is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C560T30.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 252160873 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 526635113 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 1376371826 uit database en vergelijk met expected VHNL02C560T30-persoon1.xml
Then lees persoon met anummer 3154025106 uit database en vergelijk met expected VHNL02C560T30-persoon2.xml
