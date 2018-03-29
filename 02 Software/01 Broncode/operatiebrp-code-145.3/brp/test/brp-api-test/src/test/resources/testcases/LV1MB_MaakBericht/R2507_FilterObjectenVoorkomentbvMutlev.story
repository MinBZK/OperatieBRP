Meta:
@status             Klaar
@usecase            LV.0.MB
@regels             R2507

Narrative:

Geen objecten 'ten behoeve van mutatielevering' leveren in andere berichtsoorten

Als object waarbij geldt dat Object bestaat alleen ten behoeve van mutatielevering (R2509), mag alleen worden opgenomen in een "Mutatiebericht"

Noot: Indien een object niet wordt opgenomen in het bericht geldt dat natuurlijk ook voor alle subobjecten in het bericht.
Dus als bijvoorbeeld de eigen betrokkenheid niet wordt opgenomen,
dan verdwijnt die hele relatie en de gerelateerde persoon uit het bericht.

Scenario:   1. Volledig bericht persoon met vervallen ouders
               LT: R2507_LT05
               Verwacht Resultaat:
               Omdat er een voorkomen van Persoon.ouderschap in de structuur bestaat waarbij IndicatievoorkomentbvMutlev = NULL
               Blijft de ouder in het bericht aanwezig

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding
Given persoonsbeelden uit specials:MaakBericht/R2507_Persoon_vervallen_ouder_xls

When voor persoon 270433417 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken

Then is het synchronisatiebericht gelijk aan expecteds/R2507_scenario_1.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario:   2. Volledig bericht persoon met vervallen ouder (Indicatie Onjuist) en nieuwe ouder opgevoerd
               LT: R2507_LT02
               Verwacht Resultaat:
               Geen objecten / voorkomens / attributen van vervallen ouder het volledig bericht
               Wel objecten / voorkomens / attributen van nieuwe actuele ouder in het bericht


Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding
Given persoonsbeelden uit specials:MaakBericht/R2507_Persoon_ouder_vervallen_nieuwe_opgevoerd_xls

When voor persoon 270433417 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken

Then is het synchronisatiebericht gelijk aan expecteds/R2507_scenario_2.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario:   3. Volledig bericht persoon met vervallen partner (Indicatie Onjuist) en nieuwe partner opgevoerd
               LT: R2507_LT04
               Verwacht Resultaat:
               Geen objecten / voorkomens / attributen van het vervallen partner het volledig bericht
               Wel objecten / voorkomens / attributen van nieuwe actuele partner in het bericht

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding
Given persoonsbeelden uit specials:MaakBericht/R2507_Persoon_partner_vervallen_nieuwe_opgevoerd_xls

When voor persoon 270433417 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken

Then is het synchronisatiebericht gelijk aan expecteds/R2507_scenario_3.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario:   4. Volledig bericht persoon met vervallen kind (Indicatie Onjuist) en nieuw kind opgevoerd
               LT: R2507_LT06
               Verwacht Resultaat:
               Geen objecten / voorkomens / attributen van het vervallen kind het volledig bericht
               Wel objecten / voorkomens / attributen van het nieuwe actuele kind in het bericht

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding
Given persoonsbeelden uit specials:MaakBericht/R2507_Persoon_kind_vervallen_nieuwe_opgevoerd_xls

When voor persoon 270433417 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken

Then is het synchronisatiebericht gelijk aan expecteds/R2507_scenario_4.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario:   5. Volledig bericht persoon met vervallen kind
               LT: R2507_LT03
               Verwacht Resultaat:
               Geen objecten / voorkomens / attributen van de vervallen partner in het volledig bericht
               GerelateerdeKind object heeft enkel voorkomens met IndicatieVoorkomenTbvLeveringMutaties = True


Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding
Given persoonsbeelden uit specials:MaakBericht/R2507_Persoon_kind_vervallen_xls

When voor persoon 270433417 wordt de laatste handeling geleverd

!-- GerelateerdeKind object niet in synchronisatie bericht
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken

Then is het synchronisatiebericht gelijk aan expecteds/R2507_scenario_5.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario:   6. Volledig bericht persoon met vervallen partner
               LT: R2507_LT01
               Verwacht Resultaat:
               Geen objecten / voorkomens / attributen van het vervallen kind in het volledig bericht
               Persoon.Partner object heeft enkel voorkomens met IndicatieVoorkomenTbvLeveringMutaties = True

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding
Given persoonsbeelden uit specials:MaakBericht/R2507_Persoon_partner_vervallen_xls

When voor persoon 270433417 wordt de laatste handeling geleverd

!-- GerelateerdeParnter object niet in synchronisatie bericht
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken

Then is het synchronisatiebericht gelijk aan expecteds/R2507_scenario_6.xml voor expressie //brp:lvg_synVerwerkPersoon
