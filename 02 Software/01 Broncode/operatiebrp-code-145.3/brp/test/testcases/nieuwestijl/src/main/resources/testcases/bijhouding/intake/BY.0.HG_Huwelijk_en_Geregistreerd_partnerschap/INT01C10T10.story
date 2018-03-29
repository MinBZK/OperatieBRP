Meta:
@status                 Klaar
@sleutelwoorden         voltrekkingHuwelijkInNederland INT01C10T10

Narrative:
Registratie aanvang huwelijk in NL tussen I-I en I-I (Happy Flow)

Scenario:   Personen Libby Thatcher (Ingeschrevene-Ingezetene) en Piet Jansen (Ingeschrevene-Ingezetene) gaan trouwen, controleer relatie, betrokkenheid, afgeleid administratief,
            LT: INT01C10T10


Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls
Given enkel initiele vulling uit bestand /LO3PL/Piet.xls

When voer een bijhouding uit INT01C10T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Then is het antwoordbericht gelijk aan /testcases/bijhouding/intake/BY.0.HG_Huwelijk_en_Geregistreerd_partnerschap/expected/INT01C10T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R
Then is in de database de persoon met bsn 159247913 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 422531881 wel als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 1868196961 uit database en vergelijk met expected INT01C10T10-persoon1.xml
Then lees persoon met anummer 5398948626 uit database en vergelijk met expected INT01C10T10-persoon2.xml





