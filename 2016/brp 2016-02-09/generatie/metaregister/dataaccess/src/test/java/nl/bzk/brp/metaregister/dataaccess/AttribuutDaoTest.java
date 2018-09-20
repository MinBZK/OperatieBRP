/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.metaregister.dataaccess;

import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.ObjectType;
import org.junit.Assert;
import org.junit.Test;


public class AttribuutDaoTest extends AbstractRepositoryTestCase {

    @Inject
    private AttribuutDao attribuutDao;

    @Inject
    private GroepDao groepDao;

    @Inject
    private ObjectTypeDao objectTypeDao;

    @Test
    public void testGetAttributenVanGroep() {
        List<Groep> groepen = groepDao.getAll();
        Assert.assertFalse(groepen.isEmpty());
        Groep groep = getGroepOpNaam(groepen, "Stuurgegevens");

        // test
        Iterator<Attribuut> attributen = attribuutDao.getAttributen(groep).iterator();

        Attribuut attribuut = attributen.next();
        Assert.assertSame(groep, attribuut.getGroep());
        Assert.assertEquals("Zendende partij", attribuut.getNaam());
        Assert.assertEquals("Zendende systeem", attributen.next().getNaam());
        Assert.assertEquals("Ontvangende partij", attributen.next().getNaam());
        Assert.assertEquals("Ontvangende systeem", attributen.next().getNaam());
        Assert.assertEquals("Referentienummer", attributen.next().getNaam());
        Assert.assertEquals("Cross referentienummer", attributen.next().getNaam());
        Assert.assertEquals("Datum/tijd verzending", attributen.next().getNaam());
        Assert.assertEquals("Datum/tijd ontvangst", attributen.next().getNaam());
        Assert.assertFalse(attributen.hasNext());
    }

    @Test
    public void testGetAttributenVanObjectType() {
        List<ObjectType> enumeratieTypes = objectTypeDao.getEnumeratieTypes();
        Assert.assertFalse(enumeratieTypes.isEmpty());
        ObjectType enumeratieType = getObjectTypeOpNaam(enumeratieTypes, "Soort persoon");

        // test
        Iterator<Attribuut> attributen = attribuutDao.getAttributen(enumeratieType).iterator();

        Attribuut attribuut = attributen.next();
        Assert.assertSame(enumeratieType, attribuut.getObjectType());
        Assert.assertEquals("ID", attribuut.getNaam());
        Assert.assertEquals("Code", attributen.next().getNaam());
        Assert.assertEquals("Naam", attributen.next().getNaam());
    }

    @Test
    public void shouldGetInverseAttributenVanObjectType() {
        ObjectType persoonObjectType = objectTypeDao.getBySyncId(3010);
        Assert.assertNotNull(persoonObjectType);

        // test
        List<Attribuut> attributen = attribuutDao.getInverseAttributen(persoonObjectType);

        Assert.assertNotSame(0, attributen.size());
        Attribuut attribuut = attributen.get(0);
        Assert.assertEquals(attribuut.getNaam() + " niet gelijk aan Geverifieerde", "Geverifieerde", attribuut.getNaam());
        Assert.assertNotNull(attribuut.getInverseAssociatieIdentCode());
        Assert.assertEquals(Integer.valueOf(2142), attribuut.getSyncid());
        Assert.assertEquals("Verificaties", attribuut.getInverseAssociatieIdentCode());
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
