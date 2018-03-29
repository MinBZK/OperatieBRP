Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: R1861 Een HGP mag alleen door betrokken gemeenten worden bijgehouden

Scenario: HGP wordt door de "Relatie.Gemeente einde" bijgehouden
          LT: EGNL01C220T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-EGNL/EGNL01C220T20-001.xls

Given pas laatste relatie van soort 2 aan tussen persoon 889397065 en persoon 785579849 met relatie id 2000073 en betrokkenheid id 2000074

When voer een bijhouding uit EGNL01C220T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/EGNL/expected/EGNL01C220T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R
