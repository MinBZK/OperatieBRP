Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: R2486 Alle acties moeten betrekking hebben op dezelfde relatie

Scenario:   In 2 verschillende acties worden verschillende relaties aangewezen
            LT: CHUW01C30T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-CHUW/CHUW01C30T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-CHUW/CHUW01C30T10-002.xls
Given enkel initiele vulling uit bestand /LO3PL-CHUW/CHUW01C30T10-003.xls

Given pas laatste relatie van soort 1 aan tussen persoon 677203913 en persoon 676763273 met relatie id 50000001 en betrokkenheid id 50000001

!-- Voltrekking van een huwelijk
When voer een bijhouding uit CHUW01C30T10a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Given pas laatste relatie van soort 1 aan tussen persoon 381191977 en persoon 455799593 met relatie id 30010001 en betrokkenheid id 30010001
And de database is aangepast met: update kern.his_relatie
                                  set    id = 9999
				  where  id = (
					         select hr.id
					         from   kern.his_relatie hr
					         join   kern.relatie r
					         on     r.id       = hr.relatie
					         where  r.srt      = 1
					         and    hr.dataanv = 20160510
					      )

Then is in de database de persoon met bsn 381191977 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 455799593 wel als PARTNER betrokken bij een HUWELIJK

!-- Correctie van het huwelijk
When voer een bijhouding uit CHUW01C30T10b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/CHUW/expected/CHUW01C30T10.xml voor expressie //brp:bhg_hgpCorrigeerHuwelijkGeregistreerdPartnerschap_R
