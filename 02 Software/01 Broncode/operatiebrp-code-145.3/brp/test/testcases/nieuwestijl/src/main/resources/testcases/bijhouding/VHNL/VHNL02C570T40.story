Meta:
@auteur                 jozon
@status                 Klaar
@sleutelwoorden         Geslaagd
@regels                 R2034
@usecase                UCS-BY.HG

Narrative:
R2034 Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: R2034 Bij geboorte in Land/gebied ongelijk Nederland zijn geen Nederlandse locatiegegevens toegestaan
          LT: VHNL02C570T40



Given bijhoudingsverzoek voor partij 'Gemeente BRP 1'

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls

When voer een bijhouding uit VHNL02C570T40.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C570T40.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 422531881 wel als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 1868196961 uit database en vergelijk met expected VHNL02C570T40-persoon1.xml




