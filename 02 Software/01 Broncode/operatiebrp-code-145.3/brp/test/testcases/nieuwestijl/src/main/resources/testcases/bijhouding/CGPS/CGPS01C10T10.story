Meta:
@status                 Klaar
@usecase                UCS-BY.0.HGP

Narrative: R1845 In het bijhoudingsbericht gerelateerde objectsleutels en voorkomensleutels moeten verwijzen naar gerelateerde objecten en voorkomens in de BRP

Scenario:   Voorkomensleutel in het bericht verwijst niet naar een bestaand voorkomen
            LT: CGPS01C10T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-CGPS/CGPS01C10T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-CGPS/CGPS01C10T10-002.xls

When voer een bijhouding uit CGPS01C10T10a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Given pas laatste relatie van soort 2 aan tussen persoon 512741001 en persoon 747048137 met relatie id 30010001 en betrokkenheid id 30010001
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

Then is in de database de persoon met bsn 512741001 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 747048137 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

When voer een bijhouding uit CGPS01C10T10b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/CGPS/expected/CGPS01C10T10.xml voor expressie //brp:bhg_hgpCorrigeerHuwelijkGeregistreerdPartnerschap_R