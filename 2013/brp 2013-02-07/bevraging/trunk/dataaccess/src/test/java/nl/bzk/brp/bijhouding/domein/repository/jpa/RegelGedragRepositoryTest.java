/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.domein.repository.jpa;

import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.AbstractRepositoryTestCase;
import nl.bzk.brp.bijhouding.domein.repository.RegelGedragRepository;
import nl.bzk.brp.domein.ber.SoortBericht;
import nl.bzk.brp.domein.brm.Regeleffect;
import nl.bzk.brp.domein.brm.Regelimplementatiesituatie;
import nl.bzk.brp.domein.kern.RedenOpschorting;
import nl.bzk.brp.domein.kern.Verantwoordelijke;

import org.junit.Assert;
import org.junit.Test;


/**
 * Testcases voor de {@link RegelGedragRepository} class.
 */
public class RegelGedragRepositoryTest extends AbstractRepositoryTestCase {

    @Inject
    private RegelGedragRepository repository;

    /**
     * Test het ophalen van gedrag op basis van de id.
     */
    @Test
    public void testZoekRegelGedragOpId() {
        Regelimplementatiesituatie gedrag = repository.findOne(1);

        Assert.assertNotNull(gedrag);
        Assert.assertTrue(gedrag.getIndicatieActief());
        Assert.assertEquals("BRPE0001", gedrag.getRegelimplementatie().getRegel().getCode());
        Assert.assertEquals(Regeleffect.HARD_VERBIEDEN, gedrag.getEffect());
        Assert.assertEquals(Verantwoordelijke.MINISTER, gedrag.getBijhoudingsverantwoordelijkheid());
        Assert.assertEquals(Boolean.TRUE, gedrag.getIndicatieOpgeschort());
        Assert.assertEquals(RedenOpschorting.OVERLIJDEN, gedrag.getRedenOpschorting());
    }

    /**
     * Test het ophalen van bedrijfsregel gedrag met behulp van specifiek filter.
     */
    @Test
    public void testZoekActiefBedrijfsregelGedrag() {
        List<Regelimplementatiesituatie> gedragingen =
            repository.zoekActiefBedrijfsregelGedrag(SoortBericht.OPVRAGENPERSOONVRAAG, Verantwoordelijke.MINISTER,
                    true, RedenOpschorting.OVERLIJDEN);

        Assert.assertNotNull(gedragingen);
        Assert.assertTrue(gedragingen.contains(repository.findOne(1)));
        Assert.assertTrue(gedragingen.contains(repository.findOne(2)));
        Assert.assertFalse(gedragingen.contains(repository.findOne(3)));
        Assert.assertFalse(gedragingen.contains(repository.findOne(4)));
        Assert.assertFalse(gedragingen.contains(repository.findOne(5)));
        Assert.assertFalse(gedragingen.contains(repository.findOne(6)));
        Assert.assertTrue(gedragingen.contains(repository.findOne(7)));
        Assert.assertTrue(gedragingen.contains(repository.findOne(8)));
        Assert.assertTrue(gedragingen.contains(repository.findOne(9)));
        Assert.assertTrue(gedragingen.contains(repository.findOne(10)));
        Assert.assertFalse(gedragingen.contains(repository.findOne(11)));
        Assert.assertFalse(gedragingen.contains(repository.findOne(12)));
    }

    /**
     * Test het ophalen van bedrijfsregel gedrag met behulp van algemeen filter.
     */
    @Test
    public void testZoekActiefBedrijfsregelGedragMetOnbekendeRedenOpschorting() {
        List<Regelimplementatiesituatie> gedragingen =
            repository.zoekActiefBedrijfsregelGedrag(SoortBericht.OPVRAGENPERSOONVRAAG, Verantwoordelijke.MINISTER,
                    true, null);

        Assert.assertNotNull(gedragingen);
        Assert.assertFalse(gedragingen.contains(repository.findOne(1)));
        Assert.assertFalse(gedragingen.contains(repository.findOne(2)));
        Assert.assertFalse(gedragingen.contains(repository.findOne(3)));
        Assert.assertFalse(gedragingen.contains(repository.findOne(4)));
        Assert.assertFalse(gedragingen.contains(repository.findOne(5)));
        Assert.assertFalse(gedragingen.contains(repository.findOne(6)));
        Assert.assertTrue(gedragingen.contains(repository.findOne(7)));
        Assert.assertFalse(gedragingen.contains(repository.findOne(8)));
        Assert.assertFalse(gedragingen.contains(repository.findOne(9)));
        Assert.assertTrue(gedragingen.contains(repository.findOne(10)));
        Assert.assertFalse(gedragingen.contains(repository.findOne(11)));
        Assert.assertFalse(gedragingen.contains(repository.findOne(12)));
    }

}
