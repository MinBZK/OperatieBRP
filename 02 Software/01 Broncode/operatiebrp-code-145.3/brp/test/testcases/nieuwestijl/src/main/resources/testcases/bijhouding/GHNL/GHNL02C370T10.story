Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R1676
@sleutelwoorden         voltrekkingHuwelijkInNederland, GHNL02C370T10
@usecase                UCS-BY.HG


Narrative:
R1676 Combinatie scheidingsteken en voorvoegsel bestaat niet in de stamtabel

Scenario:   R1676 Combinatie scheidingsteken en voorvoegsel bestaat niet in de stamtabel
            LT: GHNL02C370T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL-Mila.xls

When voer een GBA bijhouding uit GHNL02C370T10.xml namens partij 'Migratievoorziening'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/GHNL/expected/GHNL02C370T10.xml voor expressie //brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 956803593 niet als PARTNER betrokken bij een HUWELIJK
