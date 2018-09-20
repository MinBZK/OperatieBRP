Meta:
@auteur                 elhoo
@status                 Klaar
@usecase                LV.1.MB
@regels                 R1343
@sleutelwoorden         Maak BRP bericht, Plaatsen afnemersindicatie, Plaatsen verstrekkingsbeperking


Narrative:
Plaatsing afnemersindicatie, persoon plaatst verstrekkingsbeperking, geautoriseerde ontvanger ontvangt waarschuwing in bericht.
ongeautoriseerde afnemer ontvangt geen bericht.

Scenario:   1.1 Plaatsing afnemersindicatie, persoon plaatst volledige verstrekkingsbeperking,
                afnemer waarbij geen verstrekkingsbeperking geldt ontvangt geen bericht. (TEAMBRP-4847: waarschuwing in bericht (R1340, inactieve regel))
                Logische testgevallen Use Case: R1343_01

Given de personen 299054457, 743274313, 618489897 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 618489897 en anr 9865250578 zonder extra gebeurtenissen

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie

And testdata uit bestand 41.2_R1343_Plaats_afnemerindicatie.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario:   1.2 Plaatsing afnemersindicatie, persoon plaatst volledige verstrekkingsbeperking,
                afnemer waarbij geen verstrekkingsbeperking geldt ontvangt geen bericht. (TEAMBRP-4847: waarschuwing in bericht (R1340, inactieve regel))
                Logische testgevallen Use Case: R1343_01

Given de persoon beschrijvingen:
def UC_Kenny = uitDatabase bsn: 618489897

Persoon.nieuweGebeurtenissenVoor(UC_Kenny) {
    verstrekkingsbeperking() {
        volledig ja
    }
}
slaOp(UC_Kenny)

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar'
Given administratieve handeling van type verhuizingIntergemeentelijk , met de acties registratieAdres
And testdata uit bestand 41.3_R1343_Verhuizing.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then is er geen synchronisatiebericht gevonden


Scenario:   2.1 Plaatsing afnemersindicatie, persoon plaatst specifieke verstrekkingsbeperking,
                afnemer waarvoor verstrekkingsbeperking geldt ontvangt waarschuwing in bericht. (bug TEAMBRP-3995: leeg bericht, waardoor niet geleverd wordt)
                Logische testgevallen Use Case: R1343_02

Given de personen 299054457, 743274313, 148047117 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 148047117 en anr 6321840914 zonder extra gebeurtenissen

Given leveringsautorisatie uit /levering_autorisaties/mutatielevering_obv_afn_ind/Mutaties_op_specifieke_personen_voor_afnemer_is_502707
Given verzoek voor leveringsautorisatie 'Mutaties op specifieke personen voor afnemer is 502707' en partij 'KUC033-PartijVerstrekkingsbeperking'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie

And testdata uit bestand 41.4_R1343_Plaats_afnemerindicatie.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Mutaties op specifieke personen voor afnemer is 502707 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario:   2.2 Plaatsing afnemersindicatie, persoon plaatst specifieke verstrekkingsbeperking,
                afnemer waarvoor verstrekkingsbeperking geldt ontvangt waarschuwing in bericht. (bug TEAMBRP-3995: leeg bericht, waardoor niet geleverd wordt)
                Logische testgevallen Use Case: R1343_02

Given de persoon beschrijvingen:
def UC_Kenny = uitDatabase bsn: 148047117

Persoon.nieuweGebeurtenissenVoor(UC_Kenny) {
    verstrekkingsbeperking() {
        registratieBeperkingen( partij: 502707 )
    }
}
slaOp(UC_Kenny)

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar'
Given administratieve handeling van type verhuizingIntergemeentelijk , met de acties registratieAdres
And testdata uit bestand 41.5_R1343_Verhuizing.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het mutatiebericht voor leveringsautorisatie Mutaties op specifieke personen voor afnemer is 502707 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

And hebben attributen in voorkomens de volgende waardes:
| groep 	            | nummer  	| attribuut             | verwachteWaarde |
| melding        	    | 1         | regelCode        	    | BRLV0036        |
| melding        	    | 1         | soortNaam      	    | Waarschuwing    |
| melding        	    | 1         | melding         	    | Bij deze persoon is een verstrekkingsbeperking vastgelegd, mutatielevering is gestopt. |



Scenario:   3.1 Plaatsing afnemersindicatie, persoon plaatst specifieke verstrekkingsbeperking,
                afnemer waarbij geen verstrekkingsbeperking geldt ontvangt geen bericht. (TEAMBRP-4847: waarschuwing in bericht (R1340, inactieve regel))
                Logische testgevallen Use Case: R1343_01

Given de personen 299054457, 743274313, 148047117 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 148047117 en anr 6321840914 zonder extra gebeurtenissen

Given leveringsautorisatie uit /levering_autorisaties/mutatielevering_obv_afn_ind/Mutaties_op_specifieke_personen_voor_afnemer_is_502707
Given verzoek voor leveringsautorisatie 'Mutaties op specifieke personen voor afnemer is 502707' en partij 'KUC033-PartijVerstrekkingsbeperking'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie

And testdata uit bestand 41.4_R1343_Plaats_afnemerindicatie.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Mutaties op specifieke personen voor afnemer is 502707 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario:   3.2 Plaatsing afnemersindicatie, persoon plaatst specifieke verstrekkingsbeperking,
                afnemer waarbij geen verstrekkingsbeperking geldt ontvangt geen bericht. (TEAMBRP-4847: waarschuwing in bericht (R1340, inactieve regel))
                Logische testgevallen Use Case: R1343_01

Given de persoon beschrijvingen:
def UC_Kenny = uitDatabase bsn: 148047117

Persoon.nieuweGebeurtenissenVoor(UC_Kenny) {
    verstrekkingsbeperking() {
        registratieBeperkingen( partij: 850001 )
    }
}
slaOp(UC_Kenny)

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar'
Given administratieve handeling van type verhuizingIntergemeentelijk , met de acties registratieAdres
And testdata uit bestand 41.5_R1343_Verhuizing.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het mutatiebericht voor leveringsautorisatie Mutaties op specifieke personen voor afnemer is 502707 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then is er geen synchronisatiebericht gevonden
