Meta:
@sprintnummer           76
@epic                   Change 2015003: Geg.model relaties & betrokkenh.
@auteur                 dihoe
@jiraIssue              TEAMBRP-2991
@status                 Klaar
@regels                 R1980

Narrative: Als afnemer
           wil ik geen lege containers in uitgaande berichten,
           zodat ze voldoen aan de BRP berichtkenmerken (bevraging)

Scenario:   1. vondeling wordt geboren en verhuist, daarna geef details persoon (bevraging) - levering
            Verwacht resultaat:
            - lege container "bronnen/" wordt niet getoond in groep "actie"

Given de gehele database is gereset
Given leveringsautorisatie uit /levering_autorisaties/abo_geef_details_persoon
Given de standaardpersoon Remi met bsn 930680303 en anr 9154947858 met extra gebeurtenissen:
verhuizing(partij: 'Gemeente Hillegom', aanvang: 19930731, registratieDatum: 19930731) {
    naarGemeente 'Hillegom',
       straat: 'Tulpstraat', nummer: 10, postcode: '2180AB', woonplaats: "Hillegom"
}

Given verzoek voor leveringsautorisatie 'Abo GeefDetailsPersoon' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_bvgGeefDetailsPersoon
And testdata uit bestand lege_containers_bevraging_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
And heeft het antwoordbericht 0 groepen 'bronnen'

Scenario:   2. half-wees wordt geboren en verhuist, daarna geef details persoon (bevraging) - levering
            Verwacht resultaat:
            - lege container "bronnen/" wordt niet getoond in groep "actie"

Given de standaardpersoon Ciske met bsn 168208313 en anr 7298295058 met extra gebeurtenissen:
verhuizing(partij: 'Gemeente Hillegom', aanvang: 19930731, registratieDatum: 19930731) {
    naarGemeente 'Hillegom',
       straat: 'Tulpstraat', nummer: 10, postcode: '2180AB', woonplaats: "Hillegom"
}

Given verzoek voor leveringsautorisatie 'Abo GeefDetailsPersoon' en partij 'Gemeente Utrecht'
And verzoek van bericht lvg_bvgGeefDetailsPersoon
And testdata uit bestand lege_containers_bevraging_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
And heeft het antwoordbericht 0 groepen 'bronnen'

