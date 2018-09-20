/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.domein.repository.jpa;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import nl.bzk.brp.AbstractRepositoryTestCase;
import nl.bzk.brp.bevraging.domein.repository.PartijRepository;
import nl.bzk.brp.bijhouding.domein.repository.BijhoudingsAutorisatieRepository;
import nl.bzk.brp.domein.autaut.Autorisatiebesluit;
import nl.bzk.brp.domein.autaut.Bijhoudingsautorisatie;
import nl.bzk.brp.domein.autaut.Bijhoudingssituatie;
import nl.bzk.brp.domein.autaut.SoortAutorisatiebesluit;
import nl.bzk.brp.domein.autaut.SoortBijhouding;
import nl.bzk.brp.domein.autaut.Toestand;
import nl.bzk.brp.domein.autaut.Uitgeslotene;
import nl.bzk.brp.domein.kern.Partij;

import org.junit.Test;


public class BijhoudingsAutorisatieRepositoryTest extends AbstractRepositoryTestCase {

    @Inject
    private BijhoudingsAutorisatieRepository bijhoudingsAutorisatieRepository;

    @Inject
    private PartijRepository                 partijRepository;

    @Test
    public void testZoekGeldigeAutorisatiesVoorBijhoudingsActie() {
        final Partij geautoriseerde = partijRepository.findOne(4);
        final Partij autoriseerder = partijRepository.findOne(3);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        final String date = simpleDateFormat.format(cal.getTime());

        List<Bijhoudingsautorisatie> autorisatieList =
            bijhoudingsAutorisatieRepository.zoekBijhoudingsAutorisaties(SoortBijhouding.BIJHOUDEN, geautoriseerde,
                    geautoriseerde.getSoort(), Integer.valueOf(date));

        // Autorisatie lijst moet niet leeg zijn.
        assertTrue(autorisatieList.iterator().hasNext());

        // Volgens testdata 2 matches.
        assertEquals(2, autorisatieList.size());

        Predicate<Bijhoudingsautorisatie> autorisatieKenmerken = new Predicate<Bijhoudingsautorisatie>() {

            @Override
            public boolean apply(@Nullable final Bijhoudingsautorisatie bijhoudingsAutorisatie) {
                final Autorisatiebesluit autorisatieBesluit = bijhoudingsAutorisatie.getBijhoudingsautorisatiebesluit();
                return (bijhoudingsAutorisatie.getGeautoriseerdePartij() != null && bijhoudingsAutorisatie
                        .getGeautoriseerdePartij().getID().equals(geautoriseerde.getID()))
                    || (bijhoudingsAutorisatie.getGeautoriseerdeSoortPartij() != null && geautoriseerde.getSoort() == bijhoudingsAutorisatie
                            .getGeautoriseerdeSoortPartij())

                    && autorisatieBesluit.getAutoriseerder().getID().equals(autoriseerder.getID())
                    && Toestand.DEFINITIEF == autorisatieBesluit.getToestand()
                    && Toestand.DEFINITIEF == bijhoudingsAutorisatie.getToestand()
                    && "A".equals(bijhoudingsAutorisatie.getBijhoudingsautorisatieStatusHis())
                    && "A".equals(autorisatieBesluit.getAutorisatiebesluitStatusHis())
                    && !autorisatieBesluit.getIndicatieIngetrokken()
                    && SoortAutorisatiebesluit.BIJHOUDINGSAUTORISATIE == autorisatieBesluit.getSoort()
                    && SoortBijhouding.BIJHOUDEN == bijhoudingsAutorisatie.getSoortBijhouding();
            }
        };

        assertTrue(Iterables.all(autorisatieList, autorisatieKenmerken));
    }

    @Test
    public void testZoekAutorisatieMetUitgeslotene() {
        final Bijhoudingsautorisatie bijhoudingsAutorisatie = bijhoudingsAutorisatieRepository.findOne(6);
        assertFalse(bijhoudingsAutorisatie.getUitgesloteneen().isEmpty());
        for (Uitgeslotene uitgeslotene : bijhoudingsAutorisatie.getUitgesloteneen()) {
            assertEquals("A", uitgeslotene.getUitgesloteneStatusHis());
        }
    }

    @Test
    public void testZoekAutorisatieMetBijhoudingsSituaties() {
        final Bijhoudingsautorisatie bijhoudingsAutorisatie = bijhoudingsAutorisatieRepository.findOne(6);
        assertFalse(bijhoudingsAutorisatie.getBijhoudingssituatieen().isEmpty());
        for (Bijhoudingssituatie situatie : bijhoudingsAutorisatie.getBijhoudingssituatieen()) {
            assertEquals("A", situatie.getBijhoudingssituatieStatusHis());
        }
    }
}
