Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1732 Ouder mag geen kinderen hebben met identieke personalia

Scenario:   De geboortedatum en personalia van het kind en pseudo-persoon kind zijn gelijk
            LT: ERKE01C190T20

Given alle personen zijn verwijderd

!-- Vulling van de OUWKIG
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-ERKE/ERKE01C190T20-001.xls

!-- Vulling van de NOUWKIG met een pseudo-persoon kind
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-ERKE/ERKE01C190T20-002.xls

Then heeft $OUWKIG_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen = 'Moeder'
Then heeft $NOUWKIG_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen = 'Vader'

!-- Geboorte van het kind van de OUWKIG
When voer een bijhouding uit ERKE01C190T20a.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

Then heeft $KIND_BSN$ de waarde van de volgende query: select bsn from kern.pers where bsn = '122283855'
Then heeft $RELATIE_ID$ de waarde van de volgende query: select r.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.voornamen='Kind' and r.srt=3
Then heeft $KIND_BETROKKENHEID$ de waarde van de volgende query: select b.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.voornamen='Kind' and r.srt=3

!-- Erkenning van het kind door NOUWKIG die al een pseudo-persoon kind heeft met dezelfde personalia en geboortedatum
When voer een bijhouding uit ERKE01C190T20b.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Foutief

Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/ERKE/expected/ERKE01C190T20.xml voor expressie /