Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R1605
@usecase                UCS-BY.HG

Narrative:
R1605 Soort Document moet verwijzen naar bestaand stamgegeven

Scenario:   Personen Mila Steens (Ingeschrevene-Ingezetene, Niet NL Nat) en Arjan Abraham (Onbekende) gaan trouwen
            LT: GHNL03C10T10



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL-Mila.xls

When voer een GBA bijhouding uit GHNL03C10T10.xml namens partij 'Migratievoorziening'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/GHNL/expected/GHNL03C10T10.xml voor expressie //brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 956803593 niet als PARTNER betrokken bij een HUWELIJK
