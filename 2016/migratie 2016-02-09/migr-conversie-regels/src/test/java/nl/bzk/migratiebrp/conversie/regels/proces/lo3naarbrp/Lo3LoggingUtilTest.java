/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp;

import java.util.Set;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogRegel;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Lo3LoggingUtil;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Lo3LoggingUtilTest {

    private static final SoortMeldingCode PRE050 = SoortMeldingCode.PRE050;
    private static final SoortMeldingCode PRE056 = SoortMeldingCode.PRE056;
    private static final Lo3Herkomst C04_S00_V00 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_04, 0, 0);
    private static final Lo3Herkomst C02_S00_V00 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_02, 0, 0);

    @Before
    public void setUp() {
        Logging.initContext();
        // 3 logregels met 2 verschillende precondities en 1 bijzondere situatie toevoegen voor de tests
        // Bijzondere situatie

        Logging.log(C04_S00_V00, LogSeverity.ERROR, SoortMeldingCode.BIJZ_CONV_LB001, null);

        // Precondities
        Logging.log(C04_S00_V00, LogSeverity.ERROR, SoortMeldingCode.PRE050, null);

        Logging.log(C02_S00_V00, LogSeverity.ERROR, SoortMeldingCode.PRE056, null);
    }

    @After
    public void tearDown() {
        Logging.destroyContext();
    }

    @Test
    public void testAantalLogRegels() {
        Assert.assertTrue(Logging.getLogging().getRegels().size() == 3);
    }

    @Test
    public void getLogRegels1Preconditie1RegelGevonden() {
        final Set<LogRegel> logRegels = Lo3LoggingUtil.getLogRegels(PRE050);
        Assert.assertFalse(logRegels.isEmpty());

        Assert.assertTrue(logRegels.size() == 1);
        for (final LogRegel regel : logRegels) {
            Assert.assertTrue(regel.getSoortMeldingCode().isPreconditie());
            Assert.assertEquals(PRE050.name(), regel.getSoortMeldingCode().name());
        }
    }

    @Test
    public void getLogRegels1PreconditieGeenRegelGevonden() {
        final Set<LogRegel> logRegels = Lo3LoggingUtil.getLogRegels(SoortMeldingCode.PRE051);
        Assert.assertTrue(logRegels.isEmpty());
    }

    @Test
    public void getLogRegels2Precondities2RegelsGevonden() {
        final Set<LogRegel> logRegels = Lo3LoggingUtil.getLogRegels(PRE050, PRE056);
        Assert.assertFalse(logRegels.isEmpty());

        Assert.assertTrue(logRegels.size() == 2);
        for (final LogRegel regel : logRegels) {
            Assert.assertTrue(regel.getSoortMeldingCode().isPreconditie());
            final String code = regel.getSoortMeldingCode().name();
            Assert.assertTrue(code.equals(PRE050.name()) || code.equals(PRE056.name()));
        }
    }

    @Test
    public void getLogRegels2Precondities1RegelGevonden() {
        final Set<LogRegel> logRegels = Lo3LoggingUtil.getLogRegels(PRE050, SoortMeldingCode.PRE051);
        Assert.assertFalse(logRegels.isEmpty());

        Assert.assertTrue(logRegels.size() == 1);
        for (final LogRegel regel : logRegels) {
            Assert.assertTrue(regel.getSoortMeldingCode().isPreconditie());
            Assert.assertEquals(PRE050.name(), regel.getSoortMeldingCode().name());
        }
    }

    @Test
    public void bevatLogPreconditie() {
        Assert.assertTrue(Lo3LoggingUtil.bevatLogPreconditie(PRE050, C04_S00_V00));
        Assert.assertTrue(Lo3LoggingUtil.bevatLogPreconditie(PRE056, C02_S00_V00));

        Assert.assertFalse(Lo3LoggingUtil.bevatLogPreconditie(PRE050, C02_S00_V00));
        Assert.assertFalse(Lo3LoggingUtil.bevatLogPreconditie(PRE056, C04_S00_V00));
        Assert.assertFalse(Lo3LoggingUtil.bevatLogPreconditie(SoortMeldingCode.PRE051, C04_S00_V00));

    }
}
