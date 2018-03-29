Meta:
@status             Klaar
@usecase            LV.1.AL
@regels             R2242
@sleutelwoorden     Autorisatie Levering

Narrative:
De Bericht.Zendende partij moet verwijzen naar een Geldig voorkomen stamgegeven op peilmoment (R1284) op 'Systeemdatum' (R2016) in Partij.

Scenario:   1.  Partij met datum ingang kleinderdan Systeemdatum en datum einde LEEG
                LT: R2242_LT01
                Verwacht resultaat:
                1. Valide bericht met verwerking Geslaagd

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit /levering_autorisaties_nieuw/oin_gelijk
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd

Scenario:   2.  Partij met datum ingang = Systeemdatum en datum einde groterdan Systeemdatum
                LT: R2242_LT02
                Verwacht resultaat:
                Partij.Datum Ingang = Systeemdatum
                Partij.Datum Einde  groterdan Systeemdatum

                Vervolg use case

Given leveringsautorisatie uit autorisatie/R2242_GeldigheidPartij/R2242.txt
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'R2242 Is partij geldig'
|zendendePartijNaam|'DatumIngangVandaagDatumEindeMorgenPartij'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd

Scenario:   3.  Partij met datum ingang groterdan Systeemdatum en datum einde groterdan Systeemdatum
                LT: R2242_LT03
                Verwacht resultaat:
                Partij.Datum Ingang groterdan Systeemdatum
                Partij.Datum Einde  groterdan Systeemdatum

                Foutieve situatie aangetroffen; verwerking blokkeert
                Meldingstekst:	De partij is niet geldig.

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'R2242 Is partij geldig'
|zendendePartijNaam|'DatumIngangOngeldigPartij'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                  |
| R2343 | De partij is niet geldig |

Then is er een autorisatiefout gelogd met regelcode R2242

Scenario:   4.  Partij met datum ingang kleinderdan Systeemdatum en datum einde = Systeemdatum
                LT: R2242_LT04
                Verwacht resultaat:
                Partij.Datum Ingang kleinderdan Systeemdatum
                Partij.Datum Einde  = Systeemdatum

                Foutieve situatie aangetroffen; verwerking blokkeert
                Meldingstekst:	De partij is niet geldig.

Given leveringsautorisatie uit autorisatie/R2242_GeldigheidPartij/R2242.txt
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'R2242 Is partij geldig'
|zendendePartijNaam|'DatumEindeVandaagPartij'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                  |
| R2343 | De partij is niet geldig |

Then is er een autorisatiefout gelogd met regelcode R2242


Scenario:   5.  Partij met datum ingang kleinderdan Systeemdatum en datum einde kleinderdan Systeemdatum
                LT: R2242_LT05
                Verwacht resultaat:
                Partij.Datum Ingang kleinderdan Systeemdatum
                Partij.Datum Einde  kleinderdan Systeemdatum

                Foutieve situatie aangetroffen; verwerking blokkeert
                Meldingstekst:	De partij is niet geldig.


Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'R2242 Is partij geldig'
|zendendePartijNaam|'GemeenteDatumIngangDatumEindeInVerleden'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                  |
| R2343 | De partij is niet geldig |

Then is er een autorisatiefout gelogd met regelcode R2242


Scenario:   6.  Partij met datum ingang = leeg en datum einde = leeg
                LT: R2242_LT06
                Verwacht resultaat:
                Partij.Datum Ingang = leeg
                Partij.Datum Einde  = leeg

                Foutieve situatie aangetroffen; verwerking blokkeert
                Meldingstekst:	De partij is niet geldig.

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'R2242 Is partij geldig'
|zendendePartijNaam|'DatumIngangEnEindeLeegPartij'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                  |
| R2343 | De partij is niet geldig |

Then is er een autorisatiefout gelogd met regelcode R2242

Scenario:   7.  Partij met datum ingang = 0 en datum einde = leeg
                LT: R2242_LT07
                Verwacht resultaat:
                Partij.Datum Ingang = 0
                Partij.Datum Einde  = leeg

                Foutieve situatie aangetroffen; verwerking blokkeert
                Meldingstekst:	De partij is niet geldig.

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'R2242 Is partij geldig'
|zendendePartijNaam|'DatumIngangNulEnEindeLeegPartij'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd

Scenario:   8.  Partij met datum ingang groterdan datum einde
                Verwacht resultaat:
                Partij.Datum Ingang = systeemdatum
                Partij.Datum Einde  = gisteren

                Foutieve situatie aangetroffen; verwerking blokkeert
                Meldingstekst:	De partij is niet geldig.

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'R2242 Is partij geldig'
|zendendePartijNaam|'DatumIngangGroterDanDatumEindePartij'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                  |
| R2343 | De partij is niet geldig |

Then is er een autorisatiefout gelogd met regelcode R2242