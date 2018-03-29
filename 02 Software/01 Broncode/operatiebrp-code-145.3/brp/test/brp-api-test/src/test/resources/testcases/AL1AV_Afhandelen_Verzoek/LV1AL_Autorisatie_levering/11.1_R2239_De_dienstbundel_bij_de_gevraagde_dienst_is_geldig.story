Meta:
@status             Klaar
@usecase            LV.1.AL
@regels             R2239
@sleutelwoorden     Autorisatie Levering

Narrative:
Als BRP wil ik dat alleen geautoriseerde afnemers toegang hebben tot de BRP


Scenario:   1.  Dienstbundel met datum ingang < Systeemdatum en datum einde > Systeemdatum
                LT: R2239_LT01
                Verwacht resultaat:
                Dienstbundel.Datum Ingang < Systeemdatum
                Dienstbundel.Datum Einde  > Systeemdatum

                Resultaat: Vervolg use case

Given leveringsautorisatie uit autorisatie/R2239/R2239_LT01
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'R2239 Geldigheid Dienstbundel'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd

Scenario:   2.  Dienstbundel met datum ingang  = Systeemdatum en datum einde > Systeemdatum
                LT: R2239_LT02
                Verwacht resultaat:
                Dienstbundel.Datum Ingang = Systeemdatum
                Dienstbundel.Datum Einde > Systeemdatum

                Resultaat: Vervolg use case

Given leveringsautorisatie uit autorisatie/R2239/R2239_LT02
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'R2239 Geldigheid Dienstbundel'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd

Scenario:   3.  Dienstbundel met datum ingang = leeg en datum einde = leeg
                LT: R2239_LT03
                Verwacht resultaat:
                Dienstbundel.Datum Ingang = leeg
                Dienstbundel.Datum Einde  = leeg

                Resultaat: Vervolg use case

Given leveringsautorisatie uit autorisatie/R2239/R2239_LT03
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'R2239 Geldigheid Dienstbundel'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd

Scenario:   4.  Dienstbundel met datum ingang  < Systeemdatum en datum einde  < Systeemdatum
                LT: R2239_LT04, R2057_LT08
                Verwacht resultaat:
                Dienstbundel.Datum Ingang < Systeemdatum
                Dienstbundel.Datum Einde < Systeemdatum

                Foutieve situatie aangetroffen; verwerking blokkeert
                Meldingstekst:	De dienstbundel is niet geldig.


Given leveringsautorisatie uit autorisatie/R2239/R2239_LT04
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'R2239 Geldigheid Dienstbundel'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                         |
| R2343 | De dienstbundel is niet geldig. |

Then is er een autorisatiefout gelogd met regelcode R2239

Scenario:   5.  Dienstbundel met datum ingang  > Systeemdatum en datum einde leeg
                LT: R2239_LT05, R2057_LT07
                Verwacht resultaat:
                Dienstbundel.Datum Ingang > Systeemdatum
                Dienstbundel.Datum Einde > Systeemdatum

                Foutieve situatie aangetroffen; verwerking blokkeert
                Meldingstekst:	De dienstbundel is niet geldig.

Given leveringsautorisatie uit autorisatie/R2239/R2239_LT05
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'R2239 Geldigheid Dienstbundel'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                         |
| R2343 | De dienstbundel is niet geldig. |

Then is er een autorisatiefout gelogd met regelcode R2239

Scenario:   6.  Dienstbundel met datum ingang  < Systeemdatum en datum einde = Systeemdatum
                LT: R2239_LT06, R2057_LT09
                Verwacht resultaat:
                Dienstbundel.Datum Ingang < Systeemdatum
                Dienstbundel.Datum Einde = Systeemdatum

                Foutieve situatie aangetroffen; verwerking blokkeert
                Meldingstekst:	De dienstbundel is niet geldig.

Given leveringsautorisatie uit autorisatie/R2239/R2239_LT06
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'R2239 Geldigheid Dienstbundel'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                         |
| R2343 | De dienstbundel is niet geldig. |

Then is er een autorisatiefout gelogd met regelcode R2239

Scenario: 7.1    Leveren voor geldige dienstenbundel
                LT: R2239_LT07
                Verwacht resultaat
                - Leveren op basis van dienst mutatielevering op basis van doebinding in geldige dienstenbundel

Given leveringsautorisatie uit autorisatie/R2239/R2239_LT07
Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken


Scenario: 7.2    Niet leveren voor ongeldige dienstenbundel
                LT: R2239_LT07
                Verwacht resultaat
                - Niet leveren op basis van dienst synchroniseer persoon in ongeldige dienstenbundel

Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                         |
| R2343 | De dienstbundel is niet geldig. |

Then is er een autorisatiefout gelogd met regelcode R2239
