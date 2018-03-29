Meta:
@status             Klaar
@usecase            VB.0.AV
@sleutelwoorden     Afhandelen Vrij Bericht
@regels             R2467

Narrative:

Versie 1:
Als het vrij bericht afkomstig is van een partij die op het BRP-stelsel aangesloten is,
dient de OIN van het PKI overheidscertifictaat dat is gebruikt voor het opzetten van de beveiligde verbinding te bestaan.

Indien Bericht.Zendende systeem <> 'ISC' geldt dat bij het in behandeling nemen van het vrije bericht
'De transporteur klopt' zoals gedefinieerd door De autorisatie voor het verzenden van een vrij bericht (R2452).

Versie 2:
Het OIN van het PKI overheidscertifictaat dat is gebruikt voor het opzetten van de beveiligde verbinding dient te bestaan.

Bij het in behandeling nemen van het vrije bericht moet gelden dat 'De transporteur klopt'
zoals gedefinieerd door De autorisatie voor het verzenden van een vrij bericht (R2452).

Scenario:   1.  Transporteur in verzoek bericht is gelijk aan Transporteur in Partij
                LT: R2467_LT01
                Verwacht resultaat:
                - Geslaagd

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijTransporteur1'
|zenderVrijBericht|'PartijTransporteur1'
|ontvangerVrijBericht|'PartijDatumIngangVandaagDatumEindeMorgen'
|ondertekenaar|'PartijTransporteur1'
|transporteur|'PartijTransporteur1'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Geslaagd

Scenario:   2.  Transporteur in Partij is Leeg, Transporteur in Bericht = 'Geautoriseerde verzend partij'
                LT: R2467_LT02
                Verwacht resultaat:
                - Geslaagd

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijDatumIngangGisterenDatumEindeLeeg'
|zenderVrijBericht|'PartijDatumIngangGisterenDatumEindeLeeg'
|ontvangerVrijBericht|'PartijDatumIngangVandaagDatumEindeMorgen'
|ondertekenaar|'PartijDatumIngangGisterenDatumEindeLeeg'
|transporteur|'PartijDatumIngangGisterenDatumEindeLeeg'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Geslaagd


Scenario:   3a. Transporteur in verzoek bericht is NIET gelijk aan Transporteur in Partij (want Leeg in bericht)
                LT: R2467_LT03
                Verwacht resultaat:
                - Foutief
                - R2343 Er is een autorisatiefout opgetreden.
                - Gelogd met regelcode R2467

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijTransporteur1'
|zenderVrijBericht|'PartijTransporteur1'
|ontvangerVrijBericht|'PartijDatumIngangVandaagDatumEindeMorgen'
|ondertekenaar|'PartijTransporteur1'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                               |
| R2343    | Er is een autorisatiefout opgetreden. |

Then is er een autorisatiefout gelogd met regelcode R2467

Scenario:   3b. Transporteur in verzoek bericht is NIET gelijk aan Transporteur in Partij (want Verkeerd gevuld in bericht)
                LT: R2467_LT03
                Verwacht resultaat:
                - Foutief
                - R2343 Er is een autorisatiefout opgetreden.
                - Gelogd met regelcode R2467

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijTransporteur1'
|zenderVrijBericht|'PartijTransporteur1'
|ontvangerVrijBericht|'PartijDatumIngangVandaagDatumEindeMorgen'
|ondertekenaar|'PartijTransporteur1'
|transporteur|'PartijDatumIngangVandaagDatumEindeMorgen'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                               |
| R2343    | Er is een autorisatiefout opgetreden. |

Then is er een autorisatiefout gelogd met regelcode R2467

Scenario:   4.a  Transporteur in Partij is Leeg, Transporteur in Bericht <> 'Geautoriseerde verzend partij' (want verkeerd Leeg)
                LT: R2467_LT04
                Verwacht resultaat:
                - Foutief
                - R2343 Er is een autorisatiefout opgetreden.
                - Gelogd met regelcode R2467

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijTransporteur1'
|zenderVrijBericht|'PartijTransporteur1'
|ontvangerVrijBericht|'PartijDatumIngangVandaagDatumEindeMorgen'
|ondertekenaar|'PartijTransporteur1'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                               |
| R2343    | Er is een autorisatiefout opgetreden. |

Then is er een autorisatiefout gelogd met regelcode R2467

Scenario:   4.b  Transporteur in Partij is Leeg, Transporteur in Bericht <> 'Geautoriseerde verzend partij' (want verkeerd gevuld
                LT: R2467_LT04
                Verwacht resultaat:
                - Foutief
                - R2343 Er is een autorisatiefout opgetreden.
                - Gelogd met regelcode R2467

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijTransporteur1'
|zenderVrijBericht|'PartijTransporteur1'
|ontvangerVrijBericht|'PartijDatumIngangVandaagDatumEindeMorgen'
|ondertekenaar|'PartijTransporteur1'
|transporteur|'PartijDatumIngangVandaagDatumEindeMorgen'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                               |
| R2343    | Er is een autorisatiefout opgetreden. |

Then is er een autorisatiefout gelogd met regelcode R2467

Scenario:   5.  Transporteur vrijbericht heeft geen oin
                LT: R2467_LT05
                Verwacht resultaat:
                - Foutief
                - R2343 Er is een autorisatiefout opgetreden.
                - Gelogd met regelcode R2467

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'ZendePartijVrijBerichtR2467'
|zenderVrijBericht|'ZendePartijVrijBerichtR2467'
|ontvangerVrijBericht|'PartijDatumIngangVandaagDatumEindeMorgen'
|ondertekenaar|'OndertekenaarVrijbericht'
|transporteur|'TransporteurVrijBerichtGeenOin'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                               |
| R2343    | Er is een autorisatiefout opgetreden. |

Then is er een autorisatiefout gelogd met regelcode R2467