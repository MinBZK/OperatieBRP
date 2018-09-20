Meta:
@auteur             kedon
@status             Klaar
@usecase            LV.1.MB
@regels             R19737777
@sleutelwoorden     Maak BRP bericht



Narrative:
Bij het opstellen van een mutatiebericht worden slechts groepen geleverd die in de betreffende Administratieve handeling zijn gewijzigd, zijn gemarkeerd als 'in onderzoek' (dit zijn de gegevens in het persoonsdeel, die zijn blijven staan omdat zij onderwerp zijn vanOnderzoek) of groepen die identificerend zijn.
Tevens worden er slechts voorkomens van objecttypen geleverd die dergelijke groepen bevatten of die dergelijke groepen verbinden.
Indien de betreffende handeling niet de laatste is die bij de Persoon is doorgevoerd, dan wordt gereconstrueerd wat het beeld van de Persoon was direct na het doorvoeren van de betreffende Administratieve handeling. Dit wordt hieronder in detail beschreven:

ActieVerval is gelijk aan:
•	ActieVerval als ActieVervalTbvLeveringMutaties geen waarde heeft
•	ActieVervalTbvLeveringMutaties als deze wel een waarde heeft

1. Ga uit van de 'Gereconstrueerde persoon na Administratieve handeling' (R1556)
2. Behoud hiervan alleen de groepen waarvoor geldt dat (de groep gewijzigd is in de Administratieve handeling) OF gemarkeerd is als 'in onderzoek' (zie R1319 - Markeer gegevens in het persoonsdeel op basis van Onderzoek, zodat deze meegaan in een mutatiebericht) OF die Identificerend zijn voor die gewijzigde of gemarkeerde groepen.

Waarbij:
De groep is gewijzigd in de Administratieve handeling als voldaan wordt aan minstens één van de volgende voorwaarden:
•	(ActieInhoud hoort bij de Administratieve handelingen) EN (ActieAanpassingGeldigheid is leeg)
•	ActieAanpassingGeldigheid hoort bij de Administratieve handeling
•	ActieVerval hoort bij de Administratieve handeling
De groep Identificerend is voor een andere groep als:
•	Het een Identificerende groep betreft (zie 'Identificerende groep' (R1542) )
•	De Identificerende groep duidelijk maakt onder welke Object de gewijzigde groep zich bevindt, als er meerdere Objecten van hetzelfde type kunnen bestaan.
In de praktijk is dat:
•	De Identificerende groepen onder de hoofdpersoon zijn identificerend voor alle groepen (en worden dus altijd opgenomen)
•	De Identificerende groepen onder een gerelateerde persoon zijn identificerend voor de groepen van de eigen betrokkenheid, de relatie, de gerelateerde betrokkenheid en de gerelateerde persoon (dus als er iets geraakt is rond een relatie of een gerelateerde, dan worden de identificerende groepen van die gerelateerde(n) opgenomen)
3. Behoud alleen de voorkomens van objecttypes waarvoor geldt dat ofwel:
•	Deze groepen bevat die gewijzigd zijn in de Administratieve handeling.
•	Deze groepen bevat die identificerend zijn bij een groep die gewijzigd is in deAdministratieve handeling
•	Deze groepen bevat die zijn gemarkeerd als 'in Onderzoek'.
•	Deze zich in het pad bevindt tussen een objecttype dat gewijzigde groepen bevat en een objecttype dat groepen bevat die deze identificeert.
•	Deze zich in het pad bevindt tussen het rootobject in het bericht (de hoofdpersoon) en een objecttype dat gewijzigde groepen bevat.




Scenario: 1.1 R1973_01 Identificeren Object onder Hoofdpersoon

Given de personen 796120201 zijn verwijderd
Given de standaardpersoon UC_Bebe met bsn 796120201 en anr 1391050482 zonder extra gebeurtenissen

Given de personen 803697417, 968282441, 412670409 zijn verwijderd
Given de standaardpersoon UC_Timmy met bsn 412670409 en anr 7157893906 zonder extra gebeurtenissen

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 42.2_R1973_01.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide


Scenario: 1.2   Identificeren Object onder Hoofdpersoon
                Logisch testgeval R1973_01
                Verwacht resultaat: Mutatiebericht met vulling
                - gegevens hoofdpersoon

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Olst.txt
Given bijhoudingsverzoek voor partij 'Gemeente Olst'
Given administratieve handeling van type verhuizingIntergemeentelijk , met de acties registratieAdres
And testdata uit bestand 42.4_R1973_verhuizing.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then hebben attributen in voorkomens de volgende waardes:
| groep                 | nummer | attribuut           | verwachteWaarde |
| samengesteldeNaam     | 1      | voornamen           | UC_Timmy        |
| samengesteldeNaam     | 1      | geslachtsnaamstam   | Burch           |
| identificatienummers  | 1      | burgerservicenummer | 412670409       |
| geboorte              | 1      | woonplaatsnaam      | Delft           |
| adres                 | 1      | woonplaatsnaam      | Haarlem         |

Scenario: 2.1 Identificeren Object onder Hoofdpersoon
              Logisch testgeval R1973_02
              Verwacht resultaat: Mutatiebericht met vulling
              - gegevens hoofdpersoon
              - voorkomens gerelateerde persoon

Given de persoon beschrijvingen:
def UC_Bebe = uitDatabase bsn: 796120201
def UC_Timmy = uitDatabase bsn: 412670409

Persoon.nieuweGebeurtenissenVoor(UC_Timmy) {
huwelijk() {
          op 20150501 te 'Delft' gemeente 'Delft'
          met UC_Bebe
    }

}
slaOp(UC_Timmy)

When voor persoon 412670409 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then hebben attributen in voorkomens de volgende waardes:
| groep                 | nummer | attribuut           | verwachteWaarde |
| samengesteldeNaam     | 1      | voornamen           | UC_Timmy        |
| samengesteldeNaam     | 1      | geslachtsnaamstam   | Burch           |
| identificatienummers  | 1      | burgerservicenummer | 412670409       |
| geboorte              | 1      | woonplaatsnaam      | Delft           |
| samengesteldeNaam     | 2      | voornamen           | UC_Bebe         |
| samengesteldeNaam     | 2      | geslachtsnaamstam   | Stevens         |
| identificatienummers  | 2      | burgerservicenummer | 796120201       |
| geboorte              | 2      | woonplaatsnaam      | Delft           |


Scenario: 2.2   Tussen pad objecttype gewijzigde groepen en pad objecttype identificeren groepen
                Logisch testgeval: R1973_02
                Verwacht resultaat: Mutatiebericht met vulling
                - gegevens hoofdpersoon
                - Tussen pad objecttype gewijzigde groepen en pad objecttype identificeren groepen

Given de persoon beschrijvingen:
def UC_Bebe = uitDatabase bsn: 796120201
def UC_Timmy = uitDatabase bsn: 412670409
Persoon.nieuweGebeurtenissenVoor(UC_Bebe) {
    scheiding() {
        van UC_Timmy
        op(20150707).te('Delft').gemeente('Delft')
    }
}
slaOp(UC_Bebe)

When voor persoon 412670409 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then hebben attributen in voorkomens de volgende waardes:
| groep                 | nummer | attribuut           | verwachteWaarde |
| samengesteldeNaam     | 1      | voornamen           | UC_Timmy        |
| samengesteldeNaam     | 1      | geslachtsnaamstam   | Burch           |
| identificatienummers  | 1      | burgerservicenummer | 412670409       |
| geboorte              | 1      | woonplaatsnaam      | Delft           |
| samengesteldeNaam     | 2      | voornamen           | UC_Bebe         |
| samengesteldeNaam     | 2      | geslachtsnaamstam   | Stevens         |
| identificatienummers  | 2      | burgerservicenummer | 796120201       |
| geboorte              | 2      | woonplaatsnaam      | Delft           |
| relatie               | 1      | redenEindeCode      | S               |


Scenario: 2.3   Pad tussen rootobject en gewijzigde groepen
                Logisch testgeval: R1973_02
                Verwacht resultaat: Mutatiebericht met vulling
                - gegevens hoofdpersoon
                - Pad tussen rootobject en gewijzigde groepen (Naamswijziging vader/familiebetrekking)

Given de persoon beschrijvingen:
def vader = uitDatabase bsn: 803697417

Persoon.nieuweGebeurtenissenVoor(vader) {

    naamswijziging(aanvang:20150801) {
            geslachtsnaam(stam: 'Burch').wordt(stam: 'Cock')
    }
}
slaOp(vader)

When voor persoon 412670409 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then hebben attributen in voorkomens de volgende waardes:
| groep                 | nummer | attribuut           | verwachteWaarde |
| samengesteldeNaam     | 1      | voornamen           | UC_Timmy        |
| samengesteldeNaam     | 1      | geslachtsnaamstam   | Burch           |
| identificatienummers  | 1      | burgerservicenummer | 412670409       |
| geboorte              | 1      | woonplaatsnaam      | Delft           |
| samengesteldeNaam     | 2      | voornamen           | Vader-UC_Timmy  |
| samengesteldeNaam     | 2      | geslachtsnaamstam   | Cock            |
| samengesteldeNaam     | 3      | geslachtsnaamstam   | Burch           |

Scenario: 3.    Gegevens in onderzoek leveren in bericht   (Zelfde test als R1544 scenario 8)
                Logisch testgeval: R1973_03
                Verwacht resultaat: Mutatiebericht met vulling
                - gegevens hoofdpersoon
                - gegevens in onderzoek

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

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(449809353)
nieuweGebeurtenissenVoor(UC_Kenny) {
    onderzoek(partij: 17401, registratieDatum: 20100101) {
        gestartOp(aanvangsDatum:'20111231', omschrijving:'Onderzoek is gestart op postcode', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Adres.Postcode')
    }
}
slaOp(UC_Kenny)

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
| gegevenInOnderzoek    | 1         | elementNaam           | Persoon.Adres.Huisnummer              |
| onderzoek             | 4         | omschrijving          | Onderzoek is gestart op postcode      |
| gegevenInOnderzoek    | 2         | elementNaam           | Persoon.Adres.Postcode                |
| samengesteldeNaam     | 1         | voornamen             | UC_Kenny                              |
| samengesteldeNaam     | 1         | geslachtsnaamstam     | McCormick                             |
| identificatienummers  | 1         | burgerservicenummer   | 449809353                             |
| geboorte              | 1         | woonplaatsnaam        | Delft                                 |

Scenario: 4. Leveren van ActieInhoud, Aanpassingsgeldigheid en Actieverval wordt al getest in R1318,R1320,R2063,R2051
            Logisch testgeval: R1973_04 vervalt
            Verwacht resultaat: Geen test