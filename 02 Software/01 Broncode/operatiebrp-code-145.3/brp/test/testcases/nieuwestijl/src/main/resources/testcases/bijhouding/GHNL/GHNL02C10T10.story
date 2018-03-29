Meta:
@auteur                 tjlee
@status                 Klaar
@sleutelwoorden         Geslaagd
@regels                 R1466
@usecase                UCS-BY.HG

Narrative:
R1466 Gemeente geboorte moet geldig zijn op geboortedatum

Scenario: R1466 Gemeente geboorte moet geldig zijn op geboortedatum
          LT: GHNL02C10T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL02C10T10-Andrea.xls

When voer een GBA bijhouding uit GHNL02C10T10.xml namens partij 'Migratievoorziening'

Then heeft het antwoordbericht verwerking Geslaagd
And is het antwoordbericht gelijk aan /testcases/bijhouding/GHNL/expected/GHNL02C10T10.xml voor expressie //brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 540933673 wel als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 2898235282 uit database en vergelijk met expected GHNL02C10T10-persoon1.xml
