Meta:
@auteur                 tjlee
@status                 Klaar
@sleutelwoorden         Geslaagd
@usecase                UCS-BY.HG

Narrative:
Stamgegevens kunnen worden gecached.

Scenario:   In de eerste bijhouding wordt de database gebruikt voor stamgegeven gemeentecode, in de tweede bijhouding de cache. De derde bijhouding is niet mogelijk omdat de cache opgeschoond is.
            LT: VHNL07C10T10

Given alle personen zijn verwijderd
Given maak bijhouding caches leeg
And de database is aangepast met: delete from kern.gem where naam='Cache';
And de database is aangepast met: insert into kern.gem (naam,code,partij,dataanvgel) values ('Cache','9900','7','19200101');

Given enkel initiele vulling uit bestand /LO3PL/VHNL07C10T10-Libby.xls

When voer een bijhouding uit VHNL07C10T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
And is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL07C10T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 450650121 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 261702233 wel als PARTNER betrokken bij een HUWELIJK




Given alle personen zijn verwijderd

Given de database is aangepast met: update kern.gem set code = '9901' where naam = 'Cache'

Given enkel initiele vulling uit bestand /LO3PL/VHNL07C10T10-Libby.xls

When voer een bijhouding uit VHNL07C10T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
And is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL07C10T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 450650121 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 261702233 wel als PARTNER betrokken bij een HUWELIJK




Given maak bijhouding caches leeg




Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL/VHNL07C10T10-Libby.xls

When voer een bijhouding uit VHNL07C10T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
And is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL07C10T10a.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 450650121 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 261702233 niet als PARTNER betrokken bij een HUWELIJK

Given de database is aangepast met: delete from kern.gem where naam = 'Cache'
