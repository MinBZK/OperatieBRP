Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: R1610 In bijhouding moet partij naar Bijhoudingspartij verwijzen

Scenario: 1. DB init
          preconditie

Given de database is aangepast met: Update kern.partijRol
                                    set rol = 3
                                    where id = (select distinct pr.id from kern.partijrol pr join kern.partij p on p.id = pr.partij and p.naam = 'Gemeente BRP 1' and pr.dateinde is null)

Given maak bijhouding caches leeg

Scenario: 2. R1610 De zendende partij is wel geautoriseerd maar niet van de rol Bijhoudingsorgaan College
          LT: AUAH01C160T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AUAH/AUAH01C160T30.xls

When voer een bijhouding uit AUAH01C160T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AUAH/expected/AUAH01C160T30.xml voor expressie //brp:bhg_vbaRegistreerVerblijfsrecht_R

Scenario: 3. DB reset
          postconditie

Given voer enkele update uit: UPDATE kern.partijrol
                              SET    rol=2
                              where id = (select distinct pr.id from kern.partijrol pr join kern.partij p on p.id = pr.partij and p.naam = 'Gemeente BRP 1' and pr.dateinde is null)

Given maak bijhouding caches leeg