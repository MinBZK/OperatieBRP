/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess.levering;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.brp.delivery.dataaccess.AbstractDataAccessTest;
import nl.bzk.brp.service.dalapi.PartijRepository;
import org.junit.Test;

/**
 * Unit test voor {@link PartijRepositoryImpl}.
 */
public class PartijRepositoryImplTest extends AbstractDataAccessTest {

    @Inject
    private PartijRepository repository;

    @Test
    public void geefAllePartijen() throws Exception {
        final List<Partij> partijen = repository.get();

        assertThat(partijen, notNullValue());
        assertThat(partijen.size(), is(greaterThan(0)));
    }

}
