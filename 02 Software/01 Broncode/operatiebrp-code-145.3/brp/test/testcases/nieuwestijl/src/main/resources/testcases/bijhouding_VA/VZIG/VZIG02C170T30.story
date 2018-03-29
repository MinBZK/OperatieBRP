Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: R2500 Datum aanvang geldigheid van de actie moet gelijk zijn aan datum aanvang adreshouding

Scenario:   DAG van de actie < datum aanvang adreshouding met deblokkering
            LT: VZIG02C170T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG02C170T30-001.xls

When voer een bijhouding uit VZIG02C170T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZIG/expected/VZIG02C170T30.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R

Then in kern heeft select dataanvgel from kern.his_persbijhouding where dataanvgel = 20160102 de volgende gegevens:
| veld                      | waarde  |
| dataanvgel                | 20160102|