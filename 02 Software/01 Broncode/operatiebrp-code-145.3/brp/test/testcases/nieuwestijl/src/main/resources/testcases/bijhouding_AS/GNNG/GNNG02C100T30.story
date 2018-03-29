Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R2545 Reden mag alleen ingevuld zijn bij verlies van Nederlandse nationaliteit

Scenario:   Reden verlies einde nationaliteit is ingevuld bij verlies van een Nederlandse nationaliteit
            LT: GNNG02C100T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG02C100T30-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG02C100T30-002.xls

When voer een bijhouding uit GNNG02C100T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GNNG/expected/GNNG02C100T30.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R