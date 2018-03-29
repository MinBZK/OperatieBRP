Meta:
@status             Klaar
@usecase            LV.1.AL
@regels             R1261, R1262, R1263, R1264, R2053, R2054, R2055, R2056, R2130, R2016, R2085, R2129
@sleutelwoorden     Authorisatie levering

Narrative:
Bij het in behandeling nemen
van een leveringsverzoek 'bevraging'
moet de Soort dienst van de in het bericht
via berichtparamter dienstIdentificatie opgegeven Dienst
overeenkomen met de Soort dienst zoals die volgt uit het "Soort bericht", volgens de onderstaande afleiding:

lvg_bvgGeefDetailsPersoon => Geef details persoon
lvg_bvgZoekPersoon => Zoek Persoon
lvg_bvgGeefMedebewoners => Geef medebewoners van persoon
Zoek persoon op adresgegevens => Zoek Persoon op Adres

Scenario:   1.  De soort van de opgegeven dienst (4445) correspondeert niet met het gebruikte soort dienst (8) in het bericht
                LT:R2054_LT02
                Verwacht Resultaat:
                - Foutmelding: R2343 Er is een autorisatiefout opgetreden.
                - Logging R2054

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|dienstId|1

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                 |
| R2343    | Er is een autorisatiefout opgetreden.   |

Then is er een autorisatiefout gelogd met regelcode R2054

Scenario: 2.    Zoek Persoon dienst komt niet overeen met soort bericht
                LT: R2054_LT04
                Verwacht Resultaat:
                - Foutmelding: R2343 Er is een autorisatiefout opgetreden.
                - Logging R2054

Given leveringsautorisatie uit autorisatie/Zoek_Persoon

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=854820425
|dienstId|1

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                 |
| R2343    | Er is een autorisatiefout opgetreden.   |

Then is er een autorisatiefout gelogd met regelcode R2054

Scenario: 3.    Geef medebewoners dienst komt niet overeen met soort bericht
                LT: R2054_LT06
                Verwacht Resultaat:
                - Foutmelding: R2343 Er is een autorisatiefout opgetreden.
                - Logging R2054


Given leveringsautorisatie uit autorisatie/Geef_Medebewoners
Given personen uit specials:specials/Jan_xls

Given verzoek geef medebewoners van persoon:
|key|value
|leveringsautorisatieNaam|'Geef Medebewoners'
|zendendePartijNaam|'Gemeente Utrecht'
|administratienummer|'5398948626'
|dienstId|1

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                 |
| R2343    | Er is een autorisatiefout opgetreden.   |

Then is er een autorisatiefout gelogd met regelcode R2054