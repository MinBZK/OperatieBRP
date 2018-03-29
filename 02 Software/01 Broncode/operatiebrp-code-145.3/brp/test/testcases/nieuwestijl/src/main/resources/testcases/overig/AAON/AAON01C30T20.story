Meta:
@status                 Klaar
@usecase                BY.0.ONZ

Narrative: R2626 Waarschuwing bij bijhouding indien gegevens van een hoofdpersoon in onderzoek staan

Scenario: Een gegeven dat in onderzoek staat vanuit de conversie wordt via de AH nogmaals in onderzoek geplaatst
          LT: AAON01C30T20

Given alle personen zijn verwijderd

!-- Vulling van een persoon waarvan Burgerservicenummer in onderzoek staat
Given enkel initiele vulling uit bestand /LO3PL-AAON/AAON01C30T20.xls

Then heeft $PERSOON_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen='Libby'
Then heeft $HIS_PERSOON_ID$ de waarde van de volgende query: select max(id) from kern.his_persids

!-- Plaats een onderzoek op Burgerservicenummer
When voer een bijhouding uit AAON01C30T20.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

Then is het antwoordbericht gelijk aan /testcases/overig/AAON/expected/AAON01C30T20.xml voor expressie //brp:bhg_ondRegistreerOnderzoek_R