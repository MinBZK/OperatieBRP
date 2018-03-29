Meta:
@auteur                 tjlee
@status                 Klaar
@sleutelwoorden         Geslaagd
@regels                 R1870
@usecase                UCS-BY.HG

Narrative:
R1870 Gemeente aanvang relatie niet geldig op datum aanvang relatie

Scenario:   R1870 Personen Frederique Frederikson (Ingeschrevene-Ingezetene, Niet NL Nat) en Fred Faber (Onbekende) gaan trouwen, Gemeente aanvang relatie niet geldig op datum aanvang relatie
            LT: GHNL02C190T10



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL02C190T10-Frederique.xls

When voer een GBA bijhouding uit GHNL02C190T10.xml namens partij 'Migratievoorziening'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/GHNL/expected/GHNL02C190T10.xml voor expressie //brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R


Then is in de database de persoon met bsn 897624713 wel als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 1762783186 uit database en vergelijk met expected GHNL02C190T10-persoon1.xml
