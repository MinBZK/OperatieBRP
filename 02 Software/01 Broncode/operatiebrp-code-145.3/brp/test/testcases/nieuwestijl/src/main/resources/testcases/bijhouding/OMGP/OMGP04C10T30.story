Meta:
@status                 Klaar
@usecase                UCS-BY.HG


Narrative: OOmzetting geregistreerd partnerschap in huwelijk

Scenario:   Omzetten van een asymmetrisch BRP Geregistreerd Partnerschap
            LT: OMGP04C10T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-OMGP/OMGP04C10T30-001.xls

When voer een bijhouding uit OMGP04C10T30a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/OMGP/expected/OMGP04C10T30a.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Given pas laatste relatie van soort 2 aan tussen persoon 973766153 en persoon 524125065 met relatie id 30010003 en betrokkenheid id 30010003

Then is in de database de persoon met bsn 973766153 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 524125065 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

When voer een bijhouding uit OMGP04C10T30b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/OMGP/expected/OMGP04C10T30b.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 973766153 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 524125065 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 973766153 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 524125065 wel als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 8920627489 uit database en vergelijk met expected OMGP04C10T30-persoon1.xml












