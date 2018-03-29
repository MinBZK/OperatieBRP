Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R1836
@sleutelwoorden         voltrekkingHuwelijkInNederland,TjieWah,AGNL01C820T10, Geslaagd
@usecase                UCS-BY.HG

Narrative:
R1861 Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: R1836 Registratie naamgebruik bij beiden  ingezetenen partners die geen betrekking hebben op de hoofdactie
          LT: AGNL01C820T10

Gemeente BRP 1

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-AGNL/Marjan.xls
Given enkel initiele vulling uit bestand /LO3PL-AGNL/Victor.xls
Given enkel initiele vulling uit bestand /LO3PL-AGNL/AGNL01C820T10-Jan.xls

When voer een bijhouding uit AGNL01C820T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AGNL/expected/AGNL01C820T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 110477200 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

