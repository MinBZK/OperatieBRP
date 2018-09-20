/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ANummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import org.junit.Test;

/** Unit test voor de specifieke methodes in de {@link PersoonIdentificatienummersGroepModelTest} class. */
public class PersoonIdentificatienummersGroepModelTest {

    @Test
    public void testEquals() {
        PersoonIdentificatienummersGroepModel identificatienummers =
            bouwNummers("123456789", "0987654321");

        Assert.assertFalse(identificatienummers.equals(null));
        Assert.assertFalse(identificatienummers.equals(new Burgerservicenummer("123456789")));

        Assert.assertFalse(identificatienummers.equals(bouwNummers(null, null)));
        Assert.assertFalse(identificatienummers.equals(bouwNummers("123456789", null)));
        Assert.assertFalse(identificatienummers.equals(bouwNummers(null, "0987654321")));
        Assert.assertFalse(identificatienummers.equals(bouwNummers("234567890", "0987654321")));
        Assert.assertFalse(identificatienummers.equals(bouwNummers("123456789", "9876543210")));
        Assert.assertFalse(identificatienummers.equals(bouwNummers("234567890", "9876543210")));

        Assert.assertTrue(identificatienummers.equals(identificatienummers));
        Assert.assertTrue(identificatienummers.equals(bouwNummers("123456789", "0987654321")));
    }

    @Test
    public void testHashCodeEquality() {
        PersoonIdentificatienummersGroepModel identificatienummers =
            bouwNummers("123456789", "0987654321");

        Assert.assertTrue(identificatienummers.hashCode() != (new Burgerservicenummer("123456789")).hashCode());

        Assert.assertTrue(identificatienummers.hashCode() != bouwNummers(null, null).hashCode());
        Assert.assertTrue(identificatienummers.hashCode() != bouwNummers("123456789", null).hashCode());
        Assert.assertTrue(identificatienummers.hashCode() != bouwNummers(null, "0987654321").hashCode());
        Assert.assertTrue(identificatienummers.hashCode() != bouwNummers("234567890", "0987654321").hashCode());
        Assert.assertTrue(identificatienummers.hashCode() != bouwNummers("123456789", "9876543210").hashCode());
        Assert.assertTrue(identificatienummers.hashCode() != bouwNummers("234567890", "9876543210").hashCode());

        Assert.assertTrue(identificatienummers.hashCode() == identificatienummers.hashCode());
        Assert.assertTrue(identificatienummers.hashCode() == bouwNummers("123456789", "0987654321").hashCode());
    }

    /**
     * Methode die de sortering van een lijst van {@link PersoonIdentificatienummersGroepModel} instanties test,
     * waarmee impliciet de {@link PersoonIdentificatienummersGroepModel#compareTo
     * (PersoonIdentificatienummersGroepModel)} methode wordt getest.
     */
    @Test
    public void testSortering() {
        List<PersoonIdentificatienummersGroepModel> lijst = new ArrayList<PersoonIdentificatienummersGroepModel>();

        lijst.add(bouwNummers("111111111", "2222222222"));
        lijst.add(bouwNummers("111111111", "1111111111"));
        lijst.add(bouwNummers("111111111", "3333333333"));
        lijst.add(bouwNummers("222222222", "2222222222"));
        lijst.add(bouwNummers("222222222", "1111111111"));
        lijst.add(bouwNummers("222222222", "3333333333"));
        lijst.add(bouwNummers("333333333", "2222222222"));
        lijst.add(bouwNummers("333333333", "1111111111"));
        lijst.add(bouwNummers("333333333", "3333333333"));
        lijst.add(bouwNummers("999999999", "9999999999"));
        lijst.add(bouwNummers("999999999", null));
        lijst.add(bouwNummers(null, "9999999999"));
        lijst.add(bouwNummers(null, null));
        lijst.add(null);

        Collections.sort(lijst);

        // Nu controleren op de juiste volgorde na sortering en dus gebruik compare to methode
        Assert.assertEquals(14, lijst.size());

        Assert.assertEquals("111111111", lijst.get(0).getBurgerservicenummer().getWaarde().toString());
        Assert.assertEquals("1111111111", lijst.get(0).getAdministratienummer().getWaarde().toString());
        Assert.assertEquals("111111111", lijst.get(1).getBurgerservicenummer().getWaarde().toString());
        Assert.assertEquals("2222222222", lijst.get(1).getAdministratienummer().getWaarde().toString());
        Assert.assertEquals("111111111", lijst.get(2).getBurgerservicenummer().getWaarde().toString());
        Assert.assertEquals("3333333333", lijst.get(2).getAdministratienummer().getWaarde().toString());
        Assert.assertEquals("222222222", lijst.get(3).getBurgerservicenummer().getWaarde().toString());
        Assert.assertEquals("1111111111", lijst.get(3).getAdministratienummer().getWaarde().toString());

        Assert.assertEquals("999999999", lijst.get(9).getBurgerservicenummer().getWaarde().toString());
        Assert.assertEquals("9999999999", lijst.get(9).getAdministratienummer().getWaarde().toString());
        Assert.assertEquals("999999999", lijst.get(10).getBurgerservicenummer().getWaarde().toString());
        Assert.assertNull(lijst.get(10).getAdministratienummer());
        Assert.assertNull(lijst.get(11).getBurgerservicenummer());
        Assert.assertEquals("9999999999", lijst.get(11).getAdministratienummer().getWaarde().toString());
        Assert.assertNull(lijst.get(12).getBurgerservicenummer());
        Assert.assertNull(lijst.get(12).getAdministratienummer());
        Assert.assertNull(lijst.get(13));
    }

    /**
     * Helper methode die een {@link PersoonIdentificatienummersGroepModel} instantie instantieert met opgegeven
     * bsn en administratie nummer.
     *
     * @param bsn het bsn voor de nieuwe identificatienummers groep.
     * @param aNummer het administratienie nummer voor de nieuwe identificatienummers groep.
     * @return een nieuwe identificatienummers groep met opgegeven waardes.
     */
    private PersoonIdentificatienummersGroepModel bouwNummers(final String bsn, final String aNummer) {
        final Burgerservicenummer bsnObj;
        final ANummer aNummerObj;

        if (bsn == null) {
            bsnObj = null;
        } else {
            bsnObj = new Burgerservicenummer(bsn);
        }

        if (aNummer == null) {
            aNummerObj = null;
        } else {
            aNummerObj = new ANummer(aNummer);
        }
        return new PersoonIdentificatienummersGroepModel(bsnObj, aNummerObj);
    }

}
