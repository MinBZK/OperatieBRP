Meta:
@auteur             kedon
@status             Onderhanden
@usecase            LV.1.MB
@regels             R2002
@sleutelwoorden     Maak BRP bericht

Narrative:
Een attribuut dat een bijhoudingsgegeven is mag alleen geleverd worden aan een Partij met de Rol "Bijhoudingsorgaan College" en "Bijhoudingsorgaan Minister" voor de Dienst waarvoor geleverd word.
Een attribuut is een bijhoudingsgegeven als de Element.Autorisatie van het Element"Bijhoudingsgegevens" is.

Scenario: 1     Bijhoudingsgegeven indicatieOuderUitWieKindIsGeboren (autorisatie 7), partijrol Afnemer (1)
                Logisch testgeval: R2002_02
                Verwacht resultaat: indicatieOuderUitWieKindIsGeboren NIET in het resultaat

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
And testdata uit bestand 27_R2002_geboorte_in_nederland_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Given leveringsautorisatie uit /levering_autorisaties/Autorisatie_op_een_bijhoudingsgegeven_voor_geen_bijhouder

Given de cache is herladen

When voor persoon 105892919 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Autorisatie op een bijhoudingsgegeven voor geen bijhouder is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep      | nummer | attribuut                         | aanwezig |
| ouderschap | 1      | indicatieOuderUitWieKindIsGeboren | nee      |
| ouderschap | 2      | indicatieOuderUitWieKindIsGeboren | nee      |


Scenario: 2     Bijhoudingsgegeven indicatieOuderUitWieKindIsGeboren (autorisatie 7), partijrol Bijhoudingsorgaan College (2)
                Logisch testgeval: R2002_02
                Verwacht resultaat: indicatieOuderUitWieKindIsGeboren in het resultaat

Given leveringsautorisatie uit /levering_autorisaties/Autorisatie_op_een_bijhoudingsgegeven_voor_bijhouder

!-- Given de database is aangepast met: update kern.partijrol set rol = 3 where id = 396
!-- Given de database is aangepast met: update kern.partijrol set rol = 2 where id = 3296

Given de cache is herladen

When voor persoon 105892919 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Autorisatie op een bijhoudingsgegeven voor bijhouder is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep      | nummer | attribuut                         | aanwezig |
| ouderschap | 1      | indicatieOuderUitWieKindIsGeboren | ja       |
| ouderschap | 2      | indicatieOuderUitWieKindIsGeboren | nee      |
