Meta:
@sprintnummer       79
@epic               Consolidatie & Release tbv stap 3.1
@auteur             rarij
@jiraIssue          TEAMBRP-1890
@status             Klaar
@regels             BRLV0008, R1403, BRAL0012, R1587

Narrative:
            Als afnemer
            wil ik dat de correcte melding getoond wordt bij het valideren van een BSN
            zodat precies weet waarom mijn bevraging fout gaat

            R1587	BRAL0012	Burgerservicenummer voorschrift
            R1403	BRLV0008	Er moet een persoon bestaan met het opgegeven burgerservicenummer

Scenario:   1. Synchronisatie van een persoon met een valide, niet in de BRP aanwezig, BSN
            Verwacht resultaat:
            - De afnemer dient een synchroon bericht te ontvangen waarin vermeld wordt dat de verwerking foutief was
            - Het synchroon bericht dient de volgende regelCode te bevatten "BRLV0008"
            - Het synchroon bericht dient de volgende melding te bevatten "Er is geen persoon geïdentificeerd met het opgegeven burgerservicenummer."

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand niet_leveren_bij_onbekend_of_niet_valide_bsn_1.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                                   |
| BRLV0008 | Er is geen persoon geïdentificeerd met het opgegeven burgerservicenummer. |

Scenario:   2. Synchronisatie van een persoon met een niet valide BSN
            Verwacht resultaat:
            - De afnemer dient een synchroon bericht te ontvangen waarin vermeld wordt dat de verwerking foutief was
            - Het synchroon bericht dient de volgende regelCode te bevatten "BRAL0012"
            - Het synchroon bericht dient de volgende melding te bevatten "Het opgegeven BSN is niet geldig."

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand niet_leveren_bij_onbekend_of_niet_valide_bsn_2.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                           |
| BRAL0012 | Het opgegeven BSN is niet geldig. |
