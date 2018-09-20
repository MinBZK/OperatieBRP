/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc302;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import nl.moderniseringgba.isc.esb.message.brp.impl.VerhuizingVerzoekBericht;
import nl.moderniseringgba.isc.migratie.domein.entities.Gemeente;
import nl.moderniseringgba.isc.migratie.repository.GemeenteRepository;

import org.junit.Before;
import org.junit.Test;

public class ControleerVerhuisberichtDecisionTest {

    private final ControleerVerhuisberichtDecision task = new ControleerVerhuisberichtDecision();

    private GemeenteRepository gemeenteRepositoryMock;

    @Before
    public void setUp() {
        gemeenteRepositoryMock = mock(GemeenteRepository.class);
        task.setGemeenteRepository(gemeenteRepositoryMock);
    }

    @Test
    public void testOudeBijhouderGeenLO3() {
        final VerhuizingVerzoekBericht verhuisBericht = new VerhuizingVerzoekBericht();
        verhuisBericht.setBsn("123456789");
        verhuisBericht.setHuidigeGemeente("1905");
        verhuisBericht.setNieuweGemeente("1904");
        final Map<String, Object> input = new HashMap<String, Object>();
        input.put("input", verhuisBericht);

        final Gemeente oldGemeente = new Gemeente();
        oldGemeente.setDatumBrp(20120101);
        oldGemeente.setGemeenteCode(1904);
        final Gemeente newGemeente = new Gemeente();
        newGemeente.setDatumBrp(20200101);
        newGemeente.setGemeenteCode(1905);

        when(gemeenteRepositoryMock.findGemeente(1904)).thenReturn(oldGemeente);
        when(gemeenteRepositoryMock.findGemeente(1905)).thenReturn(newGemeente);
        final String output = task.execute(input);
        Assert.assertEquals("2a. Bijhouder fout", output);
    }

    @Test
    public void testNieuweBijhouderGeenBRP() {
        final VerhuizingVerzoekBericht verhuisBericht = new VerhuizingVerzoekBericht();
        verhuisBericht.setBsn("123456789");
        verhuisBericht.setHuidigeGemeente("1905");
        verhuisBericht.setNieuweGemeente("1904");
        final Map<String, Object> input = new HashMap<String, Object>();
        input.put("input", verhuisBericht);

        final Gemeente oldGemeente = new Gemeente();
        oldGemeente.setDatumBrp(20120101);
        oldGemeente.setGemeenteCode(1904);
        final Gemeente newGemeente = new Gemeente();
        newGemeente.setDatumBrp(20120101);
        newGemeente.setGemeenteCode(1905);
        when(gemeenteRepositoryMock.findGemeente(1904)).thenReturn(oldGemeente);
        when(gemeenteRepositoryMock.findGemeente(1905)).thenReturn(newGemeente);

        final String output = task.execute(input);
        Assert.assertEquals("2a. Bijhouder fout", output);
    }

    @Test
    public void testZelfdeBijhouder() {
        final VerhuizingVerzoekBericht verhuisBericht = new VerhuizingVerzoekBericht();
        verhuisBericht.setBsn("123456789");
        verhuisBericht.setHuidigeGemeente("1905");
        verhuisBericht.setNieuweGemeente("1904");
        final Map<String, Object> input = new HashMap<String, Object>();
        input.put("input", verhuisBericht);

        final Gemeente oldGemeente = new Gemeente();
        oldGemeente.setDatumBrp(20120101);
        oldGemeente.setGemeenteCode(1904);
        final Gemeente newGemeente = new Gemeente();
        newGemeente.setDatumBrp(20120101);
        newGemeente.setGemeenteCode(1904);
        when(gemeenteRepositoryMock.findGemeente(1904)).thenReturn(oldGemeente);
        when(gemeenteRepositoryMock.findGemeente(1905)).thenReturn(newGemeente);

        final String output = task.execute(input);
        Assert.assertEquals("2a. Bijhouder fout", output);
    }

    @Test
    public void testJuisteBijhouder() {
        final VerhuizingVerzoekBericht verhuisBericht = new VerhuizingVerzoekBericht();
        verhuisBericht.setBsn("123456789");
        verhuisBericht.setHuidigeGemeente("1904");
        verhuisBericht.setNieuweGemeente("1905");
        final Map<String, Object> input = new HashMap<String, Object>();
        input.put("input", verhuisBericht);

        final Gemeente oldGemeente = new Gemeente();
        oldGemeente.setDatumBrp(20200101);
        oldGemeente.setGemeenteCode(1904);
        final Gemeente newGemeente = new Gemeente();
        newGemeente.setDatumBrp(20120101);
        newGemeente.setGemeenteCode(1905);
        when(gemeenteRepositoryMock.findGemeente(1904)).thenReturn(oldGemeente);
        when(gemeenteRepositoryMock.findGemeente(1905)).thenReturn(newGemeente);

        final String output = task.execute(input);
        Assert.assertEquals(null, output);
    }
}
