Meta:
@status                 Klaar
@usecase                BY.0.ONZ

Narrative: R2599 Voorkomen sleutel gegeven moet bestaan als niet vervallen voorkomen op de persoonlijst en van dezelfde groep zijn als het in onderzoek geplaatste element

Scenario: voorkomensleutel is van een andere groep
          LT: AAON01C20T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AAON/AAON01C20T20.xls

Then heeft $PERSOON_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen='Libby'
Then heeft $GROEP_ID$ de waarde van de volgende query: select max(id) from kern.his_persinschr

When voer een bijhouding uit AAON01C20T20.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/overig/AAON/expected/AAON01C20T20.xml voor expressie //brp:bhg_ondRegistreerOnderzoek_R