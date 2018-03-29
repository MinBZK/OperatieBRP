Meta:
@auteur                 reboe
@status                 Klaar
@sleutelwoorden         voorvoegsel,Naamgebruik,VHNL06C30T40
@usecase                UCS-BY.HG

Narrative:
R1683, R1684 Registratie Naamgebruik Ingeschrevenen

Scenario: Registratie Eigen naamgebruik Happy flow
          LT: VHNL06C30T40

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL/VHNL06C30T40-Libby.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL06C30T40-Piet.xls

When voer een bijhouding uit VHNL06C30T40.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL06C30T40.xml voor expressie
//brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 429516745 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 312772841 wel als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 6439046930 uit database en vergelijk met expected VHNL06C30T40-persoon1.xml
Then lees persoon met anummer 5419046162 uit database en vergelijk met expected VHNL06C30T40-persoon2.xml
