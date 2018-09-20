/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.metaregister.model;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
        Assert.assertEquals("T", tuple.getSoortElement().getCode());

        Assert.assertEquals("Kind", tuple.getNaam());
        Assert.assertEquals("Ouder", tuples.next().getNaam());
        Assert.assertEquals("Partner", tuples.next().getNaam());
        // De rest van de betrokkenheden wordt niet expliciet getest.
    }

    @Test
    public void testGetTuples2() {
        List<ObjectType> enumeratieTypes = dao.getEnumeratieTypes();
        Assert.assertFalse(enumeratieTypes.isEmpty());
        ObjectType enumeratieType = getObjectTypeOpNaam(enumeratieTypes, "Geslachtsaanduiding");

        // test
        Iterator<Tuple> tuples = enumeratieType.getTuples().iterator();
        Tuple tuple = tuples.next();
        Assert.assertEquals("T", tuple.getSoortElement().getCode());

        Assert.assertEquals("Man", tuple.getNaam());
        Assert.assertEquals("Vrouw", tuples.next().getNaam());
        Assert.assertEquals("Onbekend", tuples.next().getNaam());
        Assert.assertFalse(tuples.hasNext());
    }

    @Test
    public void testGetExtraWaarden() {
        List<ObjectType> objectTypes = dao.getStatischeObjectTypes();
        Assert.assertFalse(objectTypes.isEmpty());
        ObjectType objectType = getObjectTypeOpNaam(objectTypes, "Adellijke titel");

        for (Tuple tuple : objectType.getTuples()) {
            if (tuple.getCode().equals("B")) {
                Set<ExtraWaarde> waarden = tuple.getExtraWaarden();
                Assert.assertEquals(2, waarden.size());
                for (ExtraWaarde extraWaarde : tuple.getExtraWaarden()) {
                    if ("Naam_mannelijk".equalsIgnoreCase(extraWaarde.getNaamExtraWaarde().getNaam())) {
                        Assert.assertEquals("baron", extraWaarde.getWaarde());
                    } else if ("Naam_vrouwelijk".equalsIgnoreCase(extraWaarde.getNaamExtraWaarde().getNaam())) {
                        Assert.assertEquals("barones", extraWaarde.getWaarde());
                    }
                }
            }
        }
    }

    @Test
    public void testGetRegels() {
        List<ObjectType> enumeratieTypes = dao.getEnumeratieTypes();
        Assert.assertFalse(enumeratieTypes.isEmpty());
        ObjectType enumeratieType = getObjectTypeOpNaam(enumeratieTypes, "Soort levering");

        // test
        Iterator<Regel> bedrijfsregels = enumeratieType.getRegels().iterator();
        Regel bedrijfsregel = bedrijfsregels.next();
        Assert.assertEquals(enumeratieType.getId(), bedrijfsregel.getElementByOuder().getId());

        Assert.assertEquals("BR4818", bedrijfsregel.getNaam());
        Assert.assertEquals("BR4819", bedrijfsregels.next().getNaam());
        Assert.assertFalse(bedrijfsregels.hasNext());
    }

}
