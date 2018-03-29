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
import nl.bzk.brp.beheer.service.dal.StamdataRepository;
import nl.bzk.brp.beheer.service.stamdata.StatischeStamdataDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit test voor {@link StamdataRepositoryImpl}.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StamdataRepositoryImplIT {

    @Inject
    private StamdataRepository stamdataRepository;

    @Test
    public void stamdata() {
        Collection<StatischeStamdataDTO> statischeStamdata = stamdataRepository.getStatischeStamdata("autaut.seltaakstatus");

        assertThat(statischeStamdata.isEmpty(), is(false));
    }
}