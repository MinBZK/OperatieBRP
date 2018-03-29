Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: Verwerking Correctie Huwelijk

Scenario:   soort brondocument Geboorteakte
            LT: CHUW04C10T90

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-CHUW/CHUW04C10T90-001.xls
Given enkel initiele vulling uit bestand /LO3PL-CHUW/CHUW04C10T90-002.xls

When voer een bijhouding uit CHUW04C10T90a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/CHUW/expected/CHUW04C10T90a.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Given pas laatste relatie van soort 1 aan tussen persoon 355911401 en persoon 944335433 met relatie id 30010001 en betrokkenheid id 30010001
And de database is aangepast met: update kern.his_relatie set id = 9999 where id = (select hr.id from kern.his_relatie hr join kern.relatie r on r.id = hr.relatie where r.srt = 1 and hr.dataanv = 20160510)

Then is in de database de persoon met bsn 355911401 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 944335433 wel als PARTNER betrokken bij een HUWELIJK

When voer een bijhouding uit CHUW04C10T90b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/CHUW/expected/CHUW04C10T90b.xml voor expressie //brp:bhg_hgpCorrigeerHuwelijkGeregistreerdPartnerschap_R

!-- Controleer verantwoordingsgegevens van de ActieBron
Then in kern heeft select    count(1),
                             sdoc.naam
                   from      kern.actiebron ab
                   join      kern.actie ainh
		   on        ainh.id = ab.actie
                   left join kern.srtactie sainh
		   on        ainh.srt = sainh.id
                   left join kern.doc doc
		   on        doc.id = ab.doc
                   left join kern.srtdoc sdoc
		   on        sdoc.id = doc.srt
                   where     sainh.naam in ('Correctieverval relatie','Correctieregistratie relatie')
		   group by  sdoc.naam de volgende gegevens:
| veld   | waarde        |
| count  | 2             |
| naam   | Geboorteakte |