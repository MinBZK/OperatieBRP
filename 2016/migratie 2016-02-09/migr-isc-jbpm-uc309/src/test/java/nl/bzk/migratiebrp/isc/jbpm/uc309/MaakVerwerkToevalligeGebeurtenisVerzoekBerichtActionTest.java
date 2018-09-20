/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc309;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.sync.generated.RelatieSluitingGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisVerzoekBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/**
 */
public class MaakVerwerkToevalligeGebeurtenisVerzoekBerichtActionTest {

    private MaakVerwerkToevalligeGebeurtenisVerzoekBerichtAction subject;
    private BerichtenDao berichtenDao;
    private final Tb02Factory tb02Factory = new Tb02Factory();

    @Before
    public void setUp() throws Exception {
        subject = new MaakVerwerkToevalligeGebeurtenisVerzoekBerichtAction();
        berichtenDao = new InMemoryBerichtenDao();
        ReflectionTestUtils.setField(subject, "berichtenDao", berichtenDao);
        ReflectionTestUtils.setField(subject, "verwerkToevalligeGebeurtenisVerzoekBerichtFactory", new VerwerkToevalligeGebeurtenisVerzoekBerichtFactory());
    }

    @Test
    public void testExecuteMetCorrecteRelatieSluiting() throws Exception {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(tb02Factory.maakTb02Bericht(Tb02Factory.Soort.SLUITING)));

        final Map<String, Object> result = subject.execute(parameters);
        assertTrue("Resultaatmap moet sleutel bevatten", result.containsKey("verwerkToevalligeGebeurtenisVerzoekBericht"));
        final Long berichtId = (Long) result.get("verwerkToevalligeGebeurtenisVerzoekBericht");
        final VerwerkToevalligeGebeurtenisVerzoekBericht bericht = (VerwerkToevalligeGebeurtenisVerzoekBericht) berichtenDao.leesBericht(berichtId);
        final RelatieSluitingGroepType sluiting = bericht.getRelatie().getSluiting().getSluiting();
        assertEquals("Sluitingdatum moet zelde zijn als in tb02 bericht", new BigInteger("19990101"), sluiting.getDatum());
        assertEquals("Plaats van sluiting moet overeenkomen", "5555", sluiting.getPlaats());
        assertEquals("Land van sluiting moet overeenkomen", "1", sluiting.getLand());
        assertNull("Bij sluiting geen ontbinding mogelijk", bericht.getRelatie().getOntbinding());
        assertNull("Bij sluiting geen omzetting mogelijk", bericht.getRelatie().getOmzetting());
        assertNotNull("Akte dient aanwezig te zijn", bericht.getAkte());
        assertNotNull("Persoon moet gevuld zijn", bericht.getPersoon());
        assertNotNull("Geldigheid moet gevuld zijn", bericht.getGeldigheid());
    }

    @Test
    public void testExecuteMetCorrecteRelatieOntbinding() throws Exception {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(tb02Factory.maakTb02Bericht(Tb02Factory.Soort.ONTBINDING)));

        final Map<String, Object> result = subject.execute(parameters);
        assertTrue("Resultaatmap moet sleutel bevatten", result.containsKey("verwerkToevalligeGebeurtenisVerzoekBericht"));
        final Long berichtId = (Long) result.get("verwerkToevalligeGebeurtenisVerzoekBericht");
        final VerwerkToevalligeGebeurtenisVerzoekBericht bericht = (VerwerkToevalligeGebeurtenisVerzoekBericht) berichtenDao.leesBericht(berichtId);
        assertNotNull("Ontbinding dient gevuld te zijn", bericht.getRelatie().getOntbinding());
        assertNotNull("Bij ontbinding moet sluiting ook gevuld zijn", bericht.getRelatie().getSluiting());
        assertNotNull("Persoon van de relatie moet gevuld zijn", bericht.getRelatie().getPersoon());
        assertNull("Bij ontbinding geen omzetting mogelijk", bericht.getRelatie().getOmzetting());
        assertNotNull("Akte dient aanwezig te zijn", bericht.getAkte());
        assertNotNull("Persoon moet gevuld zijn", bericht.getPersoon());
        assertNotNull("Geldigheid moet gevuld zijn", bericht.getGeldigheid());
    }
}
