package nl.brp.acceptance.tests

import nl.bzk.brp.funqmachine.AcceptanceTest
import nl.bzk.brp.funqmachine.jbehave.stories.AbstractSpringJBehaveStories

/**
 * Basic test for running JBehave stories.
 */
@AcceptanceTest
class RamonStoriesTest extends AbstractSpringJBehaveStories {

    @Override
    List<String> metaFilters() {
        ["groovy: jiraIssue =~ 'TEAMBRP-3818'"]
    }
}
