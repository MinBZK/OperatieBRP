Meta:
@status                 Klaar
@usecase                BY.0.ONZ

Narrative: R2626 Waarschuwing bij bijhouding indien gegevens van een hoofdpersoon in onderzoek staan

Scenario: Een onderzoek wordt nogmaals in onderzoek geplaatst
          LT: AAON01C30T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AAON/AAON01C30T10.xls

Then heeft $PERSOON_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen='Libby'
Then heeft $HIS_PERSOON_ID$ de waarde van de volgende query: select max(id) from kern.his_persids

!-- Onderzoek in uitvoering
When voer een bijhouding uit AAON01C30T10a.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

!-- Hetzelfde onderzoek nogmaals in uitvoering
When voer een bijhouding uit AAON01C30T10b.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

Then is het antwoordbericht gelijk aan /testcases/overig/AAON/expected/AAON01C30T10.xml voor expressie //brp:bhg_ondRegistreerOnderzoek_R