Meta:
@auteur                 dihoe
@regels                 BRLV0032,R1340
@epic                   Levering indicatoren
@jiraIssue              TEAMBRP-2153
@sleutelwoorden         verstrekkingsbeperking
@status                 Klaar

Narrative:
Waarschuwing geleverde persoon heeft verstrekkingsbeperking:
Als wetgever, afnemer en bijhouder wil ik dat bij het leveren van de persoonsgegevens aangegeven wordt
dat er sprake is van een actuele verstrekkingsbeperking,
zodat de afnemer of bijhouder weet dat niet alle afnemende partijen over deze persoonsgegevens mogen beschikken.
Dit wordt vormgegeven, door het opnemen van een Melding in de vorm van een
Waarschuwing, waarin de Meldingstekst “De persoon heeft een verstrekkingsbeperking” in het bericht.

Scenario: 1.  persoon met een geldige volledige verstrekkingsbeperking verhuist
              afnemer/partij met indverstrbeperkingmogelijk = false krijgt een bericht
              afnemer/partij met indverstrbeperkingmogelijk = true krijgt geen bericht

Given de gehele database is gereset
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding, /levering_autorisaties/afnemer_502707_verstrekkingbeperking_mogelijk, /levering_autorisaties/attendering/Attenderingen_op_pers_met_gewijzigde_postcode_EN_woonplaatsnaam_(pop.bep.=true)
Given de standaardpersoon Gregory met bsn 125850979 en anr 3512419602 met extra gebeurtenissen:
verstrekkingsbeperking(aanvang: 20150101, registratieDatum: 20150101) {
    volledig ja
}
verhuizing(partij: 'Gemeente Utrecht', aanvang: 20150131, registratieDatum: 20150131) {
    naarGemeente 'Utrecht',
        straat: 'Lindelaan', nummer: 2, postcode: '3500AA', woonplaats: "Utrecht"
}
When voor persoon 125850979 wordt de laatste handeling geleverd
When het mutatiebericht voor partij 34401 en leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

And hebben attributen in voorkomens de volgende waardes:
| groep         | nummer | attribuut         | verwachteWaarde                                |
| stuurgegevens | 1      | ontvangendePartij | 034401                                         |
| melding       | 1      | regelCode         | BRLV0032                                       |
| melding       | 1      | soortNaam         | Waarschuwing                                   |
| melding       | 1      | melding           | Waarschuwing: verstrekkingsbeperking aanwezig. |

When het mutatiebericht voor leveringsautorisatie afnemer 502707 verstrekkingbeperking mogelijk is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

When het volledigbericht voor leveringsautorisatie Attenderingen op pers met gewijzigde postcode EN woonplaatsnaam (pop.bep.=true) is ontvangen en wordt bekeken
Then is het bericht xsd-valide

And hebben attributen in voorkomens de volgende waardes:
| groep          | nummer | attribuut         | verwachteWaarde                                |
| stuurgegevens  | 1      | ontvangendePartij | 034401                                         |
| melding        | 1      | regelCode         | BRLV0032                                       |
| melding        | 1      | soortNaam         | Waarschuwing                                   |
| melding        | 1      | melding           | Waarschuwing: verstrekkingsbeperking aanwezig. |
| synchronisatie | 1      | naam              | Verhuizing intergemeentelijk                   |

Scenario: 2. Verwijderen van volledige verstrekkingsbeperking

Given de persoon beschrijvingen:
def Gregory = uitDatabase bsn: 125850979

Persoon.nieuweGebeurtenissenVoor(Gregory) {
    verstrekkingsbeperking(aanvang: 20150728, registratieDatum: 20150728) {
        volledig nee
    }
    verhuizing(partij: 'Gemeente Haarlem', aanvang: 20150730, registratieDatum: 20150730) {
        naarGemeente 'Haarlem',
            straat: 'Strandweg', nummer: 10, postcode: '2000AA', woonplaats: "Haarlem"
    }
}
slaOp(Gregory)

When voor persoon 125850979 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

And hebben attributen in voorkomens de volgende waardes:
| groep          | nummer | attribuut         | verwachteWaarde              |
| synchronisatie | 1      | naam              | Verhuizing intergemeentelijk |

When voor persoon 125850979 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie afnemer 502707 verstrekkingbeperking mogelijk is ontvangen en wordt bekeken
Then is het bericht xsd-valide

And hebben attributen in voorkomens de volgende waardes:
| groep          | nummer | attribuut         | verwachteWaarde              |
| stuurgegevens  | 1      | ontvangendePartij | 502707                       |
| synchronisatie | 1      | naam              | Verhuizing intergemeentelijk |

Scenario: 3.  persoon met een geldige specifieke verstrekkingsbeperking verhuist
              afnemer/partij met indverstrbeperkingmogelijk = true krijgt geen bericht
              afnemer/partij met indverstrbeperkingmogelijk = false krijgt een bericht

Given de gehele database is gereset
Given de standaardpersoon Gregory met bsn 125850979 en anr 3512419602 met extra gebeurtenissen:
verstrekkingsbeperking(aanvang: 20150115, registratieDatum: 20150115) {
    registratieBeperkingen( partij: 502707 )
}
verhuizing(partij: 'Gemeente Utrecht', aanvang: 20150131, registratieDatum: 20150131) {
    naarGemeente 'Utrecht',
        straat: 'Lindelaan', nummer: 2, postcode: '3500AA', woonplaats: "Utrecht"
}
When voor persoon 125850979 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie afnemer 502707 verstrekkingbeperking mogelijk is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden
When het mutatiebericht voor partij 34401 en leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken

Then is het bericht xsd-valide

And hebben attributen in voorkomens de volgende waardes:
| groep         | nummer | attribuut         | verwachteWaarde                                |
| stuurgegevens | 1      | ontvangendePartij | 034401                                         |
| melding       | 1      | regelCode         | BRLV0032                                       |
| melding       | 1      | soortNaam         | Waarschuwing                                   |
| melding       | 1      | melding           | Waarschuwing: verstrekkingsbeperking aanwezig. |

Scenario: 4. Beeindigen van specifieke verstrekkingsbeperking
             afnemer/partij met indverstrbeperkingmogelijk = true krijgt wel bericht

Given de persoon beschrijvingen:
def Gregory = uitDatabase bsn: 125850979

Persoon.nieuweGebeurtenissenVoor(Gregory) {
    verstrekkingsbeperking(aanvang: 20150728, registratieDatum: 20150728) {
        vervalBeperkingen( partij: 502707 )
    }
    verhuizing(partij: 'Gemeente Haarlem', aanvang: 20150730, registratieDatum: 20150730) {
        naarGemeente 'Haarlem',
            straat: 'Strandweg', nummer: 10, postcode: '2000AA', woonplaats: "Haarlem"
    }
}
slaOp(Gregory)

When voor persoon 125850979 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie afnemer 502707 verstrekkingbeperking mogelijk is ontvangen en wordt bekeken
Then is het bericht xsd-valide

And hebben attributen in voorkomens de volgende waardes:
| groep          | nummer | attribuut         | verwachteWaarde              |
| stuurgegevens  | 1      | ontvangendePartij | 502707                       |
| synchronisatie | 1      | naam              | Verhuizing intergemeentelijk |


