/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.foutafhandeling;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.notificatie.NotificatieBericht;
import nl.bzk.migratiebrp.bericht.model.notificatie.factory.NotificatieBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.notificatie.impl.VrijBerichtNotificatieBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VrijBerichtVerzoekBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

public class MaakVbActionTest {

    private BerichtenDao berichtenDao = new InMemoryBerichtenDao();
    private MaakVbAction subject = new MaakVbAction(berichtenDao);

    @Test
    public void testVrijbericht() throws IOException {
        final String xml = IOUtils.toString(this.getClass().getResourceAsStream("vrijbericht.xml"), "UTF-8");
        final NotificatieBericht syncBericht = NotificatieBerichtFactory.SINGLETON.getBericht(xml);
        final String
                foutmelding =
                "Het versturen van het onderstaande vrij bericht aan 123401 is niet gelukt: " + ((VrijBerichtNotificatieBericht) syncBericht).getInhoud()
                        .getVrijBericht().getValue().getInhoud().getValue().getValue();
        final Map<String, Object> variabelen = new HashMap<>();
        variabelen.put(FoutafhandelingConstants.BERICHT_LO3, berichtenDao.bewaarBericht(syncBericht));
        variabelen.put(FoutafhandelingConstants.FOUTMELDING, foutmelding);
        variabelen.put(FoutafhandelingConstants.DOEL_PARTIJ_CODE, "123401");
        variabelen.put(FoutafhandelingConstants.BRON_PARTIJ_CODE, "987601");

        final Map<String, Object> result = subject.execute(variabelen);

        Assert.assertEquals(1, result.size());
        final VrijBerichtVerzoekBericht vbBericht =
                (VrijBerichtVerzoekBericht) berichtenDao.leesBericht((Long) result.get(FoutafhandelingConstants.BERICHT_VB));
        Assert.assertNotNull(vbBericht);
        Assert.assertEquals(((VrijBerichtNotificatieBericht) syncBericht).getReferentieNummer(), vbBericht.getReferentienummer());
        Assert.assertEquals(foutmelding, vbBericht.getBericht());
    }

    @Test
    public void testVrijberichtMetTeGrootBericht() throws IOException {
        final String xml = IOUtils.toString(this.getClass().getResourceAsStream("vrijberichtTeGroot.xml"), "UTF-8");
        final NotificatieBericht syncBericht = NotificatieBerichtFactory.SINGLETON.getBericht(xml);
        final String
                foutmelding =
                "Het versturen van het onderstaande vrij bericht aan 123401 is niet gelukt: " + ((VrijBerichtNotificatieBericht) syncBericht).getInhoud()
                        .getVrijBericht().getValue().getInhoud().getValue().getValue();

        final Map<String, Object> variabelen = new HashMap<>();
        variabelen.put(FoutafhandelingConstants.BERICHT_LO3, berichtenDao.bewaarBericht(syncBericht));
        variabelen.put(FoutafhandelingConstants.FOUTMELDING, foutmelding);
        variabelen.put(FoutafhandelingConstants.DOEL_PARTIJ_CODE, "123401");
        variabelen.put(FoutafhandelingConstants.BRON_PARTIJ_CODE, "987601");

        final Map<String, Object> result = subject.execute(variabelen);

        Assert.assertEquals(1, result.size());
        final VrijBerichtVerzoekBericht vbBericht =
                (VrijBerichtVerzoekBericht) berichtenDao.leesBericht((Long) result.get(FoutafhandelingConstants.BERICHT_VB));
        Assert.assertNotNull(vbBericht);
        Assert.assertEquals(((VrijBerichtNotificatieBericht) syncBericht).getReferentieNummer(), vbBericht.getReferentienummer());
        Assert.assertEquals(foutmelding.substring(0, 99_000), vbBericht.getBericht());
    }
}
