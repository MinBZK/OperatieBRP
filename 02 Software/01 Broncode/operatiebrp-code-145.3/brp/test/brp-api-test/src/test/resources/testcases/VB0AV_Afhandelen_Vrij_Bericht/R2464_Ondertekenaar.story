Meta:
@status             Klaar
@usecase            VB.0.AV
@sleutelwoorden     Afhandelen Vrij Bericht
@regels             R2464

Narrative:
Versie 1:
Als het vrij bericht afkomstig is van een partij die op het BRP-stelsel aangesloten is,
dient de OIN van het PKI overheidscertifictaat dat is gebruikt voor de digitale ondertekening van het bericht te bestaan.

Indien Bericht.Zendende systeem <> 'ISC' geldt dat bij het in behandeling nemen van het vrije bericht
'De ondertekenaar klopt' zoals gedefinieerd door De autorisatie voor het verzenden van een vrij bericht (R2452).

Versie 2:

Het OIN van het PKI overheidscertifictaat dat is gebruikt voor de digitale ondertekening van het bericht dient te bestaan.

Bij het in behandeling nemen van het vrije bericht moet gelden dat 'De ondertekenaar klopt'
zoals gedefinieerd door De autorisatie voor het verzenden van een vrij bericht (R2452).


Scenario:   1.  Ondertekenaar in verzoek bericht is gelijk aan Ondertekenaar in Partij
                LT: R2464_LT01
                Verwacht resultaat:
                - Geslaagd

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijOndertekenaar1'
|zenderVrijBericht|'PartijOndertekenaar1'
|ontvangerVrijBericht|'PartijDatumIngangVandaagDatumEindeMorgen'
|ondertekenaar|'Partij2DatumIngangGisterenDatumEindeLeeg'
|transporteur|'PartijOndertekenaar1'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Geslaagd

Scenario:   2.a Ondertekenaar in Partij is Leeg, Ondertekenaar in Bericht = 'Geautoriseerde verzend partij'
                LT: R2464_LT02
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

Scenario:   2.b Ondertekenaar in Partij is Leeg, Ondertekenaar in Bericht <> 'Geautoriseerde verzend partij' (want zelfde OIN als goed gevuld)
                LT: R2464_LT02
                Verwacht resultaat:
                - Geslaagd; omdat oin gelijk is

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijDatumIngangGisterenDatumEindeLeeg'
|zenderVrijBericht|'PartijDatumIngangGisterenDatumEindeLeeg'
|ontvangerVrijBericht|'PartijDatumIngangVandaagDatumEindeMorgen'
|ondertekenaar|'VerkeerdePartijZelfdeOIN2'
|transporteur|'PartijDatumIngangGisterenDatumEindeLeeg'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Geslaagd

Scenario:   3a. Ondertekenaar in verzoek bericht is NIET gelijk aan Ondertekenaar in Partij (want Leeg in bericht)
                LT: R2464_LT03
                Verwacht resultaat:
                - Foutief
                - R2343 Er is een autorisatiefout opgetreden.
                - Gelogd met regelcode R2464

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijOndertekenaar1'
|zenderVrijBericht|'PartijOndertekenaar1'
|ontvangerVrijBericht|'PartijDatumIngangVandaagDatumEindeMorgen'
|transporteur|'PartijOndertekenaar1'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                               |
| R2343    | Er is een autorisatiefout opgetreden. |

Then is er een autorisatiefout gelogd met regelcode R2464

Scenario:   3b. Ondertekenaar in verzoek bericht is NIET gelijk aan Ondertekenaar in Partij (want Verkeerd gevuld in bericht)
                LT: R2464_LT03
                Verwacht resultaat:
                - Foutief
                - R2343 Er is een autorisatiefout opgetreden.
                - Gelogd met regelcode R2464

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijOndertekenaar1'
|zenderVrijBericht|'PartijOndertekenaar1'
|ontvangerVrijBericht|'PartijDatumIngangVandaagDatumEindeMorgen'
|ondertekenaar|'PartijDatumIngangVandaagDatumEindeMorgen'
|transporteur|'PartijOndertekenaar1'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                               |
| R2343    | Er is een autorisatiefout opgetreden. |

Then is er een autorisatiefout gelogd met regelcode R2464

Scenario:   4.a  Ondertekenaar in Partij is Leeg, Ondertekenaar in Bericht <> 'Geautoriseerde verzend partij' (want verkeerd Leeg)
                LT: R2464_LT04
                Verwacht resultaat:
                - Foutief
                - R2343 Er is een autorisatiefout opgetreden.
                - Gelogd met regelcode R2464

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijDatumIngangGisterenDatumEindeLeeg'
|zenderVrijBericht|'PartijDatumIngangGisterenDatumEindeLeeg'
|ontvangerVrijBericht|'PartijDatumIngangVandaagDatumEindeMorgen'
|transporteur|'PartijDatumIngangGisterenDatumEindeLeeg'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                               |
| R2343    | Er is een autorisatiefout opgetreden. |

Then is er een autorisatiefout gelogd met regelcode R2464

Scenario:   4.b  Ondertekenaar in Partij is Leeg, Ondertekenaar in Bericht <> 'Geautoriseerde verzend partij' (want verkeerd gevuld
                LT: R2464_LT04
                Verwacht resultaat:
                - Foutief
                - R2343 Er is een autorisatiefout opgetreden.
                - Gelogd met regelcode R2464

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijDatumIngangGisterenDatumEindeLeeg'
|zenderVrijBericht|'PartijDatumIngangGisterenDatumEindeLeeg'
|ontvangerVrijBericht|'PartijDatumIngangVandaagDatumEindeMorgen'
|ondertekenaar|'PartijDatumIngangVandaagDatumEindeMorgen'
|transporteur|'PartijDatumIngangGisterenDatumEindeLeeg'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                               |
| R2343    | Er is een autorisatiefout opgetreden. |

Then is er een autorisatiefout gelogd met regelcode R2464

Scenario:   5.  Ondertekenaar vrijbericht heeft geen oin
                LT: R2464_LT05
                Verwacht resultaat:
                - Foutief
                - R2343 Er is een autorisatiefout opgetreden.
                - Gelogd met regelcode R2464

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'ZendePartijVrijBericht'
|zenderVrijBericht|'ZendePartijVrijBericht'
|ontvangerVrijBericht|'PartijDatumIngangVandaagDatumEindeMorgen'
|ondertekenaar|'OndertekenaarVrijberichtOinLeeg'
|transporteur|'TransporteurVrijBericht'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                               |
| R2343    | Er is een autorisatiefout opgetreden. |

Then is er een autorisatiefout gelogd met regelcode R2464

Scenario:   6.  Partij vrij bericht is zelf ondertekenaarvrijber en transporteurvrijber
                LT:
                Verwacht resultaat:
                - Geslaagd

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijZelfOndertekenaarTekenaar'
|zenderVrijBericht|'PartijZelfOndertekenaarTekenaar'
|ontvangerVrijBericht|'PartijDatumIngangVandaagDatumEindeMorgen'
|ondertekenaar|'PartijZelfOndertekenaarTekenaar'
|transporteur|'PartijZelfOndertekenaarTekenaar'
|soortNaam|'Beheermelding'
|inhoud|'Test'

Then heeft het antwoordbericht verwerking Geslaagd