Meta:
@auteur             kedon
@status             Vervallen
@usecase            LV.1.MB
@regels             R2063,R2065
@sleutelwoorden     Maak BRP bericht

Narrative:
Deze regel heeft tot doel om ervoor zorg te dragen dat de gegevens uit Onderzoek, die betrekking hebben op de te leveren Persoon in het Bericht
worden opgenomen.
We hebben het hier altijd over de aan de Persoon gerelateerde Onderzoeken. Dus de Persoon \ Onderzoek gegevens, die direct gerelateerd zijn aan
de uitgangspersoon.
De onderzoeken die door toepassing van deze regel gevonden worden, vullen het onderzoeksdeel van het VolledigBericht.
Voor een Mutatiebericht geldt dat het onderzoeksdeel beperkt wordt tot het/de Onderzoek(en) die door de Administratieve Handeling
(van het MutatieBericht) worden geraakt.
Van de te leveren onderzoeksgegevens moeten de onderzoeksregels die betrekking hebben op een "Onderzoek naar een ontbrekend gegeven" worden uitgezonderd.
 Het is Juridisch niet toegestaan om deze onderzoeksgegevens te leveren aan Afnemers.
 (zie tevens de comment van 23-09-2015 10:14AM in Jira story TEAMBRP-2510).
Uit het samengestelde onderzoeksdeel moeten dus de volgende onderzoeksregels worden verwijderd:
•	Alle Gegeven in onderzoek, waarvoor geldt dat deze GEEN Gegeven in onderzoek.Object sleutel gegeven én GEEN
Gegeven in onderzoek.Voorkomen sleutel gegeven bevatten (dus uitsluitend een verwijzing naar Gegeven in onderzoek.Element)

Zie ook 'Onderzoeksgroep' (R1543) voor de definitie van de "Onderzoeksgroep" en de daarin gehanteerde begrippen

Scenario: 1.1   Gegeven in onderzoek heeft een verwijzing naar het persoonsdeel
                Object sleutel gegeven verwijst naar een nog in het bericht staand gegeven in het persoonsdeel
                Voorkomen sleutel gegeven verwijst naar een nog in het bericht staand gegeven in het persoonsdeel
                Onderzoek in huidige administratieve handeling
                Onderzoek naar ontbrkend gegeven = nee

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
                Onderzoek in huidige administratieve handeling
                Onderzoek naar ontbrkend gegeven = nee
                Logisch testgeval: R1564_01
                Verwacht resultaat: Mutatiebericht met gegven in onderzoek in bericht

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
                Onderzoek in huidige administratieve handeling
                Onderzoek naar ontbrekend gegeven = nee

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
                Onderzoek in huidige administratieve handeling
                Onderzoek naar ontbrekend gegeven = nee
                Logisch testgeval: R1564_02
                Verwacht resultaat: Mutatiebericht met gegven in onderzoek in bericht

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
                Object sleutel gegeven verwijst  naar een nog in het bericht staand gegeven in het persoonsdeel
                Voorkomen sleutel gegeven verwijst NIET naar een nog in het bericht staand gegeven in het persoonsdeel
                Onderzoek in huidige administratieve handeling
                Onderzoek naar ontbrekend gegeven = nee

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
                Object sleutel gegeven verwijst  naar een nog in het bericht staand gegeven in het persoonsdeel
                Voorkomen sleutel gegeven verwijst NIET naar een nog in het bericht staand gegeven in het persoonsdeel
                Onderzoek in huidige administratieve handeling
                Onderzoek naar ontbrekend gegeven = nee
                Logisch testgeval: R1564_03
                Verwacht resultaat: Mutatiebericht met gegven in onderzoek in bericht

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
                Onderzoek in huidige administratieve handeling
                Onderzoek naar ontbrekend gegeven = nee

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
                Onderzoek in huidige administratieve handeling
                Onderzoek naar ontbrekend gegeven = nee
                Logisch testgeval: R1564_04
                Verwacht resultaat: Gegeven in onderzoek wordt weggefilterd dus geen bericht

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
                Object sleutel gegeven verwijst  naar een nog in het bericht staand gegeven in het persoonsdeel
                Voorkomen sleutel gegeven verwijst  naar een nog in het bericht staand gegeven in het persoonsdeel
                Onderzoek in huidige administratieve handeling = NEE
                Onderzoek naar ontbrekend gegeven = nee
                Logisch testgeval: R1564_05
                Verwacht resultaat: Gegeven in onderzoek wordt weggefilterd dus geen bericht

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given de personen 299054457, 743274313, 449809353 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 449809353 en anr 2476721810 zonder extra gebeurtenissen

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 6.5_R1544_plaats_afnemerindicatie.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When voor persoon 449809353 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 5.2   Gegeven in onderzoek heeft een verwijzing naar het persoonsdeel
                Object sleutel gegeven verwijst  naar een nog in het bericht staand gegeven in het persoonsdeel
                Voorkomen sleutel gegeven verwijst  naar een nog in het bericht staand gegeven in het persoonsdeel
                Onderzoek in huidige administratieve handeling = NEE
                Onderzoek naar ontbrekend gegeven = nee
                Logisch testgeval: R1564_05
                Verwacht resultaat: Geen mutatiebericht van onderzoek

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(449809353)
nieuweGebeurtenissenVoor(UC_Kenny) {
    onderzoek(partij: 17401, registratieDatum: 20100101) {
        gestartOp(aanvangsDatum:'20111231', omschrijving:'Onderzoek is gestart op huisnummer', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Adres.Huisnummer')
    }
}
slaOp(UC_Kenny)

When voor persoon 449809353 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario: 6.1   Gegeven in onderzoek heeft een verwijzing naar het persoonsdeel
                Object sleutel gegeven verwijst  naar een nog in het bericht staand gegeven in het persoonsdeel
                Voorkomen sleutel gegeven verwijst  naar een nog in het bericht staand gegeven in het persoonsdeel
                Onderzoek in huidige administratieve handeling
                Onderzoek naar ontbrekend gegeven = JA
                Logisch testgeval: R1564_06
                Verwacht resultaat: Geen onderzoek op ontbrekende gegevens leveren, dus geen bericht

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de personen 627129705, 304953337, 411372233 zijn verwijderd
Given de standaardpersoon Olivia met bsn 411372233 en anr 1086049514 zonder extra gebeurtenissen

Given de persoon beschrijvingen:
persoon = Persoon.metBsn(411372233)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401) {
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