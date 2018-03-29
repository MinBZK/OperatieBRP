Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R2582 Waarschuwing indien het verkrijgen van de Nederlandse nationaliteit gevolgen heeft voor kind(eren)

Scenario:   Bij erkenning krijgt de erkende de NL nationaliteit en heeft een kind
            LT: ERKE01C70T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-ERKE/ERKE01C70T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-ERKE/ERKE01C70T10-002.xls

Then heeft $KIND_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen = 'Marie'
Then heeft $NOUWKIG_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen = 'Henk'


Then heeft $RELATIE_ID$ de waarde van de volgende query: select r.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.voornamen = 'Marie' and r.srt=3
Then heeft $KIND_BETROKKENHEID$ de waarde van de volgende query: select b.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.voornamen = 'Marie' and r.srt=3 and b.rol =1


When voer een bijhouding uit ERKE01C70T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/ERKE/expected/ERKE01C70T10.xml voor expressie //brp:bhg_afsRegistreerErkenning_R