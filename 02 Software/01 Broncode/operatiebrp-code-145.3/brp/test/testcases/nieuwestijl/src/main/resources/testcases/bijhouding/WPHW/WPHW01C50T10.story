Meta:
@status                 Klaar
@sleutelwoorden         Geslaagd
@usecase                UCS-BY.HG

Narrative: R2784 Datum aanvang geldigheid van identificatienummers, samengestelde naam en geslachtsaanduiding moeten overeen komen

Scenario:  Persoon Identificatienummers Datum aanvang geldigheid anders dan andere datums
            LT: WPHW01C50T10


Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-WPHW/WPHW01C50T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-WPHW/WPHW01C50T10-002.xls

When voer een bijhouding uit WPHW01C50T10a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Then is in de database de persoon met bsn 563420777 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 366970409 wel als PARTNER betrokken bij een HUWELIJK

Given pas laatste relatie van soort 1 aan tussen persoon 563420777 en persoon 366970409 met relatie id 50000001 en betrokkenheid id 50000001
Given pas laatste relatie van soort 1 aan tussen persoon 366970409 en persoon 563420777 met relatie id 50000002 en betrokkenheid id 50000002

When voer een bijhouding uit WPHW01C50T10b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/WPHW/expected/WPHW01C50T10.xml voor expressie /
