Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative:
Ongedaan maken huwelijk, hoofdactie verval huwelijk

Scenario:   Ongedaan maken van een huwelijk BRP (I) en BRP (I), daarna huwelijk voltrekking met dezelfde persoon
            LT: ONHW04C10T90

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-ONHW/ONHW04C10T90-001.xls
Given enkel initiele vulling uit bestand /LO3PL-ONHW/ONHW04C10T90-002.xls


When voer een bijhouding uit ONHW04C10T90a.xml namens partij 'Gemeente BRP 1'

Given pas laatste relatie van soort 1 aan tussen persoon 986092873 en persoon 293300057 met relatie id 50000001 en betrokkenheid id 50000001
Then heeft het antwoordbericht verwerking Geslaagd

Then is in de database de persoon met bsn 986092873 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 293300057 wel als PARTNER betrokken bij een HUWELIJK

When voer een bijhouding uit ONHW04C10T90b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Then is in de database de persoon met bsn 986092873 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 293300057 niet als PARTNER betrokken bij een HUWELIJK

When voer een bijhouding uit ONHW04C10T90c.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Then is in de database de persoon met bsn 986092873 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 293300057 wel als PARTNER betrokken bij een HUWELIJK

