Meta:

@status             Klaar
@usecase            LV.1.AL
@regels             R2244
@sleutelwoorden     Autorisatie Levering

Narrative:
Het OIN van het PKI-overheidscertificaat waarmee de beveiligde verbinding is opgezet (de transporteur)
moet verwijzen naar Geldig voorkomen stamgegeven op peilmoment (R1284) op 'Systeemdatum' (R2016) in Partij.

Scenario:   1.  OIN Transporteur Partij geldig, datum Ingang = Leeg, Datum einde = Leeg
                LT: R2244_LT01
                Verwacht resultaat:
                - Foutmelding: R2244
                De transporteur is geen geldige partij.


Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit /levering_autorisaties_nieuw/R2244/oin_ongelijk_geldig_datums_leeg
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|ondertekenaar|'Gemeente Utrecht'
|transporteur|'DatumIngangEnEindeLeegPartij'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                     |
| R2343 | De transporteur is geen geldige partij.     |

Then is er een autorisatiefout gelogd met regelcode R2244

Scenario:   2.  OIN Transporteur Partij geldig, datum Ingang = Gisteren, Datum einde = Morgen
                LT: R2244_LT02
                Verwacht resultaat:
                - verwerking Geslaagd

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit /levering_autorisaties_nieuw/R2244/oin_ongelijk_geldig_datumInganggisteren_datumEindemorgen_geen_ondertektransporteur
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'DatumIngangGisterenDatumEindeMorgenPartij'
|bsn|606417801
|ondertekenaar|'DatumIngangGisterenDatumEindeMorgenPartij'
|transporteur|'DatumIngangGisterenDatumEindeMorgenPartij'

Then heeft het antwoordbericht verwerking Geslaagd

Scenario:   3.  OIN Transporteur Partij geldig, datum ingang = Gisteren, Datum einde = Morgen
                LT: R2244_LT03
                Verwacht resultaat:
                - verwerking Geslaagd

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit /levering_autorisaties_nieuw/R2244/oin_ongelijk_geldig_datumIngangVandaag_datumEindemorgen_geen_ondertektransporteur
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'DatumIngangVandaagDatumEindeMorgenPartij'
|bsn|606417801
|ondertekenaar|'DatumIngangVandaagDatumEindeMorgenPartij'
|transporteur|'DatumIngangVandaagDatumEindeMorgenPartij'

Then heeft het antwoordbericht verwerking Geslaagd

Scenario:   4.  OIN Transporteur Partij ongeldig, datum einde = gisteren
                LT: R2244_LT04
                Verwacht resultaat:
                - Foutmelding: R2244
                De transporteur is geen geldige partij.


Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit /levering_autorisaties_nieuw/R2244/oin_ongelijk_Ongeldig_dat_einde_gisteren
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|ondertekenaar|'Gemeente Utrecht'
|transporteur|'DatumEindeGisterenPartij'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                     |
| R2343 | De transporteur is geen geldige partij.     |

Then is er een autorisatiefout gelogd met regelcode R2244

Scenario:   5.  OIN Transporteur Partij ongeldig, datum ingang = morgen
                LT: R2244_LT05
                Verwacht resultaat:
                - Foutmelding: R2244
                De transporteur is geen geldige partij.


Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit /levering_autorisaties_nieuw/R2244/oin_ongelijk_Ongeldig_dat_ing_morgen
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|ondertekenaar|'Gemeente Utrecht'
|transporteur|'DatumIngangOngeldigPartij'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                     |
| R2343 | De transporteur is geen geldige partij.     |

Then is er een autorisatiefout gelogd met regelcode R2244

Scenario:   6.  OIN Transporteur Partij ongeldig, datum einde = vandaag
                LT: R2244_LT06
                Verwacht resultaat:
                - Foutmelding: R2244
                De transporteur is geen geldige partij.


Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit /levering_autorisaties_nieuw/R2244/oin_ongelijk_Ongeldig_dat_einde_vandaag
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|ondertekenaar|'Gemeente Utrecht'
|transporteur|'DatumEindeVandaagPartij'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                     |
| R2343 | De transporteur is geen geldige partij.     |

Then is er een autorisatiefout gelogd met regelcode R2244