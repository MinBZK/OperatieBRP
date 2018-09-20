/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.autorisatie;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Autorisatie;
import nl.bzk.migratiebrp.test.common.autorisatie.AutorisatieReader;
import nl.bzk.migratiebrp.test.common.autorisatie.CsvAutorisatieReader;
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
    private static final int GEFIXEERDE_LENGTE_AFNEMERSIDENTIFICATIE = 6;

    private final AutorisatieReader autorisatieReader = new CsvAutorisatieReader();
    private final AutowireCapableBeanFactory autowireBeanFactory;

    /**
     * Constructor.
     *
     * @param autowireBeanFactory
     *            spring bean factory
     */
    protected ConversieTestCasusFactory(final AutowireCapableBeanFactory autowireBeanFactory) {
        this.autowireBeanFactory = autowireBeanFactory;
    }

    @Override
    public List<TestCasus> leesTestCasussen(final File input) throws IOException {
        LOG.info("leesTestCasussen(thema={}, input={})", getThema(), input.getName());
        if (!input.isFile()) {
            LOG.error("Bestand '{}' is geen bestand.", input.getName());
        }

        if (input.getName().endsWith("NOK")) {
            return new ArrayList<>();
        }

        try (final FileInputStream inputStream = new FileInputStream(input)) {
            final Collection<Lo3Autorisatie> autorisaties = autorisatieReader.read(inputStream);

            final List<TestCasus> testCasussen = new ArrayList<>();
            for (final Lo3Autorisatie autorisatie : autorisaties) {
                // final int versieNr = autorisatie.getAutorisatieStapel().get(0).getInhoud().getVersieNr();
                final String testCasusNaam =
                        maakNaamMetGefixeerdeIdentificatieLengte(
                            input.getName(),
                            autorisatie.getAfnemersindicatie(),
                            GEFIXEERDE_LENGTE_AFNEMERSIDENTIFICATIE);// + "-" + versieNr;
                final TestCasus testCasus = new ConversieTestCasus(getThema(), testCasusNaam, getOutputFolder(), getExpectedFolder(), autorisatie, input);

                autowireBeanFactory.autowireBean(testCasus);
                testCasussen.add(testCasus);
            }

            return testCasussen;
        }
    }

}
