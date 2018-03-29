Meta:
@auteur                 fuman
@status                 Klaar
@sleutelwoorden         Geslaagd
@regels                 R1869
@usecase                UCS-BY.HG

Narrative:
R1869 Geen gelijktijdig ander H/GP

Scenario: R1869 Dora doet een poging te trouwen met Ab, Dora is al getrouwd met Piet
          LT: GHNL01C70T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL01C70T10-Dora.xls

When voer een GBA bijhouding uit GHNL01C70T10.xml namens partij 'Migratievoorziening'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/GHNL/expected/GHNL01C70T10.xml voor expressie //brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R

Then lees persoon met anummer 9538159393 uit database en vergelijk met expected GHNL01C70T10-persoon1.xml
