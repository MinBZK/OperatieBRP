Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R1672
@usecase                UCS-BY.HG


Narrative:
R1672 Voorvoegsel en scheidingsteken beide gevuld

Scenario:   R1672 Personen Mila Steens(Ingeschrevene-Ingezetene, Niet NL Nat) en Cornelis Cornelissen(Onbekende) gaan trouwen, alleen scheidingsteken ingevuld
            LT: GHNL02C340T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL-Mila.xls

When voer een GBA bijhouding uit GHNL02C340T10.xml namens partij 'Migratievoorziening'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/GHNL/expected/GHNL02C340T10.xml voor expressie //brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 956803593 niet als PARTNER betrokken bij een HUWELIJK
