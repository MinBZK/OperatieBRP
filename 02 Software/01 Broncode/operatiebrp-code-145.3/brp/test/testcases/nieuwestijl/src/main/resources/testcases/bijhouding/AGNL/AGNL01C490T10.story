Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R1649
@usecase                UCS-BY.HG

Narrative:
Land/Gebied verwijst niet naar een bestaand stamgegeven

Scenario:   Personen Libby Thatcher (Ingeschrevene-Ingezetene, Niet NL Nat) en Piet Jansen (Onbekende) gaan trouwen, Land/Gebied verwijst niet naar een bestaand stamgegeven
            LT: AGNL01C490T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGNL/Marjan.xls

When voer een bijhouding uit AGNL01C490T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AGNL/expected/AGNL01C490T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 221087849 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
