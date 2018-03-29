Meta:
@status                 Klaar
@usecase                UCS-BY.1.ON


Narrative: Triggers voor ontrelateren

Scenario:   Bijhoudingsvoorstel volledig verwerkt
            LT: ONTR01C10T50

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-ONTR/ONTR01C10T50-001.xls
Given enkel initiele vulling uit bestand /LO3PL-ONTR/ONTR01C10T50-002.xls

When voer een bijhouding uit ONTR01C10T50a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/ONTR/expected/ONTR01C10T50a.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Given pas laatste relatie van soort 1 aan tussen persoon 821029769 en persoon 813507273 met relatie id 30010001 en betrokkenheid id 30010001

Then is in de database de persoon met bsn 821029769 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 813507273 wel als PARTNER betrokken bij een HUWELIJK

When voer een bijhouding uit ONTR01C10T50b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/ONTR/expected/ONTR01C10T50b.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn i:821029769 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn i:813507273 niet als PARTNER betrokken bij een HUWELIJK

!-- Controleer nieuwe betrokkenheden
Then in kern heeft select count(1)
                   from kern.his_betr hr join kern.actie ainh on ainh.id = hr.actieinh LEFT join kern.actie av on av.id = hr.actieverval left join kern.srtactie sainh on ainh.srt = sainh.id left join kern.srtactie saav on av.srt = saav.id
                   where sainh.naam ='Ontrelateren' and av.srt is NULL de volgende gegevens:
| veld                      | waarde |
| count                     | 0      |