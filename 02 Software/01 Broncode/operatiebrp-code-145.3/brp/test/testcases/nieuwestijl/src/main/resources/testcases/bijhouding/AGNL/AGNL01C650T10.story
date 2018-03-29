Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R1869
@sleutelwoorden         voltrekkingHuwelijkInNederland,TjieWah,AGNL01C650T10,Geslaagd
@usecase                UCS-BY.HG

Narrative:
R1869 Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: R1869 (BRBY0417) Geen gelijktijdig ander Geregistreerd Partnerschap
          LT: AGNL01C650T10

Gemeente BRP 1

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-AGNL/AGNL01C650T10-Marjan.xls
Given enkel initiele vulling uit bestand /LO3PL-AGNL/AGNL01C650T10-Jerry.xls

When voer een bijhouding uit AGNL01C650T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AGNL/expected/AGNL01C650T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 199286425 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

