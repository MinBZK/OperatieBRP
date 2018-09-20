Meta:
@auteur                 dihoe
@regels                 VR00082,R1548
@epic                   Levering indicatoren
@jiraIssue              TEAMBRP-2137
@sleutelwoorden         nadereaanduidingverval
@status                 Klaar

Narrative: Dit scenario laat het element "nadere aanduiding verval" zien in een leveringbericht.

Scenario: 1. Correctie op adres, dan wordt de waarde "O" geplaatst in de DB bij kern.his_persadres, nadereaandverval

Given de gehele database is gereset
Given de standaardpersoon Vanilla met bsn 495957033 en anr 6910643474 met extra gebeurtenissen:
verhuizing(partij: 'Gemeente Utrecht', aanvang: 20150728, registratieDatum: 20150728) {
    naarGemeente 'Utrecht',
        straat: 'Lindelaan', nummer: 2, postcode: '3500AA', woonplaats: "Utrecht"
}
Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar'
Given administratieve handeling van type correctieAdres , met de acties correctieAdres
And testdata uit bestand nadere_aanduiding_verval_in_leveringbericht_01.yml
And de database is aangepast met: update kern.his_persadres set nadereaandverval = 'O' where (dataanvgel = 20150728 and dataanvadresh = 20150728)

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 2. De inhoud van de volledigbericht wordt gecontroleerd

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand nadere_aanduiding_verval_in_leveringbericht_02.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende waardes:
| groep | nummer | attribuut              | verwachteWaarde |
| adres | 1      | nadereAanduidingVerval | O               |
