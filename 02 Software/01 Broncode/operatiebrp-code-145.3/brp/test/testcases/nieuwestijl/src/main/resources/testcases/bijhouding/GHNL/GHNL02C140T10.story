Meta:
@auteur                 tjlee
@status                 Klaar
@sleutelwoorden         Geslaagd
@regels                 R1862
@usecase                UCS-BY.HG

Narrative:
R1862 Gemeente aanvang H/GP moet registergemeente zijn

Scenario: R1862 Gemeente aanvang HGP moet registergemeente zijn
          LT: GHNL02C140T10



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL02C140T10-Erica.xls

When voer een GBA bijhouding uit GHNL02C140T10.xml namens partij 'Migratievoorziening'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/GHNL/expected/GHNL02C140T10.xml voor expressie //brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 362072425 wel als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 3830576402 uit database en vergelijk met expected GHNL02C140T10-persoon1.xml
