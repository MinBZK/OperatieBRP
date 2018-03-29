Meta:
@status                 Klaar
@usecase                BY.0.ONZ

Narrative: R2595 Datum aanvang onderzoek mag niet in de toekomst liggen

Scenario: Datum aanvang onderzoek ligt op de systeemdatum
          LT: AAON02C10T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AAON/AAON02C10T10-001.xls

Then heeft $PERSOON_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen='Libby'
Then heeft $HIS_PERSOON_ID$ de waarde van de volgende query: select max(id) from kern.his_persids
When voer een bijhouding uit AAON02C10T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/overig/AAON/expected/AAON02C10T10.xml voor expressie //brp:bhg_ondRegistreerOnderzoek_R

