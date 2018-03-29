Meta:
@auteur                 fuman
@status                 Klaar
@regels                 R2046
@usecase                UCS-BY.HG

Narrative:
R2046 Locatie-omschrijving geboorte verplicht als Land = "Onbekend"

Scenario: R2046 Landgebied = 0000 Omschrijving geboorte niet gevuld
          LT: GHNL02C300T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL-Mila.xls

When voer een GBA bijhouding uit GHNL02C300T10.xml namens partij 'Migratievoorziening'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/GHNL/expected/GHNL02C300T10.xml voor expressie //brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 956803593 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 189975945 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 3528125729 uit database en vergelijk met expected GHNL02C300T10-persoon1.xml
