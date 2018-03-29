Meta:
@auteur                 jozon
@status                 Klaar
@regels                 R1848
@sleutelwoorden         voltrekkingHuwelijkInNederland, VHNL02C250T10
@usecase                UCS-BY.HG

Narrative:
R1848 Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: R1848 Registersoort moet geldig zijn voor soort document
          LT: VHNL02C250T10



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls
Given enkel initiele vulling uit bestand /LO3PL/Piet.xls

When voer een bijhouding uit VHNL02C250T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C250T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 422531881 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 1868196961 uit database en vergelijk met expected VHNL02C250T10-persoon1.xml
Then lees persoon met anummer 5398948626 uit database en vergelijk met expected VHNL02C250T10-persoon2.xml







