Meta:
@status                 Klaar
@usecase                BY.0.ONZ

Narrative:
Aanvang onderzoek gegevens persoon

Scenario: Persoon krijgt onderzoek op Behandeld als NL, Signalering Reisdoc
          LT: AAON04C10T70

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AAON/AAON04C10T70-Libby.xls

Then heeft $HIS_PERSOON_INDICATIE_BNL$ de waarde van de volgende query: select max(hpi.id) from kern.his_persindicatie hpi join kern.persindicatie pi on pi.id = hpi.persindicatie join kern.srtindicatie si on si.id = pi.srt where si.naam = 'Behandeld als Nederlander?'
Then heeft $PERSOON_INDICATIE_BNL_OBJ$ de waarde van de volgende query: select max(pi.id) from kern.persindicatie pi join kern.srtindicatie si on si.id = pi.srt where si.naam = 'Behandeld als Nederlander?'
Then heeft $HIS_PERSOON_INDICATIE_RDOC$ de waarde van de volgende query: select max(hpi.id) from kern.his_persindicatie hpi join kern.persindicatie pi on pi.id = hpi.persindicatie join kern.srtindicatie si on si.id = pi.srt where si.naam = 'Signalering met betrekking tot verstrekken reisdocument?'
Then heeft $PERSOON_INDICATIE_RDOC_OBJ$ de waarde van de volgende query: select max(pi.id) from kern.persindicatie pi join kern.srtindicatie si on si.id = pi.srt where si.naam = 'Signalering met betrekking tot verstrekken reisdocument?'

When voer een bijhouding uit AAON04C10T70.xml namens partij 'Gemeente BRP 1'

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
| element    | Persoon.Indicatie.BehandeldAlsNederlander.Identiteit    |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Indicatie.BehandeldAlsNederlander.Standaard   |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Indicatie.SignaleringMetBetrekkingTotVerstrekkenReisdocument.Identiteit    |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Indicatie.SignaleringMetBetrekkingTotVerstrekkenReisdocument.Standaard   |


