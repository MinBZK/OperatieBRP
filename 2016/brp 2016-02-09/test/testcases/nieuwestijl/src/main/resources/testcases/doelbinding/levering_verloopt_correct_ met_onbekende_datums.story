Meta:
@sprintnummer           65
@epic                   Mutatielevering basis
@auteur                 dihoe
@jiraIssue              TEAMBRP-2182
@status                 Klaar

Narrative:
    Als stelselbeheerder
    wil ik dat bij (deels of volledig) onbekende datums de bepaling OF geleverd moet worden correct verloopt
    zodat de levering over het LO3 koppelvlak in stap 3.1a al werkt.

Scenario: Een persoon met deels onbekende geboortedatum is verhuisd naar een nieuwe gemeente, afnemer krijgt een mutatiebericht

Given leveringsautorisatie uit /levering_autorisaties/Abo_op_basis_van_geboortedatum_persoon
Given de database is gereset voor de personen 360381169
Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar'
And administratieve handeling van type verhuizingIntergemeentelijk , met de acties registratieAdres
And testdata uit bestand levering_verloopt_correct_met_onbekende_datums_01.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het mutatiebericht voor leveringsautorisatie Abo op basis van geboortedatum persoon is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep          | attribuut | verwachteWaardes                                           |
| synchronisatie | naam      | Verhuizing intergemeentelijk, Verhuizing intergemeentelijk |
| geboorte       | datum     | 1984-07                                                    |

And is het bericht xsd-valide
