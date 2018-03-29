Meta:
@status                 Klaar
@usecase                UCS-BY.HG


Narrative: R1848 Registersoort moet geldig zijn voor soort document

Scenario:   Registersoort van huwelijksakte is niet geldig
            LT: GOGP01C10T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-GOGP/GOGP01C10T10-001.xls

Given pas laatste relatie van soort 2 aan tussen persoon 534066185 en persoon 798498249 met relatie id 31000001 en betrokkenheid id 31000001

When voer een GBA bijhouding uit GOGP01C10T10.xml namens partij 'Migratievoorziening'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/GOGP/expected/GOGP01C10T10.xml voor expressie //brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 534066185 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 798498249 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 534066185 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 798498249 wel als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 6361452321 uit database en vergelijk met expected GOGP01C10T10-persoon1.xml













