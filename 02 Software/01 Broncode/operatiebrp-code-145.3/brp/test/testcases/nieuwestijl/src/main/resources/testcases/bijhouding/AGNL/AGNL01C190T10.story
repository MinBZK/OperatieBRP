Meta:
@auteur                 jozon
@status                 Klaar
@regels                 R1469
@sleutelwoorden         AGNL01C190T10
@usecase                UCS-BY.HG

Narrative:
R1469 Geboortedatum mag niet onbekend zijn bij geboorte in Nederland

Scenario: Geboortedatum deels onbekend bij geboorte in Nederland(19660100)
          LT: AGNL01C190T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGNL/Libby-gebdat-deels-onbekend1.xls

When voer een bijhouding uit AGNL01C190T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AGNL/expected/AGNL01C190T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 422531881 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP




