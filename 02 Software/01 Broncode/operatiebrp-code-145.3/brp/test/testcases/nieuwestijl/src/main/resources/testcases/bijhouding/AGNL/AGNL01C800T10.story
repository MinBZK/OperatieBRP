Meta:
@auteur                 tjlee
@status                 Klaar
@sleutelwoorden         Klaar
@regels                 R1572
@usecase                UCS-BY.HG

Narrative:
R1572 Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: R1572 Ouder en wijzigen Geslachtsnaamcomponent.stam en Ouder.Ouderschap.DAG overlapt met Geslachtsnaamcomponent.Standaard.DAG
          LT: AGNL01C800T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGNL/AGNL01C800T10-Marjan.xls
Given enkel initiele vulling uit bestand /LO3PL-AGNL/AGNL01C800T10-Victor.xls

When voer een bijhouding uit AGNL01C800T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
And is het antwoordbericht gelijk aan /testcases/bijhouding/AGNL/expected/AGNL01C800T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 485927081 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 188518113 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
