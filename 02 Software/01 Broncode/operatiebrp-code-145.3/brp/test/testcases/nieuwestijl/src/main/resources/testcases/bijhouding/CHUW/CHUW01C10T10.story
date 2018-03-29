Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: R1878 Correctie beëindigingsgegevens alleen mogelijk als de relatie al beëindigd was

Scenario:   Relatie.Datum einde gevuld terwijl relatie nog niet beëindigd is
            LT: CHUW01C10T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-CHUW/CHUW-003.xls

Given pas laatste relatie van soort 1 aan tussen persoon 781445577 en persoon 905351241 met relatie id 30010001 en betrokkenheid id 30010001
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

Then is in de database de persoon met bsn 781445577 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 905351241 wel als PARTNER betrokken bij een HUWELIJK

!-- Correctie van het huwelijk
When voer een bijhouding uit CHUW01C10T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/CHUW/expected/CHUW01C10T10.xml voor expressie //brp:bhg_hgpCorrigeerHuwelijkGeregistreerdPartnerschap_R
