Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R2434 Geboorte van ingezetene mag alleen ingediend worden door gemeente van geboorte of bijhoudingspartij van OUWKIG

Scenario: 1. DB init
          preconditie

Given alle personen zijn verwijderd
Given de database is aangepast met: Update kern.partijRol
                                    set rol = 4
                                    where id = (select pr.id from kern.partijrol pr join kern.partij p on p.id = pr.partij and p.naam = 'Gemeente BRP 1' and pr.rol != 1)

Given maak bijhouding caches leeg

Scenario: 2. Administratieve handeling.Partij rol is ongelijk aan Rol 'Bijhoudingsorgaan College' en komt niet overeen met één van de 3 partijen uit de definitie
            LT: AUAH01C170T50

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AUAH/AUAH01C170T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AUAH/AUAH01C170T10-002.xls

When voer een bijhouding uit AUAH01C170T50.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AUAH/expected/AUAH01C170T50.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R

Scenario: 3. DB reset
          postconditie

Given de database is aangepast met: Update kern.partijRol
                                    set rol = 2
                                    where id = (select pr.id from kern.partijrol pr join kern.partij p on p.id = pr.partij and p.naam = 'Gemeente BRP 1' and pr.rol != 1)

Given maak bijhouding caches leeg