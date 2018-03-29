/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.afnemersindicatie;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.test.dal.AbstractTestCasusFactory;
import nl.bzk.migratiebrp.test.dal.TestCasus;
import nl.bzk.migratiebrp.test.dal.TestSkipper;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

/**
 * Test casus factory: conversie lo3 naar brp.
 */
public final class ConversieTestCasusFactory extends AbstractTestCasusFactory {

    private static final Logger LOG = LoggerFactory.getLogger();
    private final AutowireCapableBeanFactory autowireBeanFactory;
    private final TestSkipper skipper;

    /**
     * Constructor.
     * @param autowireBeanFactory spring bean factory
     */
    protected ConversieTestCasusFactory(final AutowireCapableBeanFactory autowireBeanFactory, final TestSkipper skipper) {
        this.autowireBeanFactory = autowireBeanFactory;
        this.skipper = skipper;
    }

    @Override
    public List<TestCasus> leesTestCasussen(final File input) throws IOException {
        LOG.info("leesTestCasussen(thema={}, input={})", getThema(), input.getName());
        if (!input.isDirectory()) {
            LOG.error("Bestand '{}' is geen directory.", input.getName());
        }

        final List<TestCasus> result = new ArrayList<>();

        // Elke file in deze directory is een testcase.
        // De subdirectories worden eenmalig gebruikt voor initialisatie
        final File[] files = input.listFiles();
        if (files != null) {
            for (final File testcaseFile : files) {
                if (testcaseFile.isFile()) {
                    final TestCasus testCasus =
                            new ConversieTestCasus(
                                    getThema(),
                                    input.getName() + File.separator + testcaseFile.getName(),
                                    getOutputFolder(),
                                    getExpectedFolder(),
                                    testcaseFile);
                    testCasus.setSkipper(skipper);
                    autowireBeanFactory.autowireBean(testCasus);
                    result.add(testCasus);
                }
            }
        }

        return result;
    }
}
