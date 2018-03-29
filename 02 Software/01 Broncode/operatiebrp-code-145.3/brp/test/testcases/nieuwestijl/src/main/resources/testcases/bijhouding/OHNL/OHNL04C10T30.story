Meta:
@status                 Klaar
@usecase                UCS-BY.HG


Narrative:
Ontbinding huwelijk in Nederland

Scenario:   Personen Anne(I) en Jan(P) hebben een asymmetrisch BRP huwelijk, dit huwelijk wordt in de BRP ontbonden
            LT: OHNL04C10T30

Gemeente BRP 1

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-OHNL/OHNL04C10T30-001.xls

When voer een bijhouding uit OHNL04C10T30a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/OHNL/expected/OHNL04C10T30a.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Given pas laatste relatie van soort 1 aan tussen persoon 111376920 en persoon 669740937 met relatie id 30010002 en betrokkenheid id 30010002

Then is in de database de persoon met bsn 111376920 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 669740937 wel als PARTNER betrokken bij een HUWELIJK

When voer een bijhouding uit OHNL04C10T30b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/OHNL/expected/OHNL04C10T30b.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 111376920 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 669740937 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 1640649761 uit database en vergelijk met expected OHNL04C10T30-persoon1.xml













