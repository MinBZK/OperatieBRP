/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;
import nl.bzk.brp.BindingUtil;
import nl.bzk.brp.model.gedeeld.SoortBetrokkenheid;
import nl.bzk.brp.model.gedeeld.SoortRelatie;
import nl.bzk.brp.model.logisch.Betrokkenheid;
import nl.bzk.brp.model.logisch.Relatie;
import org.junit.Test;

/**
 * Unit test klasse voor de {@link BindingUtil} klasse.
 */
public class BindingUtilTest {

    @Test
    public void testLijstInstantiatie() {
        List nieuweLijst = BindingUtil.newListInstance();
        Assert.assertNotNull(nieuweLijst);
        Assert.assertTrue(nieuweLijst.isEmpty());
    }

    @Test
    public void testSetInstantiatie() {
        Set nieuweSet = BindingUtil.newSetInstance();
        Assert.assertNotNull(nieuweSet);
        Assert.assertTrue(nieuweSet.isEmpty());
    }

    @Test
    public void testTreeSetInstantiatie() {
        Set nieuweSet = BindingUtil.newTreeSetInstance();
        Assert.assertNotNull(nieuweSet);
        Assert.assertTrue(nieuweSet.isEmpty());
    }

    @Test
    public void testFamilieRechtelijkeBetrekkingInstantiatie() {
        Relatie relatie = BindingUtil.newFamilieRechtelijkeBetrekking();
        Assert.assertNotNull(relatie);
        Assert.assertEquals(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING, relatie.getSoortRelatie());
    }

    @Test
    public void testKindBetrokkenheidInstantiatie() {
        Betrokkenheid betrokkenheid = BindingUtil.newKindBetrokkenheid();
        Assert.assertNotNull(betrokkenheid);
        Assert.assertEquals(SoortBetrokkenheid.KIND, betrokkenheid.getSoortBetrokkenheid());
    }

    @Test
    public void testOuderBetrokkenheidInstantiatie() {
        Betrokkenheid betrokkenheid = BindingUtil.newOuderBetrokkenheid();
        Assert.assertNotNull(betrokkenheid);
        Assert.assertEquals(SoortBetrokkenheid.OUDER, betrokkenheid.getSoortBetrokkenheid());
    }

    @Test
    public void testBooleanNaarJaNee() {
        Assert.assertEquals("N", BindingUtil.booleanNaarJaNee(null));
        Assert.assertEquals("N", BindingUtil.booleanNaarJaNee(false));
        Assert.assertEquals("J", BindingUtil.booleanNaarJaNee(true));
    }

    @Test
    public void testJaNeeNaarBoolean() {
        Assert.assertFalse(BindingUtil.jaNeeNaarBoolean(null));
        Assert.assertFalse(BindingUtil.jaNeeNaarBoolean("N"));
        Assert.assertTrue(BindingUtil.jaNeeNaarBoolean("J"));
    }

    @Test
    public void testDateNaarDatumTijd() throws ParseException {
        Assert.assertEquals("", BindingUtil.dateNaarDatumTijd(null));
        Assert.assertEquals("2012010203040500", BindingUtil.dateNaarDatumTijd(maakDatum("2012/01/02 03:04:05:006")));
        Assert.assertEquals("2012112213243510", BindingUtil.dateNaarDatumTijd(maakDatum("2012/11/22 13:24:35:106")));
    }

    @Test
    public void testDatumTijdNaarDate() throws ParseException {
        Assert.assertNull(BindingUtil.datumTijdNaarDate(null));
        Assert.assertNull(BindingUtil.datumTijdNaarDate(""));
        Assert.assertEquals(0, maakDatum("2012/01/02 03:04:05:000").compareTo(BindingUtil.datumTijdNaarDate("2012010203040500")));
        Assert.assertEquals(0, new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:S").parse("2012/11/22 13:24:35:100").compareTo(BindingUtil.datumTijdNaarDate("2012112213243510")));
    }

    private Date maakDatum(final String datumTijd) throws ParseException {
        return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS").parse((datumTijd));
    }
}
