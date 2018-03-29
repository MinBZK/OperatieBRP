Meta:
@jiraIssue
@status             Bug
@regels
@sleutelwoorden     GBA Indicatie onjuist

Narrative:
Deze test is nog onderhanden in afwachting van de uitkomsten van ROOD-58.

Scenario:   20. DELTAVERS02C10T40 CAT03 Correctie Ouder2
            Issue: INTEST-1782

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding
Given persoonsbeelden uit oranje:DELTAVERS02/DELTAVERS02C10T40_xls
When voor persoon 279574873 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken

Scenario:   26. DELTAVERS02C20T20
            Issue: INTEST-1782

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding
Given persoonsbeelden uit oranje:DELTAVERS02/DELTAVERS02C20T20_xls
When voor persoon 258096329 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken

