Meta:
@auteur                 reboe
@status                 Klaar
@sleutelwoorden         voorvoegsel,Naamgebruik,VHNL06C30T80
@usecase                UCS-BY.HG

Narrative:
R1683, R1684 Registratie Naamgebruik Ingeschrevenen

Scenario: Registratie eigen geslachtsnaam met voorvoegsel en scheidingsteken mislukt
          LT: VHNL06C30T80


Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL/VHNL06C30T80-Libby.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL06C30T80-Piet.xls

When voer een bijhouding uit VHNL06C30T80.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL06C30T80.xml voor expressie
//brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 884863561 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 487775913 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 6824581906 uit database en vergelijk met expected VHNL06C30T80-persoon1.xml
Then lees persoon met anummer 1017543908 uit database en vergelijk met expected VHNL06C30T80-persoon2.xml
