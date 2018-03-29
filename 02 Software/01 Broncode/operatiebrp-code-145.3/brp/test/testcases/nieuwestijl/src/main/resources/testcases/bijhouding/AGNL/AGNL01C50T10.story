Meta:
@auteur                 jozon
@status                 Klaar
@sleutelwoorden         AGNL01C50T10
@usecase                UCS-BY.HG

Narrative: R1605 Soort Document moet verwijzen naar bestaand stamgegeven

Scenario:   Personen Marjan (Ingeschrevene-Ingezetene, Niet NL Nat) en Victor (Onbekende)!! gaan trouwen
            LT: AGNL01C50T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGNL/Marjan.xls

When voer een bijhouding uit AGNL01C50T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AGNL/expected/AGNL01C50T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 221087849 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP





