/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.dal.jpa;

import static org.junit.Assert.assertEquals;

import java.util.List;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangBijhoudingsautorisatie;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.algemeenbrp.test.dal.DBUnit.InsertBefore;
import nl.bzk.brp.bijhouding.dal.AbstractRepositoryTest;
import nl.bzk.brp.bijhouding.dal.ToegangBijhoudingsautorisatieRepository;
import org.junit.Test;

/**
 * Testen voor {@link ToegangBijhoudingsautorisatieRepositoryImpl}.
 */
public class ToegangBijhoudingsautorisatieRepositoryImplTest extends AbstractRepositoryTest {

    @Inject
    private DynamischeStamtabelRepository stamtabelRepository;

    @Inject
    private ToegangBijhoudingsautorisatieRepository toegangBijhoudingsautorisatieRepository;

    @Test
    @InsertBefore("ToegangBijhoudingsautorisatie_data.xml")
    public void testFindByGeautoriseerde() {
        final Partij almere = stamtabelRepository.getPartijByCode("003401");
        final Partij amsterdam = stamtabelRepository.getPartijByCode("036301");
        final List<ToegangBijhoudingsautorisatie> autorisaties = toegangBijhoudingsautorisatieRepository.findByGeautoriseerde(almere);
        assertEquals(2, autorisaties.size());
        assertEquals(0, toegangBijhoudingsautorisatieRepository.findByGeautoriseerde(amsterdam).size());
    }
}
