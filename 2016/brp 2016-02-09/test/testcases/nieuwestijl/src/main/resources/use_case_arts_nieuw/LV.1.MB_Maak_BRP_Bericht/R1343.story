Meta:
@auteur                 jowil
@regels                 R1343
@sleutelwoorden         SA.1.PA
@status                 Onderhanden

Narrative:
Plaatsing afnemersindicatie, persoon plaatst verstrekkingsbeperking, geautoriseerde ontvanger ontvangt waarschuwing in bericht.
ongeautoriseerde afnemer ontvangt geen bericht.

Scenario: 1. Plaatsing afnemersindicatie, persoon plaatst verstrekkingsbeperking, geautoriseerde ontvanger ontvangt waarschuwing in bericht.
R1343
Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given de personen 299054457, 743274313, 618489897 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 618489897 en anr 9865250578 zonder extra gebeurtenissen

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand R1343_Plaats_afnemerindicatie_02.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 2.

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
And testdata uit bestand R1343_verhuizing.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 3. Plaatsing afnemersindicatie, persoon plaatst verstrekkingsbeperking, ongeautoriseerde afnemer ontvangt geen bericht.
R1343
Meta:
@regels R1343_L01
Given leveringsautorisatie uit /levering_autorisaties/mutatielevering_obv_afn_ind/Mutaties_op_specifieke_personen_voor_afnemer_is_502707

Given de personen 299054457, 743274313, 148047117 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 148047117 en anr 6321840914 zonder extra gebeurtenissen

Given verzoek voor leveringsautorisatie 'Mutaties op specifieke personen voor afnemer is 502707' en partij 'KUC033-PartijVerstrekkingsbeperking'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand R1343_Plaats_afnemerindicatie_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Mutaties op specifieke personen voor afnemer is 502707 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 4. Plaatsing afnemersindicatie, persoon plaatst verstrekkingsbeperking, ongeautoriseerde afnemer ontvangt geen bericht.
R1343
Meta:
@regels R1343_L01

Given de persoon beschrijvingen:
def UC_Kenny = uitDatabase bsn: 148047117

Persoon.nieuweGebeurtenissenVoor(UC_Kenny) {
    verstrekkingsbeperking() {
        registratieBeperkingen( partij: 502707 )
    }

}
slaOp(UC_Kenny)


When het mutatiebericht voor leveringsautorisatie Mutaties op specifieke personen voor afnemer is 502707 is ontvangen en wordt bekeken
Then is het bericht xsd-valide

