Meta:
@status             Onderhanden
@usecase            BV.0.GM
@regels             R1833
@sleutelwoorden     Geef Medebewoners

!-- Onderhanden omdat dit een test is voor bevraging met objectsleutel, dit wordt niet in de testtooling niet ondersteund

Narrative:
Elke sleutel in een inkomend Bericht om een Persoon te identificeren, voldoet aan 'Gemaskeerde objectsleutel' (R1834).

Scenario:   1.  Geef medebewoners met identificatie criteria 'objectsleutel'
                LT: R1833_LT01
                Verwacht resultaat:
                - Geslaagd

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan_naderebijhoudingsaard_fout.xls

Given verzoek voor leveringsautorisatie 'GeefMedebewoners' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0GM_Geef_Medebewoners/xml/test.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

Scenario:   2.  Geef medebewoners met identificatie criteria 'objectsleutel'
                LT: R1833_LT02
                Verwacht resultaat:
                - Foutief; De opgegeven objectsleutel voldoet niet aan de gestelde eisen.

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan_naderebijhoudingsaard_fout.xls

Given verzoek voor leveringsautorisatie 'GeefMedebewoners' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0GM_Geef_Medebewoners/xml/test.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide