Meta:
@auteur                 jozon
@status                 Klaar
@sleutelwoorden         Geslaagd
@regels                 R1809
@usecase                UCS-BY.HG

Narrative:
R1809 Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: Ingeschrevene trouwt met Niet-Ingeschrevene waarbij voornaam = niet gevuld en Namenreeks = Nee
          LT: VHNL02C220T30



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls

When voer een bijhouding uit VHNL02C220T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C220T30.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 422531881 wel als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 1868196961 uit database en vergelijk met expected VHNL02C220T30-persoon1.xml


