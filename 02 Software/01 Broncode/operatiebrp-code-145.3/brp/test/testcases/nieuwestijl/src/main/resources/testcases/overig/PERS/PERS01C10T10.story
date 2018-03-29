Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: Persoonsbeelden GBA en BRP (huwelijk en ontbinding)

Scenario:   Huwelijk tussen BRP persoon en een BRP pseudo
            LT: PERS01C10T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-PERS/PERS01C10T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-PERS/PERS01C10T10-002.xls

Given de database is aangepast met: update kern.gem set dataanvgel = '19800101' where naam = 'Gemeente BRP 1'
And de database is aangepast met: update kern.partij set datingang = '19800101' where naam = 'Gemeente BRP 1'

!-- Geboorte Libby-Marie
When voer een bijhouding uit PERS01C10T10a.xml namens partij 'Gemeente BRP 1'


!-- Huwelijk Libby-Marie (I) en Pieter (P)
When voer een bijhouding uit PERS01C10T10b.xml namens partij 'Gemeente BRP 1'

Then is in de database de persoon met bsn 306706921 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 793558025 wel als PARTNER betrokken bij een HUWELIJK

Scenario:   3. DB reset scenario om de aangepaste data weer terug te zetten
            postconditie

Given de database is aangepast met: update kern.gem set dataanvgel = '20160101' where naam = 'Gemeente BRP 1'
And de database is aangepast met: update kern.partij set datingang = '20160101' where naam = 'Gemeente BRP 1'






