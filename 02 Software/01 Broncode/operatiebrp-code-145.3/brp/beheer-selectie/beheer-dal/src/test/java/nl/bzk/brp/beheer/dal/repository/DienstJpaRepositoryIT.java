/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.dal.repository;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * IT voor {@link DienstJpaRepository}.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DienstJpaRepositoryIT {

    @Inject
    private DienstJpaRepository dienstJpaRepository;

    @Test
    public void geefAlleSelectieDienstenBinnenPeriode() {
        Collection<Dienst> diensten = dienstJpaRepository.getSelectieDienstenBinnenPeriode(20300101);

        assertThat(diensten.size(), is(3));
    }
}