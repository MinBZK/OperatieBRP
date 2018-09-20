/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie;

import java.util.Set;
import javax.inject.Inject;
import junit.framework.Assert;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigeAutorisatieException;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Autorisatie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3AutorisatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogRegel;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.conversie.regels.proces.AbstractLoggingTest;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import org.junit.Test;

public class PreconditiesServicesAutorisatiesTest extends AbstractLoggingTest {

    @Inject
    private PreconditiesService subject;

    @Test
    public void testHappy() throws OngeldigeAutorisatieException {

        final Lo3AutorisatieInhoud autorisatieInhoud = new Lo3AutorisatieInhoud();
        autorisatieInhoud.setAfnemersindicatie(841891);
        autorisatieInhoud.setAfnemernaam("Belastingdienst");
        autorisatieInhoud.setVerstrekkingsbeperking(1);
        autorisatieInhoud.setDatumIngang(new Lo3Datum(20130101));
        final Lo3Herkomst herkomst = Lo3StapelHelper.lo3Her(35, 1, 1);
        final Lo3Historie historie = Lo3StapelHelper.lo3His(20130101);
        final Lo3Autorisatie input = new Lo3Autorisatie(Lo3StapelHelper.lo3Stapel(new Lo3Categorie<>(autorisatieInhoud, null, historie, herkomst)));

        subject.verwerk(input);
    }

    @Test
    public void testAUT001() {

        final Lo3AutorisatieInhoud autorisatieInhoud = new Lo3AutorisatieInhoud();
        autorisatieInhoud.setAfnemernaam("Belastingdienst");
        autorisatieInhoud.setVerstrekkingsbeperking(1);
        autorisatieInhoud.setDatumIngang(new Lo3Datum(20130101));
        final Lo3Herkomst herkomst = Lo3StapelHelper.lo3Her(35, 1, 1);
        final Lo3Historie historie = Lo3StapelHelper.lo3His(20130101);
        final Lo3Autorisatie input = new Lo3Autorisatie(Lo3StapelHelper.lo3Stapel(new Lo3Categorie<>(autorisatieInhoud, null, historie, herkomst)));

        try {
            subject.verwerk(input);
            // fail if no exception thrown
            Assert.fail("OngeldigeAutorisatieException verwacht");
        } catch (final OngeldigeAutorisatieException e) {
            final Set<LogRegel> logRegels = Logging.getLogging().getRegels();
            Assert.assertEquals("Verwacht 1 logregel", 1, logRegels.size());
            Assert.assertEquals("Verwacht AUT001", "AUT001", logRegels.iterator().next().getSoortMeldingCode().toString());
        }
    }

}
