/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.opschoner;

import java.sql.Timestamp;
import nl.bzk.migratiebrp.isc.opschoner.service.OpschonerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OpschonerJMXImplTest {

    @Mock
    private OpschonerService opschonerService;

    @InjectMocks
    private OpschonerJMXImpl subject;

    @Test
    public void test() {
        subject.setAantalUrenSindsVerwerkt(3);

        subject.opschonen();

        Mockito.verify(opschonerService, Mockito.timeout(1000)).opschonenProcessen(Matchers.any(Timestamp.class), Matchers.anyInt());
    }
}
