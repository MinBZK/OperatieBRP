/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import nl.bzk.brp.bijhouding.business.dto.bijhouding.BijhoudingResultaat;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.configuratie.BrpBusinessConfiguratie;
import nl.bzk.brp.locking.BrpLocker;
import nl.bzk.brp.locking.BrpLockerExceptie;
import nl.bzk.brp.locking.LockingElement;
import nl.bzk.brp.locking.LockingMode;
import nl.bzk.brp.model.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.utils.junit.OverslaanBijInMemoryDatabase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

@Category(OverslaanBijInMemoryDatabase.class)
public class LockStapTest extends AbstractStapTest {

    @Mock
    private BijhoudingsBericht bericht;

    @Mock
    private BrpBusinessConfiguratie brpBusinessConfiguratie;

    @Mock
    private BrpLocker brpLocker;

    @Mock
    private BijhoudingBerichtContext berichtContext;

    private LockStap lockStap;

    @Before
    public final void initMocksEnService() {
        MockitoAnnotations.initMocks(this);
        lockStap = new LockStap();

        bericht = createBericht();
        berichtContext = mock(BijhoudingBerichtContext.class);

        ReflectionTestUtils.setField(lockStap, "brpLocker", brpLocker);
        ReflectionTestUtils.setField(lockStap, "brpBusinessConfiguratie", brpBusinessConfiguratie);
    }

    @After
    public void cleanup() {
        lockStap.ontgrendel();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public void testVergrendel() throws BrpLockerExceptie {
        int timeout = 10;
        List<Melding> meldingen = new ArrayList<>();
        BijhoudingResultaat resultaat = new BijhoudingResultaat(meldingen);
        ArgumentCaptor<Collection> lockingIds = ArgumentCaptor.forClass(Collection.class);
        ArgumentCaptor<LockingElement> lockingElement = ArgumentCaptor.forClass(LockingElement.class);
        ArgumentCaptor<LockingMode> lockingMode = ArgumentCaptor.forClass(LockingMode.class);
        ArgumentCaptor<Integer> lockingTimeout = ArgumentCaptor.forClass(Integer.class);

        when(brpBusinessConfiguratie.getLockingTimeOutProperty()).thenReturn(timeout);
        lockStap.vergrendel(berichtContext);

        verify(brpLocker).lock(lockingIds.capture(), lockingElement.capture(), lockingMode.capture(), lockingTimeout.capture());

        assertEquals(berichtContext.getLockingIds(), lockingIds.getValue());
        assertEquals(berichtContext.getLockingElement(), lockingElement.getValue());
        assertEquals(berichtContext.getLockingMode(), lockingMode.getValue());
        assertEquals(timeout, lockingTimeout.getValue().intValue());
    }

    @Test
    public void testOntgrendel() {
        lockStap.ontgrendel();
        verify(brpLocker).unLock();
    }

    private BijhoudingsBericht createBericht() {
        return new BijhoudingsBericht(null) {
        };
    }

}
