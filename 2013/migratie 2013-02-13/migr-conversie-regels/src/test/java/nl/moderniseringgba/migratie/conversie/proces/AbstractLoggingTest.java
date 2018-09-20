/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces;

import nl.moderniseringgba.migratie.BijzondereSituaties;
import nl.moderniseringgba.migratie.Precondities;
import nl.moderniseringgba.migratie.conversie.model.logging.LogRegel;
import nl.moderniseringgba.migratie.conversie.model.logging.LogSeverity;
import nl.moderniseringgba.migratie.conversie.proces.logging.Logging;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("beanFactory.xml")
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
            if (regel.getSeverity().compareTo(LogSeverity.INFO) >= 0) {
                actual++;
            }
        }

        Assert.assertEquals("Verwacht aantal logregels (niveau INFO of hoger) niet aangetroffen in de logging.",
                aantal, actual);
    }

    protected void assertAantalWarnings(final int aantal) {
        int actual = 0;

        for (final LogRegel regel : Logging.getLogging().getRegels()) {
            if (regel.getSeverity().compareTo(LogSeverity.WARNING) >= 0) {
                actual++;
            }
        }

        Assert.assertEquals("Verwacht aantal logregels (niveau WARNING of hoger) niet aangetroffen in de logging.",
                aantal, actual);
    }

    protected void assertAantalErrors(final int aantal) {
        int actual = 0;

        for (final LogRegel regel : Logging.getLogging().getRegels()) {
            if (regel.getSeverity().compareTo(LogSeverity.ERROR) >= 0) {
                actual++;
            }
        }

        Assert.assertEquals("Verwacht aantal logregels (niveau ERROR of hoger)  niet aangetroffen in de logging.",
                aantal, actual);
    }

    protected void assertPreconditie(final Precondities preconditie) {
        boolean found = false;

        for (final LogRegel regel : Logging.getLogging().getRegels()) {
            if (regel.getSeverity().compareTo(LogSeverity.ERROR) >= 0) {
                if (preconditie.name().equals(regel.getCode())) {
                    found = true;
                    break;
                }
            }
        }

        if (!found) {
            Assert.fail("Preconditie " + preconditie.name() + " niet aangetroffen in de logging.");
        }
    }

    protected void assertBijzondereSituatie(final BijzondereSituaties bijzondereSituatie) {
        boolean found = false;

        for (final LogRegel regel : Logging.getLogging().getRegels()) {
            if (regel.getSeverity().compareTo(LogSeverity.INFO) >= 0) {
                if (bijzondereSituatie.name().equals(regel.getCode())) {
                    found = true;
                    break;
                }
            }
        }

        if (!found) {
            Assert.fail("Bijzondere situatie " + bijzondereSituatie.name() + " niet aangetroffen in de logging.");
        }
    }

    protected void assertBijzondereSituatieOmschrijving(final String verwachteOmschrijving) {
        final LogRegel regel = Logging.getLogging().getRegels().get(0);
        final String omschrijving = regel.getOmschrijving();
        Assert.assertFalse(omschrijving.contains("%s"));
        Assert.assertTrue(omschrijving.contains(verwachteOmschrijving));
    }
}
