/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.preconditie;

import java.util.List;

import javax.inject.Inject;

import junit.framework.Assert;
import nl.moderniseringgba.migratie.conversie.model.logging.LogRegel;
import nl.moderniseringgba.migratie.conversie.proces.AbstractLoggingTest;
import nl.moderniseringgba.migratie.conversie.proces.logging.Logging;
import nl.moderniseringgba.migratie.conversie.proces.preconditie.lo3.Lo3PersoonslijstPreconditiesTest;

import org.junit.Test;

public class PreconditiesServiceTest extends AbstractLoggingTest {

    @Inject
    private PreconditiesService subject;

    @Test
    public void testVerwerk() throws Exception {
        final Lo3PersoonslijstPreconditiesTest helper = new Lo3PersoonslijstPreconditiesTest();

        try {
            subject.verwerk(helper.builder().persoonStapel(null).build());
            // fail if no exception thrown
            Assert.fail("OngeldigePersoonslijstException verwacht");
        } catch (final OngeldigePersoonslijstException e) {
            final List<LogRegel> logRegels = Logging.getLogging().getRegels();
            Assert.assertEquals("Verwacht 1 logregel", 1, logRegels.size());
            Assert.assertEquals("Verwacht PRE047", "PRE047", logRegels.get(0).getCode());
        }
    }
}
