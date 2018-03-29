Meta:
@auteur                 tjlee
@status                 Klaar
@usecase                BY.1.MP

Narrative:
Stap 10 Bepaal de personen die bij de administratieve handeling betrokken zijn

Scenario:   Een persoon is een gerelateerde, maar er worden geen gegevens gewijzigd
            LT: MBHP02C20T20



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-MBHP/MBHP02C20T20-Libby.xls
Given enkel initiele vulling uit bestand /LO3PL-MBHP/MBHP02C20T20-Kind.xls

When voer een bijhouding uit MBHP02C20T20.xml namens partij 'Gemeente BRP 1'

Then is het antwoordbericht gelijk aan /testcases/bijhouding/MBHP/expected/MBHP02C20T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then controleer tijdstip laatste wijziging in bijhoudingsplan voor bijgehouden personen
