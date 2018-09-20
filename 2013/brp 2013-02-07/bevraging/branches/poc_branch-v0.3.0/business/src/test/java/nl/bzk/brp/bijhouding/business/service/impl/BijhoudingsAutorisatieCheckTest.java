/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.service.impl;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import nl.bzk.brp.bevraging.domein.Partij;
import nl.bzk.brp.bevraging.domein.SoortPartij;
import nl.bzk.brp.bevraging.domein.Verantwoordelijke;
import nl.bzk.brp.bevraging.domein.repository.PartijRepository;
import nl.bzk.brp.bijhouding.business.bijhoudingsautorisatie.BijhoudingsAutorisatieCheck;
import nl.bzk.brp.bijhouding.domein.aut.BeperkingPopulatie;
import nl.bzk.brp.bijhouding.domein.aut.BijhoudingsAutorisatie;
import nl.bzk.brp.bijhouding.domein.aut.Uitgeslotene;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


public class BijhoudingsAutorisatieCheckTest {

    private Partij partijGemeenteA;
    private Partij partijGemeenteB;
    private Partij partijMinister;
    private Partij partijWetgever;

    @Before
    public void init() {
        partijWetgever = initPartij(SoortPartij.WETGEVER, PartijRepository.ID_PARTIJ_REGERING_EN_STATEN_GENERAAL);
        partijMinister = initPartij(SoortPartij.VERTEGENWOORDIGER_REGERING, PartijRepository.ID_PARTIJ_MINISTER);
        partijGemeenteA = initPartij(SoortPartij.GEMEENTE, 3L);
        partijGemeenteB = initPartij(SoortPartij.GEMEENTE, 4L);
    }

    @Test
    public void testGeenVerantwoordelijkeEnPopulatieBeperking() {
        BijhoudingsAutorisatieCheck check = new BijhoudingsAutorisatieCheck(partijGemeenteA, partijGemeenteB);

        BijhoudingsAutorisatie bijhoudingsAutorisatie = initBijhoudingsAutorisatie(null, null);

        assertTrue(check.apply(bijhoudingsAutorisatie));
    }

    @Test
    public void testVerantwoordelijkeBeperkingCollege() {
        // Populatie eigenaar voor bijhouding MOET een gemeente zijn.
        BijhoudingsAutorisatie bijhoudingsAutorisatie = initBijhoudingsAutorisatie(Verantwoordelijke.COLLEGE, null);
        BijhoudingsAutorisatieCheck check = new BijhoudingsAutorisatieCheck(partijGemeenteA, partijGemeenteB);

        assertTrue(check.apply(bijhoudingsAutorisatie));

        check = new BijhoudingsAutorisatieCheck(partijMinister, partijGemeenteB);

        assertFalse(check.apply(bijhoudingsAutorisatie));

        check = new BijhoudingsAutorisatieCheck(partijWetgever, partijGemeenteB);

        assertFalse(check.apply(bijhoudingsAutorisatie));
    }

    @Test
    public void testVerantwoordelijkeBeperkingMinister() {
        // Populatie eigenaar voor bijhouding MOET de minister zijn.
        BijhoudingsAutorisatie bijhoudingsAutorisatie = initBijhoudingsAutorisatie(Verantwoordelijke.MINISTER, null);
        BijhoudingsAutorisatieCheck check = new BijhoudingsAutorisatieCheck(partijMinister, partijGemeenteB);

        assertTrue(check.apply(bijhoudingsAutorisatie));

        check = new BijhoudingsAutorisatieCheck(partijGemeenteA, partijGemeenteB);

        assertFalse(check.apply(bijhoudingsAutorisatie));

        check = new BijhoudingsAutorisatieCheck(partijWetgever, partijGemeenteB);

        assertFalse(check.apply(bijhoudingsAutorisatie));
    }

    @Test
    public void testPopulatieBeperkingOntvanger() {
        BijhoudingsAutorisatie bijhoudingsAutorisatie = initBijhoudingsAutorisatie(null, BeperkingPopulatie.ONTVANGER);
        BijhoudingsAutorisatieCheck check = new BijhoudingsAutorisatieCheck(partijGemeenteA, partijGemeenteA);

        assertTrue(check.apply(bijhoudingsAutorisatie));

        check = new BijhoudingsAutorisatieCheck(partijGemeenteA, partijGemeenteB);

        assertFalse(check.apply(bijhoudingsAutorisatie));

        check = new BijhoudingsAutorisatieCheck(partijGemeenteA, partijMinister);

        assertFalse(check.apply(bijhoudingsAutorisatie));
    }

    @Test
    public void testPopulatieBeperkingVerstrekker() {
        // Populatie beperking op Verstrekker zal altijd false moeten opleveren omdat deze waarde in de praktijk
        // niet gebruikt wordt.
        BijhoudingsAutorisatie bijhoudingsAutorisatie =
            initBijhoudingsAutorisatie(null, BeperkingPopulatie.VERSTREKKER);
        BijhoudingsAutorisatieCheck check = new BijhoudingsAutorisatieCheck(partijGemeenteA, partijGemeenteA);

        assertFalse(check.apply(bijhoudingsAutorisatie));

        check = new BijhoudingsAutorisatieCheck(partijGemeenteA, partijGemeenteB);

        assertFalse(check.apply(bijhoudingsAutorisatie));

        check = new BijhoudingsAutorisatieCheck(partijGemeenteA, partijMinister);

        assertFalse(check.apply(bijhoudingsAutorisatie));
    }

    @Test
    public void testUitgeslotene() {
        BijhoudingsAutorisatie bijhoudingsAutorisatie =
            initBijhoudingsAutorisatie(null, null, partijGemeenteA, partijGemeenteB);

        BijhoudingsAutorisatieCheck check = new BijhoudingsAutorisatieCheck(partijGemeenteB, partijGemeenteA);

        assertFalse(check.apply(bijhoudingsAutorisatie));

        check = new BijhoudingsAutorisatieCheck(partijGemeenteB, partijGemeenteB);

        assertFalse(check.apply(bijhoudingsAutorisatie));

        check = new BijhoudingsAutorisatieCheck(partijGemeenteB, partijMinister);

        assertTrue(check.apply(bijhoudingsAutorisatie));
    }

    private BijhoudingsAutorisatie initBijhoudingsAutorisatie(final Verantwoordelijke verantwoordelijke,
            final BeperkingPopulatie beperkingPopulatie, final Partij... uitgeslotenPartijen)
    {
        final BijhoudingsAutorisatie bijhoudingsAutorisatie = new BijhoudingsAutorisatie();
        Set<Uitgeslotene> uitgeslotenen = new HashSet<Uitgeslotene>();
        for (Partij partij : uitgeslotenPartijen) {
            Uitgeslotene uitgeslotene = new Uitgeslotene();
            ReflectionTestUtils.setField(uitgeslotene, "bijhoudingsAutorisatie", bijhoudingsAutorisatie);
            ReflectionTestUtils.setField(uitgeslotene, "uitgeslotenPartij", partij);
            uitgeslotenen.add(uitgeslotene);
        }
        ReflectionTestUtils.setField(bijhoudingsAutorisatie, "verantwoordelijke", verantwoordelijke);
        ReflectionTestUtils.setField(bijhoudingsAutorisatie, "beperkingPopulatie", beperkingPopulatie);
        ReflectionTestUtils.setField(bijhoudingsAutorisatie, "uitgeslotenen", uitgeslotenen);
        return bijhoudingsAutorisatie;
    }

    private Partij initPartij(final SoortPartij soort, final Long id) {
        final Partij partij = new Partij(soort);
        ReflectionTestUtils.setField(partij, "id", id);
        return partij;
    }
}
