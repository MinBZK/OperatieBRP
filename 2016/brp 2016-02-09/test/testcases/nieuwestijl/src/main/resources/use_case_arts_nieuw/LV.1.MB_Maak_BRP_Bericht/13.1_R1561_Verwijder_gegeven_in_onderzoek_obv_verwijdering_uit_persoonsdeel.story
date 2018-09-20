Meta:
@auteur             kedon
@status             Klaar
@usecase            LV.1.MB
@regels             R1561
@sleutelwoorden     Maak BRP bericht

Narrative:
Een Gegeven in onderzoek met een verwijzing naar het persoonsdeel in het bericht, mag uitsluitend in het bericht blijven staan,
als deze met een Gegeven in onderzoek.Object sleutel gegeven en/of Gegeven in onderzoek.Voorkomen sleutel gegeven verwijst naar
een nog in het bericht bestaand gegeven in het persoonsdeel.

Als uit het persoonsdeel voorkomens zijn verwijderd op basis van een aanwezige Persoon \ Afnemerindicatie.Datum aanvang materiële periode
of op basis van formele-of materiële historie-autorisatie, dan moeten vervolgens binnen het 'Onderzoeksdeel' de onderzoeksregels die een verwijzing
hebben naar de verwijderde voorkomens gewist worden.

Binnen het te vervaardigen bericht gaat dat als volgt:
Verwijder de Gegeven in onderzoek bij de Onderzoeken in het onderzoeksdeel van het bericht, als deze een directe verwijzing bezit naar een in het
persoonsdeel verwijderd Voorkomen (de objectsleutel of de voorkomensleutel in de onderzoeksregel verwijst naar een niet (meer) aanwezig gegeven
in het persoonsdeel)

NB: De onderzoeksregels die geen directe verwijzingen bezitten (geen object-en/of voorkomensleutel bezitten) zullen dus blijven staan.
Dit zijn onderzoeken die verwijzen naar een ontbrekend gegeven (uitsluitend de elementnaam gevuld in de onderzoeksregel)

Opmerking: Bovenstaande NB is niet van toepassing op de huidige filtering.
De filtering is nu gebaseerd op de geautoriseerde gegevens in het persoonsdeel van het bericht.
Er zullen dus GEEN onderzoeksregels op ontbrekende gegevens blijven staan (deze worden momenteel ook weggefilterd).
Vanwege het feit dat we geen Onderzoeken op ontbrekende gegevens mogen gaan leveren
(zie ook R2065 - Onderzoek naar een ontbrekend gegeven niet leveren aan afnemers) levert dit geen problemen op.
Mocht besloten worden om deze onderzoeken toch (bijvoorbeeld aan bijhouders) te gaan leveren, dan zal de werking van deze regel daarop aangepast moeten worden.

Noot: het verwijderen van Gegeven in onderzoek moet plaatsvinden ná het verwijderen van de voorkomens.
Dit kan dus achteraf via deze regel, maar kan ook direct na het verwijderen van de voorkomens uit het persoonsdeel.
Het is aan de bouw, waar deze functionele eis het beste kan worden ingevuld.

Scenario: 1.1   Gegeven in onderzoek heeft een verwijzing naar het persoonsdeel
                Object sleutel gegeven verwijst naar een nog in het bericht staand gegeven in het persoonsdeel
                Voorkomen sleutel gegeven verwijst naar een nog in het bericht staand gegeven in het persoonsdeel
                Datum aanvang materiele periode van de afnemerindicatie = LEEG
                Leveringsautorisatie heeft autorisatie op formele historie
                Leveringsautorisatie heeft autorisatie op materiele historie
                Logisch testgeval: R1561_01
                Verwacht resultaat: Mutatiebericht geleverd met onderzoek op Onderzoek is gestart op datum aanvang huwelijk

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given de personen 299054457, 457627529, 743274313, 606417801 zijn verwijderd
And de standaardpersoon UC_Kenny met bsn 606417801 en anr 1383746930 zonder extra gebeurtenissen
And de standaardpersoon UC_Lieze met bsn 457627529 en anr 5072093729 zonder extra gebeurtenissen


Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 13.2_R1561_Plaats_Afnemerindicatie.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 1.2   UC_Kenny trouwt met UC_Lieze

Given de persoon beschrijvingen:
def UC_Lieze = uitDatabase bsn: 457627529
def UC_Kenny = uitDatabase bsn: 606417801

Persoon.nieuweGebeurtenissenVoor(UC_Kenny) {
huwelijk(aanvang: 20150501) {
          op 20150501 te 'Delft' gemeente 'Delft'
          met UC_Lieze
    }

}
slaOp(UC_Kenny)

When voor persoon 606417801 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 1.3   Gegeven in onderzoek heeft een verwijzing naar het persoonsdeel
                Object sleutel gegeven verwijst naar een nog in het bericht staand gegeven in het persoonsdeel
                Voorkomen sleutel gegeven verwijst naar een nog in het bericht staand gegeven in het persoonsdeel
                Datum aanvang materiele periode van de afnemerindicatie = LEEG
                Leveringsautorisatie heeft autorisatie op formele historie
                Leveringsautorisatie heeft autorisatie op materiele historie
                Logisch testgeval: R1561_01
                Verwacht resultaat: Mutatiebericht geleverd met onderzoek op Onderzoek is gestart op datum aanvang huwelijk


Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(606417801)
nieuweGebeurtenissenVoor(UC_Kenny) {
    onderzoek(partij: 17401) {
        gestartOp(aanvangsDatum:'2015-06-01', omschrijving:'Onderzoek is gestart op datum aanvang huwelijk', verwachteAfhandelDatum:'2015-09-01')
          gegevensInOnderzoek('GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.Voornamen')
    }
}
slaOp(UC_Kenny)

When voor persoon 606417801 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then hebben attributen in voorkomens de volgende waardes:
| groep              | nummer | attribuut    | verwachteWaarde                                      |
| onderzoek          | 2      | omschrijving | Onderzoek is gestart op datum aanvang huwelijk       |

Scenario: 2.1   Gegeven in onderzoek heeft een verwijzing naar het persoonsdeel
                Object sleutel gegeven verwijst NIET naar een nog in het bericht staand gegeven in het persoonsdeel
                Voorkomen sleutel gegeven verwijst naar een nog in het bericht staand gegeven in het persoonsdeel
                Datum aanvang materiele periode van de afnemerindicatie = LEEG
                Leveringsautorisatie heeft autorisatie op formele historie
                Leveringsautorisatie heeft autorisatie op materiele historie

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

Scenario: 2.2   Gegeven in onderzoek heeft een verwijzing naar het persoonsdeel
                Object sleutel gegeven verwijst NIET naar een nog in het bericht staand gegeven in het persoonsdeel
                Voorkomen sleutel gegeven verwijst naar een nog in het bericht staand gegeven in het persoonsdeel
                Datum aanvang materiele periode van de afnemerindicatie = LEEG
                Leveringsautorisatie heeft autorisatie op formele historie
                Leveringsautorisatie heeft autorisatie op materiele historie
                Logisch testgeval: R1561_02
                Verwacht resultaat: Mutatiebericht geleverd met onderzoek op Onderzoek is gestart op postcode

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(606417801)
nieuweGebeurtenissenVoor(UC_Kenny) {
    onderzoek(partij: 17401) {
        gestartOp(aanvangsDatum:'20160101', omschrijving:'Onderzoek is gestart op postcode', verwachteAfhandelDatum:'2016-08-01')
        gegevensInOnderzoek('Persoon.Adres.Postcode')
    }
}
slaOp(UC_Kenny)

When voor persoon 606417801 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then hebben attributen in voorkomens de volgende waardes:
| groep              | nummer | attribuut    | verwachteWaarde                       |
| onderzoek          | 2      | omschrijving | Onderzoek is gestart op postcode      |



Scenario: 3.1   Gegeven in onderzoek heeft een verwijzing naar het persoonsdeel
                Object sleutel gegeven verwijst naar een nog in het bericht staand gegeven in het persoonsdeel
                Voorkomen sleutel gegeven verwijst NIET naar een nog in het bericht staand gegeven in het persoonsdeel
                Datum aanvang materiele periode van de afnemerindicatie = LEEG
                Leveringsautorisatie heeft autorisatie op formele historie
                Leveringsautorisatie heeft autorisatie op materiele historie

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

Scenario: 3.2   UC_Kenny trouwt met UC_Lieze
Given de persoon beschrijvingen:
def UC_Lieze = uitDatabase bsn: 457627529
def UC_Kenny = uitDatabase bsn: 606417801

Persoon.nieuweGebeurtenissenVoor(UC_Kenny) {
huwelijk(aanvang: 20150501) {
          op 20150501 te 'Delft' gemeente 'Delft'
          met UC_Lieze
    }

}
slaOp(UC_Kenny)

When voor persoon 606417801 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 3.3   Gegeven in onderzoek heeft een verwijzing naar het persoonsdeel
                Object sleutel gegeven verwijst naar een nog in het bericht staand gegeven in het persoonsdeel
                Voorkomen sleutel gegeven verwijst NIET naar een nog in het bericht staand gegeven in het persoonsdeel
                Datum aanvang materiele periode van de afnemerindicatie = LEEG
                Leveringsautorisatie heeft autorisatie op formele historie
                Leveringsautorisatie heeft autorisatie op materiele historie
                Logisch testgeval: R1561_03
                Verwacht resultaat: Mutatiebericht geleverd met onderzoek op Onderzoek is gestart op datum aanvang huwelijk

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(606417801)
nieuweGebeurtenissenVoor(UC_Kenny) {
    onderzoek(partij: 17401) {
        gestartOp(aanvangsDatum:'2015-06-01', omschrijving:'Onderzoek is gestart op datum aanvang huwelijk', verwachteAfhandelDatum:'2015-09-01')
          gegevensInOnderzoek('GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.Voornamen')
    }
}
slaOp(UC_Kenny)

Given de database is aangepast met: update kern.gegeveninonderzoek set voorkomensleutelgegeven = 999999 where onderzoek = (select onderzoek from kern.his_onderzoek where dataanv = 20160101 and verwachteafhandeldat = 20160801 and oms = 'Onderzoek is gestart op postcode')
When voor persoon 606417801 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then hebben attributen in voorkomens de volgende waardes:
| groep              | nummer | attribuut    | verwachteWaarde                                      |
| onderzoek          | 2      | omschrijving | Onderzoek is gestart op datum aanvang huwelijk       |

Scenario: 4.1   Gegeven in onderzoek heeft een verwijzing naar het persoonsdeel
                Object sleutel gegeven verwijst NIET naar een nog in het bericht staand gegeven in het persoonsdeel
                Voorkomen sleutel gegeven verwijst NIET naar een nog in het bericht staand gegeven in het persoonsdeel
                Datum aanvang materiele periode van de afnemerindicatie = LEEG
                Leveringsautorisatie heeft autorisatie op formele historie
                Leveringsautorisatie heeft autorisatie op materiele historie
                Logisch testgeval: R1561_04
                Verwacht resultaat: onderzoek niet in bericht ( en dus ook geen bericht)

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

Scenario: 4.2   Gegeven in onderzoek heeft een verwijzing naar het persoonsdeel
                Object sleutel gegeven verwijst NIET naar een nog in het bericht staand gegeven in het persoonsdeel
                Voorkomen sleutel gegeven verwijst NIET naar een nog in het bericht staand gegeven in het persoonsdeel
                Datum aanvang materiele periode van de afnemerindicatie = LEEG
                Leveringsautorisatie heeft autorisatie op formele historie
                Leveringsautorisatie heeft autorisatie op materiele historie
                Logisch testgeval: R1561_04
                Verwacht resultaat: onderzoek niet in bericht ( en dus ook geen bericht)

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(606417801)
nieuweGebeurtenissenVoor(UC_Kenny) {
    onderzoek(partij: 17401) {
        gestartOp(aanvangsDatum:'20160101', omschrijving:'Onderzoek is gestart op postcode', verwachteAfhandelDatum:'2016-08-01')
        gegevensInOnderzoek('Persoon.Adres.Postcode')
    }
}
slaOp(UC_Kenny)

Given de database is aangepast met: update kern.gegeveninonderzoek set voorkomensleutelgegeven = 999999 where onderzoek = (select onderzoek from kern.his_onderzoek where dataanv = 20160101 and verwachteafhandeldat = 20160801 and oms = 'Onderzoek is gestart op postcode')
When voor persoon 606417801 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario: 5.1   Gegeven in onderzoek heeft een verwijzing naar het persoonsdeel
                Object sleutel gegeven verwijst naar een nog in het bericht staand gegeven in het persoonsdeel
                Voorkomen sleutel gegeven verwijst naar een nog in het bericht staand gegeven in het persoonsdeel
                Datum aanvang materiele periode van de afnemerindicatie > datum einde verzoek
                Leveringsautorisatie heeft autorisatie op formele historie
                Leveringsautorisatie heeft autorisatie op materiele historie
                Logisch testgeval: R1561_05
                Verwacht resultaat: onderzoek niet in bericht ( en dus ook geen bericht)
Meta:
@status Onderhanden
Given de personen 299054457, 743274313, 606417801 zijn verwijderd
And de standaardpersoon UC_Kenny met bsn 606417801 en anr 1383746930 zonder extra gebeurtenissen

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie

And testdata uit bestand 13.3_R1561_Plaats_Afnemerindicatie.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 5.2   Gegeven in onderzoek heeft een verwijzing naar het persoonsdeel
                Object sleutel gegeven verwijst naar een nog in het bericht staand gegeven in het persoonsdeel
                Voorkomen sleutel gegeven verwijst naar een nog in het bericht staand gegeven in het persoonsdeel
                Datum aanvang materiele periode van de afnemerindicatie > datum einde verzoek
                Leveringsautorisatie heeft autorisatie op formele historie
                Leveringsautorisatie heeft autorisatie op materiele historie
                Logisch testgeval: R1561_05
                Verwacht resultaat: onderzoek niet in bericht ( en dus ook geen bericht)

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(606417801)
nieuweGebeurtenissenVoor(UC_Kenny) {
    onderzoek(partij: 17401) {
        gestartOp(aanvangsDatum:'20110101', omschrijving:'Onderzoek is gestart op postcode', verwachteAfhandelDatum:'2011-08-01')
        gegevensInOnderzoek('Persoon.Adres.Postcode')
    }
}
slaOp(UC_Kenny)

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(606417801)
nieuweGebeurtenissenVoor(UC_Kenny) {
    onderzoek(partij: 'Gemeente Haarlem', registratieDatum: 20120404) {
            afgeslotenOp(eindDatum: 20120404)
        }
}
slaOp(UC_Kenny)

When voor persoon 606417801 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario: 6.1   Gegeven in onderzoek heeft een verwijzing naar het persoonsdeel
                Object sleutel gegeven verwijst naar een nog in het bericht staand gegeven in het persoonsdeel
                Voorkomen sleutel gegeven verwijst naar een nog in het bericht staand gegeven in het persoonsdeel
                Datum aanvang materiele periode van de afnemerindicatie = LEEG
                Leveringsautorisatie heeft GEEN autorisatie op formele historie
                Leveringsautorisatie heeft autorisatie op materiele historie
                Logisch testgeval: R1561_06
                Verwacht resultaat: onderzoek nadereAanduidingVerval niet in onderzoek door geen autorisatie op formele historie

Given leveringsautorisatie uit /levering_autorisaties/Populatiebeperking_levering_op_basis_van_doelbinding_Haarlem, /levering_autorisaties/Populatiebeperking_levering_op_basis_van_doelbinding_Haarlem_Geen_Formele_historie
Given de personen 299054457, 743274313, 228708977 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 228708977 en anr 2010486354 zonder extra gebeurtenissen

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(228708977)
nieuweGebeurtenissenVoor(UC_Kenny) {
    onderzoek(partij: 'Gemeente Haarlem', registratieDatum: 20150102) {
        gestartOp(aanvangsDatum:'2015-01-01', omschrijving:'Onderzoek is gestart op postcode', verwachteAfhandelDatum:'2015-04-01')
        gegevensInOnderzoek('Persoon.Adres.Postcode')
    }
}
slaOp(UC_Kenny)

When voor persoon 228708977 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Populatiebeperking levering op basis van doelbinding Haarlem is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 6.2   Gegeven in onderzoek heeft een verwijzing naar het persoonsdeel
                Object sleutel gegeven verwijst naar een nog in het bericht staand gegeven in het persoonsdeel
                Voorkomen sleutel gegeven verwijst naar een nog in het bericht staand gegeven in het persoonsdeel
                Datum aanvang materiele periode van de afnemerindicatie = LEEG
                Leveringsautorisatie heeft GEEN autorisatie op formele historie
                Leveringsautorisatie heeft autorisatie op materiele historie
                Logisch testgeval: R1561_06
                Verwacht resultaat: onderzoek nadereAanduidingVerval niet in onderzoek door geen autorisatie op formele historie

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(228708977)
nieuweGebeurtenissenVoor(UC_Kenny) {
    onderzoek(partij: 'Gemeente Haarlem', registratieDatum: 20151111) {
            wijzigOnderzoek(wijzigingsDatum:'2015-11-11', omschrijving:'Wijziging onderzoek verwachte afhandel datum', aanvangsDatum: '2015-11-11', verwachteAfhandelDatum: '2015-12-10')
        }
    }
    slaOp(UC_Kenny)

When voor persoon 228708977 wordt de laatste handeling geleverd

Given de database is aangepast met: update kern.his_onderzoek set nadereaandverval = 'O' where verwachteafhandeldat = 20151010

When het mutatiebericht voor leveringsautorisatie Popbep levering obv doelbinding Haarlem geen formele historie is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep         | nummer | attribuut              | aanwezig   |
| onderzoek     | 3      | tijdstipRegistratie    | nee        |
| onderzoek     | 3      | tijdstipVerval         | nee        |
| onderzoek     | 3      | nadereAanduidingVerval | nee        |

Scenario: 7.1   Gegeven in onderzoek heeft een verwijzing naar het persoonsdeel
                Object sleutel gegeven verwijst naar een nog in het bericht staand gegeven in het persoonsdeel
                Voorkomen sleutel gegeven verwijst naar een nog in het bericht staand gegeven in het persoonsdeel
                Datum aanvang materiele periode van de afnemerindicatie = LEEG
                Leveringsautorisatie heeft autorisatie op formele historie
                Leveringsautorisatie heeft GEEN autorisatie op materiele historie
                Logisch testgeval: R1561_07
                Verwacht resultaat: Materiele historie is niet van toepassing op onderzoek als filter dus leveren van onderzoek in bericht

Given de personen 299054457, 743274313, 606417801 zijn verwijderd
And de standaardpersoon UC_Kenny met bsn 606417801 en anr 1383746930 zonder extra gebeurtenissen

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie_mat_his
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie mat his' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie

And testdata uit bestand 13.2_R1561_Plaats_Afnemerindicatie.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie mat his is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 7.2   Gegeven in onderzoek heeft een verwijzing naar het persoonsdeel
                Object sleutel gegeven verwijst naar een nog in het bericht staand gegeven in het persoonsdeel
                Voorkomen sleutel gegeven verwijst naar een nog in het bericht staand gegeven in het persoonsdeel
                Datum aanvang materiele periode van de afnemerindicatie = LEEG
                Leveringsautorisatie heeft autorisatie op formele historie
                Leveringsautorisatie heeft GEEN autorisatie op materiele historie
                Logisch testgeval: R1561_07
                Verwacht resultaat: Materiele historie is niet van toepassing op onderzoek als filter dus leveren van onderzoek in bericht

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(606417801)
nieuweGebeurtenissenVoor(UC_Kenny) {
    onderzoek(partij: 17401) {
        gestartOp(aanvangsDatum:'20160101', omschrijving:'Onderzoek is gestart op postcode', verwachteAfhandelDatum:'2016-08-01')
        gegevensInOnderzoek('Persoon.Adres.Postcode')
    }
}
slaOp(UC_Kenny)

When voor persoon 606417801 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie mat his is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then hebben attributen in voorkomens de volgende waardes:
| groep              | nummer | attribuut    | verwachteWaarde                        |
| onderzoek          | 2      | omschrijving | Onderzoek is gestart op postcode       |


Scenario: 8 Onderzoek op gegeven dat niet tot het persoonsdeel behoort
            Logisch testgeval: R1561_08
            Verwacht resultaat: onderzoek niet in bericht ( en dus ook geen bericht)

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de personen 627129705, 304953337, 411372233 zijn verwijderd
Given de standaardpersoon Olivia met bsn 411372233 en anr 1086049514 zonder extra gebeurtenissen

Given de persoon beschrijvingen:
persoon = Persoon.metBsn(411372233)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401, registratieDatum: 20150101) {
        gestartOp(aanvangsDatum:'2015-01-01', omschrijving:'Onderzoek is gestart op datum aanvang huwelijk/partnerschap', verwachteAfhandelDatum:'2015-04-01')
        gegevensInOnderzoek('HuwelijkGeregistreerdPartnerschap.DatumAanvang')
    }
}
slaOp(persoon)

When voor persoon 411372233 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden