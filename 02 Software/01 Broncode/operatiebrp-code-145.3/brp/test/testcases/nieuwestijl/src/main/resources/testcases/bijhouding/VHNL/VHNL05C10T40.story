Meta:
@auteur                 fuman
@status                 Klaar
@sleutelwoorden         voltrekkingHuwelijkInNederland VHNL05C10T40
@usecase                UCS-BY.HG

Narrative: Registratie Geslachtsnaam voor 1 partner (Ingeschrevene) met NL nationaliteit

Scenario: Personen Libby Thatcher (Ingeschrevene-Ingezetene, Niet NL Nat) en Piet Jansen (Ingeschrevene-Ingezetene, NL Nat) gaan trouwen, controleer relatie, betrokkenheid, afgeleid administratief,
          LT: VHNL05C10T40



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C30T10-Libby.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C30T10-Piet.xls

When voer een bijhouding uit VHNL05C10T40.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL05C10T40.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 422531881 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 159247913 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 1868196961 uit database en vergelijk met expected VHNL05C10T40-persoon1.xml
Then lees persoon met anummer 5398948626 uit database en vergelijk met expected VHNL05C10T40-persoon2.xml






