Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1727 Geslachtsnaam Nederlands kind moet overeenkomen met die van eerder kind uit dezelfde relatie

Scenario:   Nederlands kind heeft een andere geslachtsnaam dan de sibling
            LT: ERKE01C150T10

Given alle personen zijn verwijderd

!-- Init. vulling van de OUWKIG
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-ERKE/ERKE01C150T10-001.xls

!-- Init. vulling van de NOUWKIG
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-ERKE/ERKE01C150T10-002.xls

Then heeft $OUWKIG_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen = 'Moeder'
Then heeft $NOUWKIG_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen = 'Vader'

!-- Geboorte van 2 kinderen met verschillende achternamen. Deblokkeer R1726 en R1727 voor de sibling.
When voer een bijhouding uit ERKE01C150T10a.xml namens partij 'Gemeente BRP 1'
When voer een bijhouding uit ERKE01C150T10b.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

Then heeft $KIND_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen = 'Kind'

Then heeft $RELATIE_ID$ de waarde van de volgende query: select r.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.voornamen = 'Kind' and r.srt=3
Then heeft $KIND_BETROKKENHEID$ de waarde van de volgende query: select b.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.voornamen = 'Kind' and r.srt=3

!-- Erkenning door een NOUWKIG van het kind die een andere achternaam heeft dan de sibling
When voer een bijhouding uit ERKE01C150T10c.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/ERKE/expected/ERKE01C150T10.xml voor expressie //brp:bhg_afsRegistreerErkenning_R
