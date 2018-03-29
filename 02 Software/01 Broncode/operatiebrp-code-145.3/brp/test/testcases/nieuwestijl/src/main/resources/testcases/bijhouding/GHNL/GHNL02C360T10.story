Meta:
@auteur                 fuman
@status                 Klaar
@regels                 R1850
@usecase                UCS-BY.HG

Narrative:
R1850 Omschrijving niet gevuld bij Nederlandse registerakten

Scenario: R1850 De omschrijving is gevuld bij een Nederlandse registerakte
          LT: GHNL02C360T10

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL-Mila.xls
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL-Tim.xls

When voer een GBA bijhouding uit GHNL02C360T10.xml namens partij 'Migratievoorziening'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/GHNL/expected/GHNL02C360T10.xml voor expressie //brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 956803593 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 979562697 niet als PARTNER betrokken bij een HUWELIJK
