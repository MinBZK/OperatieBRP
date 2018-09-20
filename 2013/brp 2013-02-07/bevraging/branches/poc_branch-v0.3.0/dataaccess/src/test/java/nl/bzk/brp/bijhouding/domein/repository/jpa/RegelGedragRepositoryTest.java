/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.domein.repository.jpa;

import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.AbstractRepositoryTestCase;
import nl.bzk.brp.bevraging.domein.RedenOpschorting;
import nl.bzk.brp.bevraging.domein.Verantwoordelijke;
import nl.bzk.brp.bevraging.domein.ber.SoortBericht;
import nl.bzk.brp.bijhouding.domein.brm.RegelEffect;
import nl.bzk.brp.bijhouding.domein.brm.RegelGedrag;
import nl.bzk.brp.bijhouding.domein.repository.RegelGedragRepository;
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
        RegelGedrag gedrag = repository.findOne(1L);

        Assert.assertNotNull(gedrag);
        Assert.assertTrue(gedrag.isActief());
        Assert.assertEquals("BRPE0001", gedrag.getRegelImplementatie().getRegel().getCode());
        Assert.assertEquals(RegelEffect.VERBIEDEN_GEEN_OVERRULE, gedrag.getEffect());
        Assert.assertEquals(Verantwoordelijke.MINISTER, gedrag.getVerantwoordelijke());
        Assert.assertEquals(Boolean.TRUE, gedrag.isOpschorting());
        Assert.assertEquals(RedenOpschorting.OVERLEDEN, gedrag.getRedenOpschorting());
    }

    /**
     * Test het ophalen van bedrijfsregel gedrag met behulp van specifiek filter.
     */
    @Test
    public void testZoekActiefBedrijfsregelGedrag() {
        List<RegelGedrag> gedragingen =
            repository.zoekActiefBedrijfsregelGedrag(SoortBericht.OPVRAGEN_PERSOON_VRAAG, Verantwoordelijke.MINISTER,
                    true, RedenOpschorting.OVERLEDEN);

        Assert.assertNotNull(gedragingen);
        Assert.assertTrue(gedragingen.contains(repository.findOne(1L)));
        Assert.assertTrue(gedragingen.contains(repository.findOne(2L)));
        Assert.assertFalse(gedragingen.contains(repository.findOne(3L)));
        Assert.assertFalse(gedragingen.contains(repository.findOne(4L)));
        Assert.assertFalse(gedragingen.contains(repository.findOne(5L)));
        Assert.assertFalse(gedragingen.contains(repository.findOne(6L)));
        Assert.assertTrue(gedragingen.contains(repository.findOne(7L)));
        Assert.assertTrue(gedragingen.contains(repository.findOne(8L)));
        Assert.assertTrue(gedragingen.contains(repository.findOne(9L)));
        Assert.assertTrue(gedragingen.contains(repository.findOne(10L)));
        Assert.assertFalse(gedragingen.contains(repository.findOne(11L)));
        Assert.assertFalse(gedragingen.contains(repository.findOne(12L)));
    }

    /**
     * Test het ophalen van bedrijfsregel gedrag met behulp van algemeen filter.
     */
    @Test
    public void testZoekActiefBedrijfsregelGedragMetOnbekendeRedenOpschorting() {
        List<RegelGedrag> gedragingen =
            repository.zoekActiefBedrijfsregelGedrag(SoortBericht.OPVRAGEN_PERSOON_VRAAG, Verantwoordelijke.MINISTER,
                    true, null);

        Assert.assertNotNull(gedragingen);
        Assert.assertFalse(gedragingen.contains(repository.findOne(1L)));
        Assert.assertFalse(gedragingen.contains(repository.findOne(2L)));
        Assert.assertFalse(gedragingen.contains(repository.findOne(3L)));
        Assert.assertFalse(gedragingen.contains(repository.findOne(4L)));
        Assert.assertFalse(gedragingen.contains(repository.findOne(5L)));
        Assert.assertFalse(gedragingen.contains(repository.findOne(6L)));
        Assert.assertTrue(gedragingen.contains(repository.findOne(7L)));
        Assert.assertFalse(gedragingen.contains(repository.findOne(8L)));
        Assert.assertFalse(gedragingen.contains(repository.findOne(9L)));
        Assert.assertTrue(gedragingen.contains(repository.findOne(10L)));
        Assert.assertFalse(gedragingen.contains(repository.findOne(11L)));
        Assert.assertFalse(gedragingen.contains(repository.findOne(12L)));
    }

}
