Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R2046
@usecase                UCS-BY.HG

Narrative:
Locatie-omschrijving geboorte verplicht als Land = "Onbekend"

Scenario: Landgebied = 0000 Omschrijving geboorte niet gevuld
          LT: AGNL01C440T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGNL/Marjan.xls

When voer een bijhouding uit AGNL01C440T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AGNL/expected/AGNL01C440T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 221087849 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
