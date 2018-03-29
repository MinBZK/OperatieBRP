Meta:
@status                 Klaar
@usecase                UCS-BY.HG


Narrative:  R1836 Nevenactie mag alleen betrekking hebben op een hoofdpersoon

Scenario:   Nevenactie waarbij partner uit andere relatie wordt aangewezen
            LT: OHNL01C80T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-OHNL/OHNL01C80T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-OHNL/OHNL01C80T10-002.xls
Given enkel initiele vulling uit bestand /LO3PL-OHNL/OHNL01C80T10-003.xls
Given enkel initiele vulling uit bestand /LO3PL-OHNL/OHNL01C80T10-004.xls

When voer een bijhouding uit OHNL01C80T10a.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

When voer een bijhouding uit OHNL01C80T10b.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

Given pas laatste relatie van soort 1 aan tussen persoon 645933065 en persoon 911872905 met relatie id 30010003 en betrokkenheid id 30010003

When voer een bijhouding uit OHNL01C80T10c.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/OHNL/expected/OHNL01C80T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R
