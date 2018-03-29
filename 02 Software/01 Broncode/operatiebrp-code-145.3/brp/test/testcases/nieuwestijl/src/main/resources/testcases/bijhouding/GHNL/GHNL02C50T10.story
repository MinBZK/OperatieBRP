Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R1630
@usecase                UCS-BY.HG

Narrative:
R1630 Persoon is hoogstens één keer betrokken in dezelfde relatie

Scenario: R1630 Persoon is meer dan één keer betrokken in dezelfde relatie
          LT: GHNL02C50T10



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL-Mila.xls

When voer een GBA bijhouding uit GHNL02C50T10.xml namens partij 'Migratievoorziening'

Then heeft het antwoordbericht verwerking Foutief
And is het antwoordbericht gelijk aan /testcases/bijhouding/GHNL/expected/GHNL02C50T10.xml voor expressie //brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 956803593 niet als PARTNER betrokken bij een HUWELIJK
