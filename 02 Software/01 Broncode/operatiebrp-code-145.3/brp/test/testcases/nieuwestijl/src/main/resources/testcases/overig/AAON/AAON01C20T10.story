Meta:
@status                 Klaar
@usecase                BY.0.ONZ

Narrative: R2599 Voorkomen sleutel gegeven moet bestaan als niet vervallen voorkomen op de persoonlijst en van dezelfde groep zijn als het in onderzoek geplaatste element

Scenario: voorkomensleutel is vervallen
          LT: AAON01C20T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AAON/AAON01C20T10.xls

Then heeft $PERSOON_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen='Libby'
Then heeft $HIS_PERSOON_ID$ de waarde van de volgende query: select id from kern.his_persids where tsverval is not null

When voer een bijhouding uit AAON01C20T10.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/overig/AAON/expected/AAON01C20T10.xml voor expressie //brp:bhg_ondRegistreerOnderzoek_R