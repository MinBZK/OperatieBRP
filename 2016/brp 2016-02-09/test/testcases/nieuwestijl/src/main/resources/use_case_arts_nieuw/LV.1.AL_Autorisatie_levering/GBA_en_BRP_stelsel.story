Meta:
@sprintnummer           91
@epic                   Autenticatie levering
@auteur                 devel
@jiraIssue              TEAMBRP-4492
@status                 Klaar

Narrative: Als beheerder van het voor zowel het BRP als het GBA stelsel kunnen leveren

Scenario:   1. GBA en BRP stelsel

Given leveringsautorisatie uit /levering_autorisaties/gba_geen_pop_bep_levering_op_basis_van_afnemerindicatie
Given de personen 627129705, 304953337, 630668681 zijn verwijderd
Given de standaardpersoon Olivia met bsn 630668681 en anr 8654063890 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(630668681)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 'Gemeente Delft', aanvang: 20100212, registratieDatum: 20100212) {
            naarGemeente 'Delft',
                straat: 'Markt', nummer: 87, postcode: '2611GW', woonplaats: "Delft"
        }
}
slaOp(persoon)

When voor persoon 622389609 wordt de laatste handeling geleverd

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Delft'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand afnemerIndicatie_plaats.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd


Given de persoon beschrijvingen:
persoon = Persoon.metBsn(630668681)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 'Gemeente Delft', aanvang: 20100212, registratieDatum: 20100212) {
            naarGemeente 'Delft',
                straat: 'Markt', nummer: 89, postcode: '2611GW', woonplaats: "Delft"
        }
}
slaOp(persoon)

When voor persoon 622389609 wordt de laatste handeling geleverd

Then is er een LO3 levering gedaan
