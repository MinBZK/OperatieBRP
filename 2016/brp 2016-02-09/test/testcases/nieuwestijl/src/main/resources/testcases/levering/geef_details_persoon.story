Meta:
@status Klaar
@regels VR00092, R1805

Narrative:
Deze case controleert binnen het response-bericht van een geefDetailsPersoon de sortering van meervoudig-voorkomende groepen:
- Nationaliteiten
- Voornamen
- Geslachtsnaamcomponenten
- Verificatie
!-- let op! nu wordt in de testdata voor persoon de partij code aangewezen dmv id, en dat is dynamisch.
!-- Daardoor kan de waardes van partij code opeens aanders zijn. zie TEAMBRP-2521

Scenario: Succesvol uitvoeren geefDetailsPersoon

Given de gehele database is gereset
Given leveringsautorisatie uit /levering_autorisaties/abo_geef_details_persoon
Given verzoek voor leveringsautorisatie 'Abo GeefDetailsPersoon' en partij 'Gemeente Utrecht'
And verzoek van bericht lvg_bvgGeefDetailsPersoon
And testdata uit bestand geef_details_persoon_testcase_01.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
And hebben in het antwoordbericht de attributen in de groepen de volgende waardes:
| groep 		            | attribuut             | verwachteWaardes              |
| identificatienummers      | burgerservicenummer   | 340014155                     |
| nationaliteit             | nationaliteitCode     | 0001,0027                     |
| voornamen                 | volgnummer            | 1,2,3                         |
| voornamen                 | naam                  | Jo,Ko,Mo                      |
| geslachtsnaamcomponenten  | volgnummer            | 1,2                           |
| geslachtsnaamcomponenten  | stam                  | Elk,Kelk                      |
| verificatie               | partijCode            | 199900, 199900                |
| verificatie               | soort                 | Attestie de vita, SoortVerificatie |
