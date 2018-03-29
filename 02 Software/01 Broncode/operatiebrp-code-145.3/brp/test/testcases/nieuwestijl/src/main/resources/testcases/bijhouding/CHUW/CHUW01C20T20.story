Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: R2334 Alleen een niet-vervallen voorkomen kan vervallen

Scenario:   Er vindt een correctie plaats op een niet-vervallen voorkomen
            LT: CHUW01C20T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-CHUW/CHUW01C20T20-001.xls
Given enkel initiele vulling uit bestand /LO3PL-CHUW/CHUW01C20T20-002.xls

When voer een bijhouding uit CHUW01C20T20a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/CHUW/expected/CHUW01C20T20a.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Given pas laatste relatie van soort 1 aan tussen persoon 894515081 en persoon 736988233 met relatie id 30010001 en betrokkenheid id 30010001
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

Then is in de database de persoon met bsn 894515081 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 736988233 wel als PARTNER betrokken bij een HUWELIJK

Then in kern heeft select statuslev from kern.admhnd where partij = 27012 and tslev is null de volgende gegevens:
| veld                      | waarde |
| statuslev                 | 1      |

Then in kern heeft select toelichtingontlening from kern.admhnd where toelichtingontlening is not null de volgende gegevens:
| veld                      | waarde                |
| toelichtingontlening      | test toelichting      |

When voer een bijhouding uit CHUW01C20T20b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/CHUW/expected/CHUW01C20T20b.xml voor expressie //brp:bhg_hgpCorrigeerHuwelijkGeregistreerdPartnerschap_R