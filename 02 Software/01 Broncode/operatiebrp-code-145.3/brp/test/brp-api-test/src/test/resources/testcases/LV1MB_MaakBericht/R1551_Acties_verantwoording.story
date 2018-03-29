Meta:
@status             Klaar
@usecase            LV.0.MB
@regels             R1551
@sleutelwoorden     Maak BRP bericht

Narrative:
In het resultaat van een levering mogen geen 'Verantwoordingsgroep' (R1541) met een 'Actie' worden opgenomen waarnaar
geen enkele verwijzing (meer) bestaat vanuit een 'Inhoudelijke groep' (R1540) of een 'Onderzoeksgroep' (R1543)
in hetzelfde resultaat.

(Toelichting: dit betekent dat wanneer een Actie alleen groepen heeft geraakt die door autorisatie of een ander
filtermechanisme niet (meer) in het bericht worden opgenomen, de Actie dus niets meer 'verantwoordt' en zelf ook
uit het bericht verwijderd moet worden).


Scenario:   1a   Mutatielevering obv afnemerindicatie. Controle in de verantwoording op administratieve handelingen
                LT: R1551_LT01
                Blob:
                Administratieve handeling 1, Verhuizing van Anna Bakker (Geen autorisatie op adres)
                Verwacht resultaat: Volledig bericht met administratieve handelingen -
                - Geen adres in volledig bericht (want niet geautoriseerd), in administratieve handelingen geen actie die naar verhuizing verwijst.

Given leveringsautorisatie uit autorisatie/Geen_autorisatie_op_adres
Given persoonsbeelden uit specials:MaakBericht/R1551_Anne_Bakker_GBA_Bijhouding_Verhuizing_xls
Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam    | partijNaam         | datumEindeVolgen | datumAanvangMaterielePeriode |tsReg                 | dienstId |
| 595891305 | 'Geen autorisatie op adres' | 'Gemeente Haarlem' |                  |                              |2014-01-01 T00:00:00Z | 1        |

When voor persoon 595891305 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Geen autorisatie op adres is ontvangen en wordt bekeken
Then heeft het bericht 0 groepen 'adres'

!-- Volledige expected bevat een administratieve handeling met naam 'GBA - Bijhouding overig' en twee bijgehouden acties (verwijzend naar inschrijving
!-- en geslachtsnaamcomponent). De actie verwijzend naar actieinhoud van het adres ivm verhuizing is niet opgenomen in de administratieve handeling
Then is het synchronisatiebericht gelijk aan expecteds/R1551_expected_scenario_1a.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario:   1b   Mutatielevering obv afnemerindicatie. Controle in de verantwoording op administratieve handelingen
                LT: R1551_LT01
                 Blob:
                 Administratieve handeling : Verhuizing van Anna Bakker
                 Verwacht resultaat: Volledig bericht met administratieve handelingen -
                 - Adres in volledig bericht, met in administratieve handelingen actie die naar verhuizing verwijst.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:MaakBericht/R1551_Anne_Bakker_GBA_Bijhouding_Verhuizing_xls
Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam                               | partijNaam      | tsReg                 | dienstId |
| 595891305 | 'Geen pop.bep. levering op basis van afnemerindicatie' | 'Gemeente Utrecht' | 2014-01-01 T00:00:00Z | 1        |

When voor persoon 595891305 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then heeft het bericht 2 groepen 'adres'
!-- Volledige expected bevat een administratieve handeling met naam 'GBA - Bijhouding overig' en drie bijgehouden acties (verwijzend naar inschrijving,
!-- geslachtsnaamcomponent, adreswijziging).
Then is het synchronisatiebericht gelijk aan expecteds/R1551_expected_scenario_1b.xml voor expressie //brp:lvg_synVerwerkPersoon




Scenario:   2a   Mutatielevering obv afnemerindicatie. Controle in de verantwoording op administratieve handelingen
                LT: R1551_LT02
                Blob:
                Administratieve handeling : Aanvang onderzoek op adres (geen autorisatie op adres)
                Verwacht resultaat: Volledig bericht met administratieve handelingen -
                - Geen adres in volledig bericht (want niet geautoriseerd), in administratieve handelingen geen actie die naar aanvang onderzoek verwijst.

Given leveringsautorisatie uit autorisatie/Geen_autorisatie_op_adres
Given persoonsbeelden uit specials:MaakBericht/R1551_Anne_Bakker_Onderzoek_Aanvang_xls
Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam    | partijNaam         | tsReg                 | dienstId |
| 595891305 | 'Geen autorisatie op adres' | 'Gemeente Haarlem' | 2014-01-01 T00:00:00Z | 1        |

When voor persoon 595891305 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Geen autorisatie op adres is ontvangen en wordt bekeken
Then heeft het bericht 0 groepen 'adres'
Then heeft het bericht 0 groepen 'onderzoek'
!-- Volledige expected bevat een administratieve handeling met naam 'GBA - Bijhouding actueel' en 1 bijgehouden actie (verwijzend naar inschrijving)
!-- De actie verwijzend naar aanvang onderzoek adres ontbreekt, want adres is niet geautoriseerd.
Then is het synchronisatiebericht gelijk aan expecteds/R1551_expected_scenario_2a.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario:   2b   Mutatielevering obv afnemerindicatie. Controle in de verantwoording op administratieve handelingen
                LT: R1551_LT02
                Blob:
                Administratieve handeling : Aanvang onderzoek op adres (geen autorisatie op adres)
                Verwacht resultaat: Volledig bericht met administratieve handelingen -
                - Geen adres in volledig bericht (want niet geautoriseerd), in administratieve handelingen geen actie die naar aanvang onderzoek verwijst.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:MaakBericht/R1551_Anne_Bakker_Onderzoek_Aanvang_xls
Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam                               | partijNaam      | tsReg                 | dienstId |
| 595891305 | 'Geen pop.bep. levering op basis van afnemerindicatie' | 'Gemeente Utrecht' | 2014-01-01 T00:00:00Z | 1        |

When voor persoon 595891305 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then heeft het bericht 1 groep 'adres'
Then heeft het bericht 1 groep 'onderzoeken'
!-- Volledige expected bevat een administratieve handeling met naam 'GBA - Bijhouding actueel' en 2 bijgehouden acties (verwijzend naar inschrijving en
!-- aanvang onderzoek)
Then is het synchronisatiebericht gelijk aan expecteds/R1551_expected_scenario_2b.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario:   3a   Mutatielevering obv afnemerindicatie. Controle in de verantwoording op administratieve handelingen
                LT: R1551_LT03
                Blob:
                Administratieve handeling 1, Verhuizing (Geen autorisatie op adres)
                Administratieve handeling 2, Aanvang onderzoek op adres
                Verwacht resultaat: Volledig bericht met administratieve handelingen
                -Adres en Onderzoek in bericht, want geautoriseerd. Bijbehorende acties in administratieve handeling in bericht.

Meta:
@sleutelwoorden onderzoek

Given leveringsautorisatie uit autorisatie/Geen_autorisatie_op_adres
Given persoonsbeelden uit specials:MaakBericht/R1551_Anne_Bakker_GBA_Bijhouding_Verhuizing_AanvOnderzoek_xls
Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam    | partijNaam      | tsReg                 | dienstId |
| 595891305 | 'Geen autorisatie op adres' | 'Gemeente Utrecht' | 2014-01-01 T00:00:00Z | 1        |

When voor persoon 595891305 wordt de laatste handeling geleverd


When het mutatiebericht voor leveringsautorisatie Geen autorisatie op adres is ontvangen en wordt bekeken
Then heeft het bericht 0 groepen 'adres'
Then heeft het bericht 0 groepen 'onderzoeken'
!-- Volledige expected bevat een administratieve handeling met 1 bijgehouden actie (verwijzend naar inchrijving). De acties verwijzend naar adres en
!-- aanvang onderzoek op adres zijn niet opgenomen, want geen autorisatie op adres)
Then is het synchronisatiebericht gelijk aan expecteds/R1551_expected_scenario_3a.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario:   3b   Mutatielevering obv afnemerindicatie. Controle in de verantwoording op administratieve handelingen
                LT: R1551_LT03
                Blob:
                Administratieve handeling 1, Verhuizing
                Administratieve handeling 2, Aanvang onderzoek op adres
                Verwacht resultaat: Volledig bericht met administratieve handelingen
                -Adres en Onderzoek in bericht, want geautoriseerd. Bijbehorende acties in administratieve handeling in bericht.

Meta:
@sleutelwoorden onderzoek

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:MaakBericht/R1551_Anne_Bakker_GBA_Bijhouding_Verhuizing_AanvOnderzoek_xls
Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam                               | partijNaam      | tsReg                 | dienstId |
| 595891305 | 'Geen pop.bep. levering op basis van afnemerindicatie' | 'Gemeente Utrecht' | 2014-01-01 T00:00:00Z | 1        |

When voor persoon 595891305 wordt de laatste handeling geleverd


When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then heeft het bericht 3 groepen 'adres'
Then heeft het bericht 1 groep 'onderzoeken'
!-- Volledige expected bevat een administratieve handeling met 3 bijgehouden acties (verwijzend naar inschrijving) en adres en aanvang onderzoek op adres)
Then is het synchronisatiebericht gelijk aan expecteds/R1551_expected_scenario_3b.xml voor expressie //brp:lvg_synVerwerkPersoon