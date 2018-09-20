Meta:
@status                 Klaar
@sleutelwoorden         geboorteInNederland,intaketest

Narrative:
Afstamming, geboorte in Nederland met de acties registratie geboorte

Scenario: 1. Het hele response bericht wordt gecontroleerd, en ook een aantal waardes in de database.

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Tiel.txt
Given bijhoudingsverzoek voor partij 'Gemeente Tiel'

Given de personen 826933129,526521673,430955145,631512457,141901317,220367681,105892919 zijn verwijderd
Given de standaardpersoon Sandy met bsn 430955145 en anr 1785480146 zonder extra gebeurtenissen
Given de standaardpersoon Danny met bsn 220367681 en anr 4909308434 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
def Sandy = Persoon.uitDatabase(bsn: 430955145)
def Danny = Persoon.uitDatabase(bsn: 220367681)
Persoon.nieuweGebeurtenissenVoor(Sandy) {
    huwelijk(aanvang: 20150606, registratieDatum: 20150606) {
          op 20150605 te 'Rotterdam' gemeente 'Rotterdam'
          met Danny
    }
}
slaOp(Sandy)

Given administratieve handeling van type geboorteInNederland , met de acties registratieGeboorte
And testdata uit bestand geboorte_in_nederland_01.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/intake/afstamming/expected_afstamming_berichten/expected_geboorte_in_nederland_scenario_1.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R

Then in kern select voornamen, geslnaamstam, voornamennaamgebruik from kern.pers where bsn = 105892919 de volgende gegevens:
| veld                 | waarde |
| voornamen            | Wouter |
| geslnaamstam         | Zuko   |
| voornamennaamgebruik | Wouter |
