Meta:
@auteur                 jozon
@status                 Klaar
@regels                 R2046
@sleutelwoorden         voltrekkingHuwelijkInNederland, VHNL02C640T60
@usecase                UCS-BY.HG

Narrative:
Locatie-omschrijving geboorte verplicht als Land = "Onbekend"

Scenario: Landgebied = 6030 Omschrijving geboorte niet gevuld
          LT: VHNL02C640T60



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls

When voer een bijhouding uit VHNL02C640T60.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C640T60.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 422531881 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 1868196961 uit database en vergelijk met expected VHNL02C640T60-persoon1.xml






