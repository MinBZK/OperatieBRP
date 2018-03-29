/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.telling.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.sql.SQLException;
import org.dbunit.DatabaseUnitException;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

/**
 * Primair toegangspunt voor DBUnit-functies vanuit een test. Is nodig bij gebruik van DBUnit in een Spring-context.
 *
 * De {@link TestExecutionListener} dient in een (super class van) de unit test class geregistreerd te worden als
 * TestExecutionListener, zodat we weten in welke context de methodes {@link #insertBefore()} en {@link #verify()} wordt
 * aangeroepen.
 */
public final class DBUnit {

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

    public static void truncateTables() throws SQLException, DatabaseUnitException {
        StaticContext.assertState();
        StaticContext.dbunit.truncateTables();
    }

    public static void setInMemory() {
        StaticContext.assertState();
        StaticContext.setInMemory();
    }

    /**
     * Verifieert de aannames die middels annotations op de unit test zijn geplaatst.
     * @see DBUnit.ExpectedAfter
     * @see DBUnit.NotExpectedAfter
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
     * @see DBUnit.InsertBefore
     */
    public static void insertBefore() throws DatabaseUnitException, SQLException {
        StaticContext.assertState();
        final Method method = StaticContext.currentTestMethod;

        final InsertBefore insertBeforeData = method.getAnnotation(InsertBefore.class);
        if (insertBeforeData != null) {
            final Class<?> testClass = method.getDeclaringClass();
            StaticContext.dbunit.insert(testClass, insertBeforeData.value());
        }
    }

    public static class TestExecutionListener extends AbstractTestExecutionListener {
        @Override
        public void prepareTestInstance(final TestContext testContext) throws Exception {
            StaticContext.dbunit = testContext.getApplicationContext().getBean(DBUnitUtil.class);
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
     * als Listener te registreren, of vice versa. In dat geval zou {@link DBUnitUtil} zelf kunnen beschikken over de
     * testcontext waarin bepaalde methodes (zoals {@link DBUnitUtil#compareExpectedWithActual(Class, String...))})
     * worden aangeroepen. Nu is deze constructie nodig om dergelijke methode-aanroepen te verrijken met deze context.
     */
    private static class StaticContext {
        private static Method currentTestMethod;
        private static DBUnitUtil dbunit;

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
