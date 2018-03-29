Meta:
@status                 Klaar
@usecase                BY.0.ONZ

Narrative: R2596 Object sleutel gegeven mag niet gevuld zijn als Voorkomen sleutel gegeven gevuld is en viceversa

Scenario: Objectsleutelgegeven en voorkomensleutelgegeven zijn beiden ingevuld
          LT: AAON02C30T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AAON/AAON02C30T10-001.xls

Then heeft $PERSOON_ID$ de waarde van de volgende query: select id from kern.pers where voornamen='Libby'
Then heeft $PERSOON_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen='Libby'
Then heeft $HIS_PERSOON_ID$ de waarde van de volgende query: select max(id) from kern.his_persids

When voer een bijhouding uit AAON02C30T10.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/overig/AAON/expected/AAON02C30T10.xml voor expressie //brp:bhg_ondRegistreerOnderzoek_R