Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R2489 De administratieve handeling mag alleen ingediend worden door gemeente van geboorte of bijhoudingspartij van kind

Scenario: 1. DB init
          preconditie

Given de database is aangepast met: update kern.gem set dateindegel = 20160102 where naam = 'Gemeente wordt voortgezet door BRP 1'

Given maak bijhouding caches leeg

Scenario: 2. de Administratieve handeling.Partij de Rol is 'Bijhoudingsorgaan College' en is'Voortzettende gemeente' van de Persoon.Gemeente geboorte van de Hoofdpersoon
          LT: AUAH01C180T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AUAH/AUAH01C180T20-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AUAH/AUAH01C180T20-002.xls

When voer een bijhouding uit AUAH01C180T20a.xml namens partij 'Gemeente BRP 2'
Then heeft het antwoordbericht verwerking Geslaagd

Then heeft $RELATIE_ID$ de waarde van de volgende query: select r.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.bsn in ('461820201') and r.srt=3
Then heeft $KIND_BETROKKENHEID$ de waarde van de volgende query: select b.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.bsn in ('461820201') and r.srt=3

When voer een bijhouding uit AUAH01C180T20b.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 3. DB reset
          postconditie
Given de database is aangepast met: update kern.gem set dateindegel = 19950101 where naam = 'Gemeente wordt voortgezet door BRP 1'

Given maak bijhouding caches leeg