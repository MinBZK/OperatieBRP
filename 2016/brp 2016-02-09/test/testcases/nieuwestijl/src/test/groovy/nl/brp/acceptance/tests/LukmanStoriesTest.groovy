package nl.brp.acceptance.tests

import nl.bzk.brp.funqmachine.AcceptanceTest
import nl.bzk.brp.funqmachine.jbehave.stories.AbstractSpringJBehaveStories

/**
 * Basic test for running JBehave stories.
 */
@AcceptanceTest
class LukmanStoriesTest extends AbstractSpringJBehaveStories {

    @Override
    List<String> metaFilters() {
//        ["groovy: sprintnummer == '69' && status == 'Klaar' && auteur == 'rarij'"]
//        ["groovy: sprintnummer == '71' && status == 'Klaar' && auteur == 'rarij'"]
//        ["groovy: epic == 'tralalalala'"]
//        ["groovy: auteur == 'rarij'"]
//        ["groovy: status == ' '"]
//      ["groovy: jiraIssue == 'TEAMBRP-4562'"]
//        ["groovy: regels =~ 'Klad'"]
//        ["groovy: sprintnummer == '99'"]
//        ["groovy: sleutelwoorden =~ 'lukman'"]
    }
}

/**
 @sprintnummer   69
 @epic           Change yyyynn: CorLev - Element tabel
 @auteur         rarij
 @jiraIssue      TEAMBRP-2435, TEAMBRP-2456
 @status         Klaar
 @regels         rarij
 */
