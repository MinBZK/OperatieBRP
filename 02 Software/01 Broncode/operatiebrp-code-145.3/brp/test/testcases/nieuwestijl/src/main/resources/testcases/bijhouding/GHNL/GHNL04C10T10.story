Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative:
GBA Voltrekking Huwelijk. Registratie aanvang huwelijk in NL tussen I-I en I-I (Happy Flow)

Scenario:   Personen Mila (Ingeschrevene-Ingezetene) en Tim (Ingeschrevene-Ingezetene) gaan trouwen, controleer relatie, betrokkenheid, afgeleid administratief
            LT: GHNL04C10T10



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL04C20T10-Mila.xls
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL04C10T10-Tim.xls

When voer een GBA bijhouding uit GHNL04C10T10.xml namens partij 'Migratievoorziening'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/GHNL/expected/GHNL04C10T10.xml voor expressie //brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 956803593 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 979562697 wel als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 1712615186 uit database en vergelijk met expected GHNL04C10T10-persoon1.xml
Then lees persoon met anummer 7403085089 uit database en vergelijk met expected GHNL04C10T10-persoon2.xml





