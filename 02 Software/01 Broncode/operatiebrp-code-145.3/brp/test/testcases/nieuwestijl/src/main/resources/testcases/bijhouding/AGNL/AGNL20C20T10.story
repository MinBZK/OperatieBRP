Meta:
@status                 Onderhanden
@sleutelwoorden         Correctie(voorbeeldbericht)
@usecase                UCS-BY.HG


Narrative: Ongedaanmaking geregistreerd partnerschap in Nederland

Scenario:   Personen Anne(I) en Jan(P) hebben een GBA geregistreerd parterschap, dit partnerschap wordt in de BRP ongedaan gemaakt
            LT: AGNL20C20T10

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-EGNL/EGNL02C10T10-April.xls

Given pas laatste relatie van soort 2 aan tussen persoon 608064361 en persoon 255220297 met relatie id 2000073 en betrokkenheid id 2000074

When voer een bijhouding uit AGNL20C20T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AGNL/expected/AGNL20C20T10.xml voor expressie //brp:bhg_hgpCorrigeerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 608064361 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

Then lees persoon met anummer 3282542354 uit database en vergelijk met expected AGNL20C20T10-persoon1.xml
