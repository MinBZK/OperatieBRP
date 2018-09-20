/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.logging;

import java.util.LinkedList;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.logging.LogRegel;
import nl.moderniseringgba.migratie.conversie.model.logging.LogSeverity;
import nl.moderniseringgba.migratie.conversie.model.logging.LogType;

import org.junit.Assert;
import org.junit.Test;

public class LoggingTest {

    @Test
    public void testConstructor() throws Exception {
        final Logging logging = new Logging(new LinkedList<LogRegel>());
        Assert.assertNotNull(logging);
    }

    @Test
    public void testConstructorNull() throws Exception {
        try {
            new Logging(null);
            Assert.fail("constructed Logging with Null logregels parameter");
            // CHECKSTYLE:OFF hier wil je juist exception vangen om de NPE te kunnen testen
        } catch (final Exception e) { // NOSONAR
            // CHECKSTYLE:ON
            Assert.assertTrue(e instanceof NullPointerException);
        }
    }

    @Test
    public void testStaticLogData() throws Exception {
        Logging.initContext(new Logging(new LinkedList<LogRegel>()));
        Logging.log(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 1, 1), LogSeverity.WARNING, LogType.VERWERKING,
                "TEST", "testStaticLogData");
        final Logging logging = Logging.getLogging();
        Logging.destroyContext();
        Assert.assertTrue(logging.getRegels().size() == 1);
    }

    @Test
    public void testStaticLogDataNoInit() throws Exception {
        try {
            Logging.log(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 1, 1), LogSeverity.WARNING,
                    LogType.VERWERKING, "TEST", "testStaticLogDataNoInit");
            Assert.fail("constructed Logging with Null logregels parameter");
            // CHECKSTYLE:OFF hier wil je juist exception vangen om een IllegalArgumentException te kunnen testen
        } catch (final Exception e) { // NOSONAR
            // CHECKSTYLE:ON
            Assert.assertTrue(e instanceof IllegalStateException);
        }
    }

    @Test
    public void testStaticLogRegel() throws Exception {
        Logging.initContext();
        Logging.log(new LogRegel(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 1, 1), LogSeverity.WARNING,
                LogType.VERWERKING, "TEST", "testStaticLogRegel"));
        final Logging logging = Logging.getLogging();
        Logging.destroyContext();
        Assert.assertTrue(logging.getRegels().size() == 1);
    }

    @Test
    public void testStaticLogRegelNoInit() throws Exception {
        try {
            Logging.log(new LogRegel(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 1, 1), LogSeverity.WARNING,
                    LogType.VERWERKING, "TEST", "testStaticLogRegelNoInit"));
            Assert.fail("constructed Logging with Null logregels parameter");
            // CHECKSTYLE:OFF hier wil je juist exception vangen om een IllegalStateException te kunnen testen
        } catch (final Exception e) { // NOSONAR
            // CHECKSTYLE:ON
            Assert.assertTrue(e instanceof IllegalStateException);
        }
    }

    public LogRegel maakLogRegel(final LogSeverity severity) {
        return new LogRegel(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 1, 1), severity, LogType.VERWERKING,
                "TEST", "LoggingTest.maakLogRegel");
    }

    @Test
    public void testGetMaxSeverity() throws Exception {
        final List<LogRegel> regels = new LinkedList<LogRegel>();
        Assert.assertNull(Logging.getMaxSeverity(regels));
        regels.add(maakLogRegel(LogSeverity.WARNING));
        Assert.assertEquals(LogSeverity.WARNING, Logging.getMaxSeverity(regels));
        regels.add(maakLogRegel(LogSeverity.INFO));
        Assert.assertEquals(LogSeverity.WARNING, Logging.getMaxSeverity(regels));
        regels.add(maakLogRegel(LogSeverity.ERROR));
        Assert.assertEquals(LogSeverity.ERROR, Logging.getMaxSeverity(regels));
    }

    @Test
    public void testGetSeverity() throws Exception {
        final Logging logging = new Logging(new LinkedList<LogRegel>());
        logging.addLogRegel(maakLogRegel(LogSeverity.WARNING));
        Assert.assertEquals(LogSeverity.WARNING, logging.getSeverity());
        logging.addLogRegel(maakLogRegel(LogSeverity.INFO));
        Assert.assertEquals(LogSeverity.WARNING, logging.getSeverity());
        logging.addLogRegel(maakLogRegel(LogSeverity.ERROR));
        Assert.assertEquals(LogSeverity.ERROR, logging.getSeverity());
    }

    @Test
    public void testToString() throws Exception {
        final Logging logging = new Logging(new LinkedList<LogRegel>());
        logging.addLogRegel(maakLogRegel(LogSeverity.WARNING));
        logging.addLogRegel(maakLogRegel(LogSeverity.INFO));
        logging.addLogRegel(maakLogRegel(LogSeverity.ERROR));
        Assert.assertEquals("Logging[regels=["
                + "LogRegel[herkomst=Lo3Herkomst[categorie=01,stapel=1,voorkomen=1],severity=WARNING,"
                + "type=VERWERKING,code=TEST,omschrijving=LoggingTest.maakLogRegel], "
                + "LogRegel[herkomst=Lo3Herkomst[categorie=01,stapel=1,voorkomen=1],severity=INFO,"
                + "type=VERWERKING,code=TEST,omschrijving=LoggingTest.maakLogRegel], "
                + "LogRegel[herkomst=Lo3Herkomst[categorie=01,stapel=1,voorkomen=1],severity=ERROR,"
                + "type=VERWERKING,code=TEST,omschrijving=LoggingTest.maakLogRegel]]]", logging.toString());
    }
}
