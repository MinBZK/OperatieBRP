Meta:

@status             Klaar
@usecase            LV.1.AL
@regels             R2243
@sleutelwoorden     Autorisatie Levering

Narrative:
Het OIN van het PKI-overheidscertificaat waarmee het bericht is ondertekend (de ondertekenaar)
moet verwijzen naar een Geldig voorkomen stamgegeven op peilmoment (R1284) op 'Systeemdatum' (R2016) in Partij.

Scenario:   1.  OIN Partij geldig, datum Ingang = Leeg, Datum einde = Leeg
                LT: R2243_LT01
                Verwacht resultaat:
                - Foutmelding: R2243
                De ondertekenaar is geen geldige partij.

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/R2243/oin_ongelijk_geldig_datums_leeg
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|ondertekenaar|'DatumIngangEnEindeLeegPartij'
|transporteur|'Gemeente Alkmaar'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                   |
| R2343 | De ondertekenaar is geen geldige partij.  |

!-- Want begindatum leeg mag niet
Then is er een autorisatiefout gelogd met regelcode R2243

Scenario:   2.  OIN Partij geldig, datum Ingang = Gisteren, Datum einde = Morgen
                LT: R2243_LT02
                Verwacht resultaat:
                - verwerking Geslaagd

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/R2243/oin_ongelijk_geldig_datumInganggisteren_datumEindemorgen
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|ondertekenaar|'DatumIngangGisterenDatumEindeMorgenPartij'
|transporteur|'Gemeente Alkmaar'

Then heeft het antwoordbericht verwerking Geslaagd

Scenario:   3.  OIN Partij geldig, datum ingang = vandaag, Datum einde = Morgen
                LT: R2243_LT03
                Verwacht resultaat:
                - verwerking Geslaagd

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/R2243/oin_ongelijk_geldig_datumIngangVandaag_datumEindemorgen
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|ondertekenaar|'DatumIngangVandaagDatumEindeMorgenPartij'
|transporteur|'Gemeente Alkmaar'

Then heeft het antwoordbericht verwerking Geslaagd

Scenario:   4.  OIN Partij ongeldig, datum einde = gisteren
                LT: R2243_LT04
                Verwacht resultaat:
                - Foutmelding: R2243
                  De ondertekenaar is geen geldige partij.


Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/R2243/oin_ongelijk_Ongeldig_dat_einde_gisteren
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|ondertekenaar|'DatumEindeGisterenPartij'
|transporteur|'Gemeente Alkmaar'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                   |
| R2343 | De ondertekenaar is geen geldige partij.  |

Then is er een autorisatiefout gelogd met regelcode R2243

Scenario:   5.  OIN Partij ongeldig, datum ingang = morgen
                LT: R2243_LT05
                Verwacht resultaat:
                - Foutmelding: R2243
                  De ondertekenaar is geen geldige partij.


Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/R2243/oin_ongelijk_Ongeldig_dat_ing_morgen
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|ondertekenaar|'DatumIngangOngeldigPartij'
|transporteur|'Gemeente Alkmaar'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                   |
| R2343 | De ondertekenaar is geen geldige partij.  |

Then is er een autorisatiefout gelogd met regelcode R2243

Scenario:   6.  OIN Partij ongeldig, datum einde = vandaag
                LT: R2243_LT06
                Verwacht resultaat:
                - Foutmelding: R2243
                  De ondertekenaar is geen geldige partij.


Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/R2243/oin_ongelijk_Ongeldig_dat_einde_vandaag
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|ondertekenaar|'DatumEindeVandaagPartij'
|transporteur|'Gemeente Alkmaar'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                   |
| R2343 | De ondertekenaar is geen geldige partij.  |

Then is er een autorisatiefout gelogd met regelcode R2243