/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.levering.mutatiebericht;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.test.dal.AbstractTestCasusFactory;
import nl.bzk.migratiebrp.test.dal.TestCasus;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

/**
 * Test casus factory: conversie lo3 naar brp.
 */
public final class LeveringMutatieberichtTestCasusFactory extends AbstractTestCasusFactory {

    private static final Logger LOG = LoggerFactory.getLogger();
    private final AutowireCapableBeanFactory migratieAutowireBeanFactory;
    private final AutowireCapableBeanFactory brpLeveringAutowireBeanFactory;
    private final AutowireCapableBeanFactory brpBijhoudingAutowireBeanFactory;

    /**
     * Constructor.
     *
     * @param migratieAutowireBeanFactory
     *            spring bean factory voor migratie
     * @param brpLeveringAutowireBeanFactory
     *            spring bean factory voor BRP Levering
     * @param brpBijhoudingAutowireBeanFactory
     *            spring bean factory voor BRP Bijhouding
     */
    protected LeveringMutatieberichtTestCasusFactory(
        final AutowireCapableBeanFactory migratieAutowireBeanFactory,
        final AutowireCapableBeanFactory brpLeveringAutowireBeanFactory,
        final AutowireCapableBeanFactory brpBijhoudingAutowireBeanFactory)
    {
        this.migratieAutowireBeanFactory = migratieAutowireBeanFactory;
        this.brpLeveringAutowireBeanFactory = brpLeveringAutowireBeanFactory;
        this.brpBijhoudingAutowireBeanFactory = brpBijhoudingAutowireBeanFactory;
    }

    @Override
    public List<TestCasus> leesTestCasussen(final File input) throws IOException {
        LOG.info("leesTestCasussen(thema={}, input={})", getThema(), input.getName());
        if (!input.isDirectory()) {
            LOG.error("Bestand '{}' is geen directory.", input.getName());
        }

        final List<TestCasus> result = new ArrayList<>();
        final LeveringMutatieberichtTestCasus testCasus =
                new LeveringMutatieberichtTestCasus(getThema(), input.getName(), getOutputFolder(), getExpectedFolder(), input);
        migratieAutowireBeanFactory.autowireBean(testCasus.getBeanForMigratieAutowire());
        brpLeveringAutowireBeanFactory.autowireBean(testCasus.getBeanForBrpLeveringAutowire());
        brpBijhoudingAutowireBeanFactory.autowireBean(testCasus.getBeanForBrpBijhoudingAutowire());

        result.add(testCasus);
        return result;
    }
}
