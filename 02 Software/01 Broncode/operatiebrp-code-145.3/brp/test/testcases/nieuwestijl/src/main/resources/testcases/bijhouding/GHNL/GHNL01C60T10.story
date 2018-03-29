Meta:
@auteur                 fuman
@status                 Klaar
@sleutelwoorden         Geslaagd
@regels                 R1867
@usecase                UCS-BY.HG

Narrative:
R1867 Partner mag niet onder curatele staan

Scenario: Persoon DAG Relatie DA en Persoon curatele DEG is leeg
          LT: GHNL01C60T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL01C60T10-Rosa.xls

When voer een GBA bijhouding uit GHNL01C60T10.xml namens partij 'Migratievoorziening'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/GHNL/expected/GHNL01C60T10.xml voor expressie //brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 874920073 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 189975945 wel als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 4308642593 uit database en vergelijk met expected GHNL01C60T10-persoon1.xml

