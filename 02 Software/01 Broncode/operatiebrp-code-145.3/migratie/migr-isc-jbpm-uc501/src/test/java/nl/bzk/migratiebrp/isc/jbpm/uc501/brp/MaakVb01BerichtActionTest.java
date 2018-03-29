/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc501.brp;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Vb01Bericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.uc501.VrijBerichtConstanten;
import nl.bzk.migratiebrp.isc.jbpm.uc501.gba.MaakVrijBerichtVerzoekBerichtAction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MaakVb01BerichtActionTest {

    private final String ONTVANGENDE_PARTIJ = "4321";
    private final String VERZENDENDE_PARTIJ = "654321";

    private MaakVrijBerichtVerzoekBerichtAction subject;

    @Mock
    private BerichtenDao berichtenDao;

    private Lo3Bericht bericht;


    @Before
    public void setup() {
        bericht = new Vb01Bericht("Vrij bericht");
        bericht.setBronPartijCode(VERZENDENDE_PARTIJ);
        bericht.setDoelPartijCode(ONTVANGENDE_PARTIJ);
        bericht.setMessageId("13424524");
        bericht.setHeader(Lo3HeaderVeld.BERICHT, "Vrij bericht");
        subject = new MaakVrijBerichtVerzoekBerichtAction(berichtenDao);
    }

    @Test
    public void testExecuteOK() {
        Mockito.when(berichtenDao.leesBericht(1L)).thenReturn(bericht);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", 1L);

        final Map<String, Object> resultaat = subject.execute(parameters);
        Assert.assertNotNull("VrijBerichtVerzoekBericht mag niet null zijn", resultaat.get(VrijBerichtConstanten.VRIJ_BERICHT_VERZOEK_BERICHT));
    }

}
