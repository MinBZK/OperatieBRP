/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.metaregister.model;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import junit.framework.Assert;
import nl.bzk.brp.metaregister.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.metaregister.dataaccess.GroepDao;
import org.junit.Test;


public class GroepTest extends AbstractRepositoryTestCase {

    @Inject
    private GroepDao dao;

    @Test
    public void testGetAttributen() {
        List<Groep> groepen = dao.getAll();
        Assert.assertFalse(groepen.isEmpty());
        Groep groep = getGroepOpNaam(groepen, "Stuurgegevens");

        // test
        Iterator<Attribuut> attributen = groep.getAttributen().iterator();

        Attribuut attribuut = attributen.next();
        Assert.assertSame(groep, attribuut.getGroep());
        Assert.assertEquals("Organisatie", attribuut.getNaam());
        Assert.assertEquals("Applicatie", attributen.next().getNaam());
        Assert.assertEquals("Referentienummer", attributen.next().getNaam());
        Assert.assertEquals("Cross referentienummer", attributen.next().getNaam());
        Assert.assertEquals("Prevalidatie?", attributen.next().getNaam());
        Assert.assertFalse(attributen.hasNext());
    }

    private Groep getGroepOpNaam(final List<Groep> groepen, final String name) {
        Groep resultaat = null;
        for (Groep objectType : groepen) {
            if (name.equals(objectType.getNaam())) {
                resultaat = objectType;
            }
        }
        return resultaat;
    }

}
