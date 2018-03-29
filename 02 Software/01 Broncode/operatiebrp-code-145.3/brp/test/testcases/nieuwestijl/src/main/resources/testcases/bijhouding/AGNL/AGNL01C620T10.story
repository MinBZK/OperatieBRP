Meta:
@auteur                 jozon
@status                 Klaar
@regels                 R1865
@sleutelwoorden         Geslaagd, AGNL01C620T10
@usecase                UCS-BY.HG

Narrative: R1865 Minimumleeftijd van de partners bij voltrekking HGP is 18 jaar

Scenario: R1865 Leeftijd van de partners bij voltrekking HGP is jonger dan 18 jaar
          LT: AGNL01C620T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGNL/Libby-jonger-18.xls

When voer een bijhouding uit AGNL01C620T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
And is het antwoordbericht gelijk aan /testcases/bijhouding/AGNL/expected/AGNL01C620T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 422531881 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
