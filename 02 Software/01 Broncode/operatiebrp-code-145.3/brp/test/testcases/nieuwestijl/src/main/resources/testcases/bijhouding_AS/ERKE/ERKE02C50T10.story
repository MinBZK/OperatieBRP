Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R2527 Datum einde geldigheid van de actie mag niet in de toekomst liggen

Scenario:   DEG ligt op vandaag van einde nationaliteit
            LT: ERKE02C50T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-ERKE/ERKE02C50T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-ERKE/ERKE02C50T10-002.xls

Then heeft $OUWKIG_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen = 'Marie'
Then heeft $NOUWKIG_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen = 'Henk'

When voer een bijhouding uit ERKE02C50T10a.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

Then heeft $RELATIE_ID$ de waarde van de volgende query: select r.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.voornamen = 'Jan' and r.srt=3
Then heeft $KIND_BETROKKENHEID$ de waarde van de volgende query: select b.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.voornamen = 'Jan' and r.srt=3
Then heeft $NATI_ID$ de waarde van de volgende query: select n.id from kern.persnation n join kern.pers p on p.id = n.pers where p.bsn = '642593097'

When voer een bijhouding uit ERKE02C50T10b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/ERKE/expected/ERKE02C50T10.xml voor expressie //brp:bhg_afsRegistreerErkenning_R