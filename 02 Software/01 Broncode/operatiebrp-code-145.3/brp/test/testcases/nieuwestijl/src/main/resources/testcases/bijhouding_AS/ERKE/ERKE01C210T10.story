Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R2737 Waarschuwing dat de naamswijziging niet doorwerkt in het Naamgebruik

Scenario:   Voornaam gewijzigd Samengestelde naam afleiding Ja en Naamgebruik afleiding Nee
            LT: ERKE01C210T10

Given alle personen zijn verwijderd

!-- Vulling van de ouders
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-ERKE/ERKE01C210T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-ERKE/ERKE01C210T10-002.xls

Then heeft $OUWKIG_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen = 'Moeder'
Then heeft $NOUWKIG_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen = 'Vader'

!-- Geboorte van het kind
When voer een bijhouding uit ERKE01C210T10a.xml namens partij 'Gemeente BRP 1'

Then heeft $KIND_BSN$ de waarde van de volgende query: select bsn from kern.pers where bsn='365789161'

Given de database is aangepast met: update kern.his_persnaamgebruik
                                    set    indnaamgebruikafgeleid=false,
				           naamgebruik=3
                                    where  pers in (select id from kern.pers where bsn='365789161')

Then heeft $RELATIE_ID$ de waarde van de volgende query: select r.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.bsn='365789161' and r.srt=3
Then heeft $KIND_BETROKKENHEID$ de waarde van de volgende query: select b.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.bsn='365789161' and r.srt=3

!-- Erkenning
When voer een bijhouding uit ERKE01C210T10b.xml namens partij 'Gemeente BRP 1'

!-- Verwerking slaagt met een waarschuwing
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/ERKE/expected/ERKE01C210T10.xml voor expressie /