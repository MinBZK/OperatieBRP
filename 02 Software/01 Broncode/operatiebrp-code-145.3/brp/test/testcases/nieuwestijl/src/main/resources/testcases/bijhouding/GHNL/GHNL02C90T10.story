Meta:
@auteur                 fuman
@status                 Klaar
@regels                 R1849
@usecase                UCS-BY.HG

Narrative:
R1849 Aktenummer verplicht bij Nederlandse registerakten en leeg bij andere documentsoorten

Scenario:   Aktenummer is gevuld bij niet-Nederlandse akte
            LT: GHNL02C90T10



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL-Mila.xls
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL-Tim.xls

When voer een GBA bijhouding uit GHNL02C90T10.xml namens partij 'Migratievoorziening'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/GHNL/expected/GHNL02C90T10.xml voor expressie //brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R









