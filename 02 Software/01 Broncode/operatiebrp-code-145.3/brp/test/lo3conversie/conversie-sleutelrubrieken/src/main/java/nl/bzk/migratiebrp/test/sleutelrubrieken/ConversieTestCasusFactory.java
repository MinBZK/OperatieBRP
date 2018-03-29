/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.sleutelrubrieken;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.text.DecimalFormat;
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
    private final AutowireCapableBeanFactory migratieAutowireBeanFactory;
    private final AutowireCapableBeanFactory brpLeveringAutowireBeanFactory;
    private final AutowireCapableBeanFactory brpBijhoudingAutowireBeanFactory;
    private final TestSkipper skipper;

    /**
     * Constructor.
     *
     * @param migratieAutowireBeanFactory
     *            spring bean factory (voor migratie beans)
     * @param brpLeveringAutowireBeanFactory
     *            spring bean factory (voor brp beans)
     * @param brpBijhoudingAutowireBeanFactory
     *            spring bean factory voor BRP Bijhouding
     */
    protected ConversieTestCasusFactory(
        final AutowireCapableBeanFactory migratieAutowireBeanFactory,
        final AutowireCapableBeanFactory brpLeveringAutowireBeanFactory,
        final AutowireCapableBeanFactory brpBijhoudingAutowireBeanFactory,
        final TestSkipper skipper)
    {
        this.migratieAutowireBeanFactory = migratieAutowireBeanFactory;
        this.brpLeveringAutowireBeanFactory = brpLeveringAutowireBeanFactory;
        this.brpBijhoudingAutowireBeanFactory = brpBijhoudingAutowireBeanFactory;
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
        final File[] files = input.listFiles(new FileFilter() {
            @Override
            public boolean accept(final File pathname) {
                final String name = pathname.getName();
                return !name.endsWith(".properties")
                       && !"persoon.xls".equals(name)
                       && !"delta.xls".equals(name)
                       && !"bzmBijhouding.xml".equals(name)
                       && !"gbaBijhouding.xls".equals(name);
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
                                    new File(input, testcaseName),
                                    line);

                        testCasus.setSkipper(skipper);
                        migratieAutowireBeanFactory.autowireBean(testCasus.getBeanForMigratieAutowire());
                        brpLeveringAutowireBeanFactory.autowireBean(testCasus.getBeanForBrpLeveringAutowire());
                        brpBijhoudingAutowireBeanFactory.autowireBean(testCasus.getBeanForBrpBijhoudingAutowire());
                        result.add(testCasus);
                    }
                }
            }
        }

        return result;
    }
}
