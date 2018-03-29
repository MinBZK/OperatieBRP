Meta:
@status             Klaar
@usecase            LV.1.AL
@regels             R2245
@sleutelwoorden     Autorisatie Levering

Narrative:
De Bericht.Zendende partij moet verwijzen naar een Geldig voorkomen stamgegeven op peilmoment (R1284) op 'Systeemdatum' (R2016) in Partij.


Scenario:   1.  PartijRol met datum ingang = gisteren en datum einde = leeg
                LT: R2245_LT01
                Verwacht resultaat:
                Verwerking geslaagd

Given leveringsautorisatie uit autorisatie/R2245_GeldigheidPartijRol/2245.txt
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'R2245 Is partijRol geldig'
|zendendePartijNaam|'PartijRolDatumIngangGisterenDatumEindeNULL'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd

Scenario:   2.  PartijRol met datum ingang = systeem datum en datum einde > systeemdatum
                LT: R2245_LT02
                Verwacht resultaat:
                Verwerking geslaagd


Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'R2245 Is partijRol geldig'
|zendendePartijNaam|'PartijRolDatumIngangVandaagDatumEindeMorgen'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd

Scenario:   3.  PartijRol met datum ingang > systeem datum en datum einde = leeg
                LT: R2245_LT03
                Verwacht resultaat:
                PartijRol.Datum Ingang > systeem datum
                PartijRol.Datum Einde  = leeg

                Foutieve situatie aangetroffen; verwerking blokkeert
                Meldingstekst:	De combinatie partij en rol is niet geldig.


Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'R2245 Is partijRol geldig'
|zendendePartijNaam|'PartijRolDatumIngangMorgenDatumEindeLeeg'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                     |
| R2343 | De combinatie partij en rol is niet geldig. |

Then is er een autorisatiefout gelogd met regelcode R2245

Scenario:   4.  PartijRol met datum ingang < systeem datum en datum einde = systeemdatum
                LT: R2245_LT04
                Verwacht resultaat:
                PartijRol.Datum Ingang < systeem datum
                PartijRol.Datum Einde  = systeem datum

                Foutieve situatie aangetroffen; verwerking blokkeert
                Meldingstekst:	De combinatie partij en rol is niet geldig.

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'R2245 Is partijRol geldig'
|zendendePartijNaam|'PartijRolDatumIngangGisterenDatumEindeVandaag'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                     |
| R2343 | De combinatie partij en rol is niet geldig. |

Then is er een autorisatiefout gelogd met regelcode R2245

Scenario:   5.  PartijRol met datum ingang < systeem datum en datum einde < systeemdatum
                LT: R2245_LT05
                Verwacht resultaat:
                PartijRol.Datum Ingang < systeem datum
                PartijRol.Datum Einde  = systeem datum

                Foutieve situatie aangetroffen; verwerking blokkeert
                Meldingstekst:	De combinatie partij en rol is niet geldig.

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'R2245 Is partijRol geldig'
|zendendePartijNaam|'PartijRolDatumIngangGisterenDatumEindeGisteren'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                     |
| R2343 | De combinatie partij en rol is niet geldig. |

Then is er een autorisatiefout gelogd met regelcode R2245

Scenario:   6.  PartijRol met datum ingang = leeg en datum einde = leeg
                LT: R2245_LT06
                Verwacht resultaat:
                PartijRol.Datum Ingang < Systeemdatum
                PartijRol.Datum Einde  > Systeemdatum
                Foutieve situatie aangetroffen; verwerking blokkeert
                Meldingstekst:	De partij is niet geldig.

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'R2245 Is partijRol geldig'
|zendendePartijNaam|'PartijRolDatumIngangNULLDatumEindeNULL'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                     |
| R2343 | De combinatie partij en rol is niet geldig. |

Then is er een autorisatiefout gelogd met regelcode R2245

Scenario:   7.  PartijRol met datum ingang = 0 en datum einde = leeg
                LT: R2245_LT07
                Verwerking geslaagd


Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'R2245 Is partijRol geldig'
|zendendePartijNaam|'PartijRolDatumIngang0DatumEindeNULL'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd
