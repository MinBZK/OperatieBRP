Meta:
@sprintnummer           76
@epic                   Afhankelijkheden migratie
@auteur                 dihoe
@jiraIssue              TEAMBRP-2865
@status                 Klaar
@regels                 BRLV0027, R1315


Narrative:  Aanscherping BRLV0027: Waarschuwing buiten doelbinding ook bij volledigbericht obv afnemerindicatie
            R1315	BRLV0027	Waarschuwing als persoon met afnemerindicatie uit doelgroep abonnement valt

Scenario:   1a. bijhouding (verhuizing buitenGemeente) met een abonnement o.b.v. afnemerindicatie
            Verwacht resultaat:
            - Een volledigBericht met een melding
            - Geen mutatieBericht

Given de gehele database is gereset
Given leveringsautorisatie uit /levering_autorisaties/mutatielevering_obv_afn_ind/postcode_gebied_Hillegom_2180-2182
Given de standaardpersoon Vanilla met bsn 809783174 en anr 9154947859 met extra gebeurtenissen:
verhuizing(partij: 'Gemeente Hillegom', aanvang: 19930731, registratieDatum: 19930731) {
    naarGemeente 'Hillegom',
       straat: 'Tulpstraat', nummer: 10, postcode: '2180AB', woonplaats: "Hillegom"
}
When voor persoon 809783174 wordt de laatste handeling geleverd

Given verzoek voor leveringsautorisatie 'postcode gebied Hillegom 2180 - 2182' en partij 'SRPUC50151-5-Partij'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand afnemerIndicatie_plaats_2.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie postcode gebied Hillegom 2180 - 2182 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario:   1b. Bijhouding waardoor persoon niet meer in doelbinding valt.

Given de persoon beschrijvingen:
def persoon    = Persoon.uitDatabase(bsn: 809783174)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 'Gemeente Almere', aanvang: 20150731) {
        naarGemeente 'Almere',
           straat: 'Stadhuisplein', nummer: 1, postcode: '1315HR', woonplaats: "Almere"
    }
  }
slaOp(persoon)

When voor persoon 809783174 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie postcode gebied Hillegom 2180 - 2182 is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep   | attribuut | verwachteWaardes |
| melding | regelCode | BRLV0027         |
| melding | soortNaam | Waarschuwing     |
| melding | melding   | De geleverde persoon valt niet meer binnen de doelbindingspopulatie van de leveringsautorisatie.|

