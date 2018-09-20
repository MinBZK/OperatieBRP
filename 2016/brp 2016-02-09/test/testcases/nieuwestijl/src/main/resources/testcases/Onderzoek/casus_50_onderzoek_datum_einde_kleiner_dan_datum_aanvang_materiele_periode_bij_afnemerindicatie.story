Meta:
@auteur                 dihoe
@epic                   Levering onderzoek
@sleutelwoorden         onderzoek,casus50
@status                 Klaar
@regels                 VR00058,R1544

Narrative: Casus 50: VolledigBericht waarop voor de afnemer een Datum Aanvang Materiële Periode geldt:
           er is een onderzoek op Adres beëindigd voorafgaand aan deze datum.

Scenario: 1. Volledigbericht met een Onderzoek.DatumEinde kleiner dan de Datum aanvang materiele periode (vandaag) bij de afnemerindicatie, er wordt geleverd zonder onderzoek

Given de gehele database is gereset
Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given de standaardpersoon Olivia met bsn 404108969 en anr 5348427538 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(404108969)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401, registratieDatum: 20150101) {
        gestartOp(aanvangsDatum:'2015-01-01', omschrijving:'Onderzoek is gestart op geboortedatum', verwachteAfhandelDatum:'2015-06-01')
        gegevensInOnderzoek('Persoon.Geboorte.Datum')
    }
    onderzoek(partij: 59401) {
        afgeslotenOp(eindDatum: gisteren())
    }
}
slaOp(persoon)

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand casus_50_onderzoek_datum_einde_kleiner_dan_datum_aanvang_materiele_periode_bij_afnemerindicatie_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When voor persoon 404108969 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep              | nummer | attribuut    | aanwezig |
| onderzoek          | 2      | omschrijving | nee      |
| onderzoek          | 3      | omschrijving | nee      |
| onderzoek          | 2      | statusNaam   | nee      |
| gegevenInOnderzoek | 1      | elementNaam  | nee      |
