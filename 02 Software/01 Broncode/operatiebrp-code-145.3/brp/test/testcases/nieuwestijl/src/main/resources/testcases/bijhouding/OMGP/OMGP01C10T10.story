Meta:
@status                 Klaar
@regels                 R2174
@usecase                UCS-BY.HG

Narrative: R2174 Geslachtsnaamwijziging bij beëindigen H/GP in Nederland alleen voor niet-Nederlanders

Scenario:   Geslachtsnaamwijziging waarbij nationaliteit = Nederlander
            LT: OMGP01C10T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-OMGP/OMGP01C10T10-001.xls

Given pas laatste relatie van soort 2 aan tussen persoon 890943369 en persoon 650999113 met relatie id 31100004 en betrokkenheid id 31100004

When voer een bijhouding uit OMGP01C10T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/OMGP/expected/OMGP01C10T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R












