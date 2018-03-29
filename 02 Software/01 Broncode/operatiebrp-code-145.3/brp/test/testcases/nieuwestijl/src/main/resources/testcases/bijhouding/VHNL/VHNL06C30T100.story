Meta:
@auteur                 reboe
@status                 Klaar
@sleutelwoorden         voorvoegsel,Naamgebruik,VHNL06C30T100
@usecase                UCS-BY.HG

Narrative:
R1683, R1684 Registratie Naamgebruik Ingeschrevenen

Scenario: Registratie Geslachtsnaam echtgenoot na eigen geslachtsnaam met voorvoegsel
          LT: VHNL06C30T100


Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL/VHNL06C30T100-Libby.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL06C30T100-Piet.xls

When voer een bijhouding uit VHNL06C30T100.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL06C30T100.xml voor expressie
//brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 592228393 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 511455689 wel als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 8732095762 uit database en vergelijk met expected VHNL06C30T100-persoon1.xml
Then lees persoon met anummer 1562508434 uit database en vergelijk met expected VHNL06C30T100-persoon2.xml
