Meta:
@auteur             kedon
@status             Klaar
@usecase            LV.1.MB
@regels             R1555
@sleutelwoorden     Maak BRP bericht

Narrative:

In het koppelvlak Levering worden van Document alleen de voorkomens in een bericht opgenomen waarvoor geldt dat ActieVerval en
Datum/tijd verval niet gevuld zijn.

In het bericht wordt het attribuut TijdstipRegistratie van Document WEL opgenomen maar attribuut ActieInhoud van Document NIET opgenomen

Scenario: 1. Verhuizing met docuement
            Logisch testgeval R1555_01
            Verwacht resultaat:
            - document leveren in bericht met Tijdstipregistratie gevuld en actieInhoud NIET aanwezig


Given de personen 299054457, 743274313, 606417801 zijn verwijderd
And de standaardpersoon UC_Kenny met bsn 606417801 en anr 1383746930 zonder extra gebeurtenissen

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Utrecht.txt
Given bijhoudingsverzoek voor partij 'Gemeente Utrecht'
Given administratieve handeling van type verhuizingIntergemeentelijk , met de acties registratieAdres
And testdata uit bestand 12.2_R1555_verhuizing_binnen_Utrecht.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep     | nummer | attribuut             | aanwezig |
| document  | 1      | tijdstipRegistratie   | ja       |
| document  | 1      | actieInhoud           | nee      |

Scenario: R1555_LO2 Alleen actuele waarden van documenten leveren

Document Geboorteakte met id=31001, identificatie = 123 en aktenummer = 4567890 is vervallen (zie kern.his_doc tabel) en is vervangen door
document met identificatie = 321 en aktenummer = 9876554

Given de gehele database is gereset
Given leveringsautorisatie uit /levering_autorisaties/abo_geef_details_persoon
Given verzoek voor leveringsautorisatie 'Abo GeefDetailsPersoon' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And extra waardes:
| SLEUTEL                                 | WAARDE                 |
| zoekcriteriaPersoon.burgerservicenummer | 310027603              |

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When voor persoon 310027603 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Abo GeefDetailsPersoon is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende waardes:
| groep    | nummer | attribuut     | verwachteWaarde |
| document | 4      | soortNaam     | Geboorteakte    |
| document | 4      | identificatie | 321             |
| document | 4      | aktenummer    | 9876554         |

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep    | nummer | attribuut           | aanwezig |
| document | 4      | tijdstipRegistratie | ja       |
| document | 5      | soortNaam           | nee      |
| document | 5      | identificatie       | nee      |
| document | 5      | aktenummer          | nee      |