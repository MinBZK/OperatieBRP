Meta:
@status                 Klaar
@usecase                BY.0.ONZ

Narrative:
Aanvang onderzoek gegevens persoon

Scenario: Aanvang onderzoek gegevens gerelateerde huwelijks partner op persoonslijst
          LT: AAON04C10T40

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AAON/AAON04C10T40-Libby.xls

Then heeft $HIS_HUWELIJK_GEBOORTE$ de waarde van de volgende query: select max(pg.id) from kern.his_persgeboorte pg join kern.pers p on p.id = pg.pers where p.bsn = '121139839'
Then heeft $HIS_HUWELIJK_GESLACHT$ de waarde van de volgende query: select max(pg.id) from kern.his_persgeslachtsaand pg join kern.pers p on p.id = pg.pers where p.bsn = '121139839'
Then heeft $HIS_HUWELIJK_IDS$ de waarde van de volgende query: select max(pg.id) from kern.his_persids pg join kern.pers p on p.id = pg.pers where p.bsn = '121139839'
Then heeft $HIS_HUWELIJK_SGNAAM$ de waarde van de volgende query: select max(pg.id) from kern.his_perssamengesteldenaam pg join kern.pers p on p.id = pg.pers where p.bsn = '121139839'

When voer een bijhouding uit AAON04C10T40.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Then in kern heeft select o.dataanv, o.oms, so.naam, e.naam as element from kern.onderzoek o
                      join kern.pers p on o.pers = p.id
                      join kern.statusonderzoek so on so.id = o.status
                      join kern.gegeveninonderzoek gio on gio.onderzoek = o.id
                      join kern.element e on e.id = gio.element
                      where p.bsn = '422531881' order by e.naam
de volgende gegevens:
| veld       | waarde                                           |
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | GerelateerdeHuwelijkspartner.Persoon.Geboorte    |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | GerelateerdeHuwelijkspartner.Persoon.Geslachtsaanduiding|
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | GerelateerdeHuwelijkspartner.Persoon.Identificatienummers|
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | GerelateerdeHuwelijkspartner.Persoon.Identiteit|
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam|


