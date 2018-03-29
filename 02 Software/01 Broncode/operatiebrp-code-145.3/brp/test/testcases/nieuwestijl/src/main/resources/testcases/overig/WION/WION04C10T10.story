Meta:
@status                 Klaar
@usecase                BY.0.ONZ

Narrative:
Scenario 4 Wijziging onderzoek

Scenario: Persoon krijgt onderzoek op verschillende objecten en voorkomens
          LT: WION04C10T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-WION/WION04C10T10-Libby.xls
And de database is aangepast met: update kern.his_persids set id = 9999 where id = (select max(id) from kern.his_persids)

Then heeft $HIS_PERSOON_ID$ de waarde van de volgende query: select max(id) from kern.his_persids
When voer een bijhouding uit WION04C10T10a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/overig/WION/expected/WION04C10T10a.xml voor expressie //brp:bhg_ondRegistreerOnderzoek_R
Then in kern heeft select o.dataanv, o.oms, so.naam, e.naam as element, pid.anr from kern.onderzoek o
                      join kern.pers p on o.pers = p.id
                      join kern.statusonderzoek so on so.id = o.status
                      join kern.gegeveninonderzoek gio on gio.onderzoek = o.id
                      join kern.element e on e.id = gio.element
                      join kern.his_persids pid on pid.id = gio.voorkomensleutelgegeven
                   where p.bsn = '788566921'
de volgende gegevens:
| veld       | waarde                                           |
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar administratienummer van Libby.    |
| naam       | In uitvoering                                    |
| element    | Persoon.Identificatienummers.Administratienummer |
| anr        | 7075456786                                       |

Then heeft $ONDERZOEK_ID$ de waarde van de volgende query: select o.id from kern.onderzoek o join kern.pers p on o.pers = p.id where p.bsn = '788566921'

When voer een bijhouding uit WION04C10T10b.xml namens partij 'Gemeente BRP 1'
Then is het antwoordbericht gelijk aan /testcases/overig/WION/expected/WION04C10T10b.xml voor expressie //brp:bhg_ondRegistreerOnderzoek_R
Then in kern heeft select o.dataanv, o.oms, so.naam, e.naam as element, pid.anr from kern.onderzoek o
                      join kern.pers p on o.pers = p.id
                      join kern.statusonderzoek so on so.id = o.status
                      join kern.gegeveninonderzoek gio on gio.onderzoek = o.id
                      join kern.element e on e.id = gio.element
                      join kern.his_persids pid on pid.id = gio.voorkomensleutelgegeven
                   where p.bsn = '788566921'
de volgende gegevens:
| veld       | waarde                                           |
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar administratienummer van Libby.    |
| naam       | Gestaakt                                    |
| element    | Persoon.Identificatienummers.Administratienummer |
| anr        | 7075456786                                       |
