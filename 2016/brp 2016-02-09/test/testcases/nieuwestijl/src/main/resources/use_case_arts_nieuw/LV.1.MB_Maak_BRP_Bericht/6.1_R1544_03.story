Meta:
@auteur             kedon
@status             Klaar
@usecase            LV.1.MB
@regels             R1544
@sleutelwoorden     Maak BRP bericht


Narrative:
Indien een VolledigBericht of een MutatieBericht wordt aangemaakt voor een aanwezige Persoon \ Afnemerindicatie, en in die Afnemerindicatie is Persoon \ Afnemerindicatie.Datum aanvang materiële periode gevuld, dan geldt dat bepaalde historische voorkomens van groepen uit de juridische persoonslijst niet geleverd worden:

Van groepen die een materiële historie kennen, mogen alleen voorkomens waarbij geldt dat DatumEindeGeldigheid (voor Onderzoeken is dit Onderzoek.Datum einde) > Persoon \ Afnemerindicatie.Datum aanvang materiële periode in het Bericht worden opgenomen.
Deze regel geldt voor zowel de inhoudelijke groepen als voor de onderzoeksgroepen.

Toelichting:
Als Persoon \ Afnemerindicatie.Datum aanvang materiële periode leeg is, dan mogen dus alle voorkomens geleverd worden.
Als DatumEindeGeldigheid leeg is, dan mag dat voorkomen altijd geleverd worden.
Als DatumEindeGeldigheid geheel of gedeeltelijk onbekend is, dan dient de (reguliere) 'soepele' vergelijking gebruikt te worden: als de bovenstaande conditie mogelijk WAAR kan zijn, dan wordt het voorkomen in het bericht opgenomen.

Woonplaats Haarlem is de geboortestad voor UC_Kenny
Huidige woonplaats Amsterdam word gebruikt als uitgangspunt in de mutatieberichten (dit in verband met de bevinding, dat er anders niet geleverd wordt)
Woonplaats Hillegom is Inhoudelijke groep 1
Woonplaats Amsterdam is Inhoudelijke groep 2

Bevinding: Het is niet datum einde geldigheid waar nu op gemeten wordt, maar op tijdstip registratie van de mutatie,
wanneer in de test een registratiedatum mee wordt gegeven uit het verleden, lijkt het systeem geen idee meer te hebben dat er een
mutatiebericht verstuurd moet worden.

Scenario: 10.1  Datum aanvang materiele periode 2012-05-13 < datum einde onderzoek voor groep 1 en
                Datum aanvang materiele periode 2012-05-13 < datum einde onderzoek voor groep 2
                Logisch testgeval: R1544_10
                Verwacht resultaat: Voorkomens onderzoeksgroep 1 en 2 geleverd

Given de gehele database is gereset
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
    onderzoek(partij: 17401) {
        afgeslotenOp(eindDatum: morgen())
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
    onderzoek(partij: 17401) {
        afgeslotenOp(eindDatum: morgen())
    }
}
slaOp(UC_Kenny)

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 6.7_R1544_plaats_afnemerindicatie.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When voor persoon 449809353 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then hebben attributen in voorkomens de volgende waardes:
| groep 	            | nummer  	| attribuut    | verwachteWaarde                    |
| onderzoek             | 2         | omschrijving | Onderzoek is gestart op huisnummer |
| gegevenInOnderzoek    | 1         | elementNaam  | Persoon.Adres.Huisnummer           |
| onderzoek             | 4         | omschrijving | Onderzoek is gestart op postcode   |
| gegevenInOnderzoek    | 2         | elementNaam  | Persoon.Adres.Postcode             |


Scenario: 11.1  Datum aanvang materiele periode  = datum einde onderzoek voor groep 1 en
                Datum aanvang materiele periode  < datum einde onderzoek voor groep 2
                Logisch testgeval: R1544_11
                Verwacht resultaat: Voorkomens onderzoeksgroep 1 niet geleverd
                Bevinding: Er wordt maar 1 onderzoek correct afgesloten, de tweede blijft open staan
                systeem kan maar 1 afsluiting onderzoek tegelijk aan

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
    onderzoek(partij: 17401) {
        afgeslotenOp(eindDatum: vandaag())
    }
}
slaOp(UC_Kenny)

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 6.7_R1544_plaats_afnemerindicatie.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When voor persoon 449809353 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep              | nummer | attribuut    | aanwezig |
| onderzoek          | 2      | omschrijving | nee      |
| onderzoek          | 3      | omschrijving | nee      |
| onderzoek          | 2      | statusNaam   | nee      |
| gegevenInOnderzoek | 1      | elementNaam  | nee      |

Scenario: 11.2  Datum aanvang materiele periode  = datum einde onderzoek voor groep 1 en
                Datum aanvang materiele periode  < datum einde onderzoek voor groep 2
                Logisch testgeval: R1544_11
                Verwacht resultaat: Alleen voorkomens van groep 2 geleverd
                Bevinding: Er wordt maar 1 onderzoek correct afgesloten, de tweede blijft open staan
                systeem kan maar 1 afsluiting onderzoek tegelijk aan

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given de personen 299054457, 743274313, 449809353 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 449809353 en anr 2476721810 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(449809353)
nieuweGebeurtenissenVoor(UC_Kenny) {
    onderzoek(partij: 17401, registratieDatum: 20100101) {
        gestartOp(aanvangsDatum:'20111231', omschrijving:'Onderzoek is gestart op postcode', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Adres.Postcode')
    }
    onderzoek(partij: 17401) {
        afgeslotenOp(eindDatum: morgen())
    }
}
slaOp(UC_Kenny)

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 6.7_R1544_plaats_afnemerindicatie.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When voor persoon 449809353 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then hebben attributen in voorkomens de volgende waardes:
| groep              | nummer | attribuut    | verwachteWaarde                       |
| onderzoek          | 2      | omschrijving | Onderzoek is gestart op postcode      |
| onderzoek          | 2      | statusNaam   | Afgesloten                            |



Scenario: 12.1  Datum aanvang materiele periode  > datum einde onderzoek voor groep 1 en
                Datum aanvang materiele periode  < datum einde onderzoek voor groep 2
                Logisch testgeval: R1544_12
                Verwacht resultaat: Alleen voorkomens van inhoudelijke groep 2 geleverd
Meta:
@status Onderhanden

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
    onderzoek(partij: 17401) {
        afgeslotenOp(eindDatum: gisteren())
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
    onderzoek(partij: 17401) {
        afgeslotenOp(eindDatum: morgen())
    }
}
slaOp(UC_Kenny)

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 6.7_R1544_plaats_afnemerindicatie.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When voor persoon 449809353 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then hebben attributen in voorkomens de volgende waardes:
| groep              | nummer | attribuut    | verwachteWaarde                       |
| onderzoek          | 2      | omschrijving | Onderzoek is gestart op postcode      |
| onderzoek          | 2      | statusNaam   | Afgesloten                            |


Scenario: 13.1  Datum aanvang materiele periode LEEG datum einde onderzoek en GEHEEL ONBEKEND voor inhoudelijke groep 1 en
                Datum aanvang materiele periode LEEG  datum einde onderzoek en LEEG voor inhoudelijke groep 2
                Logisch testgeval: R1544_13
                Verwacht resultaat: Voorkomens beide groepen geleverd

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
    onderzoek(partij: 17401) {
        afgeslotenOp(eindDatum: 00000000)
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
| groep              | nummer | attribuut    | verwachteWaarde                       |
| onderzoek          | 2      | omschrijving | Onderzoek is gestart op huisnummer    |
| onderzoek          | 5      | omschrijving | Onderzoek is gestart op postcode      |
| gegevenInOnderzoek | 1      | elementNaam  | Persoon.Adres.Huisnummer              |
| gegevenInOnderzoek | 2      | elementNaam  | Persoon.Adres.Postcode                |


Scenario: 14.1  Datum aanvang materiele periode 2012-05-15 > GEDEELTELIJK ONBEKENDE datum einde onderzoekvoor inhoudelijke groep 1 en
                Datum aanvang materiele periode 2012-05-15 <  GEDEELTELIJK ONBEKENDE datum einde onderzoekvoor inhoudelijke groep 2
                Logisch testgeval: R1544_14
                Verwacht resultaat: Alleen voorkomens van inhoudelijke groep 2 geleverd
                Bevinding: Voorkomens van onderzoeksgroep 1 worden geleverd.
Meta:
@status Onderhanden

Given de personen 299054457, 743274313, 449809353 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 449809353 en anr 2476721810 zonder extra gebeurtenissen

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 6.2_R1544_plaats_afnemerindicatie.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 14.2  Datum aanvang materiele periode 2012-05-15 > GEDEELTELIJK ONBEKENDE datum einde onderzoekvoor inhoudelijke groep 1 en
                Datum aanvang materiele periode 2012-05-15 <  GEDEELTELIJK ONBEKENDE datum einde onderzoekvoor inhoudelijke groep 2
                Logisch testgeval: R1544_14
                Verwacht resultaat: Alleen voorkomens van inhoudelijke groep 2 geleverd
                Bevinding: Voorkomens van onderzoeksgroep 1 worden geleverd.
Meta:
@status Onderhanden

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(449809353)
nieuweGebeurtenissenVoor(UC_Kenny) {
    onderzoek(partij: 17401, registratieDatum: 20100101) {
        gestartOp(aanvangsDatum:'20091231', omschrijving:'Onderzoek is gestart op huisnummer', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Adres.Huisnummer')
    }
    onderzoek(partij: 17401, registratieDatum: 20120401) {
        afgeslotenOp(eindDatum: 20120400)
    }
}
slaOp(UC_Kenny)

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(449809353)
nieuweGebeurtenissenVoor(UC_Kenny) {
    onderzoek(partij: 17401, registratieDatum: 20120401) {
        gestartOp(aanvangsDatum:'20111231', omschrijving:'Onderzoek is gestart op postcode', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Adres.Postcode')
    }
    onderzoek(partij: 17401, registratieDatum: 20120501) {
        afgeslotenOp(eindDatum: 20120500)
    }
}
slaOp(UC_Kenny)


When voor persoon 449809353 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden


Scenario: 14.3  Datum aanvang materiele periode 2012-05-15 > GEDEELTELIJK ONBEKENDE datum einde onderzoekvoor inhoudelijke groep 1 en
                Datum aanvang materiele periode 2012-05-15 <  GEDEELTELIJK ONBEKENDE datum einde onderzoekvoor inhoudelijke groep 2
                Logisch testgeval: R1544_14
                Verwacht resultaat: Alleen voorkomens van inhoudelijke groep 2 geleverd
                Bevinding: Voorkomens van onderzoeksgroep 1 worden geleverd.
Meta:
@status Onderhanden

Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 6.6_R1544_Synchroniseer_Persoon.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
And heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Geen'
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide