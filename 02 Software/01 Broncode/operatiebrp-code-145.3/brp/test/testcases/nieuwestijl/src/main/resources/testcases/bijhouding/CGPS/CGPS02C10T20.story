Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: R2478 Reden einde en Landgebied einde verplicht als Datum einde is gevuld

Scenario:   Reden einde is niet ingevuld. Landgebied einde en Datum einde zijn ingevuld.
            LT: CGPS02C10T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-CGPS/CGPS02C10T20-001.xls
Given enkel initiele vulling uit bestand /LO3PL-CGPS/CGPS02C10T20-002.xls

!-- Aangaan geregistreerd partnerschap in Nederland
When voer een bijhouding uit CGPS02C10T20a.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

Given pas laatste relatie van soort 2 aan tussen persoon 573833801 en persoon 755185353 met relatie id 30010001 en betrokkenheid id 30010001
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

Then is in de database de persoon met bsn 573833801 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 755185353 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

!-- Einde geregistreerd partnerschap
When voer een bijhouding uit CGPS02C10T20b.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

Then is in de database de persoon met bsn 573833801 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 755185353 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

!-- De niet-vervallen rij gereed maken om als voorkomensleutel te gebruiken
Given de database is aangepast met: update kern.his_relatie
                                    set    id=9998
				    where  relatie=30010001
				    and    id<>9999
				    and    tsverval is null

!-- Correctie registratie geregistreerd partnerschap
When voer een bijhouding uit CGPS02C10T20c.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/CGPS/expected/CGPS02C10T20c.xml voor expressie //brp:bhg_hgpCorrigeerHuwelijkGeregistreerdPartnerschap_R