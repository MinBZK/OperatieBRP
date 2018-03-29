/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.test.dal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Arrays;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.IDatabaseConnection;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

/**
 * Primair toegangspunt voor DBUnit-functies vanuit een test. Is nodig bij gebruik van DBUnit in een Spring-context.
 *
 * De {@link TestExecutionListener} dient in een (super class van) de unit test class geregistreerd te worden als
 * TestExecutionListener, zodat we weten in welke context de methodes {@link DBUnit#insertBefore(IDatabaseConnection)}
 * en {@link #verify()} wordt aangeroepen.
 */
public final class DBUnit {

    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Annotatie waarmee wordt aangegeven welke data in de database ge-insert moet worden voordat de unittest uitgevoerd
     * wordt.
     *
     * Alleen classpath-locaties zijn toegestaan; de classpath-URL wordt relatief aan de Test Class geresolved. Als
     * dataset wordt het DBUnit Flat-XML formaat ondersteund.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public static @interface InsertBefore {
        String[] value();
    }

    /**
     * Annotatie waarmee wordt aangegeven welke data <b>in ieder geval</b> aanwezig dient te zijn in de database, na het
     * uitvoeren van de unittest.
     *
     * Let op: er wordt dus niet gecontroleerd of er naast de aangegeven 'expected' data geen andere gegevens zijn
     * gevonden. Dit kan wel worden afgedwongen middels het toepassen van de annotation {@link NotExpectedAfter}.
     *
     * Alleen classpath-locaties zijn toegestaan; de classpath-URL wordt relatief aan de Test Class geresolved. Als
     * dataset wordt het DBUnit Flat-XML formaat ondersteund.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public static @interface ExpectedAfter {
        String[] value();
    }

    /**
     * Annotatie waarmee wordt aangegeven welke data <b>in ieder geval niet</b> aanwezig mag zijn in de database, na het
     * uitvoeren van de unittest.
     *
     *
     * Alleen classpath-locaties zijn toegestaan; de classpath-URL wordt relatief aan de Test Class geresolved. Als
     * dataset wordt het DBUnit Flat-XML formaat ondersteund.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public static @interface NotExpectedAfter {
        String[] value();
    }

    private DBUnit() {
    }

    public static IDatabaseConnection createConnection() throws SQLException, DatabaseUnitException {
        return StaticContext.dbunit.createConnection();
    }

    public static void resetSequences(final IDatabaseConnection connection) throws SQLException, DatabaseUnitException {
        try {
            StaticContext.assertState();
            StaticContext.dbunit.resetSequences(connection);
        } catch (final Exception e) {
            LOG.error("", e);
            throw e;
        }
    }

    /**
     * Zet de waarde van stamgegevens sequences.
     *
     * @param connection
     *            stamgegevens sequences
     * @throws SQLException
     *             the SQL exception
     * @throws DatabaseUnitException
     *             the database unit exception
     */
    public static void setStamgegevensSequences(final IDatabaseConnection connection) throws SQLException, DatabaseUnitException {
        try {
            StaticContext.assertState();
            StaticContext.dbunit.setStamgegevensSequences(connection);
        } catch (final Exception e) {
            LOG.error("", e);
            throw e;
        }
    }

    public static void truncateTables(final IDatabaseConnection connection) throws SQLException, DatabaseUnitException {
        try {
            StaticContext.assertState();
            StaticContext.dbunit.truncateTables(connection);
        } catch (final Exception e) {
            LOG.error("", e);
            throw e;
        }
    }

    public static void setInMemory() {
        StaticContext.assertState();
        StaticContext.setInMemory();
    }

    /**
     * Verifieert de aannames die middels annotations op de unit test zijn geplaatst.
     *
     * @see DBUnit.ExpectedAfter
     * @see DBUnit.NotExpectedAfter
     * @throws SQLException
     * @throws DatabaseUnitException
     */
    public static void verify() throws DatabaseUnitException, SQLException {
        StaticContext.assertState();
        final Method method = StaticContext.currentTestMethod;
        final Class<?> testClass = method.getDeclaringClass();

        final ExpectedAfter expectedData = method.getAnnotation(ExpectedAfter.class);
        final NotExpectedAfter notExpectedData = method.getAnnotation(NotExpectedAfter.class);
        if (expectedData != null) {
            StaticContext.dbunit.compareExpectedWithActual(testClass, expectedData.value());
        }
        if (notExpectedData != null) {
            StaticContext.dbunit.compareNotExpectedWithActual(testClass, notExpectedData.value());
        }
    }

    /**
     * Voert de methode-specifieke insert van testdata uit. Deze testdata kan worden opgegeven middels een annotatie.
     *
     * @see DBUnit.InsertBefore
     * @throws SQLException
     * @throws DatabaseUnitException
     */
    public static void insertBefore(final IDatabaseConnection connection) throws DatabaseUnitException, SQLException {
        StaticContext.assertState();
        final Method method = StaticContext.currentTestMethod;

        final InsertBefore insertBeforeData = method.getAnnotation(InsertBefore.class);
        if (insertBeforeData != null) {
            final Class<?> testClass = method.getDeclaringClass();
            LOG.info("Insert data from " + Arrays.asList(insertBeforeData.value()));
            StaticContext.dbunit.insert(connection, testClass, insertBeforeData.value());
        }
    }

    public static class TestExecutionListener extends AbstractTestExecutionListener {
        @Override
        public void prepareTestInstance(final TestContext testContext) throws Exception {
            StaticContext.dbunit = testContext.getApplicationContext().getBean(AbstractDBUnitUtil.class);
        }

        @Override
        public void beforeTestMethod(final TestContext testContext) throws Exception {
            StaticContext.currentTestMethod = testContext.getTestMethod();
        }

        @Override
        public void afterTestMethod(final TestContext testContext) throws Exception {
            StaticContext.currentTestMethod = null;
        }
    }

    /**
     * Nota: Deze constructie via een Static Test Context is nodig omdat Spring het niet toestaat een Spring Bean ook
     * als Listener te registreren, of vice versa. In dat geval zou {@link AbstractDBUnitUtil} zelf kunnen beschikken over de
     * testcontext waarin bepaalde methodes (zoals {@link AbstractDBUnitUtil#compareExpectedWithActual(Class, String...))})
     * worden aangeroepen. Nu is deze constructie nodig om dergelijke methode-aanroepen te verrijken met deze context.
     */
    private static class StaticContext {
        private static Method currentTestMethod;
        private static AbstractDBUnitUtil dbunit;

        private static void assertState() {
            if (dbunit == null || currentTestMethod == null) {
                throw new IllegalStateException("DBUnit-commando aangeroepen zonder initialisatie van testcontext");
            }
        }

        private static void setInMemory() {
            dbunit.setInMemory();
        }
    }
}
