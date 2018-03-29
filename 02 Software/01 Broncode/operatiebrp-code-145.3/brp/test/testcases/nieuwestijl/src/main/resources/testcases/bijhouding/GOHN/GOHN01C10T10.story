Meta:
@regels                 R1848
@status                 Klaar
@usecase                UCS-BY.HG


Narrative:
R1848 Registersoort moet geldig zijn voor soort document

Scenario:   R1848 Registersoort moet geldig zijn voor soort document
            LT: GOHN01C10T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-GOHN/GOHN-Anne.xls

Given pas laatste relatie van soort 1 aan tussen persoon 303251049 en persoon 547166473 met relatie id 1000001 en betrokkenheid id 1000002

When voer een GBA bijhouding uit GOHN01C10T10.xml namens partij 'Migratievoorziening'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/GOHN/expected/GOHN01C10T10.xml voor expressie //brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 303251049 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 547166473 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 3862424609 uit database en vergelijk met expected GOHN01C10T10-persoon1.xml
