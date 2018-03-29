Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: R2322 De opgegeven datum aanvang adreshouding moet op of na de datum aanvang adreshouding van het huidige adres liggen

Scenario:   Datum aanvang adreshouding bijhoudingsbericht gelijk datum aanvang adreshouding huidig adres
            LT: VZIG01C10T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG01C10T10-001.xls

When voer een bijhouding uit VZIG01C10T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZIG/expected/VZIG01C10T10.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R
