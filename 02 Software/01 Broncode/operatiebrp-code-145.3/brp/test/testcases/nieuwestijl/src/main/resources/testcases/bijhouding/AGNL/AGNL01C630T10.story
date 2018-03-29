Meta:
@status                 Klaar
@regels                 R1867
@sleutelwoorden         AGNL01C630T10, Geslaagd
@usecase                UCS-BY.HG

Narrative: R1867 Partner mag niet onder curatele staan

Scenario: Persoon DAG kleiner of gelijk Relatie DA en Persoon curatele DEG is leeg
          LT: AGNL01C630T10

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-AGNL/AGNL-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL-AGNL/AGNL-danny.xls

When voer een bijhouding uit AGNL01C630T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AGNL/expected/AGNL01C630T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 966238473 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

