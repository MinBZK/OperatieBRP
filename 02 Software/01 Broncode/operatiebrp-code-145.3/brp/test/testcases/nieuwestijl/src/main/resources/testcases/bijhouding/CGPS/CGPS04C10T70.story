Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: Verwerking Correctie geregistreerd partnerschap

Scenario:   IST-gegevens worden verwijderd na een Correctie van een GBA-GP
            LT: CGPS04C10T70

Given alle personen zijn verwijderd
!-- Een PL met een GBA-geregistreerd partnerschap
Given enkel initiele vulling uit bestand /LO3PL-CGPS/CGPS04C10T70-001.xls

!-- Controleer de aanwezigheid van het GBA-GP in ist.stapelvoorkomen
Then in kern heeft select count(*)
                  from   ist.stapelvoorkomen
		  where  docoms='Akte van partnerschapsregistratie' de volgende gegevens:
| veld  | waarde |
| count | 1      |

Given pas laatste relatie van soort 2 aan tussen persoon 342729081 en persoon 525858313 met relatie id 30010001 en betrokkenheid id 30010001
And de database is aangepast met: update kern.his_relatie
                                  set    id = 9999
				  where  id = (
				                  select hr.id
						  from   kern.his_relatie hr
						  join   kern.relatie r
						  on     r.id = hr.relatie
						  where  r.srt = 2
					      )

!-- Voer een Correctie uit.
When voer een bijhouding uit CGPS04C10T70.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

!-- Controleer dat het GP uit ist.stapelvoorkomen is verwijderd
Then in kern heeft select count(*)
                  from   ist.stapelvoorkomen
		  where  docoms='Akte van partnerschapsregistratie' de volgende gegevens:
| veld  | waarde |
| count | 0      |