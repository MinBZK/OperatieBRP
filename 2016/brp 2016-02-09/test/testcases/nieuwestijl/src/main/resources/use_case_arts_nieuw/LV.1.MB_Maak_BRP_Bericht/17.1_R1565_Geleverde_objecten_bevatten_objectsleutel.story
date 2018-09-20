Meta:
@auteur             kedon
@status             Klaar
@usecase            LV.1.MB
@regels             R1565
@sleutelwoorden     Maak BRP bericht

Narrative:
Voor elk Object dat wordt opgenomen in een bericht geldt dat de bijbehorende ObjectSleutel moet worden meegeleverd.
Dit betreft altijd de ID van het A-laag voorkomen van het betreffende object.

Als het ObjectType 'Persoon' is, geldt bovendien dat de ObjectSleutel versleuteld volgens R1834 -
Objectsleutel in bericht is versleutelde ID van persoon in het bericht moet worden opgenomen.


Scenario: 1.1   Afnemerindicatie wordt geplaatst met meerder objecten. Controle op objecten voor objectsleutel
                Logsich testgeval: R1565_01
                Verwacht resultaat: Alle objecten krijgen objectsleutel mee

Given de personen 299054457, 743274313, 606417801 zijn verwijderd
And de standaardpersoon UC_Kenny met bsn 606417801 en anr 1383746930 zonder extra gebeurtenissen

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie

And testdata uit bestand 13.2_R1561_Plaats_Afnemerindicatie.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then is er voor xpath //brp:adres[@brp:objectSleutel] een node aanwezig in het levering bericht
Then is er voor xpath //brp:voornaam[@brp:objectSleutel] een node aanwezig in het levering bericht
Then is er voor xpath //brp:geslachtsnaamcomponent[@brp:objectSleutel] een node aanwezig in het levering bericht
Then is er voor xpath //brp:nationaliteit[@brp:objectSleutel] een node aanwezig in het levering bericht

Scenario: 2. Het in onderzoek zetten van het object Persoon
                Logsich testgeval: R1565_01
                Verwacht resultaat: Alle objecten krijgen objectsleutel mee, omdat de objectsleutel in dit geval verwijst naar de persoon, is de object sleutel versleuteld.

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de personen 627129705, 304953337, 921674569 zijn verwijderd
Given de standaardpersoon Olivia met bsn 921674569 en anr 5235604242 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(921674569)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401, registratieDatum: 20150101) {
        gestartOp(aanvangsDatum:'2015-01-01', omschrijving:'Onderzoek naar attribuut in identiteitsgroep', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon')
    }

}
slaOp(persoon)

When voor persoon 921674569 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende waardes:
| groep              | nummer | attribuut              | verwachteWaarde                              |
| synchronisatie     | 1      | naam                   | Aanvang onderzoek                            |
| synchronisatie     | 1      | partijCode             | 059401                                       |
| onderzoek          | 2      | datumAanvang           | 2015-01-01                                   |
| onderzoek          | 2      | verwachteAfhandeldatum | 2015-08-01                                   |
| onderzoek          | 2      | omschrijving           | Onderzoek naar attribuut in identiteitsgroep |
| gegevenInOnderzoek | 1      | elementNaam            | Persoon                                      |
| actie              | 1      | soortNaam              | Registratie onderzoek                        |

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep              | nummer | attribuut            | aanwezig |
| gegevenInOnderzoek | 1      | objectSleutelGegeven | ja       |

Then is er voor xpath //brp:persoon[@brp:objectSleutel=//brp:objectSleutelGegeven/text()] een node aanwezig in het levering bericht