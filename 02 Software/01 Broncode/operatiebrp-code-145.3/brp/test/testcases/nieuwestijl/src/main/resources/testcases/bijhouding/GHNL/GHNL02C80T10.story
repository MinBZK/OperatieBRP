Meta:
@auteur                 fuman
@status                 Klaar
@sleutelwoorden         Geslaagd
@regels                 R1848
@usecase                UCS-BY.HG

Narrative:
R1848 Registersoort moet geldig zijn voor soort document

Scenario: Ongeldige registersoort Ongeldige registersoort
          LT: GHNL02C80T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL-Mila.xls
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL-Tim.xls

When voer een GBA bijhouding uit GHNL02C80T10.xml namens partij 'Migratievoorziening'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/GHNL/expected/GHNL02C80T10.xml voor expressie //brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 956803593 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 979562697 wel als PARTNER betrokken bij een HUWELIJK









