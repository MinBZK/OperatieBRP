Meta:
@status             Klaar
@usecase            SL.0.PA
@sleutelwoorden     Selectie

Narrative:
Indien
Selectietaak.Peilmoment formeel resultaat < Persoon.Tijdstip laatste wijziging GBA-systematiek
dan stopt de verwerking van de Selectietaak en worden de volgende status gegevens gevuld:
Selectietaak.Status = 'Uitvoering afgebroken'
Selectietaak.Status gewijzigd door = 'Systeem'
Selectietaak.Status toelichting = 'Opgegeven peildatum formeel resultaat ligt voor de overgang naar BRP systematiek'
Selectietaak.Status.Datum/tijd registratie = 'Systeemdatum' (R2016)

Scenario:   1.  Peilmoment formeel resultaat kleiner dan Persoon.Tijdstip laatste wijziging GBA-systematiek
                LT: R2723_LT01
                Verwacht resultaat:
                - Per run voor de eerste 4 taken wordt Anne Bakker gevonden, voor de vijfde taak niet vanwege populatiebeperking

Given leveringsautorisatie uit aut/MeerdereToegangLeveringsAutorisiatie
Given een selectierun met de volgende selectie taken:
|id |datplanning | status        |peilmommaterieelresultaat |peilmomformeelresultaat |dienstSleutel |
|1  |vandaag     | Uitvoerbaar   |20151231                  |2015-12-31T23:00:00Z   |ToegangA/DienstC |

Given persoonsbeelden uit specials:specials/Jan_xls
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|606417801|'2017-05-31 T23:59:00Z'

When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar

Then is status transitie:
| selectieTaakId | statusTransitie |
| 1              | [UITVOERBAAR, IN_UITVOERING, UITVOERING_AFGEBROKEN]     |
