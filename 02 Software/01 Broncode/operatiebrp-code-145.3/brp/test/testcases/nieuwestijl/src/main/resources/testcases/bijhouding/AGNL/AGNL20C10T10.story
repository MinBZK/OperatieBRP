Meta:
@status                 Onderhanden
@sleutelwoorden         Correctie(voorbeeldbericht)
@usecase                UCS-BY.HG

Narrative:
Correctie geregistreerd partnerschap tussen I-I en I-I (Happy Flow)

Scenario:   Personen Libby Thatcher (Ingeschrevene-Ingezetene) en Piet Jansen (Ingeschrevene-Ingezetene) gaan een geregistreerd partnerschap aan, controleer relatie, betrokkenheid, afgeleid administratief,
            LT: AGNL20C10T10

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-EGNL/EGNL02C10T30-April.xls
Given enkel initiele vulling uit bestand /LO3PL-EGNL/EGNL02C10T30-August.xls

When voer een bijhouding uit AGNL20C10T10a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AGNL/expected/AGNL20C10T10a.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 958201225 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

Given pas laatste relatie van soort 2 aan tussen persoon 958201225 en persoon 961325641	met relatie id 2000077 en betrokkenheid id 2000078
And de database is aangepast met: update kern.his_relatie set id = 9999 where id = (select hr.id from kern.his_relatie hr join kern.relatie r on r.id = hr.relatie where r.srt = 2 and hr.dataanv = 20160510)

When voer een bijhouding uit AGNL20C10T10b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AGNL/expected/AGNL20C10T10b.xml voor expressie //brp:bhg_hgpCorrigeerHuwelijkGeregistreerdPartnerschap_R
