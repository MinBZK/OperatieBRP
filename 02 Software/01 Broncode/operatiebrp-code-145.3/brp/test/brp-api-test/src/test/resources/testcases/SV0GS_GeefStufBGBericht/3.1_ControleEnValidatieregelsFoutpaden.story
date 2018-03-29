Meta:
@status             Klaar
@usecase            SV.0.GS
@sleutelwoorden     Stuf bg vertaal

Narrative:
Verstuur stuf vertaal verzoek:
Verstuur verzoek en ontvang vertaling
- Gegevensvalidatieregels

Scenario:   1.  StandaardStufAutorisatie verstuurt een stuf vertaal verzoek versie nr niet bestaand
                LT: R2439_LT02
                Verwacht resultaat:
                - Foutief R2439
                - Parameter StUF BG versie moet verwijzen naar een bestaand stamgegeven


Given leveringsautorisatie uit autorisatie/StandaardStufAutorisatie
Given verzoek verstuur stuf bericht:
|key|value
|leveringsautorisatieNaam|'StandaardStufAutorisatie'
|zendendePartijNaam|'Gemeente Utrecht'
|versieNr|0311
|soortBericht|Mutatiebericht
|soortSynchronisatie|Mutatiebericht

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                      |
| R2439    | StUF BG versie moet verwijzen naar een bestaand stamgegeven. |

Scenario:   2.  StandaardStufAutorisatie verstuurt een stuf vertaal verzoek soort bericht niet bestaand
                LT: R2441_LT02
                Verwacht resultaat:
                - Foutief R2241
                - Parameter Vertaling berichtsoort BRP moet verwijzen naar een bestaand stamgegeven


Given leveringsautorisatie uit autorisatie/StandaardStufAutorisatie
Given verzoek verstuur stuf bericht:
|key|value
|leveringsautorisatieNaam|'StandaardStufAutorisatie'
|zendendePartijNaam|'Gemeente Utrecht'
|versieNr|0310
|soortBericht|Mutatiebericht1
|soortSynchronisatie|Mutatiebericht1

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                                     |
| R2441    | De Vertaling Berichtsoort BRP moet verwijzen naar een bestaand stamgegeven. |

Scenario:   3.  StandaardStufAutorisatie verstuurt een stuf vertaal verzoek versie nr verlopen
                LT: R2440_LT02
                Verwacht resultaat:
                - Foutief R2440
                - Parameter StUF BG versie moet verwijzen naar een geldig stamgegeven

Given leveringsautorisatie uit autorisatie/StandaardStufAutorisatie
Given verzoek verstuur stuf bericht:
|key|value
|leveringsautorisatieNaam|'StandaardStufAutorisatie'
|zendendePartijNaam|'Gemeente Utrecht'
|versieNr|0310Verlopen
|soortBericht|Mutatiebericht
|soortSynchronisatie|Mutatiebericht

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                      |
| R2440    | StUF BG versie moet geldig zijn op systeemdatum. |

Scenario:   4. StandaardStufAutorisatie verstuurt een stuf vertaal verzoek soort bericht verlopen
                LT: R2442_LT02
                Verwacht resultaat:
                - Foutief R2442
                - Parameter Vertaling berichtsoort BRP moet verwijzen naar een geldig stamgegeven


Given leveringsautorisatie uit autorisatie/StandaardStufAutorisatie
Given verzoek verstuur stuf bericht:
|key|value
|leveringsautorisatieNaam|'StandaardStufAutorisatie'
|zendendePartijNaam|'Gemeente Utrecht'
|versieNr|0310
|soortBericht|MutatieberichtVerlopen
|soortSynchronisatie|MutatieberichtVerlopen

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                      |
| R2442    | Vertaling Berichtsoort BRP moet geldig zijn op systeemdatum. |

Scenario:   5. StandaardStufAutorisatie verstuurt een stuf vertaal verzoek soort bericht mismatch met soort synchronisatie
            LT: R2444_LT02
            Verwacht resultaat:
            - Foutief R2444
            - De berichtsoort van het te vertalen BRPXML-bericht moet overeenkomen met de opgegeven Vertaling berichtsoort BRP

Given leveringsautorisatie uit autorisatie/StandaardStufAutorisatie
Given verzoek verstuur stuf bericht:
|key|value
|leveringsautorisatieNaam|'StandaardStufAutorisatie'
|zendendePartijNaam|'Gemeente Utrecht'
|versieNr|0310
|soortBericht|Mutatiebericht
|soortSynchronisatie|Mutatiebericht1

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                      |
| R2444    | De berichtsoort van het te vertalen BRPXML-bericht moet overeenkomen met de opgegeven Vertaling berichtsoort BRP. |

Scenario:   6.  StandaardStufAutorisatieWaarschuwing verstuurt een stuf vertaal verzoek soort bericht met waarschuwing uit externe vertaler
                LT: R2445_LT01
                Verwacht resultaat:
                - Foutief R2445
                - Meldingtekst StUF BG Transfromatiecomponent

Given leveringsautorisatie uit autorisatie/StandaardStufAutorisatieWaarschuwing
Given verzoek verstuur stuf bericht:
|key|value
|leveringsautorisatieNaam|'StandaardStufAutorisatieWaarschuwing'
|zendendePartijNaam|'Gemeente Haarlem'
|versieNr|0310
|soortBericht|Mutatiebericht
|soortSynchronisatie|Mutatiebericht

Then heeft het antwoordbericht verwerking Geslaagd
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                      |
| R2445    | meldingen tekst 1 uit vertaalservice. |

Scenario:   7.  StandaardStufAutorisatieFout verstuurt een stuf vertaal verzoek soort bericht met fout uit externe vertaler
                LT: R2446_LT01
                Verwacht resultaat:
                - Foutief R2446
                - Meldingtekst StUF BG Transfromatiecomponent


Given leveringsautorisatie uit autorisatie/StandaardStufAutorisatieFout
Given verzoek verstuur stuf bericht:
|key|value
|leveringsautorisatieNaam|'StandaardStufAutorisatieFout'
|zendendePartijNaam|'Gemeente Olst'
|versieNr|0310
|soortBericht|Mutatiebericht
|soortSynchronisatie|Mutatiebericht

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                |
| R2446    | meldingen tekst 2 uit vertaalservice. |



