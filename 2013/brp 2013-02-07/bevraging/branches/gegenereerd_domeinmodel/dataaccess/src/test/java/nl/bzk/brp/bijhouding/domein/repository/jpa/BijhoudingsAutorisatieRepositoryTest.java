/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.domein.repository.jpa;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;

import nl.bzk.brp.AbstractRepositoryTestCase;
import nl.bzk.brp.bevraging.domein.Partij;
import nl.bzk.brp.bevraging.domein.StatusHistorie;
import nl.bzk.brp.bevraging.domein.aut.AutorisatieBesluit;
import nl.bzk.brp.bevraging.domein.aut.SoortAutorisatieBesluit;
import nl.bzk.brp.bevraging.domein.aut.Toestand;
import nl.bzk.brp.bevraging.domein.repository.PartijRepository;
import nl.bzk.brp.bijhouding.domein.aut.BijhoudingsAutorisatie;
import nl.bzk.brp.bijhouding.domein.aut.BijhoudingsSituatie;
import nl.bzk.brp.bijhouding.domein.aut.SoortBijhouding;
import nl.bzk.brp.bijhouding.domein.aut.Uitgeslotene;
import nl.bzk.brp.bijhouding.domein.repository.BijhoudingsAutorisatieRepository;
import org.junit.Test;


public class BijhoudingsAutorisatieRepositoryTest extends AbstractRepositoryTestCase {

    @Inject
    private BijhoudingsAutorisatieRepository bijhoudingsAutorisatieRepository;

    @Inject
    private PartijRepository                 partijRepository;

    @Test
    public void testZoekGeldigeAutorisatiesVoorBijhoudingsActie() {
        final Partij geautoriseerde = partijRepository.findOne(4L);
        final Partij autoriseerder = partijRepository.findOne(3L);

        List<BijhoudingsAutorisatie> autorisatieList =
            bijhoudingsAutorisatieRepository.zoekBijhoudingsAutorisaties(SoortBijhouding.BIJHOUDEN, geautoriseerde,
                    geautoriseerde.getSoort());

        // Autorisatie lijst moet niet leeg zijn.
        assertTrue(autorisatieList.iterator().hasNext());

        // Volgens testdata 2 matches.
        assertEquals(2, autorisatieList.size());

        Predicate<BijhoudingsAutorisatie> autorisatieKenmerken = new Predicate<BijhoudingsAutorisatie>() {

            @Override
            public boolean apply(@Nullable final BijhoudingsAutorisatie bijhoudingsAutorisatie) {
                final AutorisatieBesluit autorisatieBesluit = bijhoudingsAutorisatie.getAutorisatieBesluit();
                return (bijhoudingsAutorisatie.getGeautoriseerdePartij() != null && bijhoudingsAutorisatie
                        .getGeautoriseerdePartij().getId().equals(geautoriseerde.getId()))
                    || (bijhoudingsAutorisatie.getGeautoriseerdeSoortPartij() != null && geautoriseerde.getSoort() == bijhoudingsAutorisatie
                            .getGeautoriseerdeSoortPartij())

                    && autorisatieBesluit.getAutoriseerder().getId().equals(autoriseerder.getId())
                    && Toestand.DEFINITIEF == autorisatieBesluit.getToestand()
                    && Toestand.DEFINITIEF == bijhoudingsAutorisatie.getToestand()
                    && StatusHistorie.A == bijhoudingsAutorisatie.getStatusHistorie()
                    && StatusHistorie.A == autorisatieBesluit.getAutorisatieBesluitStatusHistorie()
                    && !autorisatieBesluit.isIngetrokken()
                    && SoortAutorisatieBesluit.BIJHOUDINGSAUTORISATIE == autorisatieBesluit.getSoort()
                    && SoortBijhouding.BIJHOUDEN == bijhoudingsAutorisatie.getSoortBijhouding();
            }
        };

        assertTrue(Iterables.all(autorisatieList, autorisatieKenmerken));
    }

    @Test
    public void testZoekAutorisatieMetUitgeslotene() {
        final BijhoudingsAutorisatie bijhoudingsAutorisatie = bijhoudingsAutorisatieRepository.findOne(6L);
        assertFalse(bijhoudingsAutorisatie.getUitgeslotenen().isEmpty());
        assertTrue(Iterables.all(new ArrayList<Uitgeslotene>(bijhoudingsAutorisatie.getUitgeslotenen()),
                new Predicate<Uitgeslotene>() {

                    @Override
                    public boolean apply(@Nullable final Uitgeslotene uitgeslotene) {
                        return StatusHistorie.A == uitgeslotene.getStatusHistorie();
                    }
                }));
    }

    @Test
    public void testZoekAutorisatieMetBijhoudingsSituaties() {
        final BijhoudingsAutorisatie bijhoudingsAutorisatie = bijhoudingsAutorisatieRepository.findOne(6L);
        assertFalse(bijhoudingsAutorisatie.getBijhoudingsSituaties().isEmpty());
        assertTrue(Iterables.all(new ArrayList<BijhoudingsSituatie>(bijhoudingsAutorisatie.getBijhoudingsSituaties()),
                new Predicate<BijhoudingsSituatie>() {

                    @Override
                    public boolean apply(@Nullable final BijhoudingsSituatie bijhoudingsSituatie) {
                        return StatusHistorie.A == bijhoudingsSituatie.getBijhSituatieStatusHis();
                    }
                }));
    }
}
