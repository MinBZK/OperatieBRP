Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R1625
@sleutelwoorden         Geslaagd,TjieWah,AGNL01C660T10
@usecase                UCS-BY.HG

Narrative:
R1625 Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: R1625 De Soort is P en de Geslachtsaanduiding is niet ingevuld
          LT: AGNL01C660T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGNL/Marjan.xls

When voer een bijhouding uit AGNL01C660T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
And is het antwoordbericht gelijk aan /testcases/bijhouding/AGNL/expected/AGNL01C660T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 221087849 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
