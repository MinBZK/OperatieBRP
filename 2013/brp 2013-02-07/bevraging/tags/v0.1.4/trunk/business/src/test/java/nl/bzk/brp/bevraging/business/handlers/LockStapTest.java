/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.handlers;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;

import nl.bzk.brp.bevraging.business.berichtcmds.BrpBerichtCommand;
import nl.bzk.brp.bevraging.business.dto.BrpBerichtContext;
import nl.bzk.brp.bevraging.business.dto.antwoord.BRPAntwoord;
import nl.bzk.brp.bevraging.business.dto.verzoek.BRPVerzoek;
import nl.bzk.brp.bevraging.business.service.BsnLocker;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


public class LockStapTest {

    @Mock
    private BrpBerichtCommand<BRPVerzoek, BRPAntwoord> berichtMock;

    @Mock
    private BsnLocker                                  bsnLocker;

    private LockStap                                   lockStap;

    private BrpBerichtContext                          context;

    private BRPVerzoek                                 verzoek;

    @Test
    public void testVoerVerwerkingsStapUitVoorBericht() {

        ArgumentCaptor<Long> berichtId = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Collection> readLocks = ArgumentCaptor.forClass(Collection.class);
        ArgumentCaptor<Collection> writeLocks = ArgumentCaptor.forClass(Collection.class);

        lockStap.voerVerwerkingsStapUitVoorBericht(berichtMock);

        Mockito.verify(bsnLocker).getLocks(berichtId.capture(), readLocks.capture(), writeLocks.capture());



        assertEquals(context.getBerichtId(), berichtId.getValue());

        assertEquals(verzoek.getReadBSNLocks(), readLocks.getValue());
        assertEquals(verzoek.getWriteBSNLocks(), writeLocks.getValue());

    }

    @Before
    public final void initMocksEnService() {
        MockitoAnnotations.initMocks(this);

        context = new BrpBerichtContext();

        context.setBerichtId(42L);

        lockStap = new LockStap();
        // BrpBerichtContext context = new BrpBerichtContext();

        verzoek = new BRPVerzoek() {

            @Override
            public Calendar getBeschouwing() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Collection<Long> getReadBSNLocks() {
                return Arrays.asList(1435L, 3242312L, 4523L);
            }

            @Override
            public Collection<Long> getWriteBSNLocks() {
                return Arrays.asList(43524L, 324133L, 1234L);
            }

        };

        // MockBrpBerichtCommand berichtCommand = new MockBrpBerichtCommand(null, null);

        Mockito.when(berichtMock.getContext()).thenReturn(context);

        Mockito.when(berichtMock.getVerzoek()).thenReturn(verzoek);

        ReflectionTestUtils.setField(lockStap, "bsnLocker", bsnLocker);
    }

}
