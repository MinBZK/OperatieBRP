Meta:

@status             Klaar
@usecase            BV.0.GD
@sleutelwoorden     Geef Details Persoon
@regels             R2400

Narrative:
Voor alle zoekcriteria in een zoekvraag moet gelden dat:
Zoekcriterium.Element mag geen deel uitmaken van een 'Verantwoordingsgroep' (R1541) of een 'Onderzoeksgroep' (R1543).

Scenario: 1.    Geef details persoon scoping op verantwoordingsgroep
                LT: R2400_LT01
                Verwacht resultaat:
                - Foutmelding R2400: De opgegeven scope elementen mogen niet verwijzen naar attributen binnen de onderzoeksgroep of de verantwoordingsgroep.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|scopingElementen|Actie.DatumAanvangGeldigheid

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                      |
| R2400     | De opgegeven scope elementen mogen niet verwijzen naar attributen binnen de onderzoeksgroep of de verantwoordingsgroep.    |

Scenario: 2.    Geef details persoon scoping op onderzoeksgroep
                LT: R2400_LT02
                Verwacht resultaat:
                - Foutmelding R2400: De opgegeven scope elementen mogen niet verwijzen naar attributen binnen de onderzoeksgroep of de verantwoordingsgroep.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|scopingElementen|Onderzoek.ActieVerval

Then heeft het antwoordbericht verwerking Foutief
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                      |
| R2400     | De opgegeven scope elementen mogen niet verwijzen naar attributen binnen de onderzoeksgroep of de verantwoordingsgroep.    |
