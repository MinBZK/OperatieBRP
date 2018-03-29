Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: Verwerking Correctie Geregistreerd partnerschap

Scenario:   Het laten herleven van een Einde Geregistreerd partnerschap
            LT: CGPS04C10T50

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-CGPS/CGPS04C10T50-001.xls
Given enkel initiele vulling uit bestand /LO3PL-CGPS/CGPS04C10T50-002.xls

!-- Voltrekking van een geregistreerd partnerschap
When voer een bijhouding uit CGPS04C10T50a.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

Given pas laatste relatie van soort 2 aan tussen persoon 902502281 en persoon 596294761 met relatie id 30010001 en betrokkenheid id 30010001
And de database is aangepast met: update kern.his_relatie
                                  set    id = 9999
				  where  id = (
				                  select hr.id
						  from   kern.his_relatie hr
						  join   kern.relatie r
						  on     r.id = hr.relatie
						  where  r.srt = 2
						  and    hr.dataanv = 20160510
					      )

Then is in de database de persoon met bsn 902502281 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 596294761 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

!-- Ontbinding geregistreerd partnerschap
When voer een bijhouding uit CGPS04C10T50b.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

Then is in de database de persoon met bsn 902502281 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 596294761 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

!-- De niet-vervallen rij gereed maken om als voorkomensleutel te gebruiken
Given de database is aangepast met: update kern.his_relatie
                                    set    id=9998
				    where  relatie=30010001
				    and    id<>9999
				    and    tsverval is null

!-- Correctie registratie geregistreerd partnerschap
When voer een bijhouding uit CGPS04C10T50c.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Then is in de database de persoon met bsn 902502281 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 596294761 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

Then is het antwoordbericht gelijk aan /testcases/bijhouding/CGPS/expected/CGPS04C10T50.xml voor expressie //brp:bhg_hgpCorrigeerHuwelijkGeregistreerdPartnerschap_R
