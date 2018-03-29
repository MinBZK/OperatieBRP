Meta:
@status                 Klaar
@usecase                BY.0.ONZ

Narrative: R2607 Toegestane statuswijziging bij wijzigen onderzoek

Scenario: Statusovergang van Afgesloten vanuit de conversie op de PL naar Gestaakt
          LT: WION01C10T60

Given alle personen zijn verwijderd

!-- Vulling van een persoon met een afgesloten onderzoek op categorie 01
Given enkel initiele vulling uit bestand /LO3PL-WION/WION01C10-afgesloten.xls

Then heeft $PERSOON_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen='Libby'
Then heeft $ONDERZOEK_ID$ de waarde van de volgende query: select o.id from kern.onderzoek o join kern.pers p on o.pers = p.id where p.voornamen='Libby'

!-- Wijziging van de status van Afgesloten naar Gestaakt
When voer een bijhouding uit WION01C10T60.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/overig/WION/expected/WION01C10T60.xml voor expressie //brp:bhg_ondRegistreerOnderzoek_R