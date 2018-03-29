Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R1673
@usecase                UCS-BY.HG


Narrative:
R1673 Predicaat en adellijke titel gevuld

Scenario:   Personen Mila Steens (Ingeschrevene-Ingezetene, Niet NL Nat) en Don Dogan (Onbekende) gaan trouwen, Predicaat en adellijke titel gevuld
            LT: GHNL02C310T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL-Mila.xls

When voer een GBA bijhouding uit GHNL02C310T10.xml namens partij 'Migratievoorziening'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/GHNL/expected/GHNL02C310T10.xml voor expressie //brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 956803593 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 3528125729 uit database en vergelijk met expected GHNL02C310T10-persoon1.xml
