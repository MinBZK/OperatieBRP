Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R2799 Een Nationaliteit mag maximaal één keer voorkomen in het bericht

Scenario:   Nationaliteit komt twee keer voor in het bericht
            LT: ERKE02C60T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-ERKE/ERKE02C60T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-ERKE/ERKE02C60T10-002.xls

Then heeft $OUWKIG_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen='Moeder'
Then heeft $NOUWKIG_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen='Vader'

When voer een bijhouding uit ERKE02C60T10a.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

Then heeft $KIND_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen='Kind'

Then heeft $RELATIE_ID$ de waarde van de volgende query: select r.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.voornamen='Kind' and r.srt=3
Then heeft $KIND_BETROKKENHEID$ de waarde van de volgende query: select b.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.voornamen='Kind' and r.srt=3
Then heeft $NATI_ID$ de waarde van de volgende query: select n.id from kern.persnation n join kern.pers p on p.id = n.pers where p.voornamen='Kind'

!-- Dezelfde nationaliteit wordt twee keer beëindigd in hetzelfde bericht. De regelmelding wordt twee keer gegeven
!-- omdat de datum einde gelijk moet zijn aan de datum erkenning waardoor niet te zien is welke einde ongeldig is.
When voer een bijhouding uit ERKE02C60T10b.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/ERKE/expected/ERKE02C60T10.xml voor expressie //brp:bhg_afsRegistreerErkenning_R