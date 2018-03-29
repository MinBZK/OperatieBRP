Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: Verwerking Correctie Huwelijk

Scenario:   Correctie aanvang huwelijk naar het verleden met een pseudo persoon.
            LT: CHUW04C10T130

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-CHUW/CHUW04C10T130-001.xls

When voer een bijhouding uit CHUW04C10T130a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Given pas laatste relatie van soort 1 aan tussen persoon 953977481 en persoon 745853705 met relatie id 30010001 en betrokkenheid id 30010001
And de database is aangepast met: update kern.his_relatie set id = 9999 where id = (select hr.id from kern.his_relatie hr join kern.relatie r on r.id = hr.relatie where r.srt = 1 and hr.dataanv = 20160510)

Then is in de database de persoon met bsn 953977481 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 745853705 wel als PARTNER betrokken bij een HUWELIJK

When voer een bijhouding uit CHUW04C10T130b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

