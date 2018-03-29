Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: Verwerking Correctie Huwelijk

Scenario:   Het laten herleven van een ontbonden huwelijk
            LT: CHUW04C10T100

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-CHUW/CHUW04C10T100-001.xls
Given enkel initiele vulling uit bestand /LO3PL-CHUW/CHUW04C10T100-002.xls

!-- Voltrekking van een huwelijk
When voer een bijhouding uit CHUW04C10T100a.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

Given pas laatste relatie van soort 1 aan tussen persoon 300039001 en persoon 541737673 met relatie id 30010001 en betrokkenheid id 30010001
And de database is aangepast met: update kern.his_relatie set id = 9999 where id = (select hr.id from kern.his_relatie hr join kern.relatie r on r.id = hr.relatie where r.srt = 1 and hr.dataanv = 20160510)

Then is in de database de persoon met bsn 300039001 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 541737673 wel als PARTNER betrokken bij een HUWELIJK

!-- Ontbinding huwelijk
When voer een bijhouding uit CHUW04C10T100b.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

Then is in de database de persoon met bsn 300039001 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 541737673 niet als PARTNER betrokken bij een HUWELIJK

!-- De niet-vervallen rij gereed maken om als voorkomensleutel te gebruiken
Given de database is aangepast met: update kern.his_relatie
                                    set    id=9998
				    where  relatie=30010001
				    and    id<>9999
				    and    tsverval is null

!-- Correctie registratie huwelijk
When voer een bijhouding uit CHUW04C10T100c.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Then is in de database de persoon met bsn 300039001 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 541737673 wel als PARTNER betrokken bij een HUWELIJK

Then is het antwoordbericht gelijk aan /testcases/bijhouding/CHUW/expected/CHUW04C10T100.xml voor expressie //brp:bhg_hgpCorrigeerHuwelijkGeregistreerdPartnerschap_R
