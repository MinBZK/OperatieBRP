Meta:
@status             Klaar
@usecase            VB.0.AV
@sleutelwoorden     Afhandelen Vrij Bericht
@regels             R2469
@versie             1

Narrative:

Het OIN van het PKI-overheidscertificaat waarmee de beveiligde verbinding is opgezet
(De 'Transporteur', zoals gedefinieerd door De autorisatie voor het verzenden van een vrij bericht (R2452))
moet verwijzen naar een Geldig voorkomen stamgegeven op peildatum (R1284) op 'Systeemdatum' (R2016) in Partij.

Scenario: 1.    Transporteur (Datum.Ingang gisteren, Datum.Einde Leeg) is geldig
                LT: R2469_LT01
                Verwacht resultaat:
                - Geslaagd

Given verzoek verstuur vrij bericht:
| key                  | value                                                   |
| zendendePartijNaam   | 'PartijTransporteurDatumIngangGisterenDatumEindeLeeg'   |
| zenderVrijBericht    | 'PartijTransporteurDatumIngangGisterenDatumEindeLeeg'   |
| ontvangerVrijBericht | 'PartijTransporteurDatumIngangGisterenDatumEindeMorgen' |
| ondertekenaar        | 'PartijTransporteurDatumIngangGisterenDatumEindeLeeg'   |
| transporteur         | 'PartijDatumIngangGisterenDatumEindeLeeg'               |
| soortNaam            | 'Beheermelding'                                         |
| inhoud               | 'Test'                                                  |

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 2.    Transporteur (Datum.Ingang gisteren, Datum.Einde morgen) is geldig
                LT: R2469_LT02
                Verwacht resultaat:
                - Geslaagd

Given verzoek verstuur vrij bericht:
| key                  | value                                                   |
| zendendePartijNaam   | 'PartijTransporteurDatumIngangGisterenDatumEindeMorgen' |
| zenderVrijBericht    | 'PartijTransporteurDatumIngangGisterenDatumEindeMorgen' |
| ontvangerVrijBericht | 'PartijTransporteurDatumIngangGisterenDatumEindeLeeg'   |
| ondertekenaar        | 'PartijTransporteurDatumIngangGisterenDatumEindeMorgen' |
| transporteur         | 'PartijTransDatumIngangGisterenDatumEindeMorgen'        |
| soortNaam            | 'Beheermelding'                                         |
| inhoud               | 'Test'                                                  |

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 3.    Transporteur (Datum.Ingang morgen, Datum.Einde Leeg) is ONgeldig
                LT: R2469_LT03
                Verwacht resultaat:
                - Foutief
                - R2343 Er is een autorisatiefout opgetreden.
                - Gelogd met regelcode R2469

Given verzoek verstuur vrij bericht:
| key                  | value                                                 |
| zendendePartijNaam   | 'PartijTransporteurDatumIngangMorgenDatumEindeLeeg'   |
| zenderVrijBericht    | 'PartijTransporteurDatumIngangMorgenDatumEindeLeeg'   |
| ontvangerVrijBericht | 'PartijTransporteurDatumIngangGisterenDatumEindeLeeg' |
| ondertekenaar        | 'PartijTransporteurDatumIngangMorgenDatumEindeLeeg'   |
| transporteur         | 'Partij1DatumIngangGisterenDatumEindeLeeg'            |
| soortNaam            | 'Beheermelding'                                       |
| inhoud               | 'Test'                                                |

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                               |
| R2343 | Er is een autorisatiefout opgetreden. |

Then is er een autorisatiefout gelogd met regelcode R2469

Scenario: 4.    Transporteur (Datum.Ingang gisteren, Datum.Einde vandaag) is ONgeldig
                LT: R2469_LT04
                Verwacht resultaat:
                - Foutief
                - R2343 Er is een autorisatiefout opgetreden.
                - Gelogd met regelcode R2468

Given verzoek verstuur vrij bericht:
| key                  | value                                                    |
| zendendePartijNaam   | 'PartijTransporteurDatumIngangGisterenDatumEindeVandaag' |
| zenderVrijBericht    | 'PartijTransporteurDatumIngangGisterenDatumEindeVandaag' |
| ontvangerVrijBericht | 'PartijTransporteurDatumIngangGisterenDatumEindeLeeg'    |
| ondertekenaar        | 'PartijTransporteurDatumIngangGisterenDatumEindeVandaag' |
| transporteur         | 'Partij6DatumIngangGisterenDatumEindeVandaag'             |
| soortNaam            | 'Beheermelding'                                          |
| inhoud               | 'Test'                                                   |

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                               |
| R2343 | Er is een autorisatiefout opgetreden. |

Then is er een autorisatiefout gelogd met regelcode R2469

Scenario: 5.    Transporteur (Datum.Ingang gisteren, Datum.Einde gisteren) is ONgeldig
                LT: R2469_LT05
                Verwacht resultaat:
                - Foutief
                - R2343 Er is een autorisatiefout opgetreden.
                - Gelogd met regelcode R2469


Given verzoek verstuur vrij bericht:
| key                  | value                                                     |
| zendendePartijNaam   | 'PartijTransporteurDatumIngangGisterenDatumEindeGisteren' |
| zenderVrijBericht    | 'PartijTransporteurDatumIngangGisterenDatumEindeGisteren' |
| ontvangerVrijBericht | 'PartijTransporteurDatumIngangGisterenDatumEindeLeeg'     |
| ondertekenaar        | 'PartijTransporteurDatumIngangGisterenDatumEindeGisteren' |
| transporteur         | 'Partij8DatumIngangGisterenDatumEindeGisteren'            |
| soortNaam            | 'Beheermelding'                                           |
| inhoud               | 'Test'                                                    |

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                               |
| R2343 | Er is een autorisatiefout opgetreden. |

Then is er een autorisatiefout gelogd met regelcode R2469

