Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R2032
@usecase                UCS-BY.HG

Narrative:
R2032 Bij geboorte in Land/gebied is Nederland zijn geen buitenlandse locatiegegevens toegestaan

Scenario: R2032 Bij Persoon.Land gebied geboorte Nederland is buitenlandsePlaats ingevuld
          LT: GHNL02C240T10



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL-Mila.xls

When voer een GBA bijhouding uit GHNL02C240T10.xml namens partij 'Migratievoorziening'

Then heeft het antwoordbericht verwerking Foutief
And is het antwoordbericht gelijk aan /testcases/bijhouding/GHNL/expected/GHNL02C240T10.xml voor expressie //brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 956803593 niet als PARTNER betrokken bij een HUWELIJK
