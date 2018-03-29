Meta:
@status                 Klaar
@sleutelwoorden         INTEST-2851

Narrative:
Beschrijving testgeval: In de IV is een persoon in de database gezet waarbij het huwelijk in onderzoek staat.
Vervolgens vindt er een infrastructurele wijziging plaats. Er staat een afnemerindicatie op deze persoon.

Verwacht resultaat: mutatielevering met de infrastructurele wijziging. Het onderzoek hoeft niet geleverd te worden,
omdat de mutatie geen betrekking heeft op de onderdelen die in onderzoek staan.

Actueel resultaat: mutatielevering met de infrastructurele wijziging en het onderzoek met het huwelijk als referentie.

Scenario: 1   INTEST2851

Given leveringsautorisatie uit autorisatie/Levering_obv_doelbinding
Given persoonsbeelden uit src/test/resources/blob/1-persoon.blob.json
When voor persoon 119265461 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Levering obv doelbinding is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan /testcases/IENT/expecteds/INTEST2851-expected.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- FIXME gebruik persoon INTEST2851 uit specials wanneer beschikbaar
