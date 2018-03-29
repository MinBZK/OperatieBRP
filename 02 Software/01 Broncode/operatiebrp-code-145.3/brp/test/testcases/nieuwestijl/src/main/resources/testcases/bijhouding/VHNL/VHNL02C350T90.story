Meta:
@auteur                 reboe
@status                 Klaar
@sleutelwoorden         Geslaagd
@regels                 R1865
@usecase                UCS-BY.HG

Narrative:
R1865 Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: Bijhouding 1 partner(LO3) precies 18(tov  Relatie.datum aanvang)
          LT: VHNL02C350T90



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL02C350T90-Libby-18.xls

When voer een bijhouding uit VHNL02C350T90.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
And is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C350T90.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 128583721 wel als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 1849054738 uit database en vergelijk met expected VHNL02C350T90-persoon1.xml
