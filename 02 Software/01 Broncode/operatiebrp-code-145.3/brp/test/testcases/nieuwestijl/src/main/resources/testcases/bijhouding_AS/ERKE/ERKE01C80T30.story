Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1691 Minderjarig kind moet Nederlandse nationaliteit hebben als een ouder ook de Nederlandse nationaliteit heeft

Scenario:   Bestaande NOUWKIG is overleden bij de erkenning
            LT: ERKE01C80T30

Given alle personen zijn verwijderd

!-- Init. vulling van het kind met een OUWKIG en eerste NOUWKIG
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-ERKE/ERKE01C80T30-001.xls

!-- Init. vulling van een overleden NOUWKIG
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-ERKE/ERKE01C80T30-002.xls

!-- Vervang de levende NOUWKIG door de overleden NOUWKIG
And de database is aangepast met: update kern.betr
                                  set    pers = (select id from kern.pers where voornamen='OverledenVader')
				  where  pers = (select id from kern.pers where voornamen='Vader');

!-- Init. vulling van de tweede NOUWKIG die het kind erkent
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-ERKE/ERKE01C80T30-003.xls

Then heeft $KIND_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen = 'Marie'
Then heeft $ERKENNENDE_NOUWKIG_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen = 'Henk'

Then heeft $RELATIE_ID$ de waarde van de volgende query: select r.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.voornamen = 'Marie' and r.srt=3
Then heeft $KIND_BETROKKENHEID$ de waarde van de volgende query: select b.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.voornamen = 'Marie' and r.srt=3

!-- Erkenning van het kind
When voer een bijhouding uit ERKE01C80T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/ERKE/expected/ERKE01C80T30.xml voor expressie //brp:bhg_afsRegistreerErkenning_R