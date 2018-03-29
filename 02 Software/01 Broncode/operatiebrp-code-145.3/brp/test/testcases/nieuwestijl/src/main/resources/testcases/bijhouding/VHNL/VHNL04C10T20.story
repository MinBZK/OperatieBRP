Meta:
@auteur                 fuman
@status                 Klaar
@sleutelwoorden         voltrekkingHuwelijkInNederland
@usecase                UCS-BY.HG

Narrative:
Registratie aanvang huwelijk in NL tussen I-I en I-I

Scenario:   Personen Libby Thatcher (Ingeschrevene-Ingezetene) en Piet Jansen (Ingeschrevene-Ingezetene) gaan trouwen met meegeven pers. gegevens, controleer relatie, betrokkenheid, afgeleid administratief,
            LT: VHNL04C10T20



Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL/Libby.xls
Given enkel initiele vulling uit bestand /LO3PL/Piet.xls

When voer een bijhouding uit VHNL04C10T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL04C10T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R


Then is in de database de persoon met bsn 159247913 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 422531881 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 1868196961 uit database en vergelijk met expected VHNL04C10T20-persoon1.xml
Then lees persoon met anummer 5398948626 uit database en vergelijk met expected VHNL04C10T20-persoon2.xml








