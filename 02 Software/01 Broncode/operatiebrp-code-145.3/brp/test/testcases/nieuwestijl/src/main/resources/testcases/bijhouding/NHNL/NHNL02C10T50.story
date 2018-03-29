Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: Nietigverklaring huwelijk in Nederland

Scenario:   Nevenactie Registratie geslachtsnaam Eigen naam voor naam partner "E"
            LT: NHNL02C10T50

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-NHNL/NHNL02C10T50-001.xls
Given enkel initiele vulling uit bestand /LO3PL-NHNL/NHNL02C10T50-002.xls

When voer een bijhouding uit NHNL02C10T50a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Given pas laatste relatie van soort 1 aan tussen persoon 472969961 en persoon 327706569 met relatie id 43000105 en betrokkenheid id 43000105

Then is in de database de persoon met bsn 472969961 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 327706569 wel als PARTNER betrokken bij een HUWELIJK

When voer een bijhouding uit NHNL02C10T50b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/NHNL/expected/NHNL02C10T50.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 472969961 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 327706569 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 1013937974 uit database en vergelijk met expected NHNL02C10T50-persoon1.xml
Then lees persoon met anummer 9735327521 uit database en vergelijk met expected NHNL02C10T50-persoon2.xml












