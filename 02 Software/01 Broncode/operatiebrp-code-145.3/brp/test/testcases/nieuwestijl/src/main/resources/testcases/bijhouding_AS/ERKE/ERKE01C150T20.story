Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1727 Geslachtsnaam Nederlands kind moet overeenkomen met die van eerder kind uit dezelfde relatie

Scenario:   Nederlands kind heeft een andere geslachtsnaam dan de sibling bij RegistratieGeslachstnaamVoornaam
            LT: ERKE01C150T20

Given alle personen zijn verwijderd

!-- Init. vulling van de OUWKIG en NOUWKIG
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-ERKE/ERKE01C150T20-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-ERKE/ERKE01C150T20-002.xls

Then heeft $OUWKIG_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen = 'Moeder'
Then heeft $NOUWKIG_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen = 'Vader'

!-- Geboorte van 2 kinderen
When voer een bijhouding uit ERKE01C150T20a.xml namens partij 'Gemeente BRP 1'
When voer een bijhouding uit ERKE01C150T20b.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

Then heeft $KIND_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen = 'Kind'

Then heeft $RELATIE_ID$ de waarde van de volgende query: select r.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.voornamen = 'Kind' and r.srt=3
Then heeft $KIND_BETROKKENHEID$ de waarde van de volgende query: select b.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.voornamen = 'Kind' and r.srt=3

!-- Erkenning door een NOUWKIG van de sibling met aanpassing van de achternaam tot dezelfde als de achternaam van de NOUWKIG
When voer een bijhouding uit ERKE01C150T20c.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/ERKE/expected/ERKE01C150T20.xml voor expressie //brp:bhg_afsRegistreerErkenning_R
