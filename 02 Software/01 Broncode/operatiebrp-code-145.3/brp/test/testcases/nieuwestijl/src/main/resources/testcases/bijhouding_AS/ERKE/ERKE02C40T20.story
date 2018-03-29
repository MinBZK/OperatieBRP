Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1697 Persoon met de Nederlandse nationaliteit moet een voornaam hebben.

Scenario:   Erkenning met registratie niet NL nationaliteit zonder voornaam
            LT: ERKE02C40T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-ERKE/ERKE02C40T20-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-ERKE/ERKE02C40T20-002.xls

Then heeft $OUWKIG_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen = 'Marie'
Then heeft $NOUWKIG_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen = 'Henk'

When voer een bijhouding uit ERKE02C40T20a.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

Then heeft $RELATIE_ID$ de waarde van de volgende query: select r.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.datgeboorte = '20160101' and r.srt=3
Then heeft $KIND_BETROKKENHEID$ de waarde van de volgende query: select b.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.datgeboorte = '20160101' and r.srt=3
Then heeft $NATI_ID$ de waarde van de volgende query: select n.id from kern.persnation n join kern.pers p on p.id = n.pers where p.bsn = '307173513'

When voer een bijhouding uit ERKE02C40T20b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
