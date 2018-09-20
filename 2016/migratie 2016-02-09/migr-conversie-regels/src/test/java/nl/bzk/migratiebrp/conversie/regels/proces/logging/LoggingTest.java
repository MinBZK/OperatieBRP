/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.logging;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.Set;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogRegel;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import org.junit.Assert;
import org.junit.Test;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class LoggingTest {

    @Test
    public void testConstructor() {
        final Logging logging = new Logging(new HashSet<LogRegel>());
        Assert.assertNotNull(logging);
    }

    @Test
    public void testSerialisationDeserialisation() throws Exception {
        final Logging logging = new Logging(new HashSet<LogRegel>());
        final ByteArrayOutputStream boas = new ByteArrayOutputStream();
        final Serializer serializer = new Persister();
        serializer.write(logging, boas);
        Assert.assertTrue(boas.size() > 0);
        final byte[] xml = boas.toByteArray();
        Assert.assertNotNull(xml);

        final File tempXmlFile = File.createTempFile("tmpXml", ".xml");
        try (final FileOutputStream fos = new FileOutputStream(tempXmlFile)) {
            fos.write(xml);
            fos.flush();
        }
        final Logging loggingFromFile = serializer.read(Logging.class, tempXmlFile);
        Assert.assertNotNull(loggingFromFile);
        tempXmlFile.deleteOnExit();
    }

    @Test
    public void testSerialisationDeserialisationMetLogregels() throws Exception {
        final Logging logging = new Logging(new HashSet<LogRegel>());

        logging.addLogRegel(new LogRegel(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, -1, -1), LogSeverity.ERROR, SoortMeldingCode.PRE001, null));
        final ByteArrayOutputStream boas = new ByteArrayOutputStream();
        final Serializer serializer = new Persister();
        serializer.write(logging, boas);
        Assert.assertTrue(boas.size() > 0);
        final byte[] xml = boas.toByteArray();
        Assert.assertNotNull(xml);

        final File tempXmlFile = File.createTempFile("tmpXml", ".xml");
        try (final FileOutputStream fos = new FileOutputStream(tempXmlFile)) {
            fos.write(xml);
            fos.flush();
        }
        final Logging loggingFromFile = serializer.read(Logging.class, tempXmlFile);
        Assert.assertNotNull(loggingFromFile);
        tempXmlFile.deleteOnExit();
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorNull() {
        new Logging(null);
    }

    @Test
    public void testStaticLogData() {
        Logging.initContext(new Logging(new HashSet<LogRegel>()));
        Logging.log(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 1, 1), LogSeverity.WARNING, SoortMeldingCode.PRE001, null);
        final Logging logging = Logging.getLogging();
        Logging.destroyContext();
        Assert.assertTrue(logging.getRegels().size() == 1);
    }

    @Test(expected = IllegalStateException.class)
    public void testStaticLogDataNoInit() {
        Logging.log(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 1, 1), LogSeverity.WARNING, SoortMeldingCode.PRE001, null);
    }

    @Test
    public void testStaticLogRegel() {
        Logging.initContext();
        Logging.log(new LogRegel(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 1, 1), LogSeverity.WARNING, SoortMeldingCode.PRE001, null));
        final Logging logging = Logging.getLogging();
        Logging.destroyContext();
        Assert.assertTrue(logging.getRegels().size() == 1);
    }

    @Test(expected = IllegalStateException.class)
    public void testStaticLogRegelNoInit() {
        Logging.log(new LogRegel(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 1, 1), LogSeverity.WARNING, SoortMeldingCode.PRE001, null));
    }

    public LogRegel maakLogRegel(final LogSeverity severity) {
        return maakLogRegel(severity, 1);
    }

    public LogRegel maakLogRegel(final LogSeverity severity, final int voorkomen) {
        return new LogRegel(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 1, voorkomen), severity, SoortMeldingCode.PRE001, null);
    }

    @Test
    public void testGetMaxSeverity() {
        final Set<LogRegel> regels = new HashSet<>();
        Assert.assertNull(Logging.getMaxSeverity(regels));
        regels.add(maakLogRegel(LogSeverity.WARNING));
        Assert.assertEquals(LogSeverity.WARNING, Logging.getMaxSeverity(regels));
        regels.add(maakLogRegel(LogSeverity.INFO));
        Assert.assertEquals(LogSeverity.WARNING, Logging.getMaxSeverity(regels));
        regels.add(maakLogRegel(LogSeverity.ERROR));
        Assert.assertEquals(LogSeverity.ERROR, Logging.getMaxSeverity(regels));
    }

    @Test
    public void testGetSeverity() {
        final Logging logging = new Logging(new HashSet<LogRegel>());
        logging.addLogRegel(maakLogRegel(LogSeverity.WARNING));
        Assert.assertEquals(LogSeverity.WARNING, logging.getSeverity());
        logging.addLogRegel(maakLogRegel(LogSeverity.INFO));
        Assert.assertEquals(LogSeverity.WARNING, logging.getSeverity());
        logging.addLogRegel(maakLogRegel(LogSeverity.ERROR));
        Assert.assertEquals(LogSeverity.ERROR, logging.getSeverity());
    }

    @Test
    public void testToString() {
        final Logging logging = new Logging(new HashSet<LogRegel>());
        logging.addLogRegel(maakLogRegel(LogSeverity.WARNING));
        logging.addLogRegel(maakLogRegel(LogSeverity.INFO));
        logging.addLogRegel(maakLogRegel(LogSeverity.ERROR));

        Assert.assertTrue(logging.getRegels().size() == 3);

        final String expectedWarningString =
                "LogRegel[herkomst=Lo3Herkomst[categorie=01,stapel=1,voorkomen=1],severity=WARNING," + "soortMeldingCode=PRE001,lo3ElementNummer=<null>]";
        final String expectedInfoString =
                "LogRegel[herkomst=Lo3Herkomst[categorie=01,stapel=1,voorkomen=1],severity=INFO," + "soortMeldingCode=PRE001,lo3ElementNummer=<null>]";
        final String expectedErrorString =
                "LogRegel[herkomst=Lo3Herkomst[categorie=01,stapel=1,voorkomen=1],severity=ERROR," + "soortMeldingCode=PRE001,lo3ElementNummer=<null>]";
        final String actualResult = logging.toString();

        Assert.assertTrue(actualResult.contains(expectedWarningString));
        Assert.assertTrue(actualResult.contains(expectedInfoString));
        Assert.assertTrue(actualResult.contains(expectedErrorString));
    }

    @Test
    public void testMeerdereLogRegelsZelfdeVoorkomen() {
        final Logging logging = new Logging();
        logging.addLogRegel(maakLogRegel(LogSeverity.ERROR));
        logging.addLogRegel(maakLogRegel(LogSeverity.ERROR));

        Assert.assertTrue(logging.getRegels().size() == 1);
        logging.addLogRegel(maakLogRegel(LogSeverity.ERROR, 0));
        Assert.assertTrue(logging.getRegels().size() == 2);
    }
}
