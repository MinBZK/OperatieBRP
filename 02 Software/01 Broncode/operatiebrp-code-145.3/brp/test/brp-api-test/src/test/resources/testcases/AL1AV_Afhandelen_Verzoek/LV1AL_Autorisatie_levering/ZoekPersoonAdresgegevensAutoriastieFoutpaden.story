Meta:

@status             Klaar
@usecase            BV.0.ZA
@sleutelwoorden     Zoek Persoon op adres

Narrative:
Foutpaden voor Zoek persoon op adresgegevens

Scenario:   1.  Leveringsautorisatie bevat dienst niet.
                LT: R2130_LT20
                Verwacht resultaat:
                - 1. Foutief bericht met de melding "De opgeven leveringsautorisatie bevat niet de opgegeven dienst"
                Uitwerking:
                - twee leveringsautorisatie ingeladen. 1 met 4 diensten, waarvan de 4e zoek persoon op adres is
                - de andere leveringsautorisatie heeft dezelfde eerste 3 diensten, maar niet de vierde: zoek persoon op adres
                - Zoeken op dienst id 4 (zoek persoon op adres)

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/doelbinding_met_zoek_persoon_op_adres, autorisatie/doelbinding_zonder_zoek_persoon_op_adres

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'doelbinding zonder zoek persoon op adres'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Postcode,Waarde=1422RZ
|dienstId|4

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                        |
| R2343    | De opgeven leveringsautorisatie bevat niet de opgegeven dienst |

Then is er een autorisatiefout gelogd met regelcode R2130

Scenario:   2. De gevraagde dienst is niet geldig, datumingang is groter dan de systeemdatum
            LT: R1262_LT24
            Dienst: Zoek Persoon op adresgegevens
            Verwacht resultaat: Response bericht met vulling
            - De gevraagde dienst is niet geldig

Given leveringsautorisatie uit /levering_autorisaties_nieuw/R1262/Zoek_Persoon_op_adresgegevens_datumingang_ongeldig
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon op adresgegevens datumingang ongeldig'
|zendendePartijNaam|'Gemeente Haarlem'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.GemeenteCode,Waarde=0626

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                            |
| R2343    | De gevraagde dienst is niet geldig.                |

Then is er een autorisatiefout gelogd met regelcode R1262

Scenario:   3. De gevraagde dienst is geblokkeerd.
            LT: R1264_LT20
            Dienst: Zoek Persoon op adres
                Verwacht resultaat: Response bericht met vulling
                - De gevraagde dienst is geblokkeerd door de beheerder

Given leveringsautorisatie uit /levering_autorisaties_nieuw/R1264/Zoek_Persoon_op_adres_dienst_geblokkeerd
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon op adres dienst geblokkeerd'
|zendendePartijNaam|'Gemeente Haarlem'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Postcode,Waarde=1422RZ

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                               |
| R2343    | De gevraagde dienst is geblokkeerd door de beheerder. |

Then is er een autorisatiefout gelogd met regelcode R1264

Scenario:   4.  De soort van de opgegeven dienst correspondeert niet met het gebruikte soort dienst 'Zoek Persoon op Adres' in het bericht
                LT:R2054_LT08
                Verwacht resultaat: Response bericht met vulling
                Foutmelding
                Meldingsniveau:
                Foutieve situatie aangetroffen; verwerking blokkeert
                Meldingstekst:
                De opgegeven dienst komt niet overeen met het soort bericht
                Loggingsniveau:
                Illegale poging

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|dienstId|1
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Huisnummer,Waarde=16
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Postcode,Waarde=2252EB

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                       |
| R2343    | De opgegeven dienst komt niet overeen met het soort bericht   |

Then is er een autorisatiefout gelogd met regelcode R2054

Scenario: 5.    De opgegeven dienst (zoek persoon op adresgegevens) bestaat niet en dient te bestaan
                LT: R2055_LT06
                Verwacht resultaat: De gevraagde dienst bestaat niet

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding_Haarlem
Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding Haarlem'
|zendendePartijNaam|'Gemeente Haarlem'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Postcode,Waarde=1422RZ
|dienstId|9999

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                           |
| R2343    | De opgegeven dienst bestaat niet  |

Then is er een autorisatiefout gelogd met regelcode R2055

Scenario:   6. De gevraagde dienst is geblokkeerd.
            LT: R2056_LT20
            Dienst: Zoek Persoon
                Verwacht resultaat: Response bericht met vulling
                - De dienstbundel van de opgegeven dienst is geblokkeerd door de beheerder.

Given leveringsautorisatie uit /levering_autorisaties_nieuw/R2056/Zoek_Persoon_op_adres_dienstbundel_geblokkeerd
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon op adresgegevens dienstbundel geblokkeerd'
|zendendePartijNaam|'Gemeente Haarlem'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Postcode,Waarde=1422RZ

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                                    |
| R2343    | De dienstbundel van de opgegeven dienst is geblokkeerd door de beheerder.  |

Then is er een autorisatiefout gelogd met regelcode R2056

Scenario:   7. R2239 Ongeldige Dienstbundel
               LT: R2239_LT04
               Verwacht Resultaat:
               Foutieve situatie aangetroffen; verwerking blokkeert
               Meldingstekst:	De dienstbundel is niet geldig.

Given leveringsautorisatie uit autorisatie/R2239/ZoekPersoonAdresDienstbundelOngeldig
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Ongeldige dienstbundel zoek persoon adres'
|zendendePartijNaam|'Gemeente Haarlem'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Postcode,Waarde=1422RZ

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                         |
| R2343 | De dienstbundel is niet geldig. |

Then is er een autorisatiefout gelogd met regelcode R2239

Scenario:   8.  R2242 Ongeldige Partij
                LT: R2242_LT05
                Verwacht resultaat:

                Foutieve situatie aangetroffen; verwerking blokkeert
                Meldingstekst:	De partij is niet geldig.

Given leveringsautorisatie uit autorisatie/R2242_GeldigheidPartij/R2242.txt
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'R2242 Is partij geldig'
|zendendePartijNaam|'GemeenteDatumIngangDatumEindeInVerleden'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Postcode,Waarde=1422RZ

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                  |
| R2343 | De partij is niet geldig |

Then is er een autorisatiefout gelogd met regelcode R2242

Scenario:   9.  R2243 Ongeldige Ondertekenaar
                LT: R2243_LT04
                Verwacht resultaat:
                - Foutmelding: R2243
                  De ondertekenaar is geen geldige partij.

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/R2243/oin_ongelijk_Ongeldig_dat_einde_gisteren
Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Postcode,Waarde=1422RZ
|ondertekenaar|'DatumEindeGisterenPartij'
|transporteur|'Gemeente Alkmaar'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                   |
| R2343 | De ondertekenaar is geen geldige partij.  |

Then is er een autorisatiefout gelogd met regelcode R2243


Scenario:   10.  R2244 Ongeldige transporteur
                LT: R2244_LT04
                Verwacht resultaat:
                - Foutmelding: R2244
                De transporteur is geen geldige partij.


Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit /levering_autorisaties_nieuw/R2244/oin_ongelijk_Ongeldig_dat_einde_gisteren
Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Autorisatietest'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Postcode,Waarde=1422RZ
|ondertekenaar|'Gemeente Utrecht'
|transporteur|'DatumEindeGisterenPartij'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                     |
| R2343 | De transporteur is geen geldige partij.     |

Then is er een autorisatiefout gelogd met regelcode R2244

Scenario:   11.  R2245 Ongeldige PartijRol
                LT: R2245_LT04
                Verwacht resultaat:

                Foutieve situatie aangetroffen; verwerking blokkeert
                Meldingstekst:	De combinatie partij en rol is niet geldig.

Given leveringsautorisatie uit autorisatie/R2245_GeldigheidPartijRol/2245.txt
Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'R2245 Is partijRol geldig'
|zendendePartijNaam|'PartijRolDatumIngangGisterenDatumEindeVandaag'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Postcode,Waarde=1422RZ

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                     |
| R2343 | De combinatie partij en rol is niet geldig. |

Then is er een autorisatiefout gelogd met regelcode R2245