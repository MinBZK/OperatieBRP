/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.dataaccess;

import static org.junit.Assert.assertThat;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.algemeenbrp.test.dal.DBUnit;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class IstTabelRepositoryTest extends AbstractIntegratieTest {

    @Autowired
    private IstTabelRepository istTabelRepository;

    @DBUnit.InsertBefore({"/data/kern.xml", "/data/autaut.xml", "/data/ist.xml"})
    @Test
    public final void testOphalenIstStapels() {
        final List<Stapel> resultaat = istTabelRepository.leesIstStapels(1L);

        assertThat(resultaat.size(), CoreMatchers.is(2));
    }
}
