Meta:
@status                 Klaar
@regels                 R2155
@usecase                UCS-BY.HG

Narrative:
R2155 Woonplaatsnaam verwijst niet naar een bestaand stamgegeven

Scenario:   R2155 Personen Mila (Ingeschrevene-Ingezetene) en Ab (Onbekende) gaan trouwen, Woonplaatsnaam is geen stamgegeven
            LT: GHNL02C320T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL02C320T10-Mila.xls

When voer een GBA bijhouding uit GHNL02C320T10.xml namens partij 'Migratievoorziening'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/GHNL/expected/GHNL02C320T10.xml voor expressie //brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 863427273 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 614856425 wel als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 5145953042 uit database en vergelijk met expected GHNL02C320T10-persoon1.xml
