/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc309;

import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.register.Partij;
import nl.bzk.migratiebrp.bericht.model.sync.register.PartijRegister;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import nl.bzk.migratiebrp.register.client.PartijService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MaakVerwerkToevalligeGebeurtenisVerzoekBerichtActionTest {

    @Mock
    private PartijService partijService;
    @Mock
    private PartijRegister partijRegister;

    private BerichtenDao berichtenDao = new InMemoryBerichtenDao();


    private final Tb02Factory tb02Factory = new Tb02Factory();

    @Test
    public void test() throws Exception {
        Mockito.when(partijService.geefRegister()).thenReturn(partijRegister);
        Mockito.when(partijRegister.zoekPartijOpPartijCode("333301")).thenReturn(new Partij("333301", "3333", null, Collections.emptyList()));
        Mockito.when(partijRegister.zoekPartijOpPartijCode("222201")).thenReturn(new Partij("222201", "2222", null, Collections.emptyList()));
        MaakVerwerkToevalligeGebeurtenisVerzoekBerichtAction subject = new MaakVerwerkToevalligeGebeurtenisVerzoekBerichtAction(partijService, berichtenDao);
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(tb02Factory.maakSluitingTb02Bericht()));

        final Map<String, Object> result = subject.execute(parameters);
        assertTrue("Resultaatmap moet sleutel bevatten", result.containsKey("verwerkToevalligeGebeurtenisVerzoekBericht"));
        final Long berichtId = (Long) result.get("verwerkToevalligeGebeurtenisVerzoekBericht");
        final VerwerkToevalligeGebeurtenisVerzoekBericht bericht = (VerwerkToevalligeGebeurtenisVerzoekBericht) berichtenDao.leesBericht(berichtId);
        Assert.assertEquals("Aktenummer dient overeen te komen met waarde uit header tb02", "3QA1234", bericht.getAktenummer());
        Assert.assertEquals("Verzendende gemeente dient overeen te komen met bronPartijCode tb02", "3333", bericht.getVerzendendeGemeente());
        Assert.assertEquals("Ontvangende gemeente dient overeen te komen met doelPartijCode tb02", "2222", bericht.getOntvangendeGemeente());
        Assert.assertNotNull("Inhoud moet gevuld zijn", bericht.getTb02InhoudAlsTeletex());
    }

}
