Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1733 Tussen ouders mag geen verwantschap bestaan

Scenario: kind wordt geboren met ouders die ouder-kind van elkaar worden na de geboorte van het kind
          LT: GBNL01C140T40

Given alle personen zijn verwijderd

!-- vulling van OUWKIG
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C140T40-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C140T40-002.xls

!-- geboorte van kind van latere OUWKIG
When voer een bijhouding uit GBNL01C140T40a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Then heeft $RELATIE_ID$ de waarde van de volgende query: select r.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.bsn in ('455083241') and r.srt=3
Then heeft $KIND_BETROKKENHEID$ de waarde van de volgende query: select b.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.bsn in ('455083241') and r.srt=3

!-- erkenning van latere OUWKIG door NOUWKIG (vader - kind relatie) met een DAG later dan de geboorte van kind
When voer een bijhouding uit GBNL01C140T40b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

!-- geboorte van kind met eerdere DAG dan de DAG van erkenning
When voer een bijhouding uit GBNL01C140T40c.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL01C140T40.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R