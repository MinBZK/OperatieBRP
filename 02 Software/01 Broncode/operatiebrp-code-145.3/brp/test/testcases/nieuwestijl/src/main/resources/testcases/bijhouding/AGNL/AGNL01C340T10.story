Meta:
@status                 Klaar
@regels                 R2021
@usecase                UCS-BY.HG

Narrative:
Bij geboorte in Buitenland is Buitenlandse regio en/of Buitenlandse plaats verplicht

Scenario: Landgebied = 6030 Buitl. Plaats leeg Buitl. Regio gevuld
          LT: AGNL01C340T10



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGNL/Victor.xls

When voer een bijhouding uit AGNL01C340T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AGNL/expected/AGNL01C340T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 110477200 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP






