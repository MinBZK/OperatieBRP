Meta:
@status                 Klaar
@usecase                BY.0.ONZ

Narrative: R2607 Toegestane statuswijziging bij wijzigen onderzoek

Scenario: Statuswijziging In uitvoering naar Afgesloten
          LT: WION01C10T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-WION/WION01C10.xls
And de database is aangepast met: update kern.his_persids set id = 9999 where id = (select max(id) from kern.his_persids)

Then heeft $PERSOON_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen='Libby'
Then heeft $HIS_PERSOON_ID$ de waarde van de volgende query: select max(id) from kern.his_persids
When voer een bijhouding uit WION01C10T10a.xml namens partij 'Gemeente BRP 1'

Then heeft $ONDERZOEK_ID$ de waarde van de volgende query: select o.id from kern.onderzoek o join kern.pers p on o.pers = p.id where p.voornamen='Libby'

When voer een bijhouding uit WION01C10T10b.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/overig/WION/expected/WION01C10T10.xml voor expressie //brp:bhg_ondRegistreerOnderzoek_R