/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.repository.jpa;

import java.util.Arrays;

import javax.inject.Inject;

import nl.bzk.brp.bevraging.domein.repository.HorizontaleAutorisatieRepository;
import org.junit.Assert;
import org.junit.Test;


/**
 * Testcases voor de {@link HorizontaleAutorisatieRepository} class.
 */
public class HorizontaleAutorisatieRepositoryJPATest extends AbstractRepositoryTestCase {

    @Inject
    private HorizontaleAutorisatieRepository horizontaleAutorisatieRepository;

    @Test
    public void testFilterPersonenMetFilterAltijdWaar() {
        Assert.assertEquals(3,
                horizontaleAutorisatieRepository.filterPersonenBijFilter(Arrays.asList(1L, 2L, 3L), "1 = 1").size());
    }

    @Test
    public void testFilterPersonenMetFilterNooitWaar() {
        Assert.assertEquals(0,
                horizontaleAutorisatieRepository.filterPersonenBijFilter(Arrays.asList(1L, 2L, 3L), "1 = 2").size());
    }

    @Test
    public void testFilterPersonenMetSelectieveFilter() {
        Assert.assertEquals(
                1,
                horizontaleAutorisatieRepository.filterPersonenBijFilter(Arrays.asList(1L, 2L, 3L),
                        "p.burgerservicenummer = 123456789").size());
    }

    @Test
    public void testFilterPersonenMetSelectieveFilters() {
        Assert.assertEquals(
                1,
                horizontaleAutorisatieRepository.filterPersonenBijFilter(Arrays.asList(1L, 2L, 3L),
                        "p.burgerservicenummer = 123456789 OR p.burgerservicenummer = 234567891",
                        "p.burgerservicenummer = 234567891 OR p.burgerservicenummer = 345678912").size());
    }

    @Test
    public void testFilterPersonenMetLegeHoofdFilter() {
        Assert.assertEquals(
                1,
                horizontaleAutorisatieRepository.filterPersonenBijFilter(Arrays.asList(1L, 2L, 3L), " ",
                        "p.burgerservicenummer = 123456789").size());
    }

    @Test
    public void testFilterPersonenMetNullVoorHoofdFilter() {
        Assert.assertEquals(
                1,
                horizontaleAutorisatieRepository.filterPersonenBijFilter(Arrays.asList(1L, 2L, 3L), null,
                        "p.burgerservicenummer = 123456789").size());
    }

    @Test
    public void testFilterPersonenMetLegeSubFilter() {
        Assert.assertEquals(
                1,
                horizontaleAutorisatieRepository.filterPersonenBijFilter(Arrays.asList(1L, 2L, 3L),
                        "p.burgerservicenummer = 123456789", "").size());
    }

    @Test
    public void testFilterPersonenMetNullVoorSubFilter() {
        Assert.assertEquals(
                1,
                horizontaleAutorisatieRepository.filterPersonenBijFilter(Arrays.asList(1L, 2L, 3L),
                        "p.burgerservicenummer = 123456789", null).size());
    }

    @Test
    public void testFilterPersonenZonderFilters() {
        Assert.assertEquals(3,
                horizontaleAutorisatieRepository.filterPersonenBijFilter(Arrays.asList(1L, 2L, 3L), " ", null).size());
        Assert.assertEquals(3, horizontaleAutorisatieRepository.filterPersonenBijFilter(Arrays.asList(1L, 2L, 3L)).size());
    }
}
