/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domein.kern.persistent;

import java.util.List;

import nl.bzk.brp.domein.DomeinObjectFactory;
import nl.bzk.brp.domein.PersistentDomeinObjectFactory;
import nl.bzk.brp.domein.kern.Partij;

import org.junit.Assert;
import org.junit.Test;


public class PersistentKernFactoryTest {

    private DomeinObjectFactory kernFactory = new PersistentDomeinObjectFactory();

    @Test
    public void testCreate() {
        Partij partij = kernFactory.create(Partij.class);
        Assert.assertNotNull(partij);
        Assert.assertTrue(partij instanceof PersistentPartij);
    }

    @Test
    public void testGetImplementatie() {
        Class<?> klasse = kernFactory.getImplementatie(Partij.class);
        Assert.assertEquals(klasse, PersistentPartij.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetImplementatieNonDomeinModelInterface() {
        kernFactory.getImplementatie(List.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetImplementatieConcreteKlasse() {
        kernFactory.getImplementatie(PersistentPartij.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateNonDomeinModelInterface() {
        kernFactory.create(List.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateConcreteKlasse() {
        kernFactory.create(PersistentPartij.class);
    }

}
