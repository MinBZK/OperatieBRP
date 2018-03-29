Meta:
@status                 Onderhanden
@usecase                UCS-BY.1.ON


Narrative: Triggers voor ontrelateren

Scenario:   Opgeschort in combinatie met Mededeling
            LT: ONTR01C10T30

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-ONTR/ONTR01C10T30-001.xls
Given enkel initiele vulling uit bestand /LO3PL-ONTR/ONTR01C10T30-002.xls
Given enkel initiele vulling uit bestand /LO3PL-ONTR/ONTR01C10T30-003.xls

When voer een bijhouding uit ONTR01C10T30a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/ONTR/expected/ONTR01C10T30a.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Given pas laatste relatie van soort 1 aan tussen persoon 148869105 en persoon 959109833 met relatie id 30010001 en betrokkenheid id 30010001

Then is in de database de persoon met bsn 148869105 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 959109833 wel als PARTNER betrokken bij een HUWELIJK

Given Pseudo-persoon 3049564193 is vervangen door ingeschreven persoon 6736901921

When voer een bijhouding uit ONTR01C10T30b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/ONTR/expected/ONTR01C10T30b.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 148869105 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 959109833 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 9757592353 uit database en vergelijk met expected ONTR01C10T30-persoon1.xml
Then lees persoon met anummer 1396041857 uit database en vergelijk met expected ONTR01C10T30-persoon2.xml
