/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.app;

import nl.bzk.brp.bevraging.AbstractIntegrationTest;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test om de beschikbare scenario's te testen.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class CommandRunnerAppTest extends AbstractIntegrationTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandRunnerAppTest.class);
    private CommandRunnerApp app;

    private Context context;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        context = new ContextBase();
        context.put(ContextParameterNames.AANTAL_PERSOONSLIJSTEN, 1);
        context.put(ContextParameterNames.AANTAL_THREADS, 1);
        context.put(ContextParameterNames.SCENARIO, "Test");
    }

    @Test
    public void verifyScenarioBevraging() {
        app = new CommandRunnerApp("bevragen-jdbc");

        app.run(context);
        Long average = (Long) context.get(ContextParameterNames.AVERAGE_PL_TIME);
        Assert.assertThat(average, Matchers.greaterThanOrEqualTo(0L));
    }


    @Test
    public void verifyScenarioBevragingJpa() {
        app = new CommandRunnerApp("bevragen-jpa");

        app.run(context);
        Long average = (Long) context.get(ContextParameterNames.AVERAGE_PL_TIME);
        Assert.assertThat(average, Matchers.greaterThan(0L));
    }


    @Test
    @Ignore
    public void verifyScenarioBevragingMetHistorieJpa() {
        app = new CommandRunnerApp("bevragen-jpa-historie");

        app.run(context);
        Long average = (Long) context.get(ContextParameterNames.AVERAGE_PL_TIME);
        Assert.assertThat(average, Matchers.greaterThan(0L));
    }

}
