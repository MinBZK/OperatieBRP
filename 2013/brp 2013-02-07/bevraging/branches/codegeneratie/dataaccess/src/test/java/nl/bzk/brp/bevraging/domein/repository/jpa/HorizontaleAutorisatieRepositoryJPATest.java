/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.repository.jpa;

import java.util.Arrays;

import javax.inject.Inject;

import nl.bzk.brp.AbstractRepositoryTestCase;
import nl.bzk.brp.bevraging.domein.repository.HorizontaleAutorisatieRepository;
import nl.bzk.brp.toegangsbewaking.parser.ParserException;

import org.junit.Assert;
import org.junit.Test;


/**
 * Testcases voor de {@link HorizontaleAutorisatieRepository} class.
 */
public class HorizontaleAutorisatieRepositoryJPATest extends AbstractRepositoryTestCase {

    @Inject
    private HorizontaleAutorisatieRepository horizontaleAutorisatieRepository;

    @Test
    public void testFilterPersonenMetFilterAltijdWaar() throws ParserException {
        Assert.assertEquals(3,
                horizontaleAutorisatieRepository.filterPersonenBijFilter(Arrays.asList(1L, 2L, 3L), "Leeftijd = Leeftijd").size());
    }

    @Test
    public void testFilterPersonenMetFilterNooitWaar() throws ParserException {
        Assert.assertEquals(0,
                horizontaleAutorisatieRepository.filterPersonenBijFilter(Arrays.asList(1L, 2L, 3L), "0 > 0").size());
    }

    @Test
    public void testFilterPersonenMetSelectieveFilter() throws ParserException {
        Assert.assertEquals(
                1,
                horizontaleAutorisatieRepository.filterPersonenBijFilter(Arrays.asList(1L, 2L, 3L),
                        "Persoon.burgerservicenummer = '123456789'").size());
    }

    @Test
    public void testFilterPersonenMetSelectieveFilters() throws ParserException {
        Assert.assertEquals(
                1,
                horizontaleAutorisatieRepository.filterPersonenBijFilter(Arrays.asList(1L, 2L, 3L),
                        "Persoon.burgerservicenummer = '123456789' of Persoon.burgerservicenummer = '234567891'",
                        "Persoon.burgerservicenummer = '234567891' of Persoon.burgerservicenummer = '345678912'").size());
    }

    @Test
    public void testFilterPersonenMetLegeHoofdFilter() throws ParserException {
        Assert.assertEquals(
                1,
                horizontaleAutorisatieRepository.filterPersonenBijFilter(Arrays.asList(1L, 2L, 3L), " ",
                        "Persoon.burgerservicenummer = '123456789'").size());
    }

    @Test
    public void testFilterPersonenMetNullVoorHoofdFilter() throws ParserException {
        Assert.assertEquals(
                1,
                horizontaleAutorisatieRepository.filterPersonenBijFilter(Arrays.asList(1L, 2L, 3L), null,
                        "Persoon.burgerservicenummer = '123456789'").size());
    }

    @Test
    public void testFilterPersonenMetLegeSubFilter() throws ParserException {
        Assert.assertEquals(
                1,
                horizontaleAutorisatieRepository.filterPersonenBijFilter(Arrays.asList(1L, 2L, 3L),
                        "Persoon.burgerservicenummer = '123456789'", "").size());
    }

    @Test
    public void testFilterPersonenMetNullVoorSubFilter() throws ParserException {
        Assert.assertEquals(
                1,
                horizontaleAutorisatieRepository.filterPersonenBijFilter(Arrays.asList(1L, 2L, 3L),
                        "Persoon.burgerservicenummer = '123456789'", null).size());
    }

    @Test
    public void testFilterPersonenZonderFilters() throws ParserException {
        Assert.assertEquals(3,
                horizontaleAutorisatieRepository.filterPersonenBijFilter(Arrays.asList(1L, 2L, 3L), " ", null).size());
        Assert.assertEquals(3, horizontaleAutorisatieRepository.filterPersonenBijFilter(Arrays.asList(1L, 2L, 3L)).size());
    }
}
