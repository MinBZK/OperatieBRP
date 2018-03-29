Meta:

@status             Klaar
@sleutelwoorden     Selectie
@regels             R2666


Narrative:
Na de verwerking van de standaard selectiedienst wijzigt de Selectietaak.Status als volgt:

Indien de uitvoering succesvol was

EN

Dienst.Selectieresultaat controleren? is gelijk aan 'Ja'

dan wijzig de status naar Selectietaak.Status = 'Controleren'

Indien de uitvoering succesvol was

EN

Dienst.Selectieresultaat controleren? is gelijk aan 'Nee'

dan wijzig de status naar Selectietaak.Status = 'Te leveren'

Indien de uitvoering is afgebroken

dan wijzig de status naar Selectietaak.Status = 'Uitvoering afgebroken'

Indien tijdens de uitvoering een fout is opgetreden

dan wijzig de status naar Selectietaak.Status = 'Uitvoering mislukt

Scenario:   1.  Uitvoering selectietaak succesvol, Dienst.SelectieresultaatControleren Ja
                LT: R2666_LT01
                Verwacht resultaat:
                - Selectietaak statussen Uitvoerbaar, In Uitvoering, Controleren
                Uitwerking:
                - Na controleren moet een keuze gemaakt worden, goedkeuren vs afkeuren, vandaar niet naar te leveren

Given leveringsautorisatie uit aut/R2666_SelectieresultaatControlerenJa
Given een selectierun met de volgende selectie taken:
| id | datplanning | status  | dienstSleutel                                                 |
| 1  | vandaag     | Uitvoerbaar | R2666_SelectieresultaatControlerenJa |

Given personen uit specials:specials/Jan_xls

When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar

Then is status transitie:
| selectieTaakId | statusTransitie |
| 1              | [UITVOERBAAR, IN_UITVOERING, CONTROLEREN]     |

Scenario:   2.  Uitvoering selectietaak succesvol, Dienst.SelectieresultaatControleren Nee
                LT: R2666_LT02
                Verwacht resultaat:
                - Selectietaak statussen Uitvoerbaar, In Uitvoering, Te leveren

Given leveringsautorisatie uit aut/R2666_SelectieresultaatControlerenNee
Given een selectierun met de volgende selectie taken:
| id | datplanning | status  | dienstSleutel                                                 |
| 1  | vandaag     | Uitvoerbaar | R2666_SelectieresultaatControlerenNee |

Given personen uit specials:specials/Jan_xls

When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
!-- Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen

Then is status transitie:
| selectieTaakId | statusTransitie |
| 1              | [UITVOERBAAR, IN_UITVOERING, TE_LEVEREN]     |
