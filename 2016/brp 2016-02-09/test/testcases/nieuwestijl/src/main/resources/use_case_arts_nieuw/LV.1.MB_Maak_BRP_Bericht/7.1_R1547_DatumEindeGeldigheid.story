Meta:
@auteur             luwid,kedon
@status             Klaar
@usecase            LV.1.MB
@regels             R1547
@sleutelwoorden     Maak BRP bericht

Narrative:
Het attribuut DatumEindeGeldigheid van een inhoudelijke groep ('Inhoudelijke groep' (R1540)) mag alleen worden opgenomen in het te leveren resultaat
als er bij de Dienst waarvoor geleverd wordt een corresponderend voorkomen bestaat van Dienstbundel \ Groep met Dienstbundel \ Groep.Materiële historie? = 'Ja'

Opmerking: Het betreft hier het wissen van materiële historie gegevens. Deze regel heeft dus geen betrekking op formele historie gegevens met een materieel aspect
(zoals Onderzoek met de datum Einde Onderzoek en Huwelijk met de datum einde Huwelijk).

NB: Deze regel leidt momenteel niet tot zichtbaar gedrag: als DatumEindeGeldigheid gevuld is wordt onder dezelfde conditie de hele groep niet opgenomen in het bericht
en als hij leeg is dan wordt het attribuut op die grond niet in het bericht opgenomen. Functioneel bestaat deze regel echter nog steeds en om redenen van toekomstbestendigheid wordt deze gehandhaafd.

Scenario: 1 beëindigde Inhoudelijke groepen (adres en Persoon.bijhouding) DatumEindeGeldigheid gevuld en een corresponderend voorkomen van dienstbundel
groep, met materiele historie = ja. Persoon UC_Kenny verhuist waardoor groepen Adres en Persoon.Bijhouding elk ÉÉN voorkomen hebben met
DatumEindeGeldigheid gevuld.
Logisch testgeval:  R1547_01
Verwacht Resultaat: beëindigde Inhoudelijke groep wordt opgenomen in het te leveren bericht

Given leveringsautorisatie uit /levering_autorisaties/Populatiebeperking_levering_op_basis_van_doelbinding_Haarlem, /levering_autorisaties/Populatiebeperking_levering_op_basis_van_doelbinding_Haarlem_Geen_Materiele_historie
Given de personen 299054457, 743274313, 869906057 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 869906057 en anr 8272560914 met extra gebeurtenissen:
verhuizing(partij: 'Gemeente Haarlem', aanvang: 20001010, registratieDatum: 20001010) {
    naarGemeente 'Haarlem',
        straat: 'Tulpstraat', nummer: 35, postcode: '2000AA', woonplaats: "Haarlem"
}

When voor persoon 869906057 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Populatiebeperking levering op basis van doelbinding Haarlem is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then heeft het bericht 3 groep 'adres'
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep   | nummer | attribuut              | aanwezig |
| adres   | 2      | datumEindeGeldigheid   | ja       |

Then heeft het bericht 3 groep 'bijhouding'
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep   | nummer | attribuut              | aanwezig |
| bijhouding   | 2      | datumEindeGeldigheid   | ja       |

Scenario: 2 beëindigde Inhoudelijke groepen (adres en Persoon.bijhouding) met DatumEindeGeldigheid gevuld en een corresponderend voorkomen van
dienstbundel groep, met materiele historie = nee. Persoon UC_Kenny verhuist waardoor groepen Adres en Persoon.Bijhouding elk ÉÉN voorkomen hebben met
DatumEindeGeldigheid gevuld.
Logisch testgeval:  R1547_02
Verwacht Resultaat: beëindigde Inhoudelijke groepen worden NIET opgenomen in het te leveren bericht

Given verzoek voor leveringsautorisatie 'Popbep levering obv doelbinding Haarlem geen materiele historie' en partij 'Gemeente Haarlem'

Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 7.3_R1547_Synchroniseer_Persoon.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Popbep levering obv doelbinding Haarlem geen materiele historie is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then heeft het bericht 2 groep 'adres'
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep   | nummer | attribuut              | aanwezig |
| adres   | 1      | datumEindeGeldigheid   | nee      |
| adres   | 2      | datumEindeGeldigheid   | nee      |

Then heeft het bericht 2 groep 'bijhouding'
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep      | nummer | attribuut            | aanwezig |
| bijhouding | 1      | datumEindeGeldigheid | nee      |
| bijhouding | 2      | datumEindeGeldigheid | nee      |

Scenario: 3 beëindigde Inhoudelijke groepen (adres en Persoon.bijhouding) met DatumEindeGeldigheid gevuld en een corresponderend
voorkomen van dienstbundel groep, met materiele historie = ja. Persoon UC_Kenny verhuist voor de 2de keer waardoor groepen Adres en Persoon.Bijhouding elk
TWEE voorkomen hebben met DatumEindeGeldigheid gevuld.
Logisch testgeval:  R1547_03
Verwacht Resultaat: beëindigde Inhoudelijke groepen worden opgenomen in het te leveren bericht

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(869906057)
nieuweGebeurtenissenVoor(UC_Kenny) {
    verhuizing(partij: 'Gemeente Haarlem', aanvang: 20101010, registratieDatum: 20101010) {
        naarGemeente 'Haarlem',
            straat: 'Tulpstraat', nummer: 37, postcode: '2000AA', woonplaats: "Haarlem"
    }
}
slaOp(UC_Kenny)

When voor persoon 869906057 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Populatiebeperking levering op basis van doelbinding Haarlem is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then heeft het bericht 3 groep 'adres'
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep   | nummer | attribuut              | aanwezig |
| adres   | 2      | datumEindeGeldigheid   | ja       |
Then heeft het bericht 3 groep 'bijhouding'
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep      | nummer | attribuut            | aanwezig |
| bijhouding | 2      | datumEindeGeldigheid | ja       |

Scenario: 4 beëindigde Inhoudelijke groepen (adres en Persoon.bijhouding) met DatumEindeGeldigheid gevuld en een corresponderend voorkomen van
dienstbundel groep, met materiele historie = nee. Persoon UC_Kenny is voor de 2de keer verhuisd waardoor groepen Adres en Persoon.Bijhouding elk TWEE
voorkomen hebben met DatumEindeGeldigheid gevuld.
Logisch testgeval:  R1547_04
Verwacht Resultaat: beëindigde Inhoudelijke groepen worden NIET opgenomen in het te leveren bericht

Given verzoek voor leveringsautorisatie 'Popbep levering obv doelbinding Haarlem geen materiele historie' en partij 'Gemeente Haarlem'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 7.3_R1547_Synchroniseer_Persoon.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Popbep levering obv doelbinding Haarlem geen materiele historie is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then heeft het bericht 3 groep 'adres'
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep   | nummer | attribuut              | aanwezig |
| adres   | 1      | datumEindeGeldigheid   | nee      |
| adres   | 2      | datumEindeGeldigheid   | nee      |
| adres   | 3      | datumEindeGeldigheid   | nee      |

Then heeft het bericht 3 groep 'bijhouding'
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep      | nummer | attribuut            | aanwezig |
| bijhouding | 1      | datumEindeGeldigheid | nee      |
| bijhouding | 2      | datumEindeGeldigheid | nee      |
| bijhouding | 3      | datumEindeGeldigheid | nee      |
