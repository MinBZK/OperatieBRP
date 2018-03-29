Meta:
@status                 Klaar
@usecase                UCS-BY.HG


Narrative: R2432 Rechtsgrond verplicht bij nadere aanduiding verval 'S'

Scenario: 1. DB init
          preconditie

Given de database is aangepast met: insert into kern.rechtsgrond
                                    (
                                        "code",
					"oms",
					"indleidttotstrijdigheid",
				        "dataanvgel"
				    )
				    select '111',
				           'Omschrijving rechtsgrond',
					    true,
					   '19001122'
				    where not exists
				    (
					select id
					from   kern.rechtsgrond
				        where  code='111'
				    )

Given maak bijhouding caches leeg


Scenario:   2. Nadere aanduiding verval is gevuld met de waarde "S" en één rechtsgrond opgegeven die de waarde J heeft
               LT: ONHW01C30T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-ONHW/ONHW-001.xls

Given pas laatste relatie van soort 1 aan tussen persoon 661372169 en persoon 512400969 met relatie id 50010001 en betrokkenheid id 50010001

When voer een bijhouding uit ONHW01C30T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/ONHW/expected/ONHW01C30T20.xml voor expressie //brp:bhg_hgpCorrigeerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 661372169 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 512400969 niet als PARTNER betrokken bij een HUWELIJK

Scenario: 3. DB reset
          postconditie

Given de database is aangepast met: delete from kern.actiebron where rechtsgrond in (select id from kern.rechtsgrond where code='111')
Given de database is aangepast met: delete from kern.rechtsgrond where code='111'

Given maak bijhouding caches leeg