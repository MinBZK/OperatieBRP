Meta:
@sprintnummer           67
@epic                   Change yyyynn: CorLev - Element tabel
@auteur                 dihoe
@jiraIssue              TEAMBRP-2434
@status                 Klaar
@regels                 VR00052, R1974, VR00081, R1547
@sleutelwoorden         synchronisatie

Narrative:
    Als stelselbeheerder
    wil ik dat de gegevensautorisatie blijft werken als de Abonnementen de groepen en attributen aanwijzen via de Elementtabel.

    R1974	VR00052	Alleen attributen waarvoor autorisatie bestaat worden geleverd
    R1547	VR00081	Leveren van DatumEindeGeldigheid mag alleen bij autorisatie op materiele historie

Deze testscenario test acceptatiecriteria 3
!--    wie:          geboorte/woonplaatsnaam: bsn:
!-- 1. hoofdpersoon: Vlaardingen              503811762
!-- 2. vader1:       Wolsum                   266512665
!-- 3. moeder1:      Wolsum                   978072777
!-- 4. vader2:       Hellendoorn              183020169
!-- 5. moeder2:      Hellendoorn              871518089
!-- 6. kind:         Tuitjenhorn              219383145
!-- 7. partner:      Twello                   968092937


Scenario: 1. Een afnemer mag wel de geboorteplaats zien van de hoofdpersoon en van zijn partner, maar niet van zijn ouders en zijn kinderen

Given de gehele database is gereset
Given leveringsautorisatie uit /levering_autorisaties/Abo_autorisatie_alleen_op_geboorteplaats_hoofdpersoon_en_partner
Given verzoek voor leveringsautorisatie 'Abo autorisatie alleen op geboorteplaats hoofdpersoon en partner' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand alleen_geboorteplaats_testpersoon_en_partner_worden_getoond_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie Abo autorisatie alleen op geboorteplaats hoofdpersoon en partner is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep    | attribuut      | verwachteWaardes   |
| geboorte | woonplaatsnaam | Vlaardingen,Twello |

Scenario: 2. Een afnemer mag alleen de geboorteplaats zien van vader1 en zijn partner

Given de gehele database is gereset
Given leveringsautorisatie uit /levering_autorisaties/Abo_autorisatie_alleen_op_geboorteplaats_hoofdpersoon_en_partner
Given verzoek voor leveringsautorisatie 'Abo autorisatie alleen op geboorteplaats hoofdpersoon en partner' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand alleen_geboorteplaats_testpersoon_en_partner_worden_getoond_02.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie Abo autorisatie alleen op geboorteplaats hoofdpersoon en partner is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep    | attribuut      | verwachteWaardes |
| geboorte | woonplaatsnaam | Wolsum,Wolsum    |
