Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R2489 De administratieve handeling mag alleen ingediend worden door gemeente van geboorte of bijhoudingspartij van kind

Scenario:   de Administratieve handeling.Partij de Rol is 'Bijhoudingsorgaan College' en is Persoon.Gemeente geboorte van de Hoofdpersoon
            LT: AUAH01C180T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AUAH/AUAH01C180T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AUAH/AUAH01C180T10-002.xls

When voer een bijhouding uit AUAH01C180T10a.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

Then heeft $RELATIE_ID$ de waarde van de volgende query: select r.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.bsn in ('461820201') and r.srt=3
Then heeft $KIND_BETROKKENHEID$ de waarde van de volgende query: select b.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.bsn in ('461820201') and r.srt=3

When voer een bijhouding uit AUAH01C180T10b.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd
