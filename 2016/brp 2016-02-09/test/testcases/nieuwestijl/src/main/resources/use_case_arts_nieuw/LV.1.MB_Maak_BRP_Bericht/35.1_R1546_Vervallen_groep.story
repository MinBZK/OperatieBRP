Meta:
@auteur             elhoo
@status             Klaar
@usecase            LV.1.MB
@regels             R1546
@sleutelwoorden     Maak BRP bericht

Narrative:
Een vervallen groep (Datum\tijd verval en ActieVerval zijn gevuld met een waarde) mag alleen in het te leveren
resultaat worden opgenomen als er bij de Dienst waarvoor geleverd wordt een corresponderend voorkomen is van
Dienstbundel\Groep met Dienstbundel\Groep.Formele historie? = 'Ja' of als het een Mutatielevering betreft.

(NB: dit geldt alleen voor berichten waar in principe een volledige Persoon geleverd wordt zoals een VolledigBericht
of een bevragingsresultaat; autorisatie op groepen met formele historie is niet van toepassing bij een Mutatielevering)


Scenario:   1.1 Vervallen groep (adres) met Datum/tijd verval en ActieVerval gevuld en een corresponderend voorkomen van
                Dienstbundel/Groep met Formele historie = Ja
                Logische testgevallen: R1546_01, R1546_04, R1546_05
                Verwacht resultaat: Vervallen groep wordt opgenomen in te leveren bericht, dus 3 groepen adres
                - Soort bericht = Mutatiebericht en Volledig bericht

Given de personen 299054457, 743274313, 472570857 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 472570857 en anr 6125942546 zonder extra gebeurtenissen

Given leveringsautorisatie uit /levering_autorisaties/Populatiebeperking_levering_op_basis_van_doelbinding_Haarlem

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Haarlem.txt
Given bijhoudingsverzoek voor partij 'Gemeente Haarlem'
Given administratieve handeling van type verhuizingIntergemeentelijk , met de acties registratieAdres
And testdata uit bestand 35.2_R1546_verhuizing_binnen_Haarlem.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het mutatiebericht voor leveringsautorisatie Populatiebeperking levering op basis van doelbinding Haarlem is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then heeft het bericht 3 groep 'adres'


Scenario:   1.2 Vervallen groep (adres) met Datum/tijd verval en ActieVerval gevuld en een corresponderend voorkomen van
                Dienstbundel/Groep met Formele historie = Ja
                Logische testgevallen: R1546_01, R1546_04, R1546_05
                Verwacht resultaat: Vervallen groep wordt opgenomen in te leveren bericht, dus 3 groepen adres
                - Soort bericht = Mutatiebericht en Volledig bericht

Given leveringsautorisatie uit /levering_autorisaties/Populatiebeperking_levering_op_basis_van_doelbinding_Haarlem
Given verzoek voor leveringsautorisatie 'Populatiebeperking levering op basis van doelbinding Haarlem' en partij 'Gemeente Haarlem'

Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 35.3_R1546_Synchroniseer_Persoon.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Populatiebeperking levering op basis van doelbinding Haarlem is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then heeft het bericht 3 groep 'adres'



Scenario:   2.1 Vervallen groep (adres) met Datum/tijd verval en ActieVerval gevuld en een corresponderend voorkomen van
                Diensbundel/Groep met Formele historie = Nee
                Logisch testgeval: R1546_02, R1546_03
                Verwacht resultaat: Vervallen groep wordt niet opgenomen in te leveren bericht, dus 3 groepen adres
                - Soort bericht = Mutatiebericht en Volledig bericht

Given de personen 299054457, 743274313, 472570857 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 472570857 en anr 6125942546 zonder extra gebeurtenissen

Given leveringsautorisatie uit /levering_autorisaties/Populatiebeperking_levering_op_basis_van_doelbinding_Haarlem_Geen_Formele_historie

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Haarlem.txt
Given bijhoudingsverzoek voor partij 'Gemeente Haarlem'
Given administratieve handeling van type verhuizingIntergemeentelijk , met de acties registratieAdres
And testdata uit bestand 35.4_R1546_verhuizing_binnen_Haarlem.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het mutatiebericht voor leveringsautorisatie Popbep levering obv doelbinding Haarlem geen formele historie is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then heeft het bericht 3 groep 'adres'


Scenario:   2.2 Vervallen groep (adres) met Datum/tijd verval en ActieVerval gevuld en een corresponderend voorkomen van
                Diensbundel/Groep met Formele historie = Nee
                Logisch testgeval: R1546_02, R1546_03
                Verwacht resultaat: Vervallen groep wordt niet opgenomen in te leveren bericht, dus 3 groepen adres
                - Soort bericht = Mutatiebericht en Volledig bericht

Given de persoon beschrijvingen:
persoon = Persoon.metBsn(472570857)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 'Gemeente Haarlem', aanvang: 20160209, registratieDatum: 20160209) {
            naarGemeente 'Haarlem',
                straat: 'Plein', nummer: 70, postcode: '2000AA', woonplaats: "Haarlem"
        }
}
slaOp(persoon)

Given leveringsautorisatie uit /levering_autorisaties/Populatiebeperking_levering_op_basis_van_doelbinding_Haarlem_Geen_Formele_historie
Given verzoek voor leveringsautorisatie 'Popbep levering obv doelbinding Haarlem geen formele historie' en partij 'Gemeente Haarlem'

Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 35.3_R1546_Synchroniseer_Persoon.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Popbep levering obv doelbinding Haarlem geen formele historie is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then heeft het bericht 3 groep 'adres'
