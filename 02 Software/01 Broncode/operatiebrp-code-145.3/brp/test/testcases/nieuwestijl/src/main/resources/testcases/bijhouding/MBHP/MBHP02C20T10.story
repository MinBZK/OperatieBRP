Meta:
@status                 Klaar
@usecase                BY.1.MP

Narrative:
Stap 10 Bepaal de personen die bij de administratieve handeling betrokken zijn

Scenario:   Een persoon is ingeschrevene, maar geen hoofdpersoon en ook geen gerelateerde
            LT: MBHP02C20T10



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-MBHP/MBHP02C20T10-Libby.xls
Given enkel initiele vulling uit bestand /LO3PL-MBHP/MBHP02C20T10-Jerry.xls

When voer een bijhouding uit MBHP02C20T10.xml namens partij 'Gemeente BRP 1'

Then is het antwoordbericht gelijk aan /testcases/bijhouding/MBHP/expected/MBHP02C20T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then controleer tijdstip laatste wijziging in bijhoudingsplan voor bijgehouden personen
