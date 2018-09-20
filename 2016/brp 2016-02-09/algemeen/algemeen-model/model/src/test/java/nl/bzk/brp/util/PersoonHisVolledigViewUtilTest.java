/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Test;

public class PersoonHisVolledigViewUtilTest {

    @Test
    public void testZetGroepenOpAttributen() {
        final PersoonHisVolledigImpl persoon = TestPersoonJohnnyJordaan.maak();
        final PersoonHisVolledigView persoonHisVolledigView = new PersoonHisVolledigView(persoon, null);
        PersoonHisVolledigViewUtil.zetGroepenOpAttributen(persoonHisVolledigView);
    }

    @Test
    public void testCorrigeerEnumWaardenVoorGerelateerden() {
        final PersoonHisVolledigImpl persoon = TestPersoonJohnnyJordaan.maak();
        final PersoonHisVolledigView persoonHisVolledigView = new PersoonHisVolledigView(persoon, null);
        PersoonHisVolledigViewUtil.corrigeerEnumWaardenVoorGerelateerden(persoonHisVolledigView);
    }

    @Test
    public void testPrefixBetrokkenheid() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        assertEquals("GERELATEERDEKIND", prefixBetrokkenheid(SoortRelatie.HUWELIJK, SoortBetrokkenheid.KIND));
        assertEquals("GERELATEERDEOUDER", prefixBetrokkenheid(SoortRelatie.HUWELIJK, SoortBetrokkenheid.OUDER));
        assertEquals("GERELATEERDEERKENNER", prefixBetrokkenheid(SoortRelatie.HUWELIJK, SoortBetrokkenheid.ERKENNER));
        assertEquals("GERELATEERDENAAMGEVER", prefixBetrokkenheid(SoortRelatie.HUWELIJK, SoortBetrokkenheid.NAAMGEVER));
        assertEquals("GERELATEERDEHUWELIJKSPARTNER", prefixBetrokkenheid(SoortRelatie.HUWELIJK, SoortBetrokkenheid.PARTNER));
        assertEquals("GERELATEERDEGEREGISTREERDEPARTNER", prefixBetrokkenheid(SoortRelatie.GEREGISTREERD_PARTNERSCHAP, SoortBetrokkenheid.PARTNER));
        assertEquals("GERELATEERDENAAMSKEUZEPARTNER", prefixBetrokkenheid(SoortRelatie.NAAMSKEUZE_ONGEBOREN_VRUCHT, SoortBetrokkenheid.PARTNER));

        assertEquals(null, prefixBetrokkenheid(SoortRelatie.HUWELIJK, SoortBetrokkenheid.DUMMY));
        assertEquals(null, prefixBetrokkenheid(SoortRelatie.DUMMY, SoortBetrokkenheid.PARTNER));
    }

    private String prefixBetrokkenheid(final SoortRelatie soortRelatie, final SoortBetrokkenheid soortBetrokkenheid)
        throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        final Class klasse = PersoonHisVolledigViewUtil.class;
        final Method method = klasse.getDeclaredMethod("geefPrefixVoorBetrokkenheid", SoortRelatie.class, SoortBetrokkenheid.class);
        method.setAccessible(true);
        return (String) method.invoke(null, soortRelatie, soortBetrokkenheid);
    }
}
