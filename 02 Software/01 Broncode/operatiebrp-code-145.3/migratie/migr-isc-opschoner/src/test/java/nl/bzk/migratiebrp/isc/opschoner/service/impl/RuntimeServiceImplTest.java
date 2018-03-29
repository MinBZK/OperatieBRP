/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.opschoner.service.impl;

import nl.bzk.migratiebrp.isc.opschoner.dao.RuntimeDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RuntimeServiceImplTest {

    private static final String RUNTIME = "Opschoner";

    @Mock
    private RuntimeDao lockDao;

    @InjectMocks
    private RuntimeServiceImpl subject;

    @Test
    public void testLockenRuntime() {

        subject.lockRuntime(RUNTIME);

        Mockito.verify(lockDao, Mockito.times(1)).voegRuntimeToe(RUNTIME);
        Mockito.verifyNoMoreInteractions(lockDao);

    }

    @Test
    public void testUnlockenRuntime() {

        subject.unlockRuntime(RUNTIME);

        Mockito.verify(lockDao, Mockito.times(1)).verwijderRuntime(RUNTIME);
        Mockito.verifyNoMoreInteractions(lockDao);

    }

}
