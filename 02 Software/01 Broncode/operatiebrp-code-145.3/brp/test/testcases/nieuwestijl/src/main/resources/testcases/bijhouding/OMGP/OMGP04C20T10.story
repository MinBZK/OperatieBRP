Meta:
@status                 Klaar
@usecase                UCS-BY.HG


Narrative: Registratie Geslachtsnaam Ingeschrevenen

Scenario:   Registratie Geslachtsnaam voor 1 partner (Ingeschrevene) met niet NL nationaliteit
            LT: OMGP04C20T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-OMGP/OMGP04C20T10-001.xls

Given pas laatste relatie van soort 2 aan tussen persoon 509181065 en persoon 772212041 met relatie id 31000004 en betrokkenheid id 31000004

When voer een bijhouding uit OMGP04C20T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/OMGP/expected/OMGP04C20T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 509181065 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 772212041 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 509181065 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 772212041 wel als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 9465101601 uit database en vergelijk met expected OMGP04C20T10-persoon1.xml













