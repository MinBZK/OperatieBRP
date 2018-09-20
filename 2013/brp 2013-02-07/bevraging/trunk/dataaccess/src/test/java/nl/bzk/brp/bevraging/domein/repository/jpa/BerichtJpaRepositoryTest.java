/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.repository.jpa;

import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.AbstractRepositoryTestCase;
import nl.bzk.brp.bevraging.domein.repository.BerichtRepository;
import nl.bzk.brp.domein.DomeinObjectFactory;
import nl.bzk.brp.domein.ber.Bericht;
import nl.bzk.brp.domein.ber.Richting;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class BerichtJpaRepositoryTest extends AbstractRepositoryTestCase {

    @Inject
    private BerichtRepository   repository;

    @Inject
    private DomeinObjectFactory factory;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        Bericht bericht = factory.createBericht();
        bericht.setRichting(Richting.INGAAND);
        bericht.setData("test");
        repository.save(bericht);
    }

    @Test
    public void testFindAll() {
        List<Bericht> berichten = repository.findAll();
        Assert.assertEquals(countRowsInTable("ber.ber"), berichten.size());
    }

    @Test
    public void testSave() {
        Bericht bericht = factory.createBericht();
        bericht.setRichting(Richting.INGAAND);
        bericht.setData("test");
        int eerst = repository.findAll().size();
        repository.save(bericht);
        Assert.assertEquals(eerst + 1, repository.findAll().size());
    }

    @Test
    public void testFindOne() {
        Long id = repository.findAll().get(0).getID();
        Bericht bericht = repository.findOne(id);
        Assert.assertNotNull(bericht);
        Assert.assertEquals(Long.valueOf(id), bericht.getID());
    }

}
