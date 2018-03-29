Meta:
@auteur                 fuman
@status                 Klaar
@regels                 R1859
@usecase                UCS-BY.HG

Narrative:
R1859 Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: R1859 Datum aanvang HGP moet geldige kalenderdatum zijn bij aanvang in NL
          LT: AGNL01C300T10



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGNL/Victor.xls

When voer een bijhouding uit AGNL01C300T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
And is het antwoordbericht gelijk aan /testcases/bijhouding/AGNL/expected/AGNL01C300T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 110477200 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

