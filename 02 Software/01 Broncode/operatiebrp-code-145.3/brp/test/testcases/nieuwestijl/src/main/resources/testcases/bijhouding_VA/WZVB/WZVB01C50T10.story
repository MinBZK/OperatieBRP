Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: R2536 Minimaal één hoofdpersoon moet een ingezetene zijn


Scenario: 1. DB init
          preconditie

Given alle personen zijn verwijderd
Given de database is aangepast met: Update kern.partijRol
                                    set rol = 4
                                    where id = (select pr.id from kern.partijrol pr join kern.partij p on p.id = pr.partij and p.naam = 'Gemeente BRP 1' and pr.rol != 1)

Given maak bijhouding caches leeg

Scenario: 2. Registratie verblijfsrecht bij een Niet-ingezetene ingediend door de rol 'Bijhoudingsvoorstelorgaan'
          LT: WZVB01C50T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-WZVB/WZVB01C50T10-001.xls


When voer een bijhouding uit WZVB01C50T10a.xml namens partij 'Gemeente BRP 2'
Then heeft het antwoordbericht verwerking Geslaagd

When voer een bijhouding uit WZVB01C50T10b.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/WZVB/expected/WZVB01C50T10.xml voor expressie //brp:bhg_vbaRegistreerVerblijfsrecht_R

Scenario: 3. DB reset
          postconditie

Given de database is aangepast met: Update kern.partijRol
                                    set rol = 2
                                    where id = (select pr.id from kern.partijrol pr join kern.partij p on p.id = pr.partij and p.naam = 'Gemeente BRP 1' and pr.rol != 1)

Given maak bijhouding caches leeg