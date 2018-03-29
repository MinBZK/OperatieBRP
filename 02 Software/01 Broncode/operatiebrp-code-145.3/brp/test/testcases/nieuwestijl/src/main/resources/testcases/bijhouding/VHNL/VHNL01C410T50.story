Meta:
@status                 Klaar
@usecase                UCS-BY.HG


Narrative: Waarschuwing indien de gerelateerde gegevens van een persoon wijzigen en deze persoon voorkomt als een pseudo-persoon op de persoonslijst van een ander.

Scenario:   1. DB init scenario om uitgangssituatie te zetten
            preconditie

Given alle personen zijn verwijderd

Scenario: 2.  Wijziging geslachtsnaam ontbinding waarbij persoon meerdere keren voorkomt als pseudo in DB
          LT: VHNL01C410T50

Given enkel initiele vulling uit bestand /LO3PL/VHNL01C410T50-001.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C410T50-002.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C410T50-003.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C410T50-004.xls

When voer een bijhouding uit VHNL01C410T50.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL01C410T50.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R


Then is in de database de persoon met bsn 787531273 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 957440777 wel als PARTNER betrokken bij een HUWELIJK

