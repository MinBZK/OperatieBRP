/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.db;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;
import javax.sql.DataSource;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3Lg01BerichtWaarde;
import nl.bzk.migratiebrp.test.common.reader.Reader;
import nl.bzk.migratiebrp.test.common.reader.ReaderFactory;
import nl.bzk.migratiebrp.test.common.util.BaseFilter;
import nl.bzk.migratiebrp.test.common.util.FilterType;
import nl.bzk.migratiebrp.test.dal.AbstractTestCasusFactory;
import nl.bzk.migratiebrp.test.dal.TestCasus;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * TestCasusFactory implementatie voor het DB-test project.
 */
public final class DbConversieTestCasusFactory extends AbstractTestCasusFactory {

    private static final Logger LOG = LoggerFactory.getLogger();
    private final Map<String, List<File>> sqlQueriesPerThema = new TreeMap<>();

    @Inject
    private ReaderFactory readerFactory;

    /**
     * Constructor waarin een Spring context meegegeven wordt. Als de hele DB niet gereset hoeft te worden, omdat deze
     * bv al helemaal ingericht is, dan kan er de optie -DnoResetDB=true worden meegegeven aan het java proces.
     * @param context spring context.
     */
    protected DbConversieTestCasusFactory(final GenericXmlApplicationContext context) {
        super(context);
        context.getAutowireCapableBeanFactory().autowireBean(this);
        // Eerst de gehele DB resetten
        resetDB(LOG);
    }

    @Override
    public List<TestCasus> leesTestCasussen(final File input) throws IOException {
        final String thema = getThema();
        final String inputName = input.getName();

        sqlQueriesPerThema.putIfAbsent(thema, leesSqlBestandenBijTestCasus());
        LOG.info("leesTestCasussen(thema={}, input={})", thema, inputName);
        if (!input.isFile()) {
            LOG.error("Bestand '{}' is geen bestand.", input.getName());
        }

        final List<TestCasus> result = new ArrayList<>();
        final AutowireCapableBeanFactory autowireBeanFactory = getContext().getAutowireCapableBeanFactory();

        try {
            if (readerFactory.accept(input)) {
                final Reader reader = readerFactory.getReader(input);
                final List<Lo3Lg01BerichtWaarde> lo3Lg01BerichtWaardes = reader.readFileAsLo3CategorieWaarde(input);

                final TestCasus testCasus =
                        new DbConversieTestCasus(
                                thema,
                                maakNaam(inputName, 0),
                                getOutputFolder(),
                                getExpectedFolder(),
                                lo3Lg01BerichtWaardes,
                                sqlQueriesPerThema);
                autowireBeanFactory.autowireBean(testCasus);
                result.add(testCasus);
            }
        } catch (final Lo3SyntaxException lse) {
            final TestCasus testCasus = new DbConversieTestCasus(thema, maakNaam(inputName, 0), getOutputFolder(), getExpectedFolder(), lse);
            autowireBeanFactory.autowireBean(testCasus);
            result.add(testCasus);
        }
        return result;
    }

    /**
     * Geef de waarde van sync dal data source.
     * @return sync dal data source
     */
    DataSource getSyncDalDataSource() {
        return (DataSource) getContext().getBean("syncDalDataSource");
    }

    private List<File> leesSqlBestandenBijTestCasus() {
        final File themaFolder = getThemaFolder();
        final File sqlInputFolder = new File(themaFolder, "sql");
        final List<File> sqlFiles = asList(sqlInputFolder.listFiles(new BaseFilter(FilterType.FILE)));
        Collections.sort(sqlFiles);

        return sqlFiles;
    }
}
