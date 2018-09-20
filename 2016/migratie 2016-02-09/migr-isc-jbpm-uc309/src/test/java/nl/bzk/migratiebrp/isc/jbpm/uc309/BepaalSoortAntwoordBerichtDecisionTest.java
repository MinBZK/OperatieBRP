/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc309;

import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisAntwoordBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 */
public class BepaalSoortAntwoordBerichtDecisionTest {

    private BepaalSoortAntwoordBerichtDecision subject;
    private BerichtenDao berichtenDao;

    @Before
    public void setUp() throws Exception {
        subject = new BepaalSoortAntwoordBerichtDecision();
        berichtenDao = new InMemoryBerichtenDao();
        ReflectionTestUtils.setField(subject, "berichtenDao", berichtenDao);
    }

    @Test
    public void testOkAntwoord() throws Exception {
        final VerwerkToevalligeGebeurtenisAntwoordBericht bericht = new VerwerkToevalligeGebeurtenisAntwoordBericht();
        bericht.setStatus(StatusType.OK);
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("verwerkToevalligeGebeurtenisAntwoordBericht", berichtenDao.bewaarBericht(bericht));
        assertEquals("Correct antwoord moet een null bericht aanmaken", "4a. maak null bericht", subject.execute(parameters));
    }

    @Test
    public void testNOkAntwoord() throws Exception {
        final VerwerkToevalligeGebeurtenisAntwoordBericht bericht = new VerwerkToevalligeGebeurtenisAntwoordBericht();
        bericht.setStatus(StatusType.FOUT);
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("verwerkToevalligeGebeurtenisAntwoordBericht", berichtenDao.bewaarBericht(bericht));
        assertEquals("Correct antwoord moet een tf21 bericht aanmaken", "4b. maak tf21 bericht", subject.execute(parameters));
    }
}