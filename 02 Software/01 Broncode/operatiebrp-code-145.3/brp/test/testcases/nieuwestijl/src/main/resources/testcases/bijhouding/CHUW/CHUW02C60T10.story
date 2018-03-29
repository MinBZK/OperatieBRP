Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative:  R2044 Gemeente aanvang relatie verplicht als land gebied gelijk is aan Nederland

Scenario:   Relatie.Land gebied aanvang = Nederland en Relatie.Gemeente aanvang  is leeg
            LT: CHUW02C60T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-CHUW/CHUW-001.xls
Given enkel initiele vulling uit bestand /LO3PL-CHUW/CHUW-002.xls

!-- Voltrekking van een huwelijk
When voer een bijhouding uit CHUW02C60T10a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Given pas laatste relatie van soort 1 aan tussen persoon 500179785 en persoon 814245833 met relatie id 30010001 en betrokkenheid id 30010001
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
When voer een bijhouding uit CHUW02C60T10b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/CHUW/expected/CHUW02C60T10.xml voor expressie //brp:bhg_hgpCorrigeerHuwelijkGeregistreerdPartnerschap_R
