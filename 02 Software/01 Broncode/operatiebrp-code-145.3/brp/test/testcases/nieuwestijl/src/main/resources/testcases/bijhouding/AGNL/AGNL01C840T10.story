Meta:
@status                 Klaar
@regels                 R2160
@sleutelwoorden         voltrekkingHuwelijkInNederland,registratieNaamgebruik,AGNL01C840T10
@usecase                UCS-BY.HG

Narrative:
R2160 Combinatie Scheidingsteken en Voorvoegsel in Naamgebruik moet voorkomen als stamgegeven

Scenario: R2160 Persoon.Predicaat naamgebruik en de Persoon.Adellijke titel naamgebruik hebben beiden een waarde
          LT: AGNL01C840T10

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-AGNL/Marjan.xls
Given enkel initiele vulling uit bestand /LO3PL-AGNL/Victor.xls

When voer een bijhouding uit AGNL01C840T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AGNL/expected/AGNL01C840T10.xml voor expressie
//brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 221087849 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 110477200 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

