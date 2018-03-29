Meta:
@status                 Bug
@usecase                UCS-BY.0.AS

Narrative: R2011 Voor een persoon met de Nederlandse nationaliteit wordt uitsluitend deze nationaliteit bijgehouden

Scenario:   Persoon heeft indicatie behandeld als NL en bij erkenning wordt een NL nationaliteit geregistreerd
            LT: ERKE01C180T50

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-ERKE/ERKE01C180T50-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-ERKE/ERKE01C180T50-002.xls

Then heeft $KIND_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen = 'Marie'
Then heeft $NOUWKIG_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen = 'Henk'


Then heeft $RELATIE_ID$ de waarde van de volgende query: select r.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.voornamen = 'Marie' and r.srt=3
Then heeft $KIND_BETROKKENHEID$ de waarde van de volgende query: select b.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.voornamen = 'Marie' and r.srt=3


When voer een bijhouding uit ERKE01C180T50.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/ERKE/expected/ERKE01C180T50.xml voor expressie //brp:bhg_afsRegistreerErkenning_R