/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto.bevraging;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;
import nl.bzk.brp.model.objecttype.impl.usr.PersoonMdl;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import org.junit.Test;

/**
 * Unit test voor de {@link OpvragenPersoonResultaat} class.
 */
public class OpvragenPersoonResultaatTest {

    @Test
    public void testGettersEnSetters() {
        OpvragenPersoonResultaat opvragenPersoonResultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());

        opvragenPersoonResultaat.setGevondenPersonen(null);
        Assert.assertNull(opvragenPersoonResultaat.getGevondenPersonen());

        Set<PersoonMdl> gevondenPersonen = new HashSet<PersoonMdl>();
        gevondenPersonen.add(new PersoonMdl());
        opvragenPersoonResultaat.setGevondenPersonen(gevondenPersonen);
        Assert.assertSame(gevondenPersonen, opvragenPersoonResultaat.getGevondenPersonen());
    }

    @Test
    public void testStandaardConstructor() {
        OpvragenPersoonResultaat opvragenPersoonResultaat = new OpvragenPersoonResultaat(null);
        Assert.assertNotNull(opvragenPersoonResultaat.getMeldingen());
        Assert.assertTrue(opvragenPersoonResultaat.getMeldingen().isEmpty());

        opvragenPersoonResultaat =
            new OpvragenPersoonResultaat(Arrays.asList(new Melding(SoortMelding.INFO, MeldingCode.ALG0001)));
        Assert.assertNotNull(opvragenPersoonResultaat.getMeldingen());
        Assert.assertEquals(1, opvragenPersoonResultaat.getMeldingen().size());
    }

    /**
     * Test ook de private constructor om te controleren of dan de meldingen wel correct worden geinitialiseerd naar
     * een lege lijst.
     */
    @Test
    public void testPrivateConstructor()
        throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException
    {
        Constructor<OpvragenPersoonResultaat> constructor = OpvragenPersoonResultaat.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        OpvragenPersoonResultaat opvragenPersoonResultaat = constructor.newInstance();
        Assert.assertNotNull(opvragenPersoonResultaat.getMeldingen());
        Assert.assertTrue(opvragenPersoonResultaat.getMeldingen().isEmpty());
    }
}
