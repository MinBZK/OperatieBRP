Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R2036
@sleutelwoorden         voltrekkingHuwelijkInNederland,TjieWah,VHNL02C580T10
@usecase                UCS-BY.HG

Narrative:
R2036 Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: R2036 Persoon Woonplaatsnaam geboorte zonder waarde van Persoon Gemeente geboorte
          LT: VHNL02C580T10

Gemeente BRP 1

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL02C580T10-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL02C580T10-danny.xls

When voer een bijhouding uit VHNL02C580T10.xml namens partij 'Gemeente Tiel'

Then heeft het antwoordbericht verwerking Foutief
And is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C580T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 346742857 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 322790025 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 4927340306 uit database en vergelijk met expected VHNL02C580T10-persoon1.xml
Then lees persoon met anummer 7581093650 uit database en vergelijk met expected VHNL02C580T10-persoon2.xml
