Meta:
@epic               foutpad Synchroniseer Persoon
@status             Klaar
@usecase            SA.0.SP

Narrative:
Synchroniseer persoon:
Als afnemer wil ik een afnemerverzoek tot het synchroniseren van een persoon afhandelen,
met dienst 'Mutatielevering op basis van doelbinding' of 'Mutatielevering op basis van afnemerindicatie',
zodat de afnemer voor de opgegeven Persoon een Volledig bericht ontvangt
Foutpaden


Scenario:   1.   Verzoek synchronisatie persoon, met dienst mutatielevering op basis van doelbinding en
            LT: R1982_LT02
            Populatiebeperking dienst mutatielevering op basis van doelbinding evalueert op ONWAAR
            Verwacht resultaat: Response bericht
                Met vulling:
                -  Verwerking = Foutief
                -  Foutmelding = De opgegeven persoon valt niet te synchroniseren binnen de opgegeven leveringsautorisatie.

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding_ONWAAR
!-- Given de database is aangepast met: update autaut.levsautorisatie set populatiebeperking='ONWAAR' where naam = 'Geen pop.bep. levering op basis van doelbinding'

Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                                                                |
| R1403 | Er is geen persoon gevonden met het opgegeven identiteitsnummer binnen uw autorisatie. |


Scenario:   3.   Verzoek synchronisatie persoon, met dienst mutatielevering op basis van doelbinding en
            LT: R1347_LT03
            DatumIngang dienst mutatielevering op basis van doelbinding > systeemdatum
            Verwacht resultaat: Response bericht
                Met vulling:
                -  Verwerking = Foutief
                -  Foutmelding = Er is geen dienst mutatielevering aanwezig binnen de opgegeven leveringsautorisatie.

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit /levering_autorisaties_nieuw/geen_pop_bep_levering_op_basis_van_doelbinding_dienst_ingang_toekomst
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                            |
| R2343    | De gevraagde dienst is niet geldig.                |

Then is er een autorisatiefout gelogd met regelcode R1347

Scenario:   4.   Verzoek synchronisatie persoon, met dienst mutatielevering op basis van doelbinding en
            LT: R1347_LT04
            DatumEinde volgen dienst mutatielevering op basis van doelbinding < systeemdatum
            Verwacht resultaat: Response bericht
                Met vulling:
                -  Verwerking = Foutief
                -  Foutmelding = De dienst van de gevraagde soort binnen het opgegeven leveringsautorisatie moet geldig zijn op de systeemdatum.

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding_dienst_einde_voor_nu

Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                            |
| R2343    | De gevraagde dienst is niet geldig.                |

Then is er een autorisatiefout gelogd met regelcode R1347


Scenario:   5.  Verzoek synchronisatie persoon, met dienst mutatielevering op basis van afnemerindicatie en
                LT: R1347_LT05
                DatumEinde volgen dienst mutatielevering op basis van afnemerindicatie < systeemdatum
                Verwacht resultaat: Response bericht
                Met vulling:
                -  Verwerking = Foutief
                -  Foutmelding = Er is geen dienst mutatielevering aanwezig binnen de opgegeven leveringsautorisatie.

Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T150_xls
Given leveringsautorisatie uit autorisatie/Geen_pop_bep_levering_op_basis_van_afnemerindicatie_dateinde_in_verleden
Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg
|434587977|Geen pop.bep. levering op basis van afnemerindicatie|'Gemeente Utrecht'|30|2016-07-28 T16:11:21Z

Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|434587977

Then heeft het antwoordbericht verwerking Foutief
Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                            |
| R2343    | De gevraagde dienst is niet geldig.                |

Then is er een autorisatiefout gelogd met regelcode R1347


Scenario:   6. Afnemer vraagt gegevens op van een persoon, deze persoon heeft een totale verstrekkingsbeperking.
            LT: R1339_LT01
            Verwacht resultaat: 1. Synchroon responsebericht
                                Met vulling:
                                -  Verwerking = foutief
                                -  Melding: Bij deze persoon geldt een verstrekkingsbeperking waardoor deze dienst niet geleverd kan worden.
                                2. Er wordt niet geleverd voor deze persoon

Given leveringsautorisatie uit autorisatie/afnemer_502707_verstrekkingbeperking_mogelijk
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T10_xls

Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'afnemer 502707 verstrekkingbeperking mogelijk'
|zendendePartijNaam|'KUC033-PartijVerstrekkingsbeperking'
|bsn|434587977

Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                                                            |
| R1339    | Bij deze persoon geldt een verstrekkingsbeperking waardoor deze dienst niet geleverd kan worden.   |

Then is het aantal ontvangen berichten 0

Scenario: 8. Synchroniseer persoon op een ongeldig BSN
            LT: R1587_LT06
            Verwacht resultaat:  synchroon responsebericht
                -  Verwerking = Foutief
                -  Melding  =   Het opgegeven BSN is niet geldig.

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding

Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|123456789

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                           |
| R1587 | Het opgegeven BSN is niet geldig. |

