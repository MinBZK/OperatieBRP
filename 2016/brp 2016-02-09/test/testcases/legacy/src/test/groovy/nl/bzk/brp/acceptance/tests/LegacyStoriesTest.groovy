package nl.bzk.brp.acceptance.tests
import nl.bzk.brp.funqmachine.AcceptanceTest
import nl.bzk.brp.funqmachine.jbehave.stories.AbstractSpringJBehaveStories
import org.junit.Ignore

/**
 * Basic test for running JBehave stories.
 */
@AcceptanceTest
@Ignore("Testen worden al gedekt door afzonderlijke testklasses")
class LegacyStoriesTest extends AbstractSpringJBehaveStories {

    @Override
    protected List<String> metaFilters() {
        return ['+status Klaar']
    }
}
