Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R2011 Voor een persoon met de Nederlandse nationaliteit wordt uitsluitend deze nationaliteit bijgehouden

Scenario:   Persoon heeft een vervallen NL nationaliteit en bij erkenning wordt een nationaliteit geregistreerd
            LT: ERKE01C180T40

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-ERKE/ERKE01C180T40-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-ERKE/ERKE01C180T40-002.xls

Then heeft $KIND_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen = 'Marie'
Then heeft $NOUWKIG_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen = 'Henk'


Then heeft $RELATIE_ID$ de waarde van de volgende query: select r.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.voornamen = 'Marie' and r.srt=3
Then heeft $KIND_BETROKKENHEID$ de waarde van de volgende query: select b.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.voornamen = 'Marie' and r.srt=3


When voer een bijhouding uit ERKE01C180T40.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

!-- Controleer nationaliteit
Then in kern heeft select p.voornamen, n.indag, sn.naam
                   from kern.persnation n
                   join kern.pers p on p.id = n.pers
                   join kern.nation sn on sn.id = n.nation
                   where p.voornamen = 'Marie'
                   order by sn.naam de volgende gegevens:
| veld                      | waarde                                        |
| voornamen                 | Marie                                         |
| indag                     | false                                         |
| naam                      | Nederlandse                                   |
----
| voornamen                 | Marie                                         |
| indag                     | true                                          |
| naam                      | Tsjechische                                   |
