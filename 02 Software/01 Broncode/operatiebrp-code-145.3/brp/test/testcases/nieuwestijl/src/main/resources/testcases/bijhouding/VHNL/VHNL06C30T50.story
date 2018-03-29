Meta:

@status                 Klaar
@sleutelwoorden         voorvoegsel,Naamgebruik,VHNL06C30T50
@usecase                UCS-BY.HG

Narrative:
R1683, R1684 Registratie Naamgebruik Ingeschrevenen

Scenario: Registratie Partner naamgebruik_XXXXXXXXXX
          LT: VHNL06C30T50


Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL/VHNL06C30T50-Libby.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL06C30T50-Piet.xls

When voer een bijhouding uit VHNL06C30T50.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL06C30T50.xml voor expressie
//brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 774121865 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 598926665 wel als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 9512503058 uit database en vergelijk met expected VHNL06C30T50-persoon1.xml
Then lees persoon met anummer 1978680786 uit database en vergelijk met expected VHNL06C30T50-persoon2.xml
