Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R2032
@sleutelwoorden         voltrekkingHuwelijkInNederland,TjieWah,VHNL02C560T10
@usecase                UCS-BY.HG

Narrative:
R2032 Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: R2032 Bij geboorte in Land/gebied is Nederland zijn geen buitenlandse locatiegegevens toegestaan
          LT: VHNL02C560T10

Gemeente BRP 1

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL02C560T10-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL02C560T10-danny.xls

When voer een bijhouding uit VHNL02C560T10.xml namens partij 'Gemeente Tiel'

Then heeft het antwoordbericht verwerking Foutief
And is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C560T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 367359017 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 878859913 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 8231626514 uit database en vergelijk met expected VHNL02C560T10-persoon1.xml
Then lees persoon met anummer 5053823250 uit database en vergelijk met expected VHNL02C560T10-persoon2.xml
