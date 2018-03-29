Meta:
@status                 Klaar
@usecase                BY.0.ONZ

Narrative:
Scenario 4 Toevoegen onderzoek

Scenario: Persoon krijgt onderzoek op verschillende objecten en voorkomens
          LT: AAON04C10T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AAON/AAON04C10T10-Libby.xls

Then heeft $HIS_PERSOON_ID$ de waarde van de volgende query: select max(id) from kern.his_persids
When voer een bijhouding uit AAON04C10T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/overig/AAON/expected/AAON04C10T10.xml voor expressie //brp:bhg_ondRegistreerOnderzoek_R
Then in kern heeft select o.dataanv, o.oms, so.naam, e.naam as element, pid.anr from kern.onderzoek o
                      join kern.pers p on o.pers = p.id
                      join kern.statusonderzoek so on so.id = o.status
                      join kern.gegeveninonderzoek gio on gio.onderzoek = o.id
                      join kern.element e on e.id = gio.element
                      join kern.his_persids pid on pid.id = gio.voorkomensleutelgegeven
                   where p.bsn = '422531881'
de volgende gegevens:
| veld       | waarde                                           |
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar administratienummer van Libby.    |
| naam       | In uitvoering                                    |
| element    | Persoon.Identificatienummers.Administratienummer |
| anr        | 1868196961                                       |
