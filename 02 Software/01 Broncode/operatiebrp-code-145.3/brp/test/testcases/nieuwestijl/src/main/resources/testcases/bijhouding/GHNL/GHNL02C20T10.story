Meta:
@auteur                 tjlee
@status                 Klaar
@sleutelwoorden         Geslaagd
@regels                 R1467
@usecase                UCS-BY.HG

Narrative:
R1467 Land gebied geboorte moet geldig zijn op geboortedatum

Scenario: Datum geboorte voor Dataanvgel LandGebied
          LT: GHNL02C20T10



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL02C20T10-Bibi.xls

When voer een GBA bijhouding uit GHNL02C20T10.xml namens partij 'Migratievoorziening'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/GHNL/expected/GHNL02C20T10.xml voor expressie //brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 120554689 wel als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 5924837650 uit database en vergelijk met expected GHNL02C20T10-persoon1.xml







