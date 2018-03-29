Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: R1379 Gemeente in het adres moet geldig zijn op datum aanvang adreshouding

Scenario: Adres.Datum aanvang adreshouding op Gemeente.datinganggel
            LT: VZIG02C60T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG02C60T10-001.xls

When voer een bijhouding uit VZIG02C60T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZIG/expected/VZIG02C60T20.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R

Then lees persoon met anummer 6214529825 uit database en vergelijk met expected VZIG02C60T20-persoon1.xml

