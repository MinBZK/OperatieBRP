/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.metamodel.repository;

import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.bmr.metamodel.AbstractRepositoryTest;
import nl.bzk.brp.bmr.metamodel.Attribuut;
import nl.bzk.brp.bmr.metamodel.BedrijfsRegel;
import nl.bzk.brp.bmr.metamodel.Domein;
import nl.bzk.brp.bmr.metamodel.Groep;
import nl.bzk.brp.bmr.metamodel.Laag;
import nl.bzk.brp.bmr.metamodel.ObjectType;
import nl.bzk.brp.bmr.metamodel.Schema;
import nl.bzk.brp.bmr.metamodel.Versie;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public class DomeinRepositoryTest extends AbstractRepositoryTest {

    private static final String AANTAL_OBJECTTYPEN = "select count(*) from element"
                                                       + " where soort = 'OT' and versie = ? and laag = ?";
    private static final String AANTAL_SCHEMAS     = "select count(*) from element where soort = 'S'";
    @Inject
    private DomeinRepository    domeinRepository;

    @Test
    @Ignore("Niet iedereen, ook Jenkins niet,  heeft een Firebird installatie met geldige BMR inhoud")
    public void testFindOne() {
        Domein domein = (Domein) domeinRepository.findByNaam("BRP");
        Assert.assertNotNull(domein);
        List<Schema> schemas = domein.getSchemas();
        int aantalSchemas = simpleJdbcTemplate.queryForInt(AANTAL_SCHEMAS);
        Assert.assertEquals(aantalSchemas, schemas.size());

        Schema kern = domein.getSchema("Kern");
        List<Versie> versies = kern.getVersies();
        Assert.assertEquals(1, versies.size());
        Versie versie = null;
        versie = kern.getWerkVersie();
        Assert.assertNotNull(versie);
        int aantalObjectTypes = simpleJdbcTemplate.queryForInt(AANTAL_OBJECTTYPEN, versie.getId(), Laag.huidigeLaag);
        Assert.assertEquals(aantalObjectTypes, versie.getObjectTypes().size());

        ObjectType persoon = vindInObjectTypes("Persoon", versie.getObjectTypes());
        Assert.assertNotNull(persoon);
        List<Groep> groepen = persoon.getGroepen();
        Assert.assertNotNull(groepen);
        if (Laag.huidigeLaag == Laag.LOGISCH_MODEL) {
            Assert.assertEquals(26, groepen.size());
            for (Groep groep : persoon.getGroepen()) {
                for (Attribuut attribuut : groep.getAttributen()) {
                    Assert.assertSame(groep, attribuut.getGroep());
                }
            }
        }

        List<BedrijfsRegel> regels = persoon.getBedrijfsRegels();
        Assert.assertNotNull(regels);
        if (Laag.huidigeLaag == Laag.LOGISCH_MODEL) {
            Assert.assertEquals(2, regels.size());
        } else {
            Assert.assertEquals(1, regels.size());
        }
        Assert.assertSame(persoon, regels.get(0).getElement());
    }

    private ObjectType vindInObjectTypes(final String string, final Iterable<ObjectType> objectTypes) {
        ObjectType resultaat = null;
        for (ObjectType objectType : objectTypes) {
            if (string.equals(objectType.getNaam())) {
                resultaat = objectType;
            }
        }
        return resultaat;
    }
}
