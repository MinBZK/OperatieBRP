Meta:
@auteur                 jozon
@status                 Klaar
@regels                 R1579
@sleutelwoorden         voltrekkingHuwelijkInNederland, VHNL01C40T10
@usecase                UCS-BY.HG

Narrative:
R1579 (BRAL9003) Geen bijhouden gegevens na overlijden persoon

Scenario: 1. In een Huwelijk-Geregistreerd Partnerschaprelatie is er een overleden partner.
            LT : VHNL01C40T10



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C40-piet.xls
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls

When voer een bijhouding uit VHNL01C40T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
And is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL01C40T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 558376617 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 422531881 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 3679783698 uit database en vergelijk met expected VHNL01C40T10-persoon1.xml
Then lees persoon met anummer 1868196961 uit database en vergelijk met expected VHNL01C40T10-persoon2.xml
