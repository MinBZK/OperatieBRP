Meta:
@auteur             kedon
@status             Klaar
@usecase            LV.1.MB
@regels             R1562
@sleutelwoorden     Maak BRP bericht

Narrative:
Bij de levering van een Onderzoeksgroep mogen alleen die Gegeven in onderzoek in het resultaat worden opgenomen waarvoor geldt dat deze binnen het
persoonsdeel zijn geautoriseerd.
Om dit op de juiste wijze te verwerken moet uitgegaan worden van de volgende stappen:
1.	Uit het onderzoeksdeel worden alle Gegeven in onderzoek verwijderd, waarvoor geldt dat deze uitsluitend via de Elementnaam verwijzen naar een
ongeautoriseerd attribuut, of een groep of object waarbinnen geen enkel attribuut is geautoriseerd.
2.	Uit het onderzoeksdeel worden alle onderzoeksregels verwijderd, die met een Gegeven in onderzoek.Object sleutel gegeven en/of Gegeven in
onderzoek.Voorkomen sleutel gegeven verwijzen naar een niet meer in de persoondeel aanwezig voorkomen.
NB: met deze stappen wordt bereikt dat een Gegeven in onderzoek die verwijst naar een in het persoonsdeel niet aanwezig gegeven, waarvoor wel
een autorisatie bestaat, niet wordt verwijderd uit het Onderzoeksdeel.
Opmerking:
Bovenstaande NB is niet van toepassing op de huidige filtering. De filtering is nu gebaseerd op de geautoriseerde gegevens in het persoonsdeel
van het bericht. Er zullen dus GEEN onderzoeksregels op ontbrekende gegevens blijven staan (deze worden momenteel ook weggefilterd).
Vanwege het feit dat we geen Onderzoeken op ontbrekende gegevens mogen gaan leveren (zie ook R1564 LET OP VERVALLEN REGEL (zie TEAMBRP-4465)- Stel het
onderzoeksdeel samen voor een
bericht)
levert dit geen problemen op. Mocht besloten worden om deze onderzoeken toch (bijvoorbeeld aan bijhouders) te gaan leveren, dan zal de werking van d
eze regel daarop aangepast moeten worden.
Noot: Voor de eerste stap gelden voor attribuut-autorisaties dezelfde regels als opgenomen inR1974 - Alleen attributen waarvoor autorisatie bestaat
worden geleverd. Deze verwerkingsstap zou dus gecombineerd uitgevoerd kunnen worden met deze regel.


Scenario: 1.1   Gegeven in onderzoek heeft een verwijzing naar het persoonsdeel
                Object sleutel gegeven verwijst naar een nog in het bericht staand gegeven in het persoonsdeel
                Voorkomen sleutel gegeven verwijst naar een nog in het bericht staand gegeven in het persoonsdeel
                Groep/object geautoriseerd
                Attribuut geautoriseerd
                Logisch testgeval: R1562_01
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
                Groep/object geautoriseerd
                Attribuut geautoriseerd
                Logisch testgeval: R1562_01
                Verwacht resultaat: Mutatiebericht geleverd met onderzoek op Onderzoek is gestart op datum aanvang huwelijk


Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(606417801)
nieuweGebeurtenissenVoor(UC_Kenny) {
    onderzoek(partij: 17401) {
        gestartOp(aanvangsDatum:'2015-06-01', omschrijving:'Onderzoek is gestart op persoon', verwachteAfhandelDatum:'2015-09-01')
          gegevensInOnderzoek('GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.Voornamen')
    }
}
slaOp(UC_Kenny)

When voor persoon 606417801 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then hebben attributen in voorkomens de volgende waardes:
| groep     | nummer | attribuut    | verwachteWaarde                 |
| onderzoek | 2      | omschrijving | Onderzoek is gestart op persoon |

Scenario: 2.1   Gegeven in onderzoek heeft een verwijzing naar het persoonsdeel
                Object sleutel gegeven verwijst NIET naar een nog in het bericht staand gegeven in het persoonsdeel
                Voorkomen sleutel gegeven verwijst naar een nog in het bericht staand gegeven in het persoonsdeel
                Groep/object geautoriseerd
                Attribuut geautoriseerd
                Logisch testgeval: R1562_02

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
                Groep/object geautoriseerd
                Attribuut geautoriseerd
                Logisch testgeval: R1562_02
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
                Groep/object geautoriseerd
                Attribuut geautoriseerd
                Logisch testgeval: R1562_03

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
                Groep/object geautoriseerd
                Attribuut geautoriseerd
                Logisch testgeval: R1562_03
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
                Groep/object geautoriseerd
                Attribuut geautoriseerd
                Logisch testgeval: R1562_04
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
                Groep/object geautoriseerd
                Attribuut NIET geautoriseerd
                Logisch testgeval: R1562_05

Given de personen 299054457, 743274313, 606417801 zijn verwijderd
And de standaardpersoon UC_Kenny met bsn 606417801 en anr 1383746930 zonder extra gebeurtenissen

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_obv_afnemerindicatie_huisnummer_niet_geautoriseerd
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering obv afnemerindicatie huisnummer niet geautoriseerd' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie

And testdata uit bestand 13.2_R1561_Plaats_Afnemerindicatie.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering obv afnemerindicatie huisnummer niet geautoriseerd is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 5.2   Gegeven in onderzoek heeft een verwijzing naar het persoonsdeel
                Object sleutel gegeven verwijst naar een nog in het bericht staand gegeven in het persoonsdeel
                Voorkomen sleutel gegeven verwijst naar een nog in het bericht staand gegeven in het persoonsdeel
                Groep/object geautoriseerd
                Attribuut NIET geautoriseerd
                Logisch testgeval: R1562_05
                Verwacht resultaat: Mutatiebericht geleverd met onderzoek op Onderzoek is gestart op postcode

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(606417801)
nieuweGebeurtenissenVoor(UC_Kenny) {
    onderzoek(partij: 17401) {
        gestartOp(aanvangsDatum:'20160101', omschrijving:'Onderzoek is gestart op huisnummer', verwachteAfhandelDatum:'2016-08-01')
        gegevensInOnderzoek('Persoon.Adres.Huisnummer')
    }
}
slaOp(UC_Kenny)

When voor persoon 606417801 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering obv afnemerindicatie huisnummer niet geautoriseerd is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario: 6.1   Gegeven in onderzoek heeft een verwijzing naar het persoonsdeel
                Object sleutel gegeven verwijst naar een nog in het bericht staand gegeven in het persoonsdeel
                Voorkomen sleutel gegeven verwijst naar een nog in het bericht staand gegeven in het persoonsdeel
                Groep/object NIET geautoriseerd
                Attribuut geautoriseerd
                Logisch testgeval: R1562_05

Given de personen 299054457, 743274313, 606417801 zijn verwijderd
And de standaardpersoon UC_Kenny met bsn 606417801 en anr 1383746930 zonder extra gebeurtenissen

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_obv_afnemerindicatie_adres_niet_geautoriseerd
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering obv afnemerindicatie adres niet geautoriseerd' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie

And testdata uit bestand 13.2_R1561_Plaats_Afnemerindicatie.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering obv afnemerindicatie adres niet geautoriseerd is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 6.2   Gegeven in onderzoek heeft een verwijzing naar het persoonsdeel
                Object sleutel gegeven verwijst naar een nog in het bericht staand gegeven in het persoonsdeel
                Voorkomen sleutel gegeven verwijst naar een nog in het bericht staand gegeven in het persoonsdeel
                Groep/object  NIET geautoriseerd
                Attribuut geautoriseerd
                Logisch testgeval: R1562_06
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

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering obv afnemerindicatie adres niet geautoriseerd is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden