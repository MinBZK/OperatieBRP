Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R2643 Bij onbeëindigde staatloosheid is registratie nationaliteit uitsluitend toegestaan als de DAG daarvan na de DAG van staatloos ligt

Scenario:   Onbeëindigde staatloosheid en registratie nationaliteit DAG na staatloos DAG
            LT: ERKE01C100T10

Given alle personen zijn verwijderd

!-- Init. vulling van de OUWKIG en NOUWKIG
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-ERKE/ERKE01C100T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-ERKE/ERKE01C100T10-002.xls

Then heeft $OUWKIG_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen = 'Moeder'
Then heeft $NOUWKIG_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen = 'Vader'

!-- Geboorte van het kind
When voer een bijhouding uit ERKE01C100T10a.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

Then heeft $KIND_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen = 'Kind'
Then heeft $RELATIE_ID$ de waarde van de volgende query: select r.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.voornamen='Kind' and r.srt=3
Then heeft $KIND_BETROKKENHEID$ de waarde van de volgende query: select b.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.voornamen='Kind' and r.srt=3

!-- Erkenning van het kind
When voer een bijhouding uit ERKE01C100T10b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/ERKE/expected/ERKE01C100T10.xml voor expressie //brp:bhg_afsRegistreerErkenning_R