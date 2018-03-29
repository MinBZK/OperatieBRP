Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: R1878 Correctie beëindigingsgegevens alleen mogelijk als de relatie al beëindigd was

Scenario:   Relatie.LandGebied Einde Code gevuld waarbij relatie nog niet beëindigd is
            LT: CHUW01C10T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-CHUW/CHUW01C10T30-001.xls

Given pas laatste relatie van soort 1 aan tussen persoon 653687369 en persoon 862882825 met relatie id 30010001 en betrokkenheid id 30010001
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

!-- Correctie van het huwelijk
When voer een bijhouding uit CHUW01C10T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/CHUW/expected/CHUW01C10T30.xml voor expressie //brp:bhg_hgpCorrigeerHuwelijkGeregistreerdPartnerschap_R
