/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc301;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.lo3.impl.Ii01Bericht;
import nl.moderniseringgba.isc.migratie.service.GemeenteService;
import nl.moderniseringgba.isc.migratie.service.Stelsel;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ControleerIi01DecisionTest {

    private final ControleerIi01Decision subject = new ControleerIi01Decision();
    private GemeenteService gemeenteServiceMock;

    @Before
    public void setUp() {
        gemeenteServiceMock = Mockito.mock(GemeenteService.class);
        subject.setGemeenteService(gemeenteServiceMock);
    }

    @Test
    public void testOk() {
        final Ii01Bericht ii01Bericht = new Ii01Bericht();
        ii01Bericht.setBronGemeente("1234");
        ii01Bericht.setDoelGemeente("5678");
        ii01Bericht.set(Lo3CategorieEnum.PERSOON, Lo3ElementEnum.BURGERSERVICENUMMER, "2342342432");

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("input", ii01Bericht);

        Mockito.when(gemeenteServiceMock.geefStelselVoorGemeente(1234)).thenReturn(Stelsel.GBA);
        Mockito.when(gemeenteServiceMock.geefStelselVoorGemeente(5678)).thenReturn(Stelsel.BRP);

        Assert.assertEquals(null, subject.execute(parameters));
    }

    @Test
    public void testStelselNok() {
        final Ii01Bericht ii01Bericht = new Ii01Bericht();
        ii01Bericht.setBronGemeente("1234");
        ii01Bericht.setDoelGemeente("5678");
        ii01Bericht.set(Lo3CategorieEnum.PERSOON, Lo3ElementEnum.BURGERSERVICENUMMER, "2342342432");

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("input", ii01Bericht);

        Mockito.when(gemeenteServiceMock.geefStelselVoorGemeente(1234)).thenReturn(Stelsel.BRP);
        Mockito.when(gemeenteServiceMock.geefStelselVoorGemeente(5678)).thenReturn(Stelsel.BRP);

        Assert.assertEquals("2b. fout (nieuwe bijhouder is BRP)", subject.execute(parameters));
    }

    @Test
    public void testCriteriaNok() {
        final Ii01Bericht ii01Bericht = new Ii01Bericht();
        ii01Bericht.setBronGemeente("1234");
        ii01Bericht.setDoelGemeente("5678");

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("input", ii01Bericht);

        Mockito.when(gemeenteServiceMock.geefStelselVoorGemeente(1234)).thenReturn(Stelsel.GBA);
        Mockito.when(gemeenteServiceMock.geefStelselVoorGemeente(5678)).thenReturn(Stelsel.BRP);

        Assert.assertEquals("2c. fout (zoekcriteria voldoen niet aan eisen)", subject.execute(parameters));
    }
}
