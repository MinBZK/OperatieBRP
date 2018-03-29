/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.preconditie;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3Lg01BerichtWaarde;
import nl.bzk.migratiebrp.test.common.reader.Reader;
import nl.bzk.migratiebrp.test.common.reader.ReaderFactory;
import nl.bzk.migratiebrp.test.dal.AbstractTestCasusFactory;
import nl.bzk.migratiebrp.test.dal.TestCasus;
import nl.bzk.migratiebrp.util.excel.ExcelAdapterException;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * Test casus factory: precondities.
 */
public final class PreconditieTestCasusFactory extends AbstractTestCasusFactory {

    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Constructor.
     * @param context spring bean factory
     */
    protected PreconditieTestCasusFactory(final GenericXmlApplicationContext context) {
        super(context);
        resetDB(LOG);
    }

    @Override
    public List<TestCasus> leesTestCasussen(final File input) throws IOException, ExcelAdapterException {
        LOG.info("leesTestCasussen(thema={}, input={})", getThema(), input.getName());
        if (!input.isFile()) {
            LOG.error("Bestand '{}' is geen bestand.", input.getName());
        }

        final List<TestCasus> result = new ArrayList<>();
        final ReaderFactory readerFactory = getContext().getBean(ReaderFactory.class);
        final AutowireCapableBeanFactory autowireBeanFactory = getContext().getAutowireCapableBeanFactory();

        try {
            if (readerFactory.accept(input)) {
                final Reader reader = readerFactory.getReader(input);
                final List<Lo3Lg01BerichtWaarde> lo3BerichtWaardenLijst = reader.readFileAsLo3CategorieWaarde(input);

                for (int i = 0; i < lo3BerichtWaardenLijst.size(); i++) {
                    final Lo3Lg01BerichtWaarde berichtWaarde = lo3BerichtWaardenLijst.get(i);
                    final List<Lo3CategorieWaarde> lo3CategorieWaarden = berichtWaarde.getLo3CategorieWaardeList();

                    final TestCasus testCasus =
                            new PreconditieTestCasus(
                                    getThema(),
                                    maakNaam(input.getName(), i),
                                    getOutputFolder(),
                                    getExpectedFolder(),
                                    lo3CategorieWaarden);
                    autowireBeanFactory.autowireBean(testCasus);
                    result.add(testCasus);
                }
            }
        } catch (final Lo3SyntaxException lse) {
            final TestCasus testCasus = new PreconditieTestCasus(getThema(), maakNaam(input.getName(), 0), getOutputFolder(), getExpectedFolder(), lse);
            autowireBeanFactory.autowireBean(testCasus);
            result.add(testCasus);
        }
        return result;
    }
}
