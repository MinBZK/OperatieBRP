Meta:
@status                 Klaar
@sleutelwoorden         Geslaagd
@usecase                UCS-BY.HG

Narrative:
R2577 Administratieve handeling moet minimaal 1 actie bevatten

Scenario:   De Administratieve handeling bevat geen acties
            LT: WPHW01C30T10


Given alle personen zijn verwijderd

When voer een bijhouding uit WPHW01C30T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/WPHW/expected/WPHW01C30T10.xml voor expressie //brp:bhg_hgpActualiseerHuwelijkGeregistreerdPartnerschap_R






