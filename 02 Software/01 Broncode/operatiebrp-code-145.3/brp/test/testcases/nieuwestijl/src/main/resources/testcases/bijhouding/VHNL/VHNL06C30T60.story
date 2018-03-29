Meta:
@auteur                 reboe
@status                 Klaar
@sleutelwoorden         voorvoegsel,Naamgebruik,VHNL06C30T60
@usecase                UCS-BY.HG

Narrative:
R1683, R1684 Registratie Naamgebruik Ingeschrevenen

Scenario: Registratie Partner naam voor eigen naam
          LT: VHNL06C30T60

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL/VHNL06C30T60-Libby.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL06C30T60-Piet.xls

When voer een bijhouding uit VHNL06C30T60.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL06C30T60.xml voor expressie
//brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 152680901 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 522958345 wel als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 4825919506 uit database en vergelijk met expected VHNL06C30T60-persoon1.xml
Then lees persoon met anummer 5827469586 uit database en vergelijk met expected VHNL06C30T60-persoon2.xml
