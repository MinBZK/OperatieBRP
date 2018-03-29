Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: R2431 Rechtsgrond moet geldig zijn op systeemdatum

Scenario: 1. DB init
          preconditie

Given de database is aangepast met: insert into kern.rechtsgrond
                                    (
                                        "code",
					"oms",
					"indleidttotstrijdigheid",
				        "dataanvgel",
					"dateindegel"
				    )
				    select '111',
				           'Omschrijving rechtsgrond',
					    true,
					    to_number((to_char(now() - interval '1 day', 'YYYYMMDD')), '99999999'),
					    to_number((to_char(now(), 'YYYYMMDD')), '99999999')
				    where not exists
				    (
					select id
					from   kern.rechtsgrond
				        where  code='111'
				    )

Given maak bijhouding caches leeg

Scenario:   2. Rechtsgrond is vanaf vandaag niet meer geldig
            LT: ONHW02C10T50

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-ONHW/ONHW02C10.xls

Given pas laatste relatie van soort 1 aan tussen persoon 205966305 en persoon 109615621 met relatie id 70000002 en betrokkenheid id 70000002

When voer een bijhouding uit ONHW02C10T50.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/ONHW/expected/ONHW02C10T50.xml voor expressie //brp:bhg_hgpCorrigeerHuwelijkGeregistreerdPartnerschap_R

Scenario: 3. DB reset
          postconditie

Given de database is aangepast met: delete from kern.actiebron where rechtsgrond in (select id from kern.rechtsgrond where code='111')
Given de database is aangepast met: delete from kern.rechtsgrond where code='111'

Given maak bijhouding caches leeg