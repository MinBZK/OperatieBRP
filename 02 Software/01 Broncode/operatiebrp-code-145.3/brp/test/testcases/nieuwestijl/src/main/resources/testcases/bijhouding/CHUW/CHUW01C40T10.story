Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: R2493 Melding indien er een feitdatum op de persoonslijst staat die recenter dan of gelijk is aan de Peildatum, DAG en DEG van de Administratieve handeling.

Scenario:   Corrigeren van huwelijk mag regel niet afgaan
            LT: CHUW01C40T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-CHUW/CHUW01C40T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-CHUW/CHUW01C40T10-002.xls

And de database is aangepast met: update kern.his_persindicatie set dataanvgel = to_number((to_char(now(), 'YYYYMMDD')), '99999999') where persindicatie in (select id from kern.persindicatie where pers in (select id from kern.pers where voornamen='Libby'));


When voer een bijhouding uit CHUW01C40T10a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Given pas laatste relatie van soort 1 aan tussen persoon 953977481 en persoon 745853705 met relatie id 30010001 en betrokkenheid id 30010001
And de database is aangepast met: update kern.his_relatie set id = 9999 where id = (select hr.id from kern.his_relatie hr join kern.relatie r on r.id = hr.relatie where r.srt = 1 and hr.dataanv = 20160510)

Then is in de database de persoon met bsn 953977481 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 745853705 wel als PARTNER betrokken bij een HUWELIJK

Then in kern heeft select statuslev from kern.admhnd where partij = 27012 and tslev is null de volgende gegevens:
| veld                      | waarde |
| statuslev                 | 1      |

Then in kern heeft select toelichtingontlening from kern.admhnd where toelichtingontlening is not null de volgende gegevens:
| veld                      | waarde                |
| toelichtingontlening      | test toelichting      |

When voer een bijhouding uit CHUW01C40T10b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/CHUW/expected/CHUW01C40T10.xml voor expressie //brp:bhg_hgpCorrigeerHuwelijkGeregistreerdPartnerschap_R