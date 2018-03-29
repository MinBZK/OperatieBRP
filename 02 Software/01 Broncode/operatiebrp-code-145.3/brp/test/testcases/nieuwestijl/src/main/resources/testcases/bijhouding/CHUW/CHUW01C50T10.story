Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: R1606 Brondocument moet geldig zijn voor de administratieve handeling en actie

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
					    to_number((to_char(now() - interval '1 day', 'YYYYMMDD')), '99999999')
				    where not exists
				    (
					select id
					from   kern.rechtsgrond
				        where  code='111'
				    )

Given maak bijhouding caches leeg

Scenario:   2. Documentsoort heeft ongeldige waarde, Actie en administratie handeling hebben een geldige waarde
            LT: CHUW01C50T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-CHUW/CHUW01C50T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-CHUW/CHUW01C50T10-002.xls

!-- Voltrekking huwelijk in Nederland
When voer een bijhouding uit CHUW01C50T10a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Given pas laatste relatie van soort 1 aan tussen persoon 469349001 en persoon 946821641 met relatie id 30010001 en betrokkenheid id 30010001
And de database is aangepast met: update kern.his_relatie set id = 9999 where id = (select hr.id from kern.his_relatie hr join kern.relatie r on r.id = hr.relatie where r.srt = 1 and hr.dataanv = 20160510)

Then is in de database de persoon met bsn 469349001 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 946821641 wel als PARTNER betrokken bij een HUWELIJK

!-- Correctie huwelijk
When voer een bijhouding uit CHUW01C50T10b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/CHUW/expected/CHUW01C50T10.xml voor expressie //brp:bhg_hgpCorrigeerHuwelijkGeregistreerdPartnerschap_R

Scenario: 3. DB reset
          postconditie

Given de database is aangepast met: delete from kern.actiebron where rechtsgrond in (select id from kern.rechtsgrond where code='111')
Given de database is aangepast met: delete from kern.rechtsgrond where code='111'

Given maak bijhouding caches leeg