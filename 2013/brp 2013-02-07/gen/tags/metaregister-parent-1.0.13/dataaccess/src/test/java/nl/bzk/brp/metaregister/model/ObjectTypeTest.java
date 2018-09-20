/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.metaregister.model;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.metaregister.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.metaregister.dataaccess.ObjectTypeDao;
import org.junit.Assert;
import org.junit.Test;


public class ObjectTypeTest extends AbstractRepositoryTestCase {

    @Inject
    private ObjectTypeDao dao;

    @Test
    public void testGetTuples() {
        List<ObjectType> enumeratieTypes = dao.getEnumeratieTypes();
        Assert.assertFalse(enumeratieTypes.isEmpty());
        ObjectType enumeratieType = getObjectTypeOpNaam(enumeratieTypes, "Soort betrokkenheid");

        // test
        Iterator<Tuple> tuples = enumeratieType.getTuples().iterator();
        Tuple tuple = tuples.next();
        Assert.assertEquals(enumeratieType.getId(), tuple.getElement().getId());

        Assert.assertEquals("Kind", tuple.getNaam());
        Assert.assertEquals("Ouder", tuples.next().getNaam());
        Assert.assertEquals("Partner", tuples.next().getNaam());
        Assert.assertFalse(tuples.hasNext());
    }

    @Test
    public void testGetBedrijfsregels() {
        List<ObjectType> enumeratieTypes = dao.getEnumeratieTypes();
        Assert.assertFalse(enumeratieTypes.isEmpty());
        ObjectType enumeratieType = getObjectTypeOpNaam(enumeratieTypes, "Soort levering");

        // test
        Iterator<Bedrijfsregel> bedrijfsregels = enumeratieType.getBedrijfsregels().iterator();
        Bedrijfsregel bedrijfsregel = bedrijfsregels.next();
        Assert.assertEquals(enumeratieType.getId(), bedrijfsregel.getElementByOuder().getId());

        Assert.assertEquals("BR4818", bedrijfsregel.getNaam());
        Assert.assertEquals("BR4819", bedrijfsregels.next().getNaam());
        Assert.assertFalse(bedrijfsregels.hasNext());
    }

}
