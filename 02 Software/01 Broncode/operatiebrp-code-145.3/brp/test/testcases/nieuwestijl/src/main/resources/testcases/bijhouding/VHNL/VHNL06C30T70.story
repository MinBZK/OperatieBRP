Meta:
@auteur                 reboe
@status                 Klaar
@sleutelwoorden         voorvoegsel,Naamgebruik,VHNL06C30T70
@usecase                UCS-BY.HG

Narrative:
R1683, R1684 Registratie Naamgebruik Ingeschrevenen

Scenario: Registratie Geslachtsnaam echtgenoot na eigen geslachtsnaam
          LT: VHNL06C30T70

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL/VHNL06C30T70-Libby.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL06C30T70-Piet.xls

When voer een bijhouding uit VHNL06C30T70.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL06C30T70.xml voor expressie
//brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 900059849 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 737509193 wel als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 9657634578 uit database en vergelijk met expected VHNL06C30T70-persoon1.xml
Then lees persoon met anummer 1291673906 uit database en vergelijk met expected VHNL06C30T70-persoon2.xml
