Meta:

@status             Klaar
@usecase            LV.0.MB
@regels             R1621
@sleutelwoorden     Maak BRP bericht

Narrative:
Indien het systeem een Volledigbericht of Mutatiebericht verstuurt naar een Afnemer,
dan dient in de logging het aantal geleverde (hoofd)personen vastgelegd te worden.

(Indien simpel mogelijk op een manier waarop dit geëxtraheerd kan worden,
zodat deze cijfers ook in een rapportage gebruikt kunnen worden)

Scenario:   1. Volledig bericht met 1 persoon wordt gelogd
            LT: R1621_LT01
            Verwacht resultaat: persoon Ciske met bsn 700934601 gelogd

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding, autorisatie/Attenderingen_op_gewijzigde_personen

Given persoonsbeelden uit specials:MaakBericht/R1318_ElisaBeth_Verhuizing_Haarlem_Beverwijk_xls
When voor persoon 270433417 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Attenderingen op pers met wijziging is ontvangen en wordt bekeken
!-- Valideer dat de geleverde persoon in de logging is opgenomen
!-- Then is er een DEBUG message gelogd die matcht met regex Aantal te leveren personen voor autorisatie \[Attenderingen op pers met wijziging\] na filtering is\: 1

Scenario:   2. Mutatiebericht met 1 persoonwordt gelogd
            LT: R1621_LT02
            Verwacht resultaat: persoon Ciske met bsn 700934601 gelogd

Given persoonsbeelden uit specials:MaakBericht/R1318_ElisaBeth_Verhuizing_Haarlem_Beverwijk_xls
When voor persoon 270433417 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
!-- Valideer dat de geleverde persoon in de logging is opgenomen
!-- Then is er een DEBUG message gelogd die matcht met regex Aantal te leveren personen voor autorisatie \[Geen pop.bep. levering op basis van doelbinding\] na filtering is\: 1


Scenario:   3. Volledig bericht met meerdere personen
            LT: R1621_LT03
            Verwacht resultaat: personen zijn vastgelegd in logging

Given persoonsbeelden uit BIJHOUDING:VHNL04C10T10/Personen_Libby_Thatcher_(Ingeschrevene-I/dbstate003
When voor persoon 422531881 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Attenderingen op pers met wijziging is ontvangen en wordt bekeken
!-- Valideer dat de geleverde personen in de logging is opgenomen
!-- Then is er een DEBUG message gelogd die matcht met regex Aantal te leveren personen voor autorisatie \[Attenderingen op pers met wijziging\] na filtering is\: 2


Scenario:   4. Mutatie bericht met meerdere personen
            LT: R1621_LT04
            Verwacht resultaat: personen zijn vastgelegd in logging

Given persoonsbeelden uit BIJHOUDING:VHNL04C10T10/Personen_Libby_Thatcher_(Ingeschrevene-I/dbstate003
When voor persoon 422531881 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
!-- Valideer dat de geleverde personen in de logging is opgenomen
!-- Then is er een DEBUG message gelogd die matcht met regex Aantal te leveren personen voor autorisatie \[Geen pop.bep. levering op basis van doelbinding\] na filtering is\: 2