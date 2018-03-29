Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R2736 Waarschuwing dat de naamswijziging niet doorwerkt in de Samengestelde naam en het Naamgebruik

Scenario:   Persoon Geslachtsnaamcomponent wijzigt en - Persoon.Afgeleid? de waarde "Nee"
            LT: ERKE01C220T20

Given alle personen zijn verwijderd

!-- Vulling van de ouders
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-ERKE/ERKE01C220T20-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-ERKE/ERKE01C220T20-002.xls

Then heeft $OUWKIG_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen = 'Moeder'
Then heeft $NOUWKIG_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen = 'Vader'

!-- Geboorte van het kind
When voer een bijhouding uit ERKE01C220T20a.xml namens partij 'Gemeente BRP 1'

Then heeft $KIND_BSN$ de waarde van de volgende query: select bsn from kern.pers where bsn='553358121'

Given de database is aangepast met: update kern.his_perssamengesteldenaam
                                    set    indafgeleid=false
                                    where  pers in (select id from kern.pers where bsn='553358121')

Then heeft $RELATIE_ID$ de waarde van de volgende query: select r.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.bsn='553358121' and r.srt=3
Then heeft $KIND_BETROKKENHEID$ de waarde van de volgende query: select b.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.bsn='553358121' and r.srt=3

!-- Erkenning
When voer een bijhouding uit ERKE01C220T20b.xml namens partij 'Gemeente BRP 1'

!-- Verwerking slaagt met een waarschuwing
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/ERKE/expected/ERKE01C220T20.xml voor expressie /