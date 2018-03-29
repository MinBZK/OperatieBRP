Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R2041
@usecase                UCS-BY.HG

Narrative:
R2041 Gemeente geboorte verplicht als Land/Gebied = Nederland

Scenario: R2041 Persoon.Land/gebied geboorte = Nederland en Persoon.Gemeente geboorte heeft geen waarde
          LT: GHNL02C270T10



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL-Mila.xls

When voer een GBA bijhouding uit GHNL02C270T10.xml namens partij 'Migratievoorziening'

Then heeft het antwoordbericht verwerking Foutief
And is het antwoordbericht gelijk aan /testcases/bijhouding/GHNL/expected/GHNL02C270T10.xml voor expressie //brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 956803593 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 3528125729 uit database en vergelijk met expected GHNL02C270T10-persoon1.xml
