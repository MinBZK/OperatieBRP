Meta:
@auteur                 jowil
@epic                   Mutatielevering op basis van doelbinding
@status                 Klaar
@regels                 R1546

Narrative:
   In het koppelvlak Levering worden van Document alleen de voorkomens in een bericht opgenomen waarvoor geldt dat:
   ActieVerval en Datum/tijd verval niet gevuld zijn.

Scenario: 1. Persoon verhuist deze gemeente krijgt een mutatie bericht.
R1546_L03

Given relevante abonnementen Abo alleen materielehistorie voor alle groepen
Given de personen 299054457, 743274313, 671144649 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 671144649 en anr 7205730578 zonder extra gebeurtenissen

Given administratieve handeling van type verhuizingIntergemeentelijk , met de acties registratieAdres
And testdata uit bestand R1546pad_3_2_verhuizing1.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het mutatiebericht voor abonnement Abo alleen materielehistorie voor alle groepen is ontvangen en wordt bekeken
Then is het bericht xsd-valide
