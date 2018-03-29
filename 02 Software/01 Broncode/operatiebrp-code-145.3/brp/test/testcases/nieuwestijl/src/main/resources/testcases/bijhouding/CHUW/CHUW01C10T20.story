Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: R1878 Correctie beëindigingsgegevens alleen mogelijk als de relatie al beëindigd was

Scenario:   Relatie.Datum einde gevuld waarbij relatie reeds beëindigd is
            LT: CHUW01C10T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-CHUW/CHUW01C10T20-001.xls

Given pas laatste relatie van soort 1 aan tussen persoon 871852809 en persoon 121527955 met relatie id 30010001 en betrokkenheid id 30010001

!-- Ontbinden van het huwelijk
When voer een bijhouding uit CHUW01C10T20a.xml namens partij 'Gemeente BRP 1'

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
When voer een bijhouding uit CHUW01C10T20b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/CHUW/expected/CHUW01C10T20.xml voor expressie //brp:bhg_hgpCorrigeerHuwelijkGeregistreerdPartnerschap_R
