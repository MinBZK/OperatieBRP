Meta:
@auteur             luwid
@status             Onderhanden
@usecase            LV.1.MB
@regels             R2015
@sleutelwoorden     Maak BRP bericht

Narrative:
In het verantwoordingsgedeelte van een levering mogen onder een Administratieve handeling geen bronnen (d.w.z. Document of Rechtsgrond) worden opgenomen
 waarnaar geen enkele verwijzing (meer) bestaat vanuit een Actie \ Bron binnen dezelfde bovenliggende Administratieve handeling.

Scenario: 1     Afnemer is niet geautoriseerd op Persoon.Nationaliteit zodat de bijbehorende actie en bron niet worden geleverd.
                Logisch testgeval: R2015_01
                Verwacht resultaat: Persoon.nationaliteit en bijbehorende Actie en Bron NIET in het resultaat

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Tiel.txt
Given bijhoudingsverzoek voor partij 'Gemeente Tiel'

Given de personen 826933129,526521673,508908681,631512457,141901317,849573385,105892919 zijn verwijderd
Given de standaardpersoon Sandy met bsn 508908681 en anr 1820279186 zonder extra gebeurtenissen
Given de standaardpersoon Danny met bsn 849573385 en anr 5490215698 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
def Sandy = Persoon.uitDatabase(bsn: 508908681)
def Danny = Persoon.uitDatabase(bsn: 849573385)
Persoon.nieuweGebeurtenissenVoor(Sandy) {
    huwelijk(aanvang: 20150606, registratieDatum: 20150606) {
          op 20150605 te 'Rotterdam' gemeente 'Rotterdam'
          met Danny
    }
}
slaOp(Sandy)

Given administratieve handeling van type geboorteInNederland , met de acties registratieGeboorte
And testdata uit bestand 28_R2015_geboorte_in_nederland_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Given leveringsautorisatie uit /levering_autorisaties/Geen_autorisatie_Persoon_Nationaliteit

Given de cache is herladen

When voor persoon 105892919 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen autorisatie op Persoon.Nationaliteit is ontvangen en wordt bekeken
Then heeft het bericht 0 groep 'nationaliteiten'
And heeft het bericht 3 groep 'bron'
And hebben de attributen in de groepen de volgende waardes:
| groep    | attribuut  | verwachteWaardes |
| document | aktenummer | 0A01000         |

Scenario: 2     Afnemer geautoriseerd op alle groepen. De actieBron en bron behorende bij groep Persoon.Nationaliteit worden geleverd.
                Logisch testgeval: R2015_02
                Verwacht resultaat: Persoon.nationaliteit en bijbehorende Actie en Bron in het resultaat

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Tiel.txt
Given bijhoudingsverzoek voor partij 'Gemeente Tiel'

Given de personen 826933129,526521673,508908681,631512457,141901317,849573385,105892919 zijn verwijderd
Given de standaardpersoon Sandy met bsn 508908681 en anr 1820279186 zonder extra gebeurtenissen
Given de standaardpersoon Danny met bsn 849573385 en anr 5490215698 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
def Sandy = Persoon.uitDatabase(bsn: 508908681)
def Danny = Persoon.uitDatabase(bsn: 849573385)
Persoon.nieuweGebeurtenissenVoor(Sandy) {
    huwelijk(aanvang: 20150606, registratieDatum: 20150606) {
          op 20150605 te 'Rotterdam' gemeente 'Rotterdam'
          met Danny
    }
}
slaOp(Sandy)

Given administratieve handeling van type geboorteInNederland , met de acties registratieGeboorte
And testdata uit bestand 28_R2015_geboorte_in_nederland_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding

Given de cache is herladen

When voor persoon 105892919 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then heeft het bericht 1 groep 'nationaliteiten'
And heeft het bericht 5 groep 'bron'
And heeft het bericht 2 groep 'document'
Then hebben attributen in voorkomens de volgende waardes:
| groep | nummer | attribuut | verwachteWaarde           |
| actie | 3      | soortNaam | Registratie nationaliteit |

!-- And hebben de attributen in de groepen de volgende waardes:
!-- | groep    | attribuut  | verwachteWaardes |
!-- | document | aktenummer | 0A01000, 0A01001 |

Scenario: 3     Afnemer is niet geautoriseerd op Persoon.Nationaliteit maar wel op Persoon.Geboorte. De actieBronnen van beiden verwijzen naar dezelfde
bron.
                Logisch testgeval: R2015_03
                Verwacht resultaat: Persoon.nationaliteit en bijbehorende actieBron NIET in het resultaat. Persoon.Geboorte en bijbehorende
                actieBron en Bronnen in het resultaat.

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Tiel.txt
Given bijhoudingsverzoek voor partij 'Gemeente Tiel'

Given de personen 826933129,526521673,508908681,631512457,141901317,849573385,105892919 zijn verwijderd
Given de standaardpersoon Sandy met bsn 508908681 en anr 1820279186 zonder extra gebeurtenissen
Given de standaardpersoon Danny met bsn 849573385 en anr 5490215698 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
def Sandy = Persoon.uitDatabase(bsn: 508908681)
def Danny = Persoon.uitDatabase(bsn: 849573385)
Persoon.nieuweGebeurtenissenVoor(Sandy) {
    huwelijk(aanvang: 20150606, registratieDatum: 20150606) {
          op 20150605 te 'Rotterdam' gemeente 'Rotterdam'
          met Danny
    }
}
slaOp(Sandy)

Given administratieve handeling van type geboorteInNederland , met de acties registratieGeboorte
And testdata uit bestand 28_R2015_geboorte_in_nederland_02.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Given leveringsautorisatie uit /levering_autorisaties/Geen_autorisatie_Persoon_Nationaliteit

Given de cache is herladen

When voor persoon 105892919 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen autorisatie op Persoon.Nationaliteit is ontvangen en wordt bekeken
Then heeft het bericht 0 groep 'nationaliteiten'
And heeft het bericht 5 groep 'bron'
And heeft het bericht 2 groep 'document'

!-- And hebben de attributen in de groepen de volgende waardes:
!-- | groep    | attribuut  | verwachteWaardes |
!-- | document | aktenummer | 0A01000, 0A01001 |

Scenario: 4     Geen autorisatie op groepen Persoon.Nationaliteit en Persoon.Identificatienummers. De actieBronnen van beiden verwijzen naar dezelfde
                bron.
                Logisch testgeval: R2015_04
                Verwacht resultaat: Persoon.nationaliteit, Persoon.Identificatienummers en bijbehorende actieBron en Bron NIET in het resultaat.

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Tiel.txt
Given bijhoudingsverzoek voor partij 'Gemeente Tiel'

Given de personen 826933129,526521673,508908681,631512457,141901317,849573385,105892919 zijn verwijderd
Given de standaardpersoon Sandy met bsn 508908681 en anr 1820279186 zonder extra gebeurtenissen
Given de standaardpersoon Danny met bsn 849573385 en anr 5490215698 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
def Sandy = Persoon.uitDatabase(bsn: 508908681)
def Danny = Persoon.uitDatabase(bsn: 849573385)
Persoon.nieuweGebeurtenissenVoor(Sandy) {
    huwelijk(aanvang: 20150606, registratieDatum: 20150606) {
          op 20150605 te 'Rotterdam' gemeente 'Rotterdam'
          met Danny
    }
}
slaOp(Sandy)

Given administratieve handeling van type geboorteInNederland , met de acties registratieGeboorte
And testdata uit bestand 28_R2015_geboorte_in_nederland_03.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Given leveringsautorisatie uit /levering_autorisaties/Geen_autorisatie_Persoon_Nationaliteit_en_Persoon_Identificatienummers

Given de cache is herladen

When voor persoon 105892919 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen autorisatie op Persoon.Nationaliteit en Persoon.Identificatienummers is ontvangen en wordt bekeken
Then heeft het bericht 0 groep 'nationaliteiten'
And heeft het bericht 2 groep 'bron'
And hebben de attributen in de groepen de volgende waardes:
| groep    | attribuut  | verwachteWaardes |
| document | aktenummer | 0A01000 |
