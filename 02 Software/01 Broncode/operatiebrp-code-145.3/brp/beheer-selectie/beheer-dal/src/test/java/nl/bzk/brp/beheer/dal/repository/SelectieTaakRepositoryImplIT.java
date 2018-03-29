/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.dal.repository;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Selectietaak;
import nl.bzk.brp.beheer.service.dal.SelectieTaakRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * IT voor {@link SelectieTaakRepositoryImpl}.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SelectieTaakRepositoryImplIT {

    @Inject
    private SelectieTaakRepository repository;

    @Test
    public void getGeldigeSelectietakenBinnenPeriode() {
        Collection<Selectietaak>
                taken =
                repository.getGeldigeSelectieTakenBinnenPeriode(LocalDate.now().minus(1, ChronoUnit.MONTHS), LocalDate.now().plus(3, ChronoUnit.MONTHS));

        assertThat(taken.size(), is(2));
    }
}