Meta:
@epic                   Voltrekking huwelijk in Nederland
@auteur                 dihoe
@status                 Klaar
@regels                 R1804

Narrative:

Scenario: 1. R1804_01 Sortering verantwoording in bericht
                Logisch testgeval: R1804_01

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding

Given de personen 727750537,212208937,814591139,875271467,646257353,564779209,282932185,743274313 zijn verwijderd
Given de standaardpersoon Ciske met bsn 282932185 en anr 8185058578 zonder extra gebeurtenissen
Given de standaardpersoon Matilda met bsn 646257353 en anr 4765349650 zonder extra gebeurtenissen
Given de standaardpersoon Gregory met bsn 564779209 en anr 7960380178 zonder extra gebeurtenissen

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar'
And administratieve handeling van type voltrekkingHuwelijkInNederland , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap, registratieNaamgebruik
And testdata uit bestand R1804_huwelijk.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Given de persoon beschrijvingen:
Ciske     =   Persoon.metBsn(282932185)
def Gregory = Persoon.uitDatabase(bsn: 564779209)

nieuweGebeurtenissenVoor(Ciske) {
        erkend(partij: 63701, aanvang: 20150101, toelichting: 'Correct n.a.v. een erkening ongeboren vrucht', registratieDatum: 20150101) {
                door Gregory
            }
        naamswijziging(aanvang: 20150201, registratieDatum: 20150202) {
                  document grond:'Officieel Document verklaring'
                  geslachtsnaam(1) wordt stam:'Zonnig', voorvoegsel:'het'
            }
        verhuizing(partij: 'Gemeente Delft', aanvang: 20150202, registratieDatum: 20150203) {
                   naarGemeente 'Delft',
                       straat: 'Aalscholverring', nummer: 30, postcode: '2623PD', woonplaats: "Delft"
               }
}
slaOp(Ciske)


When voor persoon 282932185 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand R1804_Synchroniseer_Persoon_01.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende waardes:
| groep                    | nummer | attribuut | verwachteWaarde                   |
| administratieveHandeling | 1      | naam      | Voltrekking huwelijk in Nederland |
| administratieveHandeling | 2      | naam      | Verhuizing intergemeentelijk      |
| administratieveHandeling | 3      | naam      | Wijziging geslachtsnaam           |
| administratieveHandeling | 4      | naam      | Erkenning na geboorte             |
| administratieveHandeling | 5      | naam      | Geboorte in Nederland             |


