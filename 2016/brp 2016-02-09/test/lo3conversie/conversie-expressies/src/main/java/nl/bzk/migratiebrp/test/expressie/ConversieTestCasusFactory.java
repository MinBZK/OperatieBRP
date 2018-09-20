/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.expressie;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.text.DecimalFormat;
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
public final class ConversieTestCasusFactory extends AbstractTestCasusFactory {

    private static final Logger LOG = LoggerFactory.getLogger();
    private final AutowireCapableBeanFactory migratieAutowireBeanFactory;
    private final AutowireCapableBeanFactory brpLeveringAutowireBeanFactory;

    /**
     * Constructor.
     *
     * @param migratieAutowireBeanFactory
     *            spring bean factory (voor migratie beans)
     * @param brpLeveringAutowireBeanFactory
     *            spring bean factory (voor brp beans)
     */
    protected ConversieTestCasusFactory(
        final AutowireCapableBeanFactory migratieAutowireBeanFactory,
        final AutowireCapableBeanFactory brpLeveringAutowireBeanFactory)
    {
        this.migratieAutowireBeanFactory = migratieAutowireBeanFactory;
        this.brpLeveringAutowireBeanFactory = brpLeveringAutowireBeanFactory;
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
        final File[] files = input.listFiles(new FileFilter() {
            @Override
            public boolean accept(final File pathname) {
                return pathname.getName().endsWith(".properties") || pathname.getName().contains("NOK") ? false : true;
            }
        });
        if (files != null) {
            for (final File testcaseFile : files) {
                if (testcaseFile.isFile()) {
                    final String name = testcaseFile.getName();
                    final int extension = name.lastIndexOf('.');

                    int counter = 0;
                    for (final String line : Files.readAllLines(testcaseFile.toPath(), Charset.defaultCharset())) {
                        final String testcaseName;
                        if (counter++ == 0) {
                            testcaseName = name;
                        } else {
                            testcaseName = name.substring(0, extension) + "-" + new DecimalFormat("00000").format(counter) + name.substring(extension);
                        }

                        final ConversieTestCasus testCasus =
                                new ConversieTestCasus(
                                    getThema(),
                                    input.getName() + File.separator + testcaseName,
                                    getOutputFolder(),
                                    getExpectedFolder(),
                                    testcaseFile,
                                    new File(input, testcaseName),
                                    line);

                        migratieAutowireBeanFactory.autowireBean(testCasus.getBeanForMigratieAutowire());
                        brpLeveringAutowireBeanFactory.autowireBean(testCasus.getBeanForBrpLeveringAutowire());
                        result.add(testCasus);

                    }

                }
            }
        }

        return result;
    }
}
