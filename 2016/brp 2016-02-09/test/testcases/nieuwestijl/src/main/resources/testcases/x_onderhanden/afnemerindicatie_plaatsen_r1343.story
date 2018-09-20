Meta:
@sprintnummer           91
@epic                   Verbeteren testtooling
@auteur                 devel
@jiraIssue              TEAMBRP-90210
@status                 Onderhanden
@sleutelwoorden         Intaketest, Mutatielevering, Automatisch_volgen

Narrative:  Als team BRP
            wil ik een geautomatiseerde intaketest die moet aantonen dat de functionaliteit, bij oplevering, aan de minimale eisen voldoet
            zodat ik kan bepalen of deze wel/niet testgereed is

Scenario:   1.  Hoofdpersoon zit niet in doelbinding van abonnement postcode gebied Heemstede 2100-2129
                Verwacht resultaat:
                - Geen levering daar persoon niet in doelbinding zit

Given leveringsautorisatie uit /levering_autorisaties/mutatielevering_obv_afn_ind/postcode_gebied_Hillegom_2180-2182
Given de personen 875271467, 814591139, 422751625 zijn verwijderd
Given de standaardpersoon Gregory met bsn 422751625 en anr 4023907602 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(422751625)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 53401, aanvang: 20130510, registratieDatum: 20130510) {
        naarGemeente 'Hillegom',
            straat: 'Hoofdstraat', nummer: 115, postcode: '2181EC', woonplaats: "Hillegom"
    }
}
slaOp(persoon)

Given verzoek voor leveringsautorisatie 'postcode gebied Hillegom 2180 - 2182' en partij 'SRPUC50151-5-Partij'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand afnemerindicatie_plaatsen.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is er voor persoon met bsn 422751625 en leveringautorisatie postcode gebied Hillegom 2180 - 2182 een afnemerindicatie geplaatst

When voor persoon 422751625 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie postcode gebied Hillegom 2180 - 2182 is ontvangen en wordt bekeken
Then is het bericht xsd-valide


Given de persoon beschrijvingen:
def greg = uitDatabase bsn: 422751625

Persoon.nieuweGebeurtenissenVoor(greg) {
    verstrekkingsbeperking() {
        registratieBeperkingen( partij: 505005 )
    }

}
slaOp(greg)

When voor persoon 422751625 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie postcode gebied Hillegom 2180 - 2182 is ontvangen en wordt bekeken
Then is het bericht xsd-valide