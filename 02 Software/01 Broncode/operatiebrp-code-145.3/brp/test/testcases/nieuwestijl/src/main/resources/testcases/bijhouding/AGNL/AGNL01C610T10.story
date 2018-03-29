Meta:
@auteur                 jozon
@status                 Klaar
@regels                 R1606
@sleutelwoorden         AGNL01C610T10, Geslaagd
@usecase                UCS-BY.HG

Narrative: R1606 Brondocument moet geldig zijn voor de administratieve handeling en actie

Scenario: Documentsoort heeft  ongeldige waarde, Actie en administratie handeling hebben een geldige waarde
          LT: AGNL01C610T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGNL/Marjan.xls
Given enkel initiele vulling uit bestand /LO3PL-AGNL/Victor.xls

When voer een bijhouding uit AGNL01C610T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het  antwoordbericht gelijk aan /testcases/bijhouding/AGNL/expected/AGNL01C610T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 221087849 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 110477200 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP



