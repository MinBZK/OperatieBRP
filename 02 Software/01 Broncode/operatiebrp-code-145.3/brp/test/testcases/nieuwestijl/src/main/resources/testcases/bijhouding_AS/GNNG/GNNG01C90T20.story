Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1815 Geen nationaliteit - ook geen onbekende - en ook geen indicatie staatloos aanwezig

Scenario:   Beeindiging nationaliteit met registratie nieuw onbekende nationaliteit
            LT: GNNG01C90T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG01C90T20-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG01C90T20-002.xls

When voer een bijhouding uit GNNG01C90T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
