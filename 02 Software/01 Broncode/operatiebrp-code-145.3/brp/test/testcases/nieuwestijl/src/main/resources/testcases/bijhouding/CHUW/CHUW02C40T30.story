Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: R2477 Bij nietigverklaring moet datum einde gelijk zijn aan datum aanvang

Scenario:   Correctie met Relatie.Reden einde gelijk is aan 'N' waarbij Relatie.Datum einde gelijk is aan Relatie.Datum aanvang
            LT: CHUW02C40T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-CHUW/CHUW02C40T30-001.xls

Given pas laatste relatie van soort 1 aan tussen persoon 632782377 en persoon 447039945 met relatie id 30010001 en betrokkenheid id 30010001

!-- Ontbinden van het huwelijk
When voer een bijhouding uit CHUW02C40T30a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Given de database is aangepast met: update kern.his_relatie
                                  set    id = 9999
				  where  id = (
					         select hr.id
					         from   kern.his_relatie hr
					         join   kern.relatie r
					         on     r.id       = hr.relatie
					         where  r.srt      = 1
					         and    hr.dataanv = 20160510
					         and    hr.dateinde = 20160601
					      )

!-- Correctie van het huwelijk
When voer een bijhouding uit CHUW02C40T30b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/CHUW/expected/CHUW02C40T30.xml voor expressie //brp:bhg_hgpCorrigeerHuwelijkGeregistreerdPartnerschap_R
