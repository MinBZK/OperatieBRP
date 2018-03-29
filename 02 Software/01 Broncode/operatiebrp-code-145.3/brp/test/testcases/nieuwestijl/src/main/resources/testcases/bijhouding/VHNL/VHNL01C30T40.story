Meta:
@auteur                 fuman
@status                 Klaar
@sleutelwoorden         voltrekkingHuwelijkInNederland VHNL01C30T40
@usecase                UCS-BY.HG

Narrative:
uwelijk waarbij 1 partner de NL nationaliteit hebben en krijgt een naamswijziging

Scenario:   Personen Libby Thatcher (Ingeschrevene-Ingezetene, NL Nat) en Piet Jansen (Ingeschrevene-Ingezetene, NL Nat) gaan trouwen, controleer relatie, betrokkenheid, afgeleid administratief,
            LT: VHNL01C30T40



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C30T40-Libby.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C30T40-Piet.xls

When voer een bijhouding uit VHNL01C30T40.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL01C30T40.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 422531881 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 1868196961 uit database en vergelijk met expected VHNL01C30T40-persoon1.xml
Then lees persoon met anummer 5907683105 uit database en vergelijk met expected VHNL01C30T40-persoon2.xml









