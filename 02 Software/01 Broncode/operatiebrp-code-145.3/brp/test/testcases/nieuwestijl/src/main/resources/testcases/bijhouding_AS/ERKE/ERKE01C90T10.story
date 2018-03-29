Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R2640 Te beëindigen materiëel historische groep mag niet al zijn beëindigd

Scenario:   De te beëindigen groep is reeds beëindigd.
            LT: ERKE01C90T10

Given alle personen zijn verwijderd

!-- Init. vulling van het kind met een materieel beëindigde nationaliteit en een OUWKIG
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-ERKE/ERKE01C90T10-001.xls

!-- Maak van de vorige NOUWKIG een kind zodat er niet teveel ouders zijn.
Given de database is aangepast met: update kern.betr set rol=1 where pers=(select id from kern.pers where voornamen='verwijder')

!-- Init. vulling van de erkennende NOUWKIG
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-ERKE/ERKE01C90T10-002.xls

Then heeft $NOUWKIG_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen = 'Vader'
Then heeft $KIND_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen = 'Kind'

Then heeft $RELATIE_ID$ de waarde van de volgende query: select r.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.voornamen='Kind' and r.srt=3
Then heeft $KIND_BETROKKENHEID$ de waarde van de volgende query: select b.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.voornamen='Kind' and r.srt=3

Then heeft $NATI_ID$ de waarde van de volgende query: select n.id from kern.persnation n join kern.pers p on p.id = n.pers where p.voornamen = 'Kind' and n.nation=(select id from kern.nation where code='0027')

!-- Erkenning van het kind
When voer een bijhouding uit ERKE01C90T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief

Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/ERKE/expected/ERKE01C90T10.xml voor expressie //brp:bhg_afsRegistreerErkenning_R