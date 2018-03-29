Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1692 Een te verkrijgen nationaliteit mag nog niet voorkomen bij persoon

Scenario:   Een te verkrijgen nationaliteit komt voor bij een geborene
            LT: ERKE01C120T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-ERKE/ERKE01C120T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-ERKE/ERKE01C120T10-002.xls

When voer een bijhouding uit ERKE01C120T10a.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

Then heeft $RELATIE_ID$ de waarde van de volgende query: select r.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.bsn in ('264484009') and r.srt=3
Then heeft $KIND_BETROKKENHEID$ de waarde van de volgende query: select b.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.bsn in ('264484009') and r.srt=3

When voer een bijhouding uit ERKE01C120T10b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/ERKE/expected/ERKE01C120T10.xml voor expressie /