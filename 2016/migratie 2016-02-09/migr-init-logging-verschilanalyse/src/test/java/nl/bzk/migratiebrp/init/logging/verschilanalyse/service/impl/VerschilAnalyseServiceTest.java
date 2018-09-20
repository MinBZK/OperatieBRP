/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.verschilanalyse.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.parser.Lo3PersoonslijstParser;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.FingerPrint;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingLog;
import nl.bzk.migratiebrp.init.logging.verschilanalyse.service.VerschilAnalyseService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:synchronisatie-logging-beans-test.xml" })
public class VerschilAnalyseServiceTest {

    private static final String DATA_FOLDER_NAME = "data/";

    @Inject
    private VerschilAnalyseService verschilAnalyseService;
    private Resource dataFolder;
    private String[] testcaseNames;

    @Before
    public void setUp() throws IOException {
        Logging.initContext();
        final PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        dataFolder = resolver.getResource(DATA_FOLDER_NAME);
        testcaseNames = dataFolder.getFile().list(new TestFilenameFilter());
    }

    @After
    public void tearDown() {
        Logging.destroyContext();
    }

    @Test
    public void testGeenBrpLo3Bericht() {
        final InitVullingLog log = maakBasisInitVullingLog(2102970797L);
        log.setLo3Bericht("dummy_bericht");
        verschilAnalyseService.bepaalVerschillen(log);
        assertTrue(log.getVerschilAnalyseRegels().isEmpty());
    }

    @Test
    public void testGeenLo3EnBrpLo3Bericht() {
        final InitVullingLog log = maakBasisInitVullingLog(7696126369L);
        verschilAnalyseService.bepaalVerschillen(log);
        assertTrue(log.getVerschilAnalyseRegels().isEmpty());
    }

    @Test
    public void testGeenLo3Bericht() {
        final InitVullingLog log = maakBasisInitVullingLog(7696126369L);
        log.setBerichtNaTerugConversie("dummy_bericht");
        verschilAnalyseService.bepaalVerschillen(log);
        assertTrue(log.getVerschilAnalyseRegels().isEmpty());
    }

    @Test
    public void testFoutInLo3Bericht() {
        final InitVullingLog log = maakBasisInitVullingLog(7696126369L);
        log.setLo3Bericht("dummy_bericht");
        log.setBerichtNaTerugConversie("dummy_bericht");
        verschilAnalyseService.bepaalVerschillen(log);

        assertTrue(log.getVerschilAnalyseRegels().isEmpty());
        assertNotNull(log.getFoutmelding());
    }

    @Test
    @Transactional(value = "loggingTransactionManager", propagation = Propagation.REQUIRED)
    public void testTestCaseBestanden() throws IOException {
        long aNr = 1234567890;
        boolean succes = true;
        for (final String testcaseName : testcaseNames) {
            final StringBuilder sb = new StringBuilder();
            try {
                final TestCaseReader frl = new TestCaseReader(dataFolder, testcaseName);
                sb.append("Testcase: ").append(testcaseName);

                final String brpLo3Xml = frl.getInputBrpLo3File();

                final InitVullingLog log = maakBasisInitVullingLog(aNr);
                log.setLo3Bericht(frl.getInputLo3());
                log.setBerichtNaTerugConversie(brpLo3Xml);
                verschilAnalyseService.bepaalVerschillen(log);

                final String brpLo3Format = log.getBerichtNaTerugConversie();
                assertFalse(brpLo3Xml.equals(brpLo3Format));
                checkLo3Format(brpLo3Format);

                final Set<FingerPrint> fingerprints = log.getFingerPrints();
                final Set<String> expectedFingerprints = frl.getExpectedFingerprints();
                assertNotNull(fingerprints);

                checkFingerprints(fingerprints, expectedFingerprints);

                if (expectedFingerprints.size() > 0) {
                    assertFalse(log.getVerschilAnalyseRegels().isEmpty());
                } else {
                    assertTrue(log.getVerschilAnalyseRegels().isEmpty());
                }

                aNr++;
                sb.append(" - OK");
            } catch (final AssertionError ae) {
                sb.append(" - FAILED. Reason: ").append(ae.getMessage());
                succes = false;
            } catch (final Throwable t /* Catch-all om alle tests te laten uitvoeren */) {
                sb.append(" - ERROR. Reason: ").append(t.getMessage());
                succes = false;
            }
            System.out.println(sb.toString());
        }
        assertTrue("Er zijn test failures", succes);
    }

    private void checkLo3Format(final String lo3Data) {
        try {
            final List<Lo3CategorieWaarde> categorieWaarden = Lo3Inhoud.parseInhoud(lo3Data);
            new Lo3PersoonslijstParser().parse(categorieWaarden);
        } catch (final BerichtSyntaxException e) {
            fail("Fout tijdens controleren LO3 Formaat. Exception: " + e.getMessage());
        }
    }

    private InitVullingLog maakBasisInitVullingLog(final long aNr) {
        final InitVullingLog log = new InitVullingLog();
        final Date now = new Date();
        log.setAnummer(aNr);
        log.setBerichtId(1024);
        log.setBerichtType(1111);
        log.setDatumTijdOpnameGbav(now);
        log.setGemeenteVanInschrijving("1904");
        log.setPlId(112);
        return log;
    }

    private void checkFingerprints(final Set<FingerPrint> actualFingerPrints, final Set<String> expectedFingerPrints) {
        if (expectedFingerPrints.size() != actualFingerPrints.size()) {
            printActualFingerprints(actualFingerPrints);
            printExpectedFingerprints(expectedFingerPrints);
            assertEquals(expectedFingerPrints.size(), actualFingerPrints.size());
        }
        for (final FingerPrint actualFingerPrint : actualFingerPrints) {
            final String fingerPrint = actualFingerPrint.getVoorkomenVerschil();
            assertTrue(fingerPrint + " niet verwacht", expectedFingerPrints.contains(fingerPrint));
        }
    }

    private void printActualFingerprints(final Set<FingerPrint> fingerprints) {
        final StringBuilder sb = new StringBuilder();
        for (final FingerPrint fingerprint : fingerprints) {
            sb.append(fingerprint.getVoorkomenVerschil()).append(",");
        }
        System.out.println("Actual:   " + sb.toString());
    }

    private void printExpectedFingerprints(final Set<String> fingerprints) {
        final StringBuilder sb = new StringBuilder();
        for (final String fingerprint : fingerprints) {
            sb.append(fingerprint).append(",");
        }
        System.out.println("Expected: " + sb.toString());
    }

    private class TestFilenameFilter implements FilenameFilter {

        @Override
        public boolean accept(final File dir, final String name) {
            return name.endsWith(".xml");
        }

    }
}
