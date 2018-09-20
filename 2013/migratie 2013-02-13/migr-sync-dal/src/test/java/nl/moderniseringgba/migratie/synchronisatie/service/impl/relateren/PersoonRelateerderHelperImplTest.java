/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.relateren;

import java.math.BigDecimal;

import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;

import org.junit.Test;

/**
 * De relateerfunctionaliteit is getest met dbunit testcases. Deze testcase is bedoel om precondities van methode
 * aanroepen te testen.
 * 
 * @see nl.moderniseringgba.migratie.synchronisatie.service.impl.ServiceIntegratieTest
 */
public class PersoonRelateerderHelperImplTest {

    private static final PersoonRelateerderHelperImpl RELATEERDER = new PersoonRelateerderHelperImpl(null, null,
            null, null);

    @Test(expected = NullPointerException.class)
    public void testPreConditieGeconverteerdePersoonNull() {
        RELATEERDER.mergeRelatiesVoorHuwelijkOfGp(null, new Persoon());
    }

    @Test(expected = NullPointerException.class)
    public void testPreConditieBestaandPersoonNull() {
        RELATEERDER.mergeRelatiesVoorHuwelijkOfGp(new Persoon(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPreConditieGeconverteerdePersoonAnummerNull() {
        final Persoon persoon1 = new Persoon();
        final Persoon persoon2 = new Persoon();
        persoon2.setAdministratienummer(new BigDecimal("1123"));
        RELATEERDER.mergeRelatiesVoorHuwelijkOfGp(persoon1, persoon2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPreConditieBestaandPersoonAnummerNull() {
        final Persoon persoon1 = new Persoon();
        final Persoon persoon2 = new Persoon();
        persoon2.setAdministratienummer(new BigDecimal("1123"));
        RELATEERDER.mergeRelatiesVoorHuwelijkOfGp(persoon2, persoon1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOngelijkAnummerNull() {
        final Persoon persoon1 = new Persoon();
        final Persoon persoon2 = new Persoon();
        persoon1.setAdministratienummer(new BigDecimal("1123"));
        persoon2.setAdministratienummer(new BigDecimal("11234"));
        RELATEERDER.mergeRelatiesVoorHuwelijkOfGp(persoon1, persoon2);
    }
}
