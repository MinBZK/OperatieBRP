Meta:
@auteur             kedon
@status             Onderhanden
@usecase            LV.1.MB
@regels             R2002
@sleutelwoorden     Maak BRP bericht

Narrative:
Een attribuut dat een bijhoudingsgegeven is mag alleen geleverd worden aan een Partij met de Rol "Bijhoudingsorgaan College" en "Bijhoudingsorgaan Minister" voor de Dienst waarvoor geleverd word.
Een attribuut is een bijhoudingsgegeven als de Element.Autorisatie van het Element"Bijhoudingsgegevens" is.

Scenario: 1     Bijhoudingsgegeven onderzoek omschrijving (autorisatie 7), partijrol Bijhoudingsorgaan College (2)
                Logisch testgeval: R2002_02
                Verwacht resultaat: Onderzoek omschrijving  in het resultaat

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given de personen 299054457, 743274313, 449809353 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 449809353 en anr 2476721810 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(449809353)
nieuweGebeurtenissenVoor(UC_Kenny) {
    onderzoek(partij: 17401, registratieDatum: 20100101) {
        gestartOp(aanvangsDatum:'20111231', omschrijving:'Onderzoek is gestart op huisnummer', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Adres.Huisnummer')
    }
}
slaOp(UC_Kenny)

Given de database is aangepast met: update kern.partijrol set rol = 3 where id = 178
Given de database is aangepast met: update kern.partijrol set rol = 2 where id = 3078
Given de cache is herladen

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 6.5_R1544_plaats_afnemerindicatie.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When voor persoon 449809353 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then hebben attributen in voorkomens de volgende waardes:
| groep 	            | nummer  	| attribuut             | verwachteWaarde                       |
| onderzoek             | 2         | omschrijving          | Onderzoek is gestart op huisnummer    |


Scenario: 2     Bijhoudingsgegeven onderzoek omschrijving (autorisatie 7), partijrol Bijhoudingsorgaan Minister (3)
                Logisch testgeval: R2002_02
                Verwacht resultaat: Onderzoek omschrijving  in het resultaat

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given de personen 299054457, 743274313, 449809353 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 449809353 en anr 2476721810 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(449809353)
nieuweGebeurtenissenVoor(UC_Kenny) {
    onderzoek(partij: 17401, registratieDatum: 20100101) {
        gestartOp(aanvangsDatum:'20111231', omschrijving:'Onderzoek is gestart op huisnummer', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Adres.Huisnummer')
    }
}
slaOp(UC_Kenny)

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 6.5_R1544_plaats_afnemerindicatie.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Given de database is aangepast met: update kern.partijrol set rol = 1 where id = 178
Given de database is aangepast met: update kern.partijrol set rol = 3 where id = 3078
Given de cache is herladen

When voor persoon 449809353 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then hebben attributen in voorkomens de volgende waardes:
| groep 	            | nummer  	| attribuut             | verwachteWaarde                       |
| onderzoek             | 2         | omschrijving          | Onderzoek is gestart op huisnummer    |

Scenario: 3     Bijhoudingsgegeven onderzoek omschrijving (autorisatie 7), partijrol Afnemer (1)
                Logisch testgeval: R2002_03
                Verwacht resultaat: Onderzoek omschrijving niet in het resultaat

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given de personen 299054457, 743274313, 449809353 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 449809353 en anr 2476721810 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(449809353)
nieuweGebeurtenissenVoor(UC_Kenny) {
    onderzoek(partij: 17401, registratieDatum: 20100101) {
        gestartOp(aanvangsDatum:'20111231', omschrijving:'Onderzoek is gestart op huisnummer', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Adres.Huisnummer')
    }
}
slaOp(UC_Kenny)

Given de database is aangepast met: update kern.partijrol set rol = 2 where id = 178
Given de database is aangepast met: update kern.partijrol set rol = 1 where id = 3078
Given de cache is herladen

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 6.5_R1544_plaats_afnemerindicatie.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When voor persoon 449809353 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep     | nummer | attribuut              | aanwezig |
| onderzoek | 2      | omschrijving           | nee      |