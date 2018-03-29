Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative:
Ongedaan maken huwelijk, hoofdactie verval huwelijk

Scenario:   Ongedaanmaking huwelijk (feitgemeente BRP) waarbij 1 partner een GBA bijhoudingsgemeente is
            LT: ONHW04C10T110

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-ONHW/ONHW04C10T110-001.xls
Given enkel initiele vulling uit bestand /LO3PL-ONHW/ONHW04C10T110-002.xls

When voer een bijhouding uit ONHW04C10T110a.xml namens partij 'Gemeente BRP 1'

Given pas laatste relatie van soort 1 aan tussen persoon 149726661 en persoon 928460617 met relatie id 50000001 en betrokkenheid id 50000001
Then heeft het antwoordbericht verwerking Geslaagd

Then staat er 1 notificatiebericht voor bijhouders op de queue

Then is in de database de persoon met bsn 149726661 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 928460617 wel als PARTNER betrokken bij een HUWELIJK

Given de database is aangepast met: update kern.partij set datovergangnaarbrp = NULL where naam = 'Gemeente BRP 3'

When voer een bijhouding uit ONHW04C10T110b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Then is in de database de persoon met bsn i:149726661 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn i:928460617 wel als PARTNER betrokken bij een HUWELIJK

Then staat er 1 notificatiebericht voor bijhouders op de queue

Scenario:   3. DB reset scenario om de aangepaste data weer terug te zetten
            postconditie

Given de database is aangepast met: update kern.partij set datovergangnaarbrp = 20160101 where naam = 'Gemeente BRP 3'


