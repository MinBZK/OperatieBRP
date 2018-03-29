Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1731 Kind moet een predicaat of adellijke titel hebben als de vader met identieke geslachtsnaam dat ook heeft

Scenario:   Ingeschrevene (man) heeft adellijke titel en Persoon.Samengestelde naam gelijk aan Kind, Kind geen adellijke titel
            LT: ERKE01C170T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-ERKE/ERKE01C170T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-ERKE/ERKE01C170T10-002.xls

!-- Geboorte van een kind
When voer een bijhouding uit ERKE01C170T10a.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

Then heeft $RELATIE_ID$ de waarde van de volgende query: select r.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.bsn in ('264484009') and r.srt=3
Then heeft $KIND_BETROKKENHEID$ de waarde van de volgende query: select b.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.bsn in ('264484009') and r.srt=3

!-- Erkenning van het kind zonder adellijke titel bij een NOUWKIG met adellijke titel
When voer een bijhouding uit ERKE01C170T10b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/ERKE/expected/ERKE01C170T10.xml voor expressie /