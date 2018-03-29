/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces;

import java.util.Set;
import nl.bzk.migratiebrp.conversie.model.logging.LogRegel;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/conversie-test.xml")
public abstract class AbstractLoggingTest {

    @Before
    public void init() {
        Logging.initContext();
    }

    @After
    public void destroy() {
        Logging.destroyContext();
    }

    protected void assertAantalInfos(final int aantal) {
        int actual = 0;

        for (final LogRegel regel : Logging.getLogging().getRegels()) {
            if (regel.getSeverity().compareTo(LogSeverity.INFO) == 0) {
                actual++;
            }
        }

        Assert.assertEquals("Verwacht aantal logregels (niveau INFO) niet aangetroffen in de logging.", aantal, actual);
    }

    protected void assertAantalWarnings(final int aantal) {
        int actual = 0;

        for (final LogRegel regel : Logging.getLogging().getRegels()) {
            if (regel.getSeverity().compareTo(LogSeverity.WARNING) == 0) {
                actual++;
            }
        }

        Assert.assertEquals("Verwacht aantal logregels (niveau WARNING) niet aangetroffen in de logging.", aantal, actual);
    }

    /**
     * Deze methode zou de assertAantal* methodes moeten vervangen als er geen logging wordt verwacht.
     */
    protected void assertGeenLogRegels() {
        final Set<LogRegel> regels = Logging.getLogging().getRegels();
        final String melding = "Er zijn %s logregels gevonden";
        final int aantalRegels = regels.size();
        Assert.assertTrue(String.format(melding, aantalRegels), regels.isEmpty());
    }

    protected void assertAantalErrors(final int aantal) {
        int actual = 0;

        for (final LogRegel regel : Logging.getLogging().getRegels()) {
            if (regel.getSeverity().compareTo(LogSeverity.ERROR) == 0) {
                actual++;
            }
        }

        Assert.assertEquals("Verwacht aantal logregels (niveau ERROR)  niet aangetroffen in de logging.", aantal, actual);
    }

    protected void assertSoortMeldingCode(final SoortMeldingCode soortMeldingCode, final int aantalVerwachtInLog) {
        int aantalSoortMeldingCodeGevonden = 0;

        for (final LogRegel regel : Logging.getLogging().getRegels()) {
            if (soortMeldingCode.equals(regel.getSoortMeldingCode())) {
                aantalSoortMeldingCodeGevonden++;
            }
        }

        if (aantalSoortMeldingCodeGevonden != aantalVerwachtInLog) {
            final String melding = "Soort melding %s %d keer gevonden in log. Verwacht %d keer.";
            Assert.fail(String.format(melding, soortMeldingCode.name(), aantalSoortMeldingCodeGevonden, aantalVerwachtInLog));
        }
    }
}
