Meta:
@auteur                 fuman
@status                 Klaar
@regels                 R2021
@usecase                UCS-BY.HG

Narrative:
Bij geboorte in Buitenland is Buitenlandse regio en/of Buitenlandse plaats verplicht

Scenario: Land/gebied = 5049 Buitl. Plaats leeg Buitl. Regio leeg
          LT: GHNL02C210T10



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL-Mila.xls

When voer een GBA bijhouding uit GHNL02C210T10.xml namens partij 'Migratievoorziening'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/GHNL/expected/GHNL02C210T10.xml voor expressie //brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 956803593 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 189975945 niet als PARTNER betrokken bij een HUWELIJK






