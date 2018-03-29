Meta:
@status                 Klaar
@usecase                BY.0.ONZ

Narrative: R2597 Object sleutel gegeven moet verwijzen naar bestaand gegeven op de persoonslijst en van het juiste type zijn

Scenario: Objectsleutelgegeven verwijst naar een ander objectsleutel
          LT: AAON01C10T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AAON/AAON01C10T30.xls

Then heeft $PERSOON_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen='Libby'
Then heeft $HUWELIJK$ de waarde van de volgende query: select id from kern.relatie where srt=1

When voer een bijhouding uit AAON01C10T30.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/overig/AAON/expected/AAON01C10T30.xml voor expressie //brp:bhg_ondRegistreerOnderzoek_R