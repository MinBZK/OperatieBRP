Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: R2430 Rechtsgrond moet verwijzen naar bestaand stamgegeven

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

Scenario:   2. Rechtsgrond verwijst niet naar bestaand stamgegeven
            LT: ONHW03C10T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-ONHW/ONHW-001.xls

Given pas laatste relatie van soort 1 aan tussen persoon 661372169 en persoon 512400969 met relatie id 70000001 en betrokkenheid id 70000001

When voer een bijhouding uit ONHW03C10T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/ONHW/expected/ONHW03C10T10.xml voor expressie //brp:bhg_hgpCorrigeerHuwelijkGeregistreerdPartnerschap_R

Scenario: 3. DB reset
          postconditie

Given de database is aangepast met: delete from kern.actiebron where rechtsgrond in (select id from kern.rechtsgrond where code='111')
Given de database is aangepast met: delete from kern.rechtsgrond where code='111'

Given maak bijhouding caches leeg