Meta:
@auteur             kedon
@status             Klaar
@usecase            LV.1.MB
@regels             R1567
@sleutelwoorden     Maak BRP bericht

Narrative:
R1565 - Geleverde objecten bevatten in het bericht een ObjectSleutel beschrijft de mogelijke versleuteling van de ObjectSleutel
in berichten. Wanneer binnen hetzelfde bericht verwezen wordt naar een object waarvan de ID versleuteld is, dan dient de verwijzing
dezelfde versleutelde waarde te gebruiken.
(Concreet: als er in een bericht een Gegeven in Onderzoek voorkomt met een ObjectSleutel van een Persoon, dan dient dezelfde
versleutelde waarde zowel in het persoonsdeel als in het onderzoeksdeel gebruikt te worden; het moet niet nodig zijn om de waarde
te ontsleutelen om te kunnen achterhalen wat er in onderzoek staat



Scenario: 1.1   Afnemerindicatie wordt geplaatst met meerder objecten. Controle op objecten voor objectsleutel
                Logsich testgeval: R1567_01
                Verwacht resultaa: mutatiebericht

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


Scenario: 1.2   Onderzoek wordt gestart. Controle op objectsleutel Onderzoek
                Logsich testgeval: R1567_01
                Verwacht resultaat: Objectsleutel persoonsdeel (adres) = objectsleutel gegeven onderzoek
Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(606417801)
nieuweGebeurtenissenVoor(UC_Kenny) {
    onderzoek(partij: 17401) {
        gestartOp(aanvangsDatum:'20160101', omschrijving:'Onderzoek is gestart op adres', verwachteAfhandelDatum:'2016-08-01')
        gegevensInOnderzoek('Persoon.Adres')
    }
}
slaOp(UC_Kenny)

When voor persoon 606417801 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep              | nummer | attribuut            | aanwezig |
| onderzoeken        | 1      | onderzoek            | ja       |
| gegevenInOnderzoek | 1      | objectSleutelGegeven | ja       |

Then is er voor xpath //brp:adres[@brp:objectSleutel=//brp:objectSleutelGegeven/text()] een node aanwezig in het levering bericht