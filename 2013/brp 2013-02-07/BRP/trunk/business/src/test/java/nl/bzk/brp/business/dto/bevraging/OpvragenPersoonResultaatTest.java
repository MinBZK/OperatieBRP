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
import java.util.Iterator;
import java.util.Set;

import junit.framework.Assert;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/** Unit test voor de {@link OpvragenPersoonResultaat} class. */
public class OpvragenPersoonResultaatTest {

    @Test
    public void testGettersEnSetters() {
        OpvragenPersoonResultaat opvragenPersoonResultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());

        opvragenPersoonResultaat.setGevondenPersonen(null);
        Assert.assertNull(opvragenPersoonResultaat.getGevondenPersonen());


        PersoonModel persoon1 = new PersoonModel(new PersoonBericht());
        ReflectionTestUtils.setField(persoon1, "iD", 1);
        PersoonModel persoon2 = new PersoonModel(new PersoonBericht());
        ReflectionTestUtils.setField(persoon2, "iD", 2);

        Set<PersoonModel> gevondenPersonen = new HashSet<PersoonModel>();
        gevondenPersonen.add(persoon2);
        gevondenPersonen.add(persoon1);


        opvragenPersoonResultaat.setGevondenPersonen(gevondenPersonen);
        Iterator<PersoonModel> personen = opvragenPersoonResultaat.getGevondenPersonen().iterator();
        Assert.assertEquals(Integer.valueOf(1), personen.next().getID());
        Assert.assertEquals(Integer.valueOf(2), personen.next().getID());
    }

    @Test
    public void testStandaardConstructor() {
        OpvragenPersoonResultaat opvragenPersoonResultaat = new OpvragenPersoonResultaat(null);
        Assert.assertNotNull(opvragenPersoonResultaat.getMeldingen());
        Assert.assertTrue(opvragenPersoonResultaat.getMeldingen().isEmpty());

        opvragenPersoonResultaat =
            new OpvragenPersoonResultaat(Arrays.asList(new Melding(SoortMelding.INFORMATIE, MeldingCode.ALG0001)));
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
