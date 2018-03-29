Meta:
@status                 Klaar
@usecase                BY.0.ONZ

Narrative:
Aanvang onderzoek gegevens persoon

Scenario: Aanvang onderzoek gegevens gerelateerde ouder op persoonslijst
          LT: AAON04C10T60

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AAON/AAON04C10T40-Libby.xls

Then heeft $HIS_OUDER_GEBOORTE$ de waarde van de volgende query: select max(pg.id) from kern.his_persgeboorte pg join kern.pers p on p.id = pg.pers where p.bsn = '431928617'
Then heeft $HIS_OUDER_GESLACHT$ de waarde van de volgende query: select max(pg.id) from kern.his_persgeslachtsaand pg join kern.pers p on p.id = pg.pers where p.bsn = '431928617'
Then heeft $HIS_OUDER_IDS$ de waarde van de volgende query: select max(pg.id) from kern.his_persids pg join kern.pers p on p.id = pg.pers where p.bsn = '431928617'
Then heeft $HIS_OUDER_SGNAAM$ de waarde van de volgende query: select max(pg.id) from kern.his_perssamengesteldenaam pg join kern.pers p on p.id = pg.pers where p.bsn = '431928617'

When voer een bijhouding uit AAON04C10T60.xml namens partij 'Gemeente BRP 1'

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
| element    | GerelateerdeOuder.Persoon.Geboorte    |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | GerelateerdeOuder.Persoon.Geslachtsaanduiding|
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | GerelateerdeOuder.Persoon.Identificatienummers|
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | GerelateerdeOuder.Persoon.Identiteit|
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | GerelateerdeOuder.Persoon.SamengesteldeNaam|


