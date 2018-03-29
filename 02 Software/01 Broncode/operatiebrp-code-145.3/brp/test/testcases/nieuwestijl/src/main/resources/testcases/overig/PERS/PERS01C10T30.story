Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: Persoonsbeelden GBA en BRP (huwelijk en ontbinding)

Scenario:   Huwelijk tussen BRP persoon en een BRP pseudo en daarna ontbinding (historie)
            LT: PERS01C10T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-PERS/PERS01C10T30-001.xls
Given enkel initiele vulling uit bestand /LO3PL-PERS/PERS01C10T30-002.xls

Given de database is aangepast met: update kern.gem set dataanvgel = '19800101' where naam = 'Gemeente BRP 1'
And de database is aangepast met: update kern.partij set datingang = '19800101' where naam = 'Gemeente BRP 1'

!-- Geboorte Libby-Marie
When voer een bijhouding uit PERS01C10T30a.xml namens partij 'Gemeente BRP 1'


!-- Huwelijk Libby-Marie (I) en Pieter (P)
When voer een bijhouding uit PERS01C10T30b.xml namens partij 'Gemeente BRP 1'

Then is in de database de persoon met bsn 803995209 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 793558025 wel als PARTNER betrokken bij een HUWELIJK

!-- Ontbinding Libby-Marie (I) en Pieter (P)

Given pas laatste relatie van soort 1 aan tussen persoon 803995209 en persoon 793558025 met relatie id 70000001 en betrokkenheid id 70000001

When voer een bijhouding uit PERS01C10T30c.xml namens partij 'Gemeente BRP 1'

Then is in de database de persoon met bsn 803995209 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 793558025 niet als PARTNER betrokken bij een HUWELIJK

Scenario:   3. DB reset scenario om de aangepaste data weer terug te zetten
            postconditie

Given de database is aangepast met: update kern.gem set dataanvgel = '20160101' where naam = 'Gemeente BRP 1'
And de database is aangepast met: update kern.partij set datingang = '20160101' where naam = 'Gemeente BRP 1'






