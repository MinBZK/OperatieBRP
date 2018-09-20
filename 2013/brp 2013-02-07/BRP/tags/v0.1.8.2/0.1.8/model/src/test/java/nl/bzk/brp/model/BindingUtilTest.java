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
import nl.bzk.brp.model.objecttype.logisch.Betrokkenheid;
import nl.bzk.brp.model.objecttype.logisch.Relatie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortRelatie;
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
        Assert.assertEquals(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING, relatie.getSoort());
    }

    @Test
    public void testKindBetrokkenheidInstantiatie() {
        Betrokkenheid betrokkenheid = BindingUtil.newKindBetrokkenheid();
        Assert.assertNotNull(betrokkenheid);
        Assert.assertEquals(SoortBetrokkenheid.KIND, betrokkenheid.getRol());
    }

    @Test
    public void testOuderBetrokkenheidInstantiatie() {
        Betrokkenheid betrokkenheid = BindingUtil.newOuderBetrokkenheid();
        Assert.assertNotNull(betrokkenheid);
        Assert.assertEquals(SoortBetrokkenheid.OUDER, betrokkenheid.getRol());
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

    @Test
    public void testCodeNaarShort() {
        Assert.assertEquals(Short.valueOf((short) 3), BindingUtil.codeNaarShort("0003"));
        Assert.assertEquals(Short.valueOf((short) 23), BindingUtil.codeNaarShort("0023"));
        Assert.assertEquals(Short.valueOf((short) 2345), BindingUtil.codeNaarShort("2345"));
        Assert.assertNull(BindingUtil.codeNaarShort(null));
        Assert.assertNull(BindingUtil.codeNaarShort(""));
        Assert.assertNull(BindingUtil.codeNaarShort(" "));
    }

    @Test
    public void testShortNaarCode() {
        Assert.assertEquals("1234", BindingUtil.shortNaarCode(Short.valueOf((short) 1234)));
        Assert.assertEquals("0034", BindingUtil.shortNaarCode(Short.valueOf((short) 34)));
        Assert.assertEquals("0001", BindingUtil.shortNaarCode(Short.valueOf((short) 1)));
        Assert.assertEquals("0000", BindingUtil.shortNaarCode(Short.valueOf((short) 0)));
        Assert.assertNull(BindingUtil.shortNaarCode(null));
    }
}
