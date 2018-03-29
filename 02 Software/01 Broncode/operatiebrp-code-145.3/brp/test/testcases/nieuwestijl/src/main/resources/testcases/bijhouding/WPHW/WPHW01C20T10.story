Meta:
@status                 Bug
@usecase                UCS-BY.HG

Narrative: R2654 De relatie met de gerelateerde moet actueel zijn.

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

Scenario:   2. De partnergegevens worden gewijzigd bij een vervallen relatie
               LT: WPHW01C20T10


Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-WPHW/WPHW01C20T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-WPHW/WPHW01C20T10-002.xls

When voer een bijhouding uit WPHW01C20T10a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Then is in de database de persoon met bsn 981725193 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 757477641 wel als PARTNER betrokken bij een HUWELIJK

Given pas laatste relatie van soort 1 aan tussen persoon 981725193 en persoon 757477641 met relatie id 50000001 en betrokkenheid id 50000001
Given pas laatste relatie van soort 1 aan tussen persoon 757477641 en persoon 981725193 met relatie id 50000002 en betrokkenheid id 50000002

When voer een bijhouding uit WPHW01C20T10b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Then is in de database de persoon met bsn 981725193 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 757477641 niet als PARTNER betrokken bij een HUWELIJK

When voer een bijhouding uit WPHW01C20T10c.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/WPHW/expected/WPHW01C20T10.xml voor expressie //brp:bhg_hgpActualiseerHuwelijkGeregistreerdPartnerschap_R

Scenario: 3. DB reset
          postconditie

Given de database is aangepast met: delete from kern.actiebron where rechtsgrond in (select id from kern.rechtsgrond where code='111')
Given de database is aangepast met: delete from kern.rechtsgrond where code='111'

Given maak bijhouding caches leeg