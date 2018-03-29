Meta:
@status                 Klaar
@usecase                UCS-BY.1.ON


Narrative: Triggers voor ontrelateren

Scenario:   Uitgesloten persoon icm Automatische fiat
            LT: ONTR01C10T20

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-ONTR/ONTR01C10T20-001.xls
Given enkel initiele vulling uit bestand /LO3PL-ONTR/ONTR01C10T20-002.xls

When voer een bijhouding uit ONTR01C10T20a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/ONTR/expected/ONTR01C10T20a.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then staat er 1 notificatiebericht voor bijhouders op de queue

Given pas laatste relatie van soort 1 aan tussen persoon 148869105 en persoon 959109833 met relatie id 30010001 en betrokkenheid id 30010001

Then is in de database de persoon met bsn 148869105 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 959109833 wel als PARTNER betrokken bij een HUWELIJK

When voer een bijhouding uit ONTR01C10T20b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/ONTR/expected/ONTR01C10T20b.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn i:148869105 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn i:959109833 wel als PARTNER betrokken bij een HUWELIJK

!-- Controleer nieuwe betrokkenheden
Then in kern heeft select count(1)
                   from kern.his_betr hr join kern.actie ainh on ainh.id = hr.actieinh LEFT join kern.actie av on av.id = hr.actieverval left join kern.srtactie sainh on ainh.srt = sainh.id left join kern.srtactie saav on av.srt = saav.id
                   where sainh.naam ='Ontrelateren' and av.srt is NULL de volgende gegevens:
| veld                      | waarde |
| count                     | 4      |

Then staat er 1 notificatiebericht voor bijhouders op de queue