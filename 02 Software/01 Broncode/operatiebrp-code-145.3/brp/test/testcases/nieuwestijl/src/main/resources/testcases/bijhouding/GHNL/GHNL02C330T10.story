Meta:
@auteur                 fuman
@status                 Klaar
@regels                 R1649
@usecase                UCS-BY.HG

Narrative:
R1649 Land/Gebied verwijst niet naar een bestaand stamgegeven

Scenario:   R1649 Personen Mila (Ingeschrevene-Ingezetene) en Ab (Onbekende) gaan trouwen, Land/Gebied verwijst niet naar een bestaand stamgegeven
            LT: GHNL02C330T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL-Mila.xls

When voer een GBA bijhouding uit GHNL02C330T10.xml namens partij 'Migratievoorziening'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/GHNL/expected/GHNL02C330T10.xml voor expressie //brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 956803593 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 189975945 niet als PARTNER betrokken bij een HUWELIJK
