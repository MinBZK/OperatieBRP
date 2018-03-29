Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R2489 De administratieve handeling mag alleen ingediend worden door gemeente van geboorte of bijhoudingspartij van kind

Scenario: 1. DB init
          preconditie

Given alle personen zijn verwijderd
Given de database is aangepast met: Update kern.partijRol
                                    set rol = 4
                                    where id = (select pr.id from kern.partijrol pr join kern.partij p on p.id = pr.partij and p.naam = 'Gemeente BRP 3' and pr.rol != 1)

Given maak bijhouding caches leeg

Scenario: 2. de Administratieve handeling.Partij de Rol is 'Bijhoudingsvoorstelorgaan'
          LT: AUAH01C180T40

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AUAH/AUAH01C180T40-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AUAH/AUAH01C180T40-002.xls

When voer een bijhouding uit AUAH01C180T40a.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

Then heeft $RELATIE_ID$ de waarde van de volgende query: select r.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.bsn in ('461820201') and r.srt=3
Then heeft $KIND_BETROKKENHEID$ de waarde van de volgende query: select b.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.bsn in ('461820201') and r.srt=3

When voer een bijhouding uit AUAH01C180T40b.xml met ondertekenaar 'Gemeente BRP 3', en transporteur 'Gemeente BRP handmatig fiat 1'
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AUAH/expected/AUAH01C180T40.xml voor expressie /

Scenario: 3. DB reset
          postconditie

Given de database is aangepast met: Update kern.partijRol
                                    set rol = 2
                                    where id = (select pr.id from kern.partijrol pr join kern.partij p on p.id = pr.partij and p.naam = 'Gemeente BRP 3' and pr.rol != 1)

Given maak bijhouding caches leeg