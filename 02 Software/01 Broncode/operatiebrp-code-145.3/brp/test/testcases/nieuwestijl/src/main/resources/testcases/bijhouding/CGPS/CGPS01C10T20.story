Meta:
@status                 Klaar
@usecase                UCS-BY.0.HGP

Narrative: R1845 In het bijhoudingsbericht gerelateerde objectsleutels en voorkomensleutels moeten verwijzen naar gerelateerde objecten en voorkomens in de BRP

Scenario:   Voorkomensleutel in het bericht verwijst niet naar een bestaand voorkomen
            LT: CGPS01C10T20

Given alle personen zijn verwijderd
!-- Vulling van de personen voor een Geregistreerd partnerschap
Given enkel initiele vulling uit bestand /LO3PL-CGPS/CGPS01C10T20-001.xls
Given enkel initiele vulling uit bestand /LO3PL-CGPS/CGPS01C10T20-002.xls

!-- Vulling van personen voor een Huwelijk
Given enkel initiele vulling uit bestand /LO3PL-CGPS/CGPS01C10T20-003.xls
Given enkel initiele vulling uit bestand /LO3PL-CGPS/CGPS01C10T20-004.xls

!-- Aangaan Geregistreerd partnerschap
When voer een bijhouding uit CGPS01C10T20a.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

Given pas laatste relatie van soort 2 aan tussen persoon 710215241 en persoon 960185033 met relatie id 30010001 en betrokkenheid id 30010001
And de database is aangepast met: update kern.his_relatie
                                  set    id = 9998
				  where  id = (
				                  select hr.id
						  from   kern.his_relatie hr
						  join   kern.relatie r
						  on     r.id = hr.relatie
						  where  r.srt = 2
						  and    hr.dataanv = 20160510
					      )

Then is in de database de persoon met bsn 710215241 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 960185033 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

!-- Voltrekking Huwelijk
When voer een bijhouding uit CGPS01C10T20b.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

Given pas laatste relatie van soort 1 aan tussen persoon 314205561 en persoon 276436313 met relatie id 30010002 en betrokkenheid id 30010002
And de database is aangepast met: update kern.his_relatie
                                  set    id = 9999
				  where  id = (
				                  select hr.id
						  from   kern.his_relatie hr
						  join   kern.relatie r
						  on     r.id = hr.relatie
						  where  r.srt = 1
						  and    hr.dataanv = 20160510
					      )

Then is in de database de persoon met bsn 314205561 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 276436313 wel als PARTNER betrokken bij een HUWELIJK

!-- Een correctie van het GP met een voorkomensleutel van het huwelijk
When voer een bijhouding uit CGPS01C10T20c.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/CGPS/expected/CGPS01C10T20.xml voor expressie //brp:bhg_hgpCorrigeerHuwelijkGeregistreerdPartnerschap_R