Meta:
@auteur                 jozon
@status                 Klaar
@regels                 R2046
@sleutelwoorden         voltrekkingHuwelijkInNederland, VHNL02C640T50
@usecase                UCS-BY.HG

Narrative:
Locatie-omschrijving geboorte verplicht als Land = "Onbekend" of Internationaal gebied

Scenario: Landgebied = 9999 Omschrijving geboorte niet gevuld
          LT: VHNL02C640T50



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls

When voer een bijhouding uit VHNL02C640T50.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C640T50.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 422531881 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 1868196961 uit database en vergelijk met expected VHNL02C640T50-persoon1.xml






